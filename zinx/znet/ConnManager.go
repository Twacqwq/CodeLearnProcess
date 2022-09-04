package znet

import (
	"errors"
	"fmt"
	"sync"
	"zinx/ziface"
)

type ConnManager struct {
	connections map[uint32]ziface.IConnection
	connLock	sync.RWMutex
}

func (connMgr *ConnManager) Add(conn ziface.IConnection) {
	connMgr.connLock.Lock()
	defer connMgr.connLock.Unlock()
	connMgr.connections[conn.GetConnID()] = conn
	fmt.Println("ConnID = ", conn.GetConnID(), "add to ConnManager success! conn num = ", connMgr.Len())
}

func (connMgr *ConnManager) Remove(conn ziface.IConnection) {
	connMgr.connLock.Lock()
	defer connMgr.connLock.Unlock()
	delete(connMgr.connections, conn.GetConnID())
	fmt.Println("ConnID = ", conn.GetConnID(), "remove from ConnManager success! conn num = ", connMgr.Len())
}

func (connMgr *ConnManager) Get(connId uint32) (ziface.IConnection, error) {
	connMgr.connLock.RLock()
	defer connMgr.connLock.RUnlock()
	if conn, ok := connMgr.connections[connId]; ok {
		return conn, nil
	}
	return nil, errors.New("conn is nil")
}

func (connMgr *ConnManager) Len() int {
	return len(connMgr.connections)
}

func (connMgr *ConnManager) ClearConn() {
	connMgr.connLock.Lock()
	defer connMgr.connLock.Unlock()
	for connId, conn := range connMgr.connections {
		conn.Stop()
		delete(connMgr.connections, connId)
	}
	fmt.Println("Clear All Conn Success! Conn Nums = ", connMgr.Len())
}

func NewConnManager() *ConnManager {
	return &ConnManager{
		connections: make(map[uint32]ziface.IConnection),
	}
}