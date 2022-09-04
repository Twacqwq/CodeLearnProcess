package znet

import (
	"fmt"
	"net"
	"zinx/utils"
	"zinx/ziface"
)

type Server struct {
	Name      string
	IPVersion string
	IP        string
	Port      int
	MsgHandle ziface.IMsgHandler
	ConnManager ziface.IConnManager
	//Hook
	OnConnStart func(ziface.IConnection)
	OnConnStop	func(ziface.IConnection)
}

func (s *Server) Start() {
	fmt.Printf("Server_Name is %s, listen as %d\n", utils.GlobalObject.Name, utils.GlobalObject.TcpPort)
	fmt.Printf("Version: %s\n", utils.GlobalObject.Version)
	addr, err := net.ResolveTCPAddr(s.IPVersion, fmt.Sprintf("%s:%d", s.IP, s.Port))
	if err != nil {
		fmt.Println("resolve addr error", err)
		return
	}
	listener, err := net.ListenTCP(s.IPVersion, addr)
	if err != nil {
		fmt.Println("listener err", err)
		return
	}
	var cid uint32 = 0
	s.MsgHandle.StartWorkerPool() //开启worker工作池
	fmt.Println("start zinx success! server_name= " + s.Name)
	go func() {
		for {
			conn, err := listener.AcceptTCP()
			if err != nil {
				fmt.Println("Accept Error", err)
				continue
			}
			//判断连接数是否超出
			if s.ConnManager.Len() > utils.GlobalObject.MaxConn {
				fmt.Println("Too Many Connections, MaxConn=", utils.GlobalObject.MaxConn)
				conn.Close()
				continue
			}
			//封装连接Connection
			dealConn := NewConnection(s, conn, cid, s.MsgHandle)
			cid++
			go dealConn.Start()
		}
	}()
}

func (s *Server) GetConnMgr() ziface.IConnManager {
	return s.ConnManager
}

func (s *Server) Stop() {
	fmt.Println("[STOP] Server is stopped")
	s.ConnManager.ClearConn()
}

func (s *Server) Serve() {
	s.Start()
	select{}
}

func (s *Server) AddRouter(msgId uint32, router ziface.IRouter) {
	s.MsgHandle.AddRouter(msgId, router)
	fmt.Println("Add Router Success!")
}

func (s *Server) SetOnConnStart(hookFunc func(ziface.IConnection)) {
	s.OnConnStart = hookFunc
}

func (s *Server) SetOnConnStop(hookFunc func(ziface.IConnection)) {
	s.OnConnStop = hookFunc
}

func (s *Server) CallOnConnStart(conn ziface.IConnection) {
	if s.OnConnStart != nil {
		s.OnConnStart(conn)
	}
}

func (s *Server) CallOnConnStop(conn ziface.IConnection) {
	if s.OnConnStop != nil {
		s.OnConnStop(conn)
	}
}

func NewServer(name string) ziface.IServer {
	return &Server {
		Name: utils.GlobalObject.Name,
		IPVersion: "tcp4",
		IP: utils.GlobalObject.Host,
		Port: utils.GlobalObject.TcpPort,
		MsgHandle: NewMsgHandle(),
		ConnManager: NewConnManager(),
	}
}
