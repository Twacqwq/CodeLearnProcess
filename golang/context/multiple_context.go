package main

import (
	"context"
	"fmt"
	"time"
)

func main() {
	ctx, cancel := context.WithCancel(context.Background())
	go worker(ctx, "node01")
	go worker(ctx, "node02")
	go worker(ctx, "node03")

	time.Sleep(5*time.Second)
	fmt.Println("send stop")
	cancel()
	time.Sleep(5*time.Second)
}

func worker(ctx context.Context, name string) {
	go func() {
		for {
			select {
			case <-ctx.Done():
				fmt.Println(name, "go to the stop")
				return
			default:
				fmt.Println(name, "still working")
				time.Sleep(time.Second)
			}
		}
	}()
}