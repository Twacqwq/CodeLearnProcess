package main

import (
	"context"
	"flag"

	"github.com/smallnest/rpcx/server"
	service "github.com/twacqwq/rpc"
)

var (
	addr1 = flag.String("addr1", "127.0.0.1:8972", "server address")
	addr2 = flag.String("addr2", "127.0.0.1:8973", "server address")
)

type Airth struct{}

func (t *Airth) Mul(ctx context.Context, args *service.Args, reply *service.Reply) error {
	reply.C = args.A * args.B
	return nil
}

type Airth2 struct{}

func (t *Airth2) Mul(ctx context.Context, args *service.Args, reply *service.Reply) error {
	reply.C = args.A * args.B * 100
	return nil
}

func main() {
	flag.Parse()

	go func() {
		s := server.NewServer()
		s.RegisterName("Airth", new(Airth), "weight=7")
		s.Serve("tcp", *addr1)
	}()

	go func() {
		s := server.NewServer()
		s.RegisterName("Airth", new(Airth2), "weight=3")
		s.Serve("tcp", *addr2)
	}()

	select{}
}
