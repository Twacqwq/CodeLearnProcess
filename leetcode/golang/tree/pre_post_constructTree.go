package tree

type TreeNode struct {
	Val   int
	Left  *TreeNode
	Right *TreeNode
}

var postCache map[int]int

// 前后序构造二叉树
// https://leetcode.cn/problems/construct-binary-tree-from-preorder-and-postorder-traversal/
func constructFromPrePost(preorder []int, postorder []int) *TreeNode {
	postCache = make(map[int]int)
	for i := 0; i < len(postorder); i++ {
		postCache[postorder[i]] = i
	}
	return build(preorder, 0, len(preorder)-1, postorder, 0, len(postorder)-1)
}

func build(preorder []int, preStart, preEnd int, postorder []int, postStart, postEnd int) *TreeNode {
	if preStart > preEnd {
		return nil
	}
	if preStart == preEnd {
		return &TreeNode{Val: preorder[preStart]}
	}
	rootVal := preorder[preStart]
	leftRootVal := preorder[preStart+1]
	index := postCache[leftRootVal]
	leftSize := index - postStart + 1
	root := &TreeNode{
		Val:   rootVal,
		Left:  build(preorder, preStart+1, preStart+leftSize, postorder, postStart, index),
		Right: build(preorder, preStart+leftSize+1, preEnd, postorder, index+1, postEnd-1),
	}

	return root
}
