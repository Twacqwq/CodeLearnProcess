package main

import (
	"flag"

	"github.com/smallnest/rpcx/server"
	service "github.com/twacqwq/rpc"
)

var addr = flag.String("addr", "127.0.0.1:8972", "server address")

func main() {
	flag.Parse()

	s := server.NewServer()
	s.Register(new(service.Arith), "")
	s.Serve("tcp", *addr)
}