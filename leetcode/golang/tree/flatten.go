package tree

type TreeNode struct {
	Val   int
	Left  *TreeNode
	Right *TreeNode
}

// 二叉树展开链表
// https://leetcode.cn/problems/flatten-binary-tree-to-linked-list/
func flatten(root *TreeNode) {
	// base case
	if root == nil {
		return
	}
	flatten(root.Left)
	flatten(root.Right)
	left := root.Left
	right := root.Right
	root.Left = nil
	root.Right = left
	node := root
	// 将原先的右子树接到当前右子树的末端
	for node.Right != nil {
		node = node.Right
	}
	node.Right = right
}
