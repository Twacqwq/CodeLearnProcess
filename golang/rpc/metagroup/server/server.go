package main

import (
	"context"
	"flag"

	"github.com/smallnest/rpcx/server"
	service "github.com/twacqwq/rpc"
)

var (
	addr1 = flag.String("addr1", "127.0.0.1:8972", "server address")
	addr2 = flag.String("addr2", "127.0.0.1:9981", "server address")
)

type Arith struct{}

func (t *Arith) Mul(ctx context.Context, args *service.Args, reply *service.Reply) error {
	reply.C = args.A * args.B * 100
	return nil
}

func main() {
	flag.Parse()
	
	go createServer1(*addr1, "")
	go createServer2(*addr2, "group=test") //设置限制组 [客户端可绕过]

	select{}
}

func createServer1(addr, meta string) {
	s := server.NewServer()
	s.RegisterName("Arith", new(service.Arith), meta)
	s.Serve("tcp", addr)
}

func createServer2(addr, meta string) {
	s := server.NewServer()
	s.RegisterName("Arith", new(Arith), meta)
	s.Serve("tcp", addr)
}