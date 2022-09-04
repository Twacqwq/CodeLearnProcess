package utils

import (
	"encoding/json"
	"os"
	"zinx/ziface"
)

type GlobalObj struct {
	TCPServer ziface.IServer
	Name string
	Host string
	TcpPort int
	Version string
	MaxConn int
	MaxPackageSize uint32
	WorkerPoolSize uint32
	MaxWorkerTaskLength uint32
}

var GlobalObject *GlobalObj

func (g *GlobalObj) load() {
	data, err := os.ReadFile("conf/zinx.json")
	if err != nil {
		panic(err)
	}
	err = json.Unmarshal(data, &GlobalObject)
	if err != nil {
		panic(err)
	}
}

func init() {
	GlobalObject = &GlobalObj{
		Name: "ZinxServerApp",
		Version: "V0.4",
		TcpPort: 50000,
		Host: "127.0.0.1",
		MaxConn: 1000,
		MaxPackageSize: 1024,
		WorkerPoolSize: 10,
		MaxWorkerTaskLength: 1024,
	}
	GlobalObject.load()
}