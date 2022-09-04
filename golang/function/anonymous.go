package main

import "fmt"

func add(a, b int) int {
	return a + b
}

func sub(a, b int) int {
	return a - b
}

type Op func(int, int) int //定义一个函数类型

func do(f Op, a, b int) int {
	return f(a, b)
}

func main() {
	a := do(add, 1, 2)
	fmt.Println(a)
	b := do(sub, 1, 2)
	fmt.Println(b)
}