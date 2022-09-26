package string

// https://leetcode.cn/problems/string-to-integer-atoi

func myAtoi(s string) int {
	result, sign, i, n := 0, 1, 0, len(s)
	const MinInt32, MaxInt32 = -1 << 31, 1<<31 - 1

	// 去除前导空格
	for ; i < n && s[i] == ' '; i++ {
	}
	if i >= n {
		return 0
	}
	switch s[i] {
	case '+':
		i++
	case '-':
		i++
		sign = -1
	}
	for ; i < n; i++ {
		if s[i] < 48 || s[i] > 57 {
			break
		}
		result = result*10 + int(s[i]-'0')
		if sign*result > MaxInt32 {
			return MaxInt32
		}
		if sign*result < MinInt32 {
			return MinInt32
		}
	}
	return sign * result
}
