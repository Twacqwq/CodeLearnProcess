package cache

import (
	"fmt"
	"log"
	"sync"
)

type Getter interface {
	Get(key string) ([]byte, error)
}

// implements Getter
type GetterFunc func(key string) ([]byte, error)

func (f GetterFunc) Get(key string) ([]byte, error) {
	return f(key)
}

// 缓存的命名空间
type Group struct {
	name      string // 命名
	getter    Getter // 缓存未命中时获取源数据的回调
	mainCache cache  // 并发缓存
}

var (
	mu     sync.RWMutex // 读写锁
	groups = make(map[string]*Group)
)

func NewGroup(name string, cacheBytes int64, getter Getter) *Group {
	if getter == nil {
		panic("nil Getter")
	}
	mu.Lock()
	defer mu.Unlock()
	g := &Group{
		name:      name,
		getter:    getter,
		mainCache: cache{cacheBytes: cacheBytes},
	}
	groups[name] = g
	return g
}

func GetGroup(name string) *Group {
	mu.RLock()
	defer mu.RUnlock()
	g := groups[name]
	return g
}

func (g *Group) Get(key string) (ByteView, error) {
	if key == "" {
		return ByteView{}, fmt.Errorf("key is required")
	}
	// 如果mainCache已缓存
	if v, ok := g.mainCache.get(key); ok {
		log.Println("[GeeCache] hit")
		return v, nil
	}

	return g.load(key)
}

func (g *Group) load(key string) (value ByteView, err error) {
	return g.getLocally(key)
}

// 调用回调函数获取缓存
func (g *Group) getLocally(key string) (ByteView, error) {
	bytes, err := g.getter.Get(key)
	if err != nil {
		return ByteView{}, err
	}
	value := ByteView{b: cloneBytes(bytes)}
	g.populateCache(key, value)
	return value, nil
}

// 向mainCache填充缓存
func (g *Group) populateCache(key string, value ByteView) {
	g.mainCache.add(key, value)
}
