package znet

import (
	"fmt"
	"zinx/utils"
	"zinx/ziface"
)

type MsgHandle struct {
	//路由映射
	Apis map[uint32]ziface.IRouter
	//消息队列
	TaskQueue []chan ziface.IRequest
	//worker工作池数量
	WorkerPoolSize uint32
}

func (mh *MsgHandle) DoMsgHandle(request ziface.IRequest) {
	handle, ok := mh.Apis[request.GetMsgId()]
	if !ok {
		fmt.Println("api MsgID=", request.GetMsgId(), "is NOT FOUND, Need Register")
		return
	}
	handle.PreHandle(request)
	handle.Handle(request)
	handle.PostHandle(request)
}

func (mh *MsgHandle) AddRouter(msgId uint32, router ziface.IRouter) {
	if _, ok := mh.Apis[msgId]; ok {
		panic(fmt.Sprintf("repeat router, msgId=%d", msgId))
	}
	mh.Apis[msgId] = router
}

//启动worker工作池（Zinx限定一次）
func (mh *MsgHandle) StartWorkerPool() {
	for i := 0; i < int(mh.WorkerPoolSize); i++ {
		//为每个worker初始化对应的channel 并指定每个队列的最大容量
		mh.TaskQueue[i] = make(chan ziface.IRequest, utils.GlobalObject.MaxWorkerTaskLength)
		//go并发启动每个worker
		go mh.startOneWorker(i, mh.TaskQueue[i])
	}
}

//启动一个工作池流程
func (mh *MsgHandle) startOneWorker(workId int, taskQueue chan ziface.IRequest) {
	fmt.Println("Worker ID = ", workId, "is started...")
	for {
		mh.DoMsgHandle(<-taskQueue)
	}
}

//将消息交由消息队列对应的worker处理
func (mh *MsgHandle) SendMsgToTaskQueue(request ziface.IRequest) {
	// 简单平均化
	workerID := request.GetConnection().GetConnID() % mh.WorkerPoolSize
	fmt.Println("Add ConnID = ", request.GetConnection().GetConnID(), "msgId = ", request.GetMsgId(), "to workerID", workerID)
	mh.TaskQueue[workerID] <- request
}

func NewMsgHandle() *MsgHandle {
	return &MsgHandle{
		Apis: make(map[uint32]ziface.IRouter),
		TaskQueue: make([]chan ziface.IRequest, utils.GlobalObject.WorkerPoolSize),
		WorkerPoolSize: utils.GlobalObject.WorkerPoolSize,
	}
}