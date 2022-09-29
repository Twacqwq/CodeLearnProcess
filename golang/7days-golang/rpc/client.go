package rpc

import (
	"errors"
	"geerpc/codec"
	"io"
	"sync"
)

// 一个具有生命周期的RPC
type Call struct {
	Seq           uint64
	ServiceMethod string
	Args          interface{}
	Reply         interface{}
	Error         error
	Done          chan *Call
}

func (c *Call) done() {
	c.Done <- c
}

// Client 代表一个 RPC 客户端。
// 可能有多个未完成的 Calls 相关联
// 使用单个客户端，并且客户端可以由
// 同时有多个 goroutine
type Client struct {
	cc       codec.Codec
	opt      *Option
	pending  map[uint64]*Call // 就绪态RPC
	sending  *sync.Mutex      // 流锁
	mu       *sync.Mutex      // 客户端写锁
	header   *codec.Header
	seq      uint64
	closing  bool //是否主动退出
	shutdown bool //是否中断
}

var _ io.Closer = (*Client)(nil)

var ErrShutDown = errors.New("connection is shut down")

func (client *Client) Close() error {
	client.mu.Lock()
	defer client.mu.Unlock()
	if client.closing {
		return ErrShutDown
	}
	client.closing = true
	return client.cc.Close()
}

// 判断客户端是否存活
func (client *Client) IsAvaliable() bool {
	client.mu.Lock()
	defer client.mu.Unlock()
	return !client.closing && !client.shutdown
}
