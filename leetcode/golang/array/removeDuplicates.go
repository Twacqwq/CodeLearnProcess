package main

func removeDuplicates(nums []int) int {
	numsLen := len(nums)
	if numsLen == 0 {
		return 0
	}
	last, finder := 0, 0
	for last < numsLen-1 {
		for nums[last] == nums[finder] {
			finder++
			if finder == numsLen {
				return last + 1
			}
		}
		nums[last+1] = nums[finder]
		last++
	}
	return last + 1
}

func main() {
	nums := []int{0, 0, 1, 1, 1, 2, 2, 3, 3, 4}
	println(removeDuplicates(nums))
}
