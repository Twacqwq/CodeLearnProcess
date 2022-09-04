package core

import (
	"fmt"
	"sync"
)

type Grid struct {
	//格子ID
	GID int
	//格子左边边界坐标
	MinX int
	//格子右边边界坐标
	MaxX int
	//格子上边边界坐标
	MinY int
	//格子下边边界坐标
	MaxY int
	//当前格子内玩家/物体成员ID集合
	playerIDs map[int]bool
	//保护当前集合的锁
	pIDLock sync.RWMutex
}

func (g *Grid) Add(playerId int) {
	g.pIDLock.Lock()
	defer g.pIDLock.Unlock()
	g.playerIDs[playerId] = true
}

func (g *Grid) Remove(playerId int) {
	g.pIDLock.Lock()
	defer g.pIDLock.Unlock()
	delete(g.playerIDs, playerId)
}

func (g *Grid) GetPlayerIDs() (playerIDs []int) {
	g.pIDLock.RLock()
	defer g.pIDLock.RUnlock()
	for k := range g.playerIDs {
		playerIDs = append(playerIDs, k)
	}
	return
}

func (g *Grid) String() string {
	return fmt.Sprintf(
		"GID=%d, MinX=%d, MaxX=%d, MinY=%d, MaxY=%d, playerIDs=%v\n",
		g.GID, g.MinX, g.MaxX, g.MinY, g.MaxY, g.playerIDs)
}

func NewGrid(gid, minX, maxX, minY, maxY int) *Grid {
	return &Grid{
		GID: gid,
		MinX: minX,
		MaxX: maxX,
		MinY: minY,
		MaxY: maxX,
		playerIDs: make(map[int]bool),
	}
}