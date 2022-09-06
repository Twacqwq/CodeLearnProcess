package main

import (
	"context"
	"log"

	"github.com/smallnest/rpcx/client"
	ss "github.com/twacqwq/rpc"
)


func main() {
	d, err := client.NewPeer2PeerDiscovery("tcp@127.0.0.1:8972", "")
	if err != nil {
		panic(err)
	}

	xclient := client.NewXClient("Arith", client.Failtry, client.RandomSelect, d, client.DefaultOption)

	args := &ss.Args{
		A: 10,
		B: 20,
	}
	reply := &ss.Reply{}

	//xclient.Call() 同步调用
	call, err := xclient.Go(context.Background(), "Mul", args, reply, nil)
	if err != nil {
		log.Fatalf("failed to call: %v", err)
	}
	replyCall := <-call.Done
	if replyCall.Error != nil {
		log.Fatalf("failed to call: %v", err)
	} else {
		log.Printf("%d * %d = %d", args.A, args.B, reply.C)
	}
}