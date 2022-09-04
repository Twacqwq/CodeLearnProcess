package main

import (
	"fmt"
	"strings"
)

func main() {
	reader := strings.NewReader("Golang Study")
	p := make([]byte, 6)
	n, err := reader.ReadAt(p, 2) //偏移两个字节
	if err != nil {
		panic(err)
	}
	fmt.Printf("%s, %d\n", p, n)
}