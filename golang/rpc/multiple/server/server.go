package main

import (
	"flag"

	"github.com/smallnest/rpcx/server"
	service "github.com/twacqwq/rpc"
)

var (
	addr1 = flag.String("addr1", "127.0.0.1:8972", "server address1")
	addr2 = flag.String("addr2", "127.0.0.1:8981", "server address2")
)

func main() {
	flag.Parse()
	
	go createServer(*addr1)
	go createServer(*addr2)
	
	select{}
}

func createServer(addr string) {
	s := server.NewServer()
	s.Register(new(service.Arith), "")
	s.Serve("tcp", addr)
}