package tree

import "fmt"

type TreeNode struct {
	Val   int
	Left  *TreeNode
	Right *TreeNode
}

// 先序遍历
func preorderTraversal(root *TreeNode) {
	var traverse func(*TreeNode)

	traverse = func(root *TreeNode) {
		if root == nil {
			return
		}
		fmt.Print(root.Val, " ")
		traverse(root.Left)
		traverse(root.Right)
	}
	traverse(root)
}

// 中序遍历
func inorderTraversal(root *TreeNode) {
	var traverse func(*TreeNode)

	traverse = func(root *TreeNode) {
		if root == nil {
			return
		}
		traverse(root.Left)
		fmt.Print(root.Val, " ")
		traverse(root.Right)
	}
	traverse(root)
}

// 后序遍历
func postorderTraversal(root *TreeNode) {
	var traverse func(*TreeNode)

	traverse = func(root *TreeNode) {
		if root == nil {
			return
		}
		traverse(root.Left)
		traverse(root.Right)
		fmt.Print(root.Val, " ")
	}
	traverse(root)
}
