package main

import (
	"context"
	"flag"
	"log"
	"time"

	"github.com/smallnest/rpcx/client"
	service "github.com/twacqwq/rpc"
)

var addr = flag.String("addr", "tcp@127.0.0.1:8972", "server address")

func main() {
	d, _ := client.NewPeer2PeerDiscovery(*addr, "")
	xclient := client.NewXClient("Arith", client.Failtry, client.RandomSelect, d, client.DefaultOption)
	defer xclient.Close()

	args := &service.Args{
		A: 10,
		B: 20,
	}

	reply := &service.Reply{}
	//在上下文对象Context设置超时
	ctx, cancelFunc := context.WithTimeout(context.Background(), time.Second)
	err := xclient.Call(ctx, "Mul", args, reply)
	if err != nil {
		log.Fatalf("falied to call: %v", err)
	}
	cancelFunc()
	log.Printf("-> %d * %d = %d", args.A, args.B, reply.C)

}