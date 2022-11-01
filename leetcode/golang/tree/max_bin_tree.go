package tree

import "math"

type TreeNode struct {
	Val   int
	Left  *TreeNode
	Right *TreeNode
}

// 构造最大的二叉树
// https://leetcode.cn/problems/maximum-binary-tree/
func constructMaximumBinaryTree(nums []int) *TreeNode {
	return build(nums, 0, len(nums))
}

func build(nums []int, lo, hi int) *TreeNode {
	// base case
	if lo > hi {
		return nil
	}
	index, maxVal := -1, math.MinInt
	for i := lo; i <= hi; i++ {
		if maxVal < nums[i] {
			// 更新索引
			index = i
			maxVal = nums[i]
		}
	}
	root := &TreeNode{
		Val:   maxVal,
		Left:  build(nums, lo, index-1),
		Right: build(nums, index+1, hi),
	}

	return root
}
