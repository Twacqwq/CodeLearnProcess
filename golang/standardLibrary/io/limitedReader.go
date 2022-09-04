package main

import (
	"fmt"
	"io"
	"strings"
)


func main() {
	content := "This is LimitReader Example"
	reader := strings.NewReader(content)
	limitReader := &io.LimitedReader{
		R: reader,
		N: 8,
	}
	//从 R 读取但将返回的数据量限制为 N 字节。每调用一次 Read 都将更新 N 来反应新的剩余数量
	for limitReader.N > 0 {
		tmp := make([]byte, 2)
		limitReader.Read(tmp)
		fmt.Printf("%s", tmp)
	}
}