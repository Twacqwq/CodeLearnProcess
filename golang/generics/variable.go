package main

import (
	"fmt"

	"golang.org/x/exp/constraints"
)

func main() {
	show(1 + 2)
	println(min(1.2, 2.2))
}

func show[num int](s num) {
	fmt.Println(s)
}

func min[T constraints.Ordered](x, y T) T {
	if x < y {
		return x
	}

	return y
}
