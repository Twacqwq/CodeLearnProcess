package main

import (
	"context"
	"flag"
	"time"

	"github.com/smallnest/rpcx/server"
	service "github.com/twacqwq/rpc"
)

var addr = flag.String("addr", "127.0.0.1:8972", "server address")

type Arith struct{}

func (t *Arith) Mul(ctx context.Context, args *service.Args, reply *service.Reply) error {
	time.Sleep(12*time.Second)
	reply.C = args.A * args.B
	return nil
}

func main() {
	s := server.NewServer()
	s.Register(new(Arith), "")
	s.Serve("tcp", *addr)
}