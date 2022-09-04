package main

import "runtime"

func main() {
	c := make(chan struct{})
	ci := make(chan int, 100)
	go func(i chan struct{}, j chan int) {
		for i := 0; i < 10; i++ {
			ci <- i
		}
		close(ci)
		c <- struct{}{}
	}(c, ci)

	//NumGoroutine
	println("NumGoroutine", runtime.NumGoroutine())
	//读通道c，同步等待
	<-c
	println("NumGoroutine", runtime.NumGoroutine())
	
	//通道关闭缓冲区有数据可读
	for v := range ci {
		println(v)
	}
}