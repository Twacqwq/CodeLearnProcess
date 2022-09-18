package main

import "fmt"

// https://leetcode.cn/problems/two-sum/

func twoSum(nums []int, target int) []int {
	record := make(map[int]int)
	for i := 0; i < len(nums); i++ {
		ant := target - nums[i]
		if _, ok := record[ant]; ok {
			return []int{record[ant], i}
		}
		record[nums[i]] = i
	}
	return nil
}

func main() {
	nums := []int{2, 7, 11, 15}
	fmt.Printf("%v", twoSum(nums, 19))
}
