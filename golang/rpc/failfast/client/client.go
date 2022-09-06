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
	addr1 = flag.String("addr1", "tcp@127.0.0.1:8972", "server address1")
	addr2 = flag.String("addr2", "tcp@127.0.0.1:8981", "server address2")
)

func main() {
	flag.Parse()
	//创建点对多服务
	d, _ := client.NewMultipleServersDiscovery([]*client.KVPair{
		{Key: *addr1},
		{Key: *addr2},
	})
	option := client.DefaultOption
	option.Retries = 10 //重试次数改为10
	xclient := client.NewXClient("Arith", client.Failbackup, client.RandomSelect, d, option)
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
		log.Printf("-> %d * %d = %d", args.A, args.B, reply.C)
		time.Sleep(1e9)
	}
}