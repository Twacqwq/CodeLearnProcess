package tree

type TreeNode struct {
	Val   int
	Left  *TreeNode
	Right *TreeNode
	Next  *TreeNode
}

// 填充每个节点的下一个右侧节点指针
// https://leetcode.cn/problems/populating-next-right-pointers-in-each-node/
func connect(root *TreeNode) *TreeNode {
	if root == nil {
		return nil
	}
	traverse(root.Left, root.Right)
	return root
}

func traverse(node1, node2 *TreeNode) {
	if node1 == nil || node2 == nil {
		return
	}
	node1.Next = node2
	// 同一父节点相连
	traverse(node1.Left, node1.Right)
	traverse(node2.Left, node2.Right)
	// 跨节点
	traverse(node1.Right, node2.Left)
}
