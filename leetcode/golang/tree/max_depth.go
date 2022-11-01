package tree

import "math"

type TreeNode struct {
	Val   int
	Left  *TreeNode
	Right *TreeNode
}

var (
	res   int = 0
	depth int = 0
)

// Maximum Depth of Binary Tree
// 二叉树的最大深度
// https://leetcode.cn/problems/maximum-depth-of-binary-tree/
func maxDepth(root *TreeNode) int {
	traverse(root)
	return res
}

func traverse(root *TreeNode) {
	if root == nil {
		return
	}
	// 前序位置
	depth++
	if root.Left == nil && root.Right == nil {
		res = int(math.Max(float64(res), float64(depth)))
	}
	traverse(root.Left)
	traverse(root.Right)
	// 后序位置
	depth--
}
