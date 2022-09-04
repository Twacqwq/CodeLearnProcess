package main

import (
	"fmt"
	"io"
	"strings"
)

func main() {
	reader := strings.NewReader("Golang Study[0]Golang Study[1]")
	// Seek 设置下一次 Read 或 Write 的偏移量为 offset，它的解释取决于 whence： 
	//0 表示相对于文件的起始处，1 表示相对于当前的偏移，而 2 表示相对于其结尾处。 Seek 返回新的偏移量和一个错误，如果有的话。
	reader.Seek(-10, io.SeekEnd) //从结尾开始 移7
	r, _, _ := reader.ReadRune()
	fmt.Printf("%c\n", r)
}