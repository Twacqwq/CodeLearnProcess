package core

import (
	"fmt"
)

//定义一些AOI边界值
const (
	AOI_MIN_X int = 85
	AOI_MAX_X int = 410
	AOI_CNTS_X int = 10
	AOI_MIN_Y int = 75
	AOI_MAX_Y int = 400
	AOI_CNTS_Y int = 20
)

type AOIManager struct {
	MinX int
	MaxX int
	//X轴方向格子的数量
	CntsX int
	MinY int
	MaxY int
	//Y轴方向格子的数量
	CntsY int
	//当前区域有哪一些格子 map[GID]Grid
	grids map[int]*Grid
}

func NewAOIManager(minX, maxX, cntsX, minY, maxY, cntsY int) *AOIManager {
	aoiManager := &AOIManager{
		MinX: minX,
		MaxX: maxX,
		CntsX: cntsX,
		MinY: minY,
		MaxY: maxY,
		CntsY: cntsY,
		grids: make(map[int]*Grid),
	}
	//初始化格子区域编号
	for y := 0; y < cntsY; y++ {
		for x := 0; x < cntsX; x++ {
			//格子编号
			gid := y*cntsX + x
			aoiManager.grids[gid] = NewGrid(gid,
				aoiManager.MinX + x*aoiManager.girdWidth(),
				aoiManager.MinX + (x+1)*aoiManager.girdWidth(),
				aoiManager.MinY + y*aoiManager.gridLenth(),
				aoiManager.MinY + (y+1)*aoiManager.gridLenth())
		}
	}
	return aoiManager
}

//得到每个格子在X轴方向的宽度
func (m *AOIManager) girdWidth() int {
	return (m.MaxX - m.MinX) / m.CntsX
}

//得到每个格子在Y轴方向的高度
func (m *AOIManager) gridLenth() int {
	return (m.MaxY - m.MinY) / m.CntsY
}

func (m *AOIManager) String() string {
	s := fmt.Sprintf("AOIManager:\n MinX:%d, MaxX:%d, CntsX:%d, MinY:%d, MaxY:%d, CntsY:%d\n Grids in AOIManager:\n",
					m.MinX, m.MaxX, m.CntsX, m.MinY, m.MaxY, m.CntsY)
	for _, grid := range m.grids {
		s += fmt.Sprintln(grid)
	}
	return s
}

//根据格子ID计算得到周围九宫格格子
func (m *AOIManager) GetSurroundGridsByGID(gID int) (grids []*Grid) {
	if _, ok := m.grids[gID]; !ok {
		return
	}
	grids = append(grids, m.grids[gID])
	idx := gID % m.CntsX
	if idx > 0 {
		grids = append(grids, m.grids[gID-1])
	}
	if idx < m.CntsX-1 {
		grids = append(grids, m.grids[gID+1])
	}
	gidX := make([]int, 0, len(grids))
	for _, v := range grids {
		gidX = append(gidX, v.GID)
	}
	for _, v := range gidX {
		idy := v / m.CntsY
		if idy > 0 {
			grids = append(grids, m.grids[v-m.CntsX])
		}
		if idy < m.CntsY-1 {
			grids = append(grids, m.grids[v+m.CntsX])
		}
	}
	return
}

//根据x, y的坐标得到当前格子的编号
func (m *AOIManager) GetGIDByPos(x, y float32) int {
	idx := (int(x) - m.MinX) / m.girdWidth()
	idy := (int(y) - m.MinY) / m.gridLenth()
	return idy*m.CntsX + idx
}

//根据x，y的坐标获得周边九宫格所有玩家的ID
func (m *AOIManager) GetPidsByPos(x, y float32) (playerIDs []int) {
	gID := m.GetGIDByPos(x, y)
	grids := m.GetSurroundGridsByGID(gID)
	for _, grid := range grids {
		playerIDs = append(playerIDs, grid.GetPlayerIDs()...)
		//fmt.Println("grid ID:", gID, "playerIDs:", grid.GetPlayerIDs())
	}
	return
}

//添加一个PlayerID到格子中
func (m *AOIManager) AddPidToGrid(pID, gID int) {
	m.grids[gID].Add(pID)
}

//移除一个格子中的PlayerID
func (m *AOIManager) RemovePidFromGrid(pID, gID int) {
	m.grids[gID].Remove(pID)
}

//通过GID获取全部的PlayerID
func (m *AOIManager) GetPidsByGid(gID int) (playerIDs []int) {
	playerIDs = m.grids[gID].GetPlayerIDs()
	return
}

//通过坐标将player添加到一个格子中
func (m *AOIManager) AddToGridByPos(pID int, x, y float32) {
	gID := m.GetGIDByPos(x, y)
	grid := m.grids[gID]
	grid.Add(pID)
}

//通过坐标把一个player从一个格子中移除
func (m *AOIManager) RemoveFromGridByPos(pID int, x, y float32) {
	gID := m.GetGIDByPos(x, y)
	grid := m.grids[gID]
	grid.Remove(pID)
}

func (m *AOIManager) GetGrids() map[int]*Grid {
	return m.grids
}