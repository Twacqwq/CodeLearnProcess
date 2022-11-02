package tree

type TreeNode struct {
	Val   int
	Left  *TreeNode
	Right *TreeNode
}

// 用于缓存index 优化查找对应 index 的时间
var cache map[int]int

// 先中序构造二叉树
func buildTree(preorder, inorder []int) *TreeNode {
	cache = make(map[int]int)
	for i := 0; i < len(inorder); i++ {
		cache[inorder[i]] = i
	}
	return build(preorder, 0, len(preorder)-1, inorder, 0, len(inorder)-1)
}

// 构造辅助函数
func build(preorder []int, preStart, preEnd int, inorder []int, inStart, inEnd int) *TreeNode {
	// base case
	if preStart > preEnd {
		return nil
	}
	rootVal := preorder[preStart] // 拿到先序的root
	index := cache[rootVal]       // 拿到对应中序的root
	leftSize := index - inStart   // 中序左边的节点数
	// 构造二叉树
	root := &TreeNode{
		Val:   rootVal,
		Left:  build(preorder, preStart+1, preStart+leftSize, inorder, inStart, index-1),
		Right: build(preorder, preStart+leftSize+1, preEnd, inorder, index+1, inEnd),
	}

	return root
}
