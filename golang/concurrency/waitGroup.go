package main

import (
	"net/http"
	"sync"
)

var wg sync.WaitGroup
var urls = []string{
	"https://www.baidu.com",
	"https://www.bing.com",
	"https://www.bilibili.com",
}

func main() {
	for _, url := range urls {
		wg.Add(1)
		go func(url string) {
			//当前goroutine结束后给wg计数减1，wg.Done()等价于wg.Add(-1)
			//defer wg.Add(-1)
			defer wg.Done()
			resp, err := http.Get(url)
			if err == nil {
				println(resp.Status)
			}
		}(url)
	}
	//等待所有请求结束
	wg.Wait()
}