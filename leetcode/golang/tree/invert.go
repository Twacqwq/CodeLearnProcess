package tree

type TreeNode struct {
	Val   int
	Left  *TreeNode
	Right *TreeNode
}

// 翻转二叉树
// https://leetcode.cn/problems/invert-binary-tree/
func invertTree(root *TreeNode) *TreeNode {
	traverse(root)
	return root
}

func traverse(root *TreeNode) {
	if root == nil {
		return
	}
	temp := root.Left
	root.Left = root.Right
	root.Right = temp
	traverse(root.Left)
	traverse(root.Right)
}

// 分解问题
// 先翻转左右子树 在整体翻转达到目的
func invertTree2(root *TreeNode) *TreeNode {
	if root == nil {
		return nil
	}
	// 翻转左右子树
	left := invertTree2(root.Left)
	right := invertTree2(root.Right)

	// 交换左右子节点
	root.Left = right
	root.Right = left

	return root
}
