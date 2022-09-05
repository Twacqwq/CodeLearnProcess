package main

import (
	"fmt"
	"net/http"
)

func main() {
	client := &http.Client{}
	request, err := http.NewRequest("GET", "http://httpbin.org/get", nil)
	if err != nil {
		panic(err)
	}
	c := &http.Cookie{
		Name: "JSESSION",
		Value: "23333",
	}
	request.AddCookie(c)
	resp, _ := client.Do(request)
	fmt.Println(request)
	fmt.Println(resp.Cookies())
}