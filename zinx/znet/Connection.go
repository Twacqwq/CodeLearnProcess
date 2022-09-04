package znet

import (
	"errors"
	"fmt"
	"io"
	"net"
	"sync"
	"zinx/utils"
	"zinx/ziface"
)

type Connection struct {
	TCPServer ziface.IServer
	Conn *net.TCPConn
	ConnID uint32
	isClosed bool
	ExitChan chan bool
	MsgHandle ziface.IMsgHandler
	//连接Reader和Writer的channel
	RWChan chan []byte
	//连接属性
	property map[string]interface{}
	propertyLock sync.RWMutex
}

func (c *Connection) StartReader() {
	fmt.Println("Reader Goroutine is running...")
	defer fmt.Println("ConnID = ", c.ConnID, "Reader is exit, Remote addr is ", c.Conn.RemoteAddr().String())
	defer c.Stop()
	for {
		//处理消息
		dp := NewDataPack()
		headData := make([]byte, dp.GetHeadLen())
		_, err := io.ReadFull(c.GetTCPConnection(), headData)
		if err != nil {
			fmt.Println("Read headData err", err)
			break
		}
		msg, err := dp.UnPack(headData)
		if err != nil {
			fmt.Println("UnPack error", err)
			break
		}
		var data []byte
		if msg.GetDataLen() > 0 {
			data = make([]byte, msg.GetDataLen())
			_, err := io.ReadFull(c.GetTCPConnection(), data)
			if err != nil {
				fmt.Println("Read Conn Error", err)
				break
			}
			msg.SetData(data)
		}
		//注册路由
		request := Request{
			conn: c,
			msg: msg,
		}
		//工作池和常规模式
		if utils.GlobalObject.WorkerPoolSize > 0 {
			c.MsgHandle.SendMsgToTaskQueue(&request)
		} else {
			go c.MsgHandle.DoMsgHandle(&request)
		}
	}
}

func (c *Connection) StartWriter() {
	fmt.Println("Writer Goroutine is running...")
	defer fmt.Println("ConnID = ", c.ConnID, "Writer is exit, Remote addr is ", c.Conn.RemoteAddr().String())
	//等待Reader发送消息
	for {
		select {
		case data := <-c.RWChan:
			if _, err := c.Conn.Write(data); err != nil {
				fmt.Println("Send Data err", err)
				return
			}
		case <-c.ExitChan:
			//如果ExitChan可读，代表Reader已经close，Writer也要close
			return
		}
	}
}

func (c *Connection) Start() {
	fmt.Println("Conn Start() ... ConnID = ", c.ConnID)
	//启动Reader协程
	go c.StartReader()
	//启动Writer协程
	go c.StartWriter()
	//启动Hook钩子
	c.TCPServer.CallOnConnStart(c)
}

func (c *Connection) Stop() {
	fmt.Println("Conn Stop() ... ConnID = ", c.ConnID)
	if c.isClosed {
		return
	}
	c.isClosed = true
	c.ExitChan <- true //发送退出信号
	c.TCPServer.CallOnConnStop(c) //hook
	c.Conn.Close()
	c.TCPServer.GetConnMgr().Remove(c)
	close(c.ExitChan)
	close(c.RWChan)
}

func (c *Connection) GetTCPConnection() *net.TCPConn {
	return c.Conn
}

func (c *Connection) GetConnID() uint32 {
	return c.ConnID
}

func (c *Connection) RemoteAddr() net.Addr {
	return c.Conn.RemoteAddr()
}

func (c *Connection) SetProperty(key string, value interface{}) {
	c.propertyLock.Lock()
	defer c.propertyLock.Unlock()
	c.property[key] = value
}

func (c *Connection) GetProperty(key string) (interface{}, error) {
	c.propertyLock.RLock()
	defer c.propertyLock.RUnlock()
	if value, ok := c.property[key]; ok {
		return value, nil
	}
	return nil, errors.New("no property found")
}

func (c *Connection) RemoveProperty(key string) {
	c.propertyLock.Lock()
	defer c.propertyLock.Unlock()
	delete(c.property, key)
}

func (c *Connection) SendMsg(msgId uint32, data []byte) error {
	if c.isClosed {
		return errors.New("Connection Closed")
	}
	dp := NewDataPack()
	sendData, err := dp.Pack(NewMessage(msgId, data))
	if err != nil {
		fmt.Println("Pack Error ID =", msgId)
		return errors.New("Pack Error" + err.Error())
	}
	c.RWChan <- sendData
	return nil
}

func NewConnection(server ziface.IServer, conn *net.TCPConn, connID uint32, msgHandle ziface.IMsgHandler) *Connection {
	c := &Connection {
		TCPServer: server,
		Conn: conn,
		ConnID: connID,
		isClosed: false,
		MsgHandle: msgHandle,
		RWChan: make(chan []byte),
		ExitChan: make(chan bool, 1),
		property: make(map[string]interface{}),
	}
	//将连接添加到连接管理器
	c.TCPServer.GetConnMgr().Add(c)
	return c
}