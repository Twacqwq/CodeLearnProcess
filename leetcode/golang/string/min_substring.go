package string

// 最小覆盖子串
// https://leetcode.cn/problems/minimum-window-substring
func minWindow(s, t string) string {
	need := make(map[uint8]int)
	window := make(map[uint8]int)
	for i := 0; i < len(t); i++ {
		need[t[i]]++
	}
	left, right, length := 0, -1, len(s)+1
	for i, j := 0, 0; j < len(s); j++ {
		window[s[j]]++
		for check(need, window) {
			if j-i+1 < length {
				length = j - i + 1
				left = i
				right = j
			}
			window[s[i]]--
			i++
		}
	}
	return s[left : right+1]
}

func check(need, window map[uint8]int) bool {
	for k, v := range need {
		if window[k] < v {
			return false
		}
	}
	return true
}
