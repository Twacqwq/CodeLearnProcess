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
	addr1 = flag.String("addr1", "tcp@127.0.0.1:8972", "server address")
	addr2 = flag.String("addr2", "tcp@127.0.0.1:8973", "server address")
)

func main() {
	flag.Parse()

	d, _ := client.NewMultipleServersDiscovery([]*client.KVPair{{Key: *addr1, Value: ""},
		{Key: *addr2, Value: ""}})
	//Hash
	xclient := client.NewXClient("Airth", client.Failtry, client.ConsistentHash, d, client.DefaultOption)
	defer xclient.Close()

	args := &service.Args{
		A: 10,
		B: 20,
	}

	for i := 0; i < 10; i++ {
		reply := &service.Reply{}
		err := xclient.Call(context.Background(), "Mul", args, reply)
		if err != nil {
			log.Fatalf("failed to call: %v", err)
		}

		log.Printf("%d * %d = %d", args.A, args.B, reply.C)

		time.Sleep(time.Second)
	}

}