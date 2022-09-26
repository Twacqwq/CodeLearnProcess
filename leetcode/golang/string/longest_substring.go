package string

import "math"

// https://leetcode.cn/problems/longest-substring-without-repeating-characters

func lengthOfLongestSubstring(s string) int {
	if len(s) == 0 {
		return 0
	}
	res := [127]int{}
	result, left, right := 0, 0, -1
	for left < len(s) {
		if right+1 < len(s) && res[s[right+1]] == 0 {
			res[s[right+1]]++
			right++
		} else {
			res[s[left]]--
			left++
		}
		result = int(math.Max(float64(result), float64(right-left+1)))
	}
	return result
}
