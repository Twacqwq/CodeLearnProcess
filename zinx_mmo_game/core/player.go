package core

import (
	"fmt"
	"math/rand"
	"sync"
	"zinx/ziface"
	"zinx_mmo_game/pb/pb"

	"google.golang.org/protobuf/proto"
)

type Player struct {
	//玩家ID
	Pid int32
	//当前玩家的连接
	Conn ziface.IConnection
	//平面X轴坐标
	X float32
	//高度
	Y float32
	//平面Y轴坐标
	Z float32
	//角度
	V float32
}

//Demo ID生成器
var idGen int32 = 1
var idLock sync.RWMutex

func NewPlayer(conn ziface.IConnection) *Player {
	idLock.Lock()
	id := idGen
	idGen++
	idLock.Unlock()
	return &Player{
		Pid: id,
		Conn: conn,
		X: float32(160 + rand.Intn(10)),
		Y: 0,
		Z: float32(140 + rand.Intn(10)),
		V: 0,
	}
}

//发送消息给客户端
func (p *Player) SendMsg(MsgId uint32, data proto.Message) {
	msg, err := proto.Marshal(data)
	if err != nil {
		fmt.Println("Marshal Msg error", err)
		return
	}
	if p.Conn == nil {
		fmt.Println("connection in player is nil")
		return
	}
	if err := p.Conn.SendMsg(MsgId, msg); err != nil {
		fmt.Println("send msg error", err)
		return
	}
}

//告知客户端玩家ID
func (p *Player) SyncPid() {
	//组建msg = 1 的proto数据
	proto_data := &pb.SyncPid{
		Pid: p.Pid,
	}
	p.SendMsg(1, proto_data)
}

//广播玩家自己的出生地
func (p *Player) BroadCastStartPosition() {
	//组建msg = 200的proto数据
	proto_msg := &pb.BroadCast{
		Pid: p.Pid,
		Tp: 2,
		Data: &pb.BroadCast_P{
			P: &pb.Position{
				X: p.X,
				Y: p.Y,
				Z: p.Z,
				V: p.V,
			},
		},
	}
	p.SendMsg(200, proto_msg)
}

//玩家广播聊天信息
func (p *Player) Talk(content string) {
	//组建msg = 200的proto数据
	proto_msg := &pb.BroadCast{
		Pid: p.Pid,
		Tp: 1,
		Data: &pb.BroadCast_Content{
			Content: content,
		},
	}
	players := WorldMgrObj.GetAllPlayers()
	for _, player := range players {
		player.SendMsg(200, proto_msg)
	}
}

//同步玩家上线的位置消息
func (p *Player) SyncSurrounding() {
	pids := WorldMgrObj.AoiMgr.GetPidsByPos(p.X, p.Z)
	players := make([]*Player, 0, len(pids))
	for _, pid := range pids {
		players = append(players, WorldMgrObj.GetPlayerByPid(int32(pid)))
	}
	proto_msg := &pb.BroadCast{
		Pid: p.Pid,
		Tp: 2,
		Data: &pb.BroadCast_P{
			P: &pb.Position{
				X: p.X,
				Y: p.Y,
				Z: p.Z,
				V: p.V,
			},
		},
	}
	for _, player := range players {
		player.SendMsg(200, proto_msg)
	}
	players_proto_msg := make([]*pb.Player, 0, len(players))
	for _, player := range players {
		p := &pb.Player{
			Pid: player.Pid,
			P: &pb.Position{
				X: player.X,
				Y: player.Y,
				Z: player.Z,
				V: player.V,
			},
		}
		players_proto_msg = append(players_proto_msg, p)
	}
	syncPlayers_proto_msg := &pb.SyncPlayers{
		Ps: players_proto_msg[:],
	}
	p.SendMsg(202, syncPlayers_proto_msg)
}

//广播当前玩家的位置信息
func (p *Player) UpdatePos(x, y, z, v float32) {
	p.X = x
	p.Y = y
	p.Z = z
	p.V = v
	proto_msg := &pb.BroadCast{
		Pid: p.Pid,
		Tp: 4,
		Data: &pb.BroadCast_P{
			P: &pb.Position{
				X: p.X,
				Y: p.Y,
				Z: p.Z,
				V: p.V,
			},
		},
	}
	players := p.GetSuroundingPlayers()
	for _, player := range players {
		player.SendMsg(200, proto_msg)
	}
}

func (p *Player) GetSuroundingPlayers() []*Player {
	pids := WorldMgrObj.AoiMgr.GetPidsByPos(p.X, p.Z)
	players := make([]*Player, 0, len(pids))
	for _, pid := range pids {
		players = append(players, WorldMgrObj.GetPlayerByPid(int32(pid)))
	}
	return players
}

func (p *Player) Offline() {
	players := p.GetSuroundingPlayers()
	proto_msg := &pb.SyncPid{
		Pid: p.Pid,
	}
	for _, player := range players {
		player.SendMsg(201, proto_msg)
	}
	WorldMgrObj.AoiMgr.RemoveFromGridByPos(int(p.Pid), p.X, p.Z)
	WorldMgrObj.RemovePlayerByPid(p.Pid)
}