package main

func main() {
	println(reverse(120))
}

func reverse(x int) int {
	temp := 0
	for x != 0 {
		temp = temp*10 + x%10
		x /= 10
	}
	if temp < -(1<<31) || temp > (1<<31)-1 {
		return 0
	}
	return temp
}
