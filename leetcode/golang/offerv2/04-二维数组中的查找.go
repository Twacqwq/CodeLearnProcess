package main

//Link: https://leetcode.cn/problems/er-wei-shu-zu-zhong-de-cha-zhao-lcof

func main() {
	matrix := [][]int{
		{1, 4, 7, 11, 15},
		{2, 5, 8, 12, 19},
		{3, 6, 9, 16, 22},
		{10, 13, 14, 17, 24},
		{18, 21, 23, 26, 30},
	}
	println(findNumberIn2DArray(matrix, 5))
}

func findNumberIn2DArray(matrix [][]int, target int) bool {
	if len(matrix) == 0 {
		return false
	}
	m, n := len(matrix), len(matrix[0])
	//初始化坐标
	i, j := 0, n-1
	for i < m && j >= 0 {
		if matrix[i][j] == target {
			return true
		}
		if matrix[i][j] < target {
			i++
		} else {
			j--
		}
	}
	return false
}