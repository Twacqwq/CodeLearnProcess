package main

import (
	"fmt"
	"time"
)

func main() {
	start := time.Now()
	timer := time.AfterFunc(1*time.Second, func() {
		fmt.Println("after func callback, elaspe:", time.Now().Sub(start))
	})
	time.Sleep(2*time.Second)
	// Reset 在 Timer 还未触发时返回 true; 触发了或 Stop 了, 返回false
	if timer.Reset(2*time.Second) {
		fmt.Println("timer has not trigger!")
	} else {
		fmt.Println("timer had expired or stop!")
	}
	time.Sleep(10*time.Second)
}