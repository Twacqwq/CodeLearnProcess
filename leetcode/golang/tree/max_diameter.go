package tree

type TreeNode struct {
	Val   int
	Left  *TreeNode
	Right *TreeNode
}

var maxDiameter int

// 二叉树的直径
// https://leetcode.cn/problems/diameter-of-binary-tree/
func diameterOfBinaryTree(root *TreeNode) int {
	maxDepth(root)
	return maxDiameter
}

func maxDepth(root *TreeNode) int {
	if root == nil {
		return 0
	}
	var leftMax = maxDepth(root.Left)
	var rightMax = maxDepth(root.Right)
	var currentDiam = leftMax + rightMax
	maxDiameter = max(maxDiameter, currentDiam)
	return 1 + max(leftMax, rightMax)
}

func max(i, j int) int {
	if i > j {
		return i
	}
	return j
}
