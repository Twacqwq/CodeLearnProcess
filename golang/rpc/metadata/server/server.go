package main

import (
	"context"
	"flag"
	"fmt"

	"github.com/smallnest/rpcx/server"
	"github.com/smallnest/rpcx/share"
	service "github.com/twacqwq/rpc"
)

var (
	addr = flag.String("addr", "127.0.0.1:7892", "server address")
)

type Arith struct{}

func (t *Arith) Mul(ctx context.Context, args *service.Args, reply *service.Reply) error {
	//获得从客户端request的数据
	requestData := ctx.Value(share.ReqMetaDataKey).(map[string]string)
	//响应给客户端
	responseData := ctx.Value(share.ResMetaDataKey).(map[string]string)
	fmt.Printf("received meta: %+v\n", requestData)
	responseData["echo"] = "from server"
	reply.C = args.A * args.B
	return nil
}

func main() {
	flag.Parse()
	s := server.NewServer()
	s.RegisterName("Arith", new(Arith), "")
	s.Serve("tcp", *addr)
}