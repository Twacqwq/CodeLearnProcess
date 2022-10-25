package cache

import "sync"

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
