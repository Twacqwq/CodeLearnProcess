package main

import "fmt"

func convert(s string, numRows int) string {
	if numRows == 1 {
		return s
	}
	matrix := make([][]byte, numRows, numRows)
	down, up := 0, numRows-2 //上下方向
	for i := 0; i < len(s); {
		if down != numRows {
			matrix[down] = append(matrix[down], byte(s[i]))
			down++
			i++
		} else if up > 0 {
			matrix[up] = append(matrix[up], byte(s[i]))
			up--
			i++
		} else {
			up = numRows - 2
			down = 0
		}
	}
	result := make([]byte, 0, len(s))
	for _, row := range matrix {
		for _, item := range row {
			result = append(result, item)
		}
	}
	return string(result)
}

func main() {
	s := "PAYPALISHIRING"
	result := convert(s, 3)
	fmt.Println(result)
}
