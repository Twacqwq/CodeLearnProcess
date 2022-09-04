package ziface

type IRequest interface {
	//获取已经连接的Conn
	GetConnection() IConnection
	//获取数据
	GetData() []byte
	//获取数据ID
	GetMsgId() uint32
}