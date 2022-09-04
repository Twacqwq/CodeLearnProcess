package apis

import (
	"fmt"
	"zinx/ziface"
	"zinx/znet"
	"zinx_mmo_game/core"
	"zinx_mmo_game/pb/pb"

	"google.golang.org/protobuf/proto"
)

type WroldChatApi struct {
	znet.BaseRouter
}

func (wc *WroldChatApi) Handle(request ziface.IRequest) {
	proto_msg := &pb.Talk{}
	err := proto.Unmarshal(request.GetData(), proto_msg)
	if err != nil {
		fmt.Println("Talk Unmarshal error", err)
		return
	}
	pid, err := request.GetConnection().GetProperty("pid")
	if err != nil {
		fmt.Println("property error", err)
	}
	player := core.WorldMgrObj.GetPlayerByPid(pid.(int32))
	player.Talk(proto_msg.Content)
}