package ziface

type IConnManager interface {
	//添加连接
	Add(conn IConnection)
	//删除连接
	Remove(conn IConnection)
	//根据连接ID获取连接
	Get(connId uint32) (IConnection, error)
	//获取连接总数
	Len() int
	//删除所有连接
	ClearConn()
}