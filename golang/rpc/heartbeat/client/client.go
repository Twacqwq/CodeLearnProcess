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
	addr = flag.String("addr", "127.0.0.1:8972", "server address")
	hb = flag.Bool("hb", true, "enable heartbeat or not")
)

func main() {
	flag.Parse()

	d, _ := client.NewPeer2PeerDiscovery("tcp@"+*addr, "")

	option := client.DefaultOption
	option.Heartbeat = *hb //开启心跳
	option.HeartbeatInterval = time.Second //心跳频率
	option.MaxWaitForHeartbeat = 2 * time.Second //等待心跳时间
	option.IdleTimeout = 3 * time.Second //设置conn的最大空闲时间

	xclient := client.NewXClient("Arith", client.Failtry, client.RandomSelect, d, option)
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
		time.Sleep(10 * time.Second)
	}
}