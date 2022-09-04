package main

func fa(a int) func(i int) int {
	return func(i int) int {
		println(&a, a)
		a += i
		return a
	}
}

func main() {
	f := fa(1) //f引用的外部的闭包包括本次函数调用的形参a的值1
	g := fa(1)

	println(f(1))
	println(f(1))

	println(g(1))
	println(g(1))
}