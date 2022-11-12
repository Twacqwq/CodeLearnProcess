package dp

// 最长递增子序列
// https://leetcode.cn/problems/longest-increasing-subsequence
func lengthOfLIS(nums []int) int {
	n := len(nums)
	res := 0
	dp := make([]int, n)
	for i := 0; i < n; i++ {
		dp[i] = 1
	}
	for i := 0; i < n; i++ {
		for j := 0; j < i; j++ {
			if nums[i] > nums[j] {
				dp[i] = max(dp[i], dp[j]+1)
			}
		}
	}
	for i := 0; i < n; i++ {
		res = max(res, dp[i])
	}
	return res
}

func max(i, j int) int {
	if i > j {
		return i
	}
	return j
}
