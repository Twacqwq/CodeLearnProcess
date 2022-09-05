package main

import (
	"fmt"
	"net/http"
	"net/http/cookiejar"
)

func main() {
	options := cookiejar.Options{
		PublicSuffixList: nil,
	}
	jar, err := cookiejar.New(&options)
	if err != nil {
		panic(err)
	}
	client := &http.Client{
		Jar: jar,
	}
	resp, err := client.Get("https://www.baidu.com")
	if err != nil {
		panic(err)
	}
	fmt.Println(resp.Cookies())
}