package main

import (
	"context"
	"flag"
	"log"

	"github.com/smallnest/rpcx/client"
	"github.com/smallnest/rpcx/share"
	service "github.com/twacqwq/rpc"
)

var (
	addr = flag.String("addr", "127.0.0.1:7892", "server address")
)

func main() {
	flag.Parse()

	d, _ := client.NewPeer2PeerDiscovery("tcp@"+*addr, "")
	xclient := client.NewXClient("Arith", client.Failtry, client.RandomSelect, d, client.DefaultOption)
	defer xclient.Close()

	args := &service.Args{
		A: 10,
		B: 20,
	}

	reply := &service.Reply{}
	ctx := context.WithValue(context.Background(), share.ReqMetaDataKey, map[string]string{"aaa": "from client"})
	ctx = context.WithValue(ctx, share.ResMetaDataKey, make(map[string]string))
	err := xclient.Call(ctx, "Mul", args, reply)
	if err != nil {
		log.Fatalf("falied to call: %v", err)
	}
	log.Printf("-> %d * %d = %d", args.A, args.B, reply.C)
	log.Printf("-> reveied meta: %+v", ctx.Value(share.ResMetaDataKey))
}