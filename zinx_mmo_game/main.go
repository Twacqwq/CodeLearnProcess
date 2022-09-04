package main

import (
	"fmt"
	"zinx/utils"
	"zinx/ziface"
	"zinx/znet"
	"zinx_mmo_game/apis"
	"zinx_mmo_game/core"
)

func OnConnectionAdd(conn ziface.IConnection) {
	player := core.NewPlayer(conn)
	player.SyncPid()
	player.BroadCastStartPosition()
	core.WorldMgrObj.AddPlayer(player)
	conn.SetProperty("pid", player.Pid)
	//同步周边玩家
	player.SyncSurrounding()
	fmt.Println("===> Player Pid = ", player.Pid, "is arrived <===")
}

func OnConnectionStop(conn ziface.IConnection) {
	pid, _ := conn.GetProperty("pid")
	player := core.WorldMgrObj.GetPlayerByPid(pid.(int32))
	player.Offline()
	fmt.Println("===> Player Pid = ", player.Pid, "is offline <===")
}

func main() {
	s := znet.NewServer(utils.GlobalObject.Name)
	s.AddRouter(2, &apis.WroldChatApi{})
	s.AddRouter(3, &apis.MoveApi{})
	s.SetOnConnStart(OnConnectionAdd)
	s.SetOnConnStop(OnConnectionStop)
	s.Serve()
}