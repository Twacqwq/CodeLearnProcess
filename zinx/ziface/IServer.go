package ziface

type IServer interface {
	Start()
	Stop()
	Serve()
	AddRouter(msgId uint32, router IRouter)
	GetConnMgr() IConnManager
	//Hook
	SetOnConnStart(func(IConnection))
	SetOnConnStop(func(IConnection))
	CallOnConnStart(IConnection)
	CallOnConnStop(IConnection)
}