package ziface

import "net"

type IConnection interface {
	//开启连接
	Start()
	//关闭连接
	Stop()
	//获取当前连接的绑定socket conn
	GetTCPConnection() *net.TCPConn
	//获取当前连接模块的ID
	GetConnID() uint32
	//获取客户端TCP状态 IP Port
	RemoteAddr() net.Addr
	//发送数据
	SendMsg(uint32, []byte) error
	//连接属性
	SetProperty(key string, value interface{})
	GetProperty(key string) (interface{}, error)
	RemoveProperty(key string)
}

//定义一个处理业务连接的方法
type HandleFunc func(*net.TCPConn, []byte, int) error