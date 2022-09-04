package main

import "math"

//Link: https://leetcode.cn/problems/shu-zu-zhong-zhong-fu-de-shu-zi-lcof

func main() {
	arr := []int{2, 3, 1, 0, 2, 5, 3}
	println(findRepeatNumber(arr))
}

func findRepeatNumber(nums []int) int {
	for _, num := range nums {
		abs := math.Abs(float64(num))
		if nums[int(abs)] < 0 {
			return int(abs)
		} else {
			nums[int(abs)] *= -1
		}
	}
	return -1
}