package tree

type TreeNode struct {
	Val   int
	Left  *TreeNode
	Right *TreeNode
}

var cache map[int]int

// 中后序构造二叉树
// https://leetcode.cn/problems/construct-binary-tree-from-preorder-and-postorder-traversal/
func buildTree(inorder, postorder []int) *TreeNode {
	cache = make(map[int]int)
	for i := 0; i < len(inorder); i++ {
		cache[inorder[i]] = i
	}
	return build(inorder, 0, len(inorder)-1, postorder, 0, len(postorder)-1)
}

func build(inorder []int, inStart, inEnd int, postorder []int, postStart, postEnd int) *TreeNode {
	// base case
	if inStart > inEnd {
		return nil
	}
	rootVal := postorder[postEnd]
	index := cache[rootVal]
	leftSize := index - inStart
	root := &TreeNode{
		Val:   rootVal,
		Left:  build(inorder, inStart, index-1, postorder, postStart, postStart+leftSize-1),
		Right: build(inorder, index+1, inEnd, postorder, postStart+leftSize, postEnd-1),
	}

	return root
}
