package ziface

type IMsgHandler interface {
	//handler
	DoMsgHandle(IRequest)
	//add
	AddRouter(uint32, IRouter)
	//start workerPool
	StartWorkerPool()
	// msg -> taskQueue
	SendMsgToTaskQueue(IRequest)
}