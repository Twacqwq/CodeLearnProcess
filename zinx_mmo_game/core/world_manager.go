package core

import "sync"

type WorldManager struct {
	AoiMgr *AOIManager
	Players map[int32]*Player
	pLock sync.RWMutex
}

var WorldMgrObj *WorldManager

//全局初始化
func init() {
	WorldMgrObj = &WorldManager{
		AoiMgr: NewAOIManager(AOI_MIN_X, AOI_MAX_X, AOI_CNTS_X, AOI_MIN_Y, AOI_MAX_Y, AOI_CNTS_Y),
		Players: make(map[int32]*Player),
	}
}

//添加一个玩家
func (wm *WorldManager) AddPlayer(player *Player) {
	wm.pLock.Lock()
	wm.Players[player.Pid] = player
	wm.pLock.Unlock()
	//将player添加到AOIManager中
	wm.AoiMgr.AddToGridByPos(int(player.Pid), player.X, player.Z)
}

func (wm *WorldManager) RemovePlayerByPid(pid int32) {
	player := wm.Players[pid]
	wm.AoiMgr.RemoveFromGridByPos(int(pid), player.X, player.Z)
	wm.pLock.Lock()
	delete(wm.Players, pid)
	wm.pLock.Unlock()
}

func (wm *WorldManager) GetPlayerByPid(pid int32) *Player {
	wm.pLock.RLock()
	defer wm.pLock.RUnlock()
	return wm.Players[pid]
}

func (wm *WorldManager) GetAllPlayers() []*Player {
	wm.pLock.RLock()
	defer wm.pLock.RUnlock()
	players := make([]*Player, 0)
	for _, v := range wm.Players {
		players = append(players, v)
	}
	return players
}