package main

import (
	"context"
	"flag"
	"log"
	"time"

	"github.com/smallnest/rpcx/client"
	service "github.com/twacqwq/rpc"
)

var (
	addr1 = flag.String("addr1", "tcp@127.0.0.1:8972", "server1 address")
	addr2 = flag.String("addr2", "tcp@127.0.0.1:9981", "server2 address")
)

func main() {
	flag.Parse()

	d, _ := client.NewMultipleServersDiscovery([]*client.KVPair{
		{Key: *addr1},
		{Key: *addr2, Value: "group=test"},
	})
	option := client.DefaultOption
	//group 就是一个元数据。如果你为服务设置了设置group， 只有在这个group的客户端才能访问这些服务
	option.Group = "test"
	xclient := client.NewXClient("Arith", client.Failtry, client.RoundRobin, d, option)
	defer xclient.Close()

	args := &service.Args{
		A: 10,
		B: 20,
	}

	for {
		reply := &service.Reply{}
		err := xclient.Call(context.Background(), "Mul", args, reply)
		if err != nil {
			log.Fatalf("failed to call: %v", err)
		}
		log.Printf("-> %d * %d = %d", args.A, args.B, reply.C)
		time.Sleep(time.Second)
	}
}