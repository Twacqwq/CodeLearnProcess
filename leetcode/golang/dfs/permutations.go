package dfs

var res [][]int

// 全排列
// https://leetcode.cn/problems/permutations
func permute(nums []int) [][]int {
	res = [][]int{}
	backtrack(nums, len(nums), []int{})
	return res
}

func backtrack(nums []int, numsLen int, path []int) {
	if len(nums) == 0 {
		p := make([]int, len(path))
		copy(p, path)
		res = append(res, p)
	}
	for i := 0; i < numsLen; i++ {
		cur := nums[i]
		path = append(path, cur)
		nums = append(nums[:i], nums[i+1:]...)
		backtrack(nums, len(nums), path)
		// 回溯
		nums = append(nums[:i], append([]int{cur}, nums[i:]...)...)
		path = path[:len(path)-1]
	}
}
