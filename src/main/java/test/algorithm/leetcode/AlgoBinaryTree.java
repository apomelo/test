package test.algorithm.leetcode;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 二叉树
 * @author C
 * @date 2023/7/24
 */
@Slf4j
public class AlgoBinaryTree {
    public static void main(String[] args) {
        // [94] 二叉树的中序遍历
        log.info("BinaryTreeInorderTraversal: {}", new BinaryTreeInorderTraversal().inorderTraversal(BinaryTreeInorderTraversal.example1()));
        log.info("BinaryTreeInorderTraversal: {}", new BinaryTreeInorderTraversal().inorderTraversal(BinaryTreeInorderTraversal.example2()));
        // [95] 不同的二叉搜索树 II
        log.info("UniqueBinarySearchTreesII: {}", new UniqueBinarySearchTreesII().generateTrees(3));
        log.info("UniqueBinarySearchTreesII: {}", new UniqueBinarySearchTreesII().generateTrees(1));
        // [96] 不同的二叉搜索树
        log.info("UniqueBinarySearchTrees: {}", new UniqueBinarySearchTrees().numTrees(3));
        log.info("UniqueBinarySearchTrees: {}", new UniqueBinarySearchTrees().numTrees(1));
        log.info("UniqueBinarySearchTrees: {}", new UniqueBinarySearchTrees().numTrees(18));
        log.info("UniqueBinarySearchTrees: {}", new UniqueBinarySearchTrees().numTrees2(18));
        // [98] 验证二叉搜索树
        log.info("ValidateBinarySearchTree: {}", new ValidateBinarySearchTree().isValidBST(ValidateBinarySearchTree.example1()));
        log.info("ValidateBinarySearchTree: {}", new ValidateBinarySearchTree().isValidBST(ValidateBinarySearchTree.example2()));
        log.info("ValidateBinarySearchTree: {}", new ValidateBinarySearchTree().isValidBST(ValidateBinarySearchTree.example3()));
        log.info("ValidateBinarySearchTree: {}", new ValidateBinarySearchTree().isValidBST(ValidateBinarySearchTree.example4()));
        // [98] 验证二叉搜索树
        TreeNode recoverBinarySearchTreeExample1 = RecoverBinarySearchTree.example1();
        new RecoverBinarySearchTree().recoverTree(recoverBinarySearchTreeExample1);
        log.info("RecoverBinarySearchTree: {}", recoverBinarySearchTreeExample1);
        TreeNode recoverBinarySearchTreeExample2 = RecoverBinarySearchTree.example2();
        new RecoverBinarySearchTree().recoverTree(recoverBinarySearchTreeExample2);
        log.info("RecoverBinarySearchTree: {}", recoverBinarySearchTreeExample2);
        // [100] 相同的树
        log.info("SameTree: {}", new SameTree().isSameTree(SameTree.example1(), SameTree.example1()));
        log.info("SameTree: {}", new SameTree().isSameTree(SameTree.example2(), SameTree.example3()));
        log.info("SameTree: {}", new SameTree().isSameTree(SameTree.example4(), SameTree.example5()));
        // [101] 对称二叉树
        log.info("SymmetricTree: {}", new SymmetricTree().isSymmetric(SymmetricTree.example1()));
        log.info("SymmetricTree: {}", new SymmetricTree().isSymmetric(SymmetricTree.example2()));
        // [102] 二叉树的层序遍历
        log.info("BinaryTreeLevelOrderTraversal: {}", new BinaryTreeLevelOrderTraversal().levelOrder(BinaryTreeLevelOrderTraversal.example1()));
        log.info("BinaryTreeLevelOrderTraversal: {}", new BinaryTreeLevelOrderTraversal().levelOrder(BinaryTreeLevelOrderTraversal.example2()));
        log.info("BinaryTreeLevelOrderTraversal: {}", new BinaryTreeLevelOrderTraversal().levelOrder(BinaryTreeLevelOrderTraversal.example3()));
        // [103] 二叉树的锯齿形层序遍历
        log.info("BinaryTreeZigzagLevelOrderTraversal: {}", new BinaryTreeZigzagLevelOrderTraversal().zigzagLevelOrder(BinaryTreeZigzagLevelOrderTraversal.example1()));
        log.info("BinaryTreeZigzagLevelOrderTraversal: {}", new BinaryTreeZigzagLevelOrderTraversal().zigzagLevelOrder(BinaryTreeZigzagLevelOrderTraversal.example2()));
        log.info("BinaryTreeZigzagLevelOrderTraversal: {}", new BinaryTreeZigzagLevelOrderTraversal().zigzagLevelOrder(BinaryTreeZigzagLevelOrderTraversal.example3()));
        // [104] 二叉树的最大深度
        log.info("MaximumDepthOfBinaryTree: {}", new MaximumDepthOfBinaryTree().maxDepth(MaximumDepthOfBinaryTree.example1()));
        log.info("MaximumDepthOfBinaryTree: {}", new MaximumDepthOfBinaryTree().maxDepth(MaximumDepthOfBinaryTree.example2()));
        // [105] 从前序与中序遍历序列构造二叉树
        log.info("ConstructBinaryTreeFromPreorderAndInorderTraversal: {}", new ConstructBinaryTreeFromPreorderAndInorderTraversal().buildTree(new int[] {3,9,20,15,7}, new int[] {9,3,15,20,7}));
        log.info("ConstructBinaryTreeFromPreorderAndInorderTraversal: {}", new ConstructBinaryTreeFromPreorderAndInorderTraversal().buildTree(new int[] {-1}, new int[] {-1}));
        // [106] 从中序与后序遍历序列构造二叉树
        log.info("ConstructBinaryTreeFromInorderAndPostorderTraversal: {}", new ConstructBinaryTreeFromInorderAndPostorderTraversal().buildTree(new int[] {9,3,15,20,7}, new int[] {9,15,7,20,3}));
        log.info("ConstructBinaryTreeFromInorderAndPostorderTraversal: {}", new ConstructBinaryTreeFromInorderAndPostorderTraversal().buildTree(new int[] {-1}, new int[] {-1}));
        // [107] 二叉树的层序遍历 II
        log.info("BinaryTreeLevelOrderTraversalII: {}", new BinaryTreeLevelOrderTraversalII().levelOrderBottom(BinaryTreeLevelOrderTraversalII.example1()));
        log.info("BinaryTreeLevelOrderTraversalII: {}", new BinaryTreeLevelOrderTraversalII().levelOrderBottom(BinaryTreeLevelOrderTraversalII.example2()));
        log.info("BinaryTreeLevelOrderTraversalII: {}", new BinaryTreeLevelOrderTraversalII().levelOrderBottom(BinaryTreeLevelOrderTraversalII.example3()));
        // [108] 将有序数组转换为二叉搜索树
        log.info("ConvertSortedArrayToBinarySearchTree: {}", new ConvertSortedArrayToBinarySearchTree().sortedArrayToBST(new int[] {-10,-3,0,5,9}));
        log.info("ConvertSortedArrayToBinarySearchTree: {}", new ConvertSortedArrayToBinarySearchTree().sortedArrayToBST(new int[] {1,3}));
        // [109] 有序链表转换二叉搜索树
        log.info("ConvertSortedListToBinarySearchTree: {}", new ConvertSortedListToBinarySearchTree().sortedListToBST(ConvertSortedListToBinarySearchTree.example1()));
        log.info("ConvertSortedListToBinarySearchTree: {}", new ConvertSortedListToBinarySearchTree().sortedListToBST(ConvertSortedListToBinarySearchTree.example2()));
        // [110] 平衡二叉树
        log.info("BalancedBinaryTree: {}", new BalancedBinaryTree().isBalanced(BalancedBinaryTree.example1()));
        log.info("BalancedBinaryTree: {}", new BalancedBinaryTree().isBalanced(BalancedBinaryTree.example2()));
        log.info("BalancedBinaryTree: {}", new BalancedBinaryTree().isBalanced(BalancedBinaryTree.example3()));
        // [111] 二叉树的最小深度
        log.info("MinimumDepthOfBinaryTree: {}", new MinimumDepthOfBinaryTree().minDepth(MinimumDepthOfBinaryTree.example1()));
        log.info("MinimumDepthOfBinaryTree: {}", new MinimumDepthOfBinaryTree().minDepth(MinimumDepthOfBinaryTree.example2()));
    }
}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode() {}
    TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return printLayer();
    }

    private String printLayer() {
        StringBuilder res = new StringBuilder("[").append(this.val);
        List<TreeNode> layer = new ArrayList<>();
        if (left != null || right != null) {
            layer.add(left);
            layer.add(right);
        }
        while (!layer.isEmpty()) {
            List<TreeNode> nextLayer = new ArrayList<>();
            boolean hasNode = false;
            for (TreeNode curNode : layer) {
                if (curNode != null) {
                    nextLayer.add(curNode.left);
                    nextLayer.add(curNode.right);
                    res.append(", ").append(curNode.val);
                    if (curNode.left != null || curNode.right != null) {
                        hasNode = true;
                    }
                } else {
                    res.append(", null");
                }
            }
            layer = hasNode ? nextLayer : new ArrayList<>();
        }
        res.append("]");
        return res.toString();
    }
}


/**
 * @lc app=leetcode.cn id=94 lang=java
 *
 * [94] 二叉树的中序遍历
 *
 * https://leetcode.cn/problems/binary-tree-inorder-traversal/description/
 *
 * algorithms
 * Easy (76.20%)
 * Likes:    1865
 * Dislikes: 0
 * Total Accepted:    1.2M
 * Total Submissions: 1.6M
 * Testcase Example:  '[1,null,2,3]'
 *
 * 给定一个二叉树的根节点 root ，返回 它的 中序 遍历 。
 *
 * 示例 1：
 * 输入：root = [1,null,2,3]
 * 输出：[1,3,2]
 *
 * 示例 2：
 * 输入：root = []
 * 输出：[]
 *
 * 示例 3：
 * 输入：root = [1]
 * 输出：[1]
 *
 * 提示：
 * 树中节点数目在范围 [0, 100] 内
 * -100 <= Node.val <= 100
 *
 * 进阶: 递归算法很简单，你可以通过迭代算法完成吗？
 */
class BinaryTreeInorderTraversal {
    /* 回溯算法思路 */
    List<Integer> res = new ArrayList<>();

    // 返回前序遍历结果
    public List<Integer> inorderTraversal(TreeNode root) {
        traverse(root);
        return res;
    }

    // 二叉树遍历函数
    void traverse(TreeNode root) {
        if (root == null) {
            return;
        }
        traverse(root.left);
        // 中序遍历位置
        res.add(root.val);
        traverse(root.right);
    }

    /* 动态规划思路 */
    // 定义：输入一个节点，返回以该节点为根的二叉树的中序遍历结果
    public List<Integer> inorderTraversal2(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        res.addAll(inorderTraversal(root.left));
        res.add(root.val);
        res.addAll(inorderTraversal(root.right));
        return res;
    }

    public static TreeNode example1() {
        return new TreeNode(1, null, new TreeNode(2, new TreeNode(3), null));
    }
    public static TreeNode example2() {
        return new TreeNode(1);
    }
}


/**
 * @lc app=leetcode.cn id=95 lang=java
 *
 * [95] 不同的二叉搜索树 II
 *
 * https://leetcode.cn/problems/unique-binary-search-trees-ii/description/
 *
 * algorithms
 * Medium (73.37%)
 * Likes:    1453
 * Dislikes: 0
 * Total Accepted:    171.6K
 * Total Submissions: 233.6K
 * Testcase Example:  '3'
 *
 * 给你一个整数 n ，请你生成并返回所有由 n 个节点组成且节点值从 1 到 n 互不相同的不同 二叉搜索树 。可以按 任意顺序 返回答案。
 *
 * 示例 1：
 * 输入：n = 3
 * 输出：[[1,null,2,null,3],[1,null,3,2],[2,1,3],[3,1,null,null,2],[3,2,null,1]]
 *
 * 示例 2：
 * 输入：n = 1
 * 输出：[[1]]
 *
 * 提示：
 * 1 <= n <= 8
 */
class UniqueBinarySearchTreesII {
    public List<TreeNode> generateTrees(int n) {
        if (n == 0) {
            return new ArrayList<>();
        }
        // 构造闭区间 [1, n] 组成的 BST
        return build(1, n);
    }

    private List<TreeNode> build(int lo, int hi) {
        List<TreeNode> res = new ArrayList<>();
        // base case
        if (lo > hi) {
            res.add(null);
            return res;
        }

        // 穷举 root 节点的所有可能
        for (int i = lo; i <= hi; i++) {
            // 递归构造出左右子树的所有合法 BST
            List<TreeNode> leftList = build(lo, i - 1);
            List<TreeNode> rightList = build(i + 1, hi);
            // 给 root 节点穷举所有左右子树的组合
            for (TreeNode left : leftList) {
                for (TreeNode right : rightList) {
                    // i 作为根节点 root 的值
                    TreeNode root = new TreeNode(i, left, right);
                    res.add(root);
                }
            }
        }
        return res;
    }
}


/**
 * @lc app=leetcode.cn id=96 lang=java
 *
 * [96] 不同的二叉搜索树
 *
 * https://leetcode.cn/problems/unique-binary-search-trees/description/
 *
 * algorithms
 * Medium (70.86%)
 * Likes:    2314
 * Dislikes: 0
 * Total Accepted:    379.9K
 * Total Submissions: 536.2K
 * Testcase Example:  '3'
 *
 * 给你一个整数 n ，求恰由 n 个节点组成且节点值从 1 到 n 互不相同的 二叉搜索树 有多少种？返回满足题意的二叉搜索树的种数。
 *
 * 示例 1：
 * 输入：n = 3
 * 输出：5
 *
 * 示例 2：
 * 输入：n = 1
 * 输出：1
 *
 * 提示：
 * 1 <= n <= 19
 */
class UniqueBinarySearchTrees {
    int numTrees(int n) {
        // 备忘录的值初始化为 0
        int[][] memo = new int[n + 1][n + 1];
        return count(1, n, memo);
    }

    int count(int lo, int hi, int[][] memo) {
        // 注意这里是 >=, 这里 > 也可以, 但是会多进行一次递归
        if (lo >= hi) return 1;
        // 查备忘录
        if (memo[lo][hi] != 0) {
            return memo[lo][hi];
        }

        int res = 0;
        for (int mid = lo; mid <= hi; mid++) {
            int left = count(lo, mid - 1, memo);
            int right = count(mid + 1, hi, memo);
            res += left * right;
        }
        // 将结果存入备忘录
        memo[lo][hi] = res;

        return res;
    }

    public int numTrees2(int n) {
        return build(1, n);
    }

    private int build(int lo, int hi) {
        // 注意这里是 >=, 这里 > 也可以, 但是会多进行一次递归
        if (lo >= hi) {
            return 1;
        }

        // 穷举 root 节点的所有可能
        int res = 0;
        for (int i = lo; i <= hi; i++) {
            // 递归计算出左右子树的所有合法 BST
            int left = build(lo, i - 1);
            int right = build(i + 1, hi);
            res += left * right;
        }
        return res;
    }
}


/**
 * @lc app=leetcode.cn id=98 lang=java
 *
 * [98] 验证二叉搜索树
 *
 * https://leetcode.cn/problems/validate-binary-search-tree/description/
 *
 * algorithms
 * Medium (37.06%)
 * Likes:    2100
 * Dislikes: 0
 * Total Accepted:    750.4K
 * Total Submissions: 2M
 * Testcase Example:  '[2,1,3]'
 *
 * 给你一个二叉树的根节点 root ，判断其是否是一个有效的二叉搜索树。
 * 有效 二叉搜索树定义如下：
 * 节点的左子树只包含 小于 当前节点的数。
 * 节点的右子树只包含 大于 当前节点的数。
 * 所有左子树和右子树自身必须也是二叉搜索树。
 *
 * 示例 1：
 * 输入：root = [2,1,3]
 * 输出：true
 *
 * 示例 2：
 * 输入：root = [5,1,4,null,null,3,6]
 * 输出：false
 * 解释：根节点的值是 5 ，但是右子节点的值是 4 。
 *
 * 提示：
 * 树中节点数目范围在[1, 10^4] 内
 * -2^31 <= Node.val <= 2^31 - 1
 */
class ValidateBinarySearchTree {
    public boolean isValidBST(TreeNode root) {
        return isValidBST(root, null, null);
    }

    /* 限定以 root 为根的子树节点必须满足 max.val > root.val > min.val */
    boolean isValidBST(TreeNode root, TreeNode min, TreeNode max) {
        // base case
        if (root == null) return true;
        // 若 root.val 不符合 max 和 min 的限制，说明不是合法 BST
        if (min != null && root.val <= min.val) return false;
        if (max != null && root.val >= max.val) return false;
        // 限定左子树的最大值是 root.val，右子树的最小值是 root.val
        return isValidBST(root.left, min, root)
                && isValidBST(root.right, root, max);
    }


    public static TreeNode example1() {
        return new TreeNode(2, new TreeNode(1), new TreeNode(3));
    }
    public static TreeNode example2() {
        return new TreeNode(5, new TreeNode(1), new TreeNode(4, new TreeNode(3), new TreeNode(6)));
    }
    public static TreeNode example3() {
        return new TreeNode(2, new TreeNode(2), new TreeNode(2));
    }
    public static TreeNode example4() {
        return new TreeNode(0);
    }
}


/**
 * @lc app=leetcode.cn id=99 lang=java
 *
 * [99] 恢复二叉搜索树
 *
 * https://leetcode.cn/problems/recover-binary-search-tree/description/
 *
 * algorithms
 * Medium (60.26%)
 * Likes:    870
 * Dislikes: 0
 * Total Accepted:    138.6K
 * Total Submissions: 229.9K
 * Testcase Example:  '[1,3,null,null,2]'
 *
 * 给你二叉搜索树的根节点 root ，该树中的 恰好 两个节点的值被错误地交换。请在不改变其结构的情况下，恢复这棵树 。
 *
 * 示例 1：
 * 输入：root = [1,3,null,null,2]
 * 输出：[3,1,null,null,2]
 * 解释：3 不能是 1 的左孩子，因为 3 > 1 。交换 1 和 3 使二叉搜索树有效。
 *
 * 示例 2：
 * 输入：root = [3,1,4,null,null,2]
 * 输出：[2,1,4,null,null,3]
 * 解释：2 不能在 3 的右子树中，因为 2 < 3 。交换 2 和 3 使二叉搜索树有效。
 *
 * 提示：
 * 树上节点的数目在范围 [2, 1000] 内
 * -2^31 <= Node.val <= 2^31 - 1
 *
 * 进阶：使用 O(n) 空间复杂度的解法很容易实现。你能想出一个只使用 O(1) 空间的解决方案吗？
 */
class RecoverBinarySearchTree {
    // 遍历过程中判断，空间复杂度O(1)
    public void recoverTree(TreeNode root) {
        TreeNode[] swapNodes = new TreeNode[2];
        TreeNode[] prev = {null};

        // 中序遍历二叉搜索树，找到错误交换的节点
        inorderTraversal(root, prev, swapNodes);

        // 交换错误节点的值，恢复二叉搜索树
        int temp = swapNodes[0].val;
        swapNodes[0].val = swapNodes[1].val;
        swapNodes[1].val = temp;
    }

    private void inorderTraversal(TreeNode node, TreeNode[] prev, TreeNode[] swapNodes) {
        if (node == null) {
            return;
        }

        inorderTraversal(node.left, prev, swapNodes);

        // 检查当前节点和前一个节点的值是否逆序，找到错误交换的节点
        if (prev[0] != null && prev[0].val > node.val) {
            // 当遍历到第一个逆序节点时，swapNodes[0] 尚未被赋值，所以将第一个逆序节点赋值给 swapNodes[0]
            // 随后，在遍历的过程中，如果又遇到逆序节点，则将第二个逆序节点赋值给 swapNodes[1]
            if (swapNodes[0] == null) {
                swapNodes[0] = prev[0];
            }
            swapNodes[1] = node;
        }

        prev[0] = node;

        inorderTraversal(node.right, prev, swapNodes);
    }

    // 解法2：用list存储中序遍历结果，然后再判断，空间复杂度O(n)，不推荐
    public void recoverTree2(TreeNode root) {
        List<TreeNode> list = new ArrayList<>();
        dfs(root, list);
        TreeNode x = null;
        TreeNode y = null;
        // 扫面遍历的结果，找出可能存在错误交换的节点x和y
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).val > list.get(i + 1).val) {
                y = list.get(i + 1);
                if (x == null) {
                    x = list.get(i);
                }
            }
        }
        // 如果x和y不为空，则交换这两个节点值，恢复二叉搜索树
        if (x != null && y != null) {
            int tmp = x.val;
            x.val = y.val;
            y.val = tmp;
        }
    }

    // 中序遍历二叉树，并将遍历的结果保存到list中
    private void dfs(TreeNode node, List<TreeNode> list) {
        if (node == null) {
            return;
        }
        dfs(node.left, list);
        list.add(node);
        dfs(node.right, list);
    }


    public static TreeNode example1() {
        return new TreeNode(1, new TreeNode(3, null, new TreeNode(2)), null);
    }
    public static TreeNode example2() {
        return new TreeNode(3, new TreeNode(1), new TreeNode(4, new TreeNode(2), null));
    }
}


/**
 * @lc app=leetcode.cn id=100 lang=java
 *
 * [100] 相同的树
 *
 * https://leetcode.cn/problems/same-tree/description/
 *
 * algorithms
 * Easy (60.01%)
 * Likes:    1047
 * Dislikes: 0
 * Total Accepted:    474.5K
 * Total Submissions: 790.3K
 * Testcase Example:  '[1,2,3]\n[1,2,3]'
 *
 * 给你两棵二叉树的根节点 p 和 q ，编写一个函数来检验这两棵树是否相同。
 * 如果两个树在结构上相同，并且节点具有相同的值，则认为它们是相同的。
 *
 * 示例 1：
 * 输入：p = [1,2,3], q = [1,2,3]
 * 输出：true
 *
 * 示例 2：
 * 输入：p = [1,2], q = [1,null,2]
 * 输出：false
 *
 * 示例 3：
 * 输入：p = [1,2,1], q = [1,1,2]
 * 输出：false
 *
 * 提示：
 * 两棵树上的节点数目都在范围 [0, 100] 内
 * -10^4
 */
class SameTree {
    public boolean isSameTree(TreeNode p, TreeNode q) {
        // 都是null，判断相同
        if (p == null && q == null) {
            return true;
        }
        // 只有一个是null，返回不同
        if (p == null || q == null) {
            return false;
        }
        // 值不同，返回不同
        if (p.val != q.val) {
            return false;
        }
        return isSameTree(p.left, q.left)
                && isSameTree(p.right, q.right);
    }


    public static TreeNode example1() {
        return new TreeNode(1, new TreeNode(2), new TreeNode(3));
    }
    public static TreeNode example2() {
        return new TreeNode(1, new TreeNode(2), null);
    }
    public static TreeNode example3() {
        return new TreeNode(1, null, new TreeNode(2));
    }
    public static TreeNode example4() {
        return new TreeNode(1, new TreeNode(2), new TreeNode(1));
    }
    public static TreeNode example5() {
        return new TreeNode(1, new TreeNode(1), new TreeNode(2));
    }
}


/**
 * @lc app=leetcode.cn id=101 lang=java
 *
 * [101] 对称二叉树
 *
 * https://leetcode.cn/problems/symmetric-tree/description/
 *
 * algorithms
 * Easy (58.89%)
 * Likes:    2490
 * Dislikes: 0
 * Total Accepted:    867.8K
 * Total Submissions: 1.5M
 * Testcase Example:  '[1,2,2,3,4,4,3]'
 *
 * 给你一个二叉树的根节点 root ， 检查它是否轴对称。
 *
 * 示例 1：
 * 输入：root = [1,2,2,3,4,4,3]
 * 输出：true
 *
 * 示例 2：
 * 输入：root = [1,2,2,null,3,null,3]
 * 输出：false
 *
 * 提示：
 * 树中节点数目在范围 [1, 1000] 内
 * -100 <= Node.val <= 100
 *
 * 进阶：你可以运用递归和迭代两种方法解决这个问题吗？
 */
class SymmetricTree {
    public boolean isSymmetric(TreeNode root) {
        // 空节点
        if (root == null) return true;
        // 判断左右节点
        return isSymmetric(root.left, root.right);
    }

    public boolean isSymmetric(TreeNode left, TreeNode right) {
        // 都为空
        if (left == null && right == null) return true;
        // 有一个不为空
        if (left == null || right == null) return false;
        // 都不为空
        // 值不相等
        if (left.val != right.val) {
            return false;
        }
        // 进入下一层对比
        return isSymmetric(left.left, right.right)
                && isSymmetric(left.right, right.left);
    }


    public static TreeNode example1() {
        return new TreeNode(1, new TreeNode(2, new TreeNode(3), new TreeNode(4)), new TreeNode(2, new TreeNode(4), new TreeNode(3)));
    }
    public static TreeNode example2() {
        return new TreeNode(1, new TreeNode(2, null, new TreeNode(3)), new TreeNode(2, null, new TreeNode(3)));
    }
}


/**
 * @lc app=leetcode.cn id=102 lang=java
 *
 * [102] 二叉树的层序遍历
 *
 * https://leetcode.cn/problems/binary-tree-level-order-traversal/description/
 *
 * algorithms
 * Medium (65.68%)
 * Likes:    1752
 * Dislikes: 0
 * Total Accepted:    852K
 * Total Submissions: 1.3M
 * Testcase Example:  '[3,9,20,null,null,15,7]'
 *
 * 给你二叉树的根节点 root ，返回其节点值的 层序遍历 。 （即逐层地，从左到右访问所有节点）。
 *
 * 示例 1：
 * 输入：root = [3,9,20,null,null,15,7]
 * 输出：[[3],[9,20],[15,7]]
 *
 * 示例 2：
 * 输入：root = [1]
 * 输出：[[1]]
 *
 * 示例 3：
 * 输入：root = []
 * 输出：[]
 *
 * 提示：
 * 树中节点数目在范围 [0, 2000] 内
 * -1000 <= Node.val <= 1000
 */
class BinaryTreeLevelOrderTraversal {
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) return res;

        List<TreeNode> layer = new ArrayList<>();
        layer.add(root);
        while (!layer.isEmpty()) {
            List<TreeNode> nextLayer = new ArrayList<>();
            boolean hasNode = false;
            List<Integer> curLayer = new ArrayList<>();
            for (TreeNode curNode : layer) {
                if (curNode != null) {
                    nextLayer.add(curNode.left);
                    nextLayer.add(curNode.right);
                    curLayer.add(curNode.val);
                    if (curNode.left != null || curNode.right != null) {
                        hasNode = true;
                    }
                }
            }
            res.add(curLayer);
            layer = hasNode ? nextLayer : new ArrayList<>();
        }
        return res;
    }


    public static TreeNode example1() {
        return new TreeNode(3, new TreeNode(9), new TreeNode(20, new TreeNode(15), new TreeNode(7)));
    }
    public static TreeNode example2() {
        return new TreeNode(1);
    }
    public static TreeNode example3() {
        return null;
    }
}


/**
 * @lc app=leetcode.cn id=103 lang=java
 *
 * [103] 二叉树的锯齿形层序遍历
 *
 * https://leetcode.cn/problems/binary-tree-zigzag-level-order-traversal/description/
 *
 * algorithms
 * Medium (57.54%)
 * Likes:    792
 * Dislikes: 0
 * Total Accepted:    313.6K
 * Total Submissions: 544.9K
 * Testcase Example:  '[3,9,20,null,null,15,7]'
 *
 * 给你二叉树的根节点 root ，返回其节点值的 锯齿形层序遍历 。（即先从左往右，再从右往左进行下一层遍历，以此类推，层与层之间交替进行）。
 *
 * 示例 1：
 * 输入：root = [3,9,20,null,null,15,7]
 * 输出：[[3],[20,9],[15,7]]
 *
 * 示例 2：
 * 输入：root = [1]
 * 输出：[[1]]
 *
 * 示例 3：
 * 输入：root = []
 * 输出：[]
 *
 * 提示：
 * 树中节点数目在范围 [0, 2000] 内
 * -100 <= Node.val <= 100
 */
class BinaryTreeZigzagLevelOrderTraversal {
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) return res;

        List<TreeNode> layer = new ArrayList<>();
        layer.add(root);
        boolean reverse = true;
        while (!layer.isEmpty()) {
            List<TreeNode> nextLayer = new ArrayList<>();
            boolean hasNode = false;
            reverse ^= true;
            List<Integer> curLayer = new ArrayList<>();
            for (int i = layer.size() - 1; i >= 0; i--) {
                TreeNode curNode = layer.get(i);
                if (curNode != null) {
                    if (!reverse) {
                        nextLayer.add(curNode.left);
                        nextLayer.add(curNode.right);
                    } else {
                        nextLayer.add(curNode.right);
                        nextLayer.add(curNode.left);
                    }
                    curLayer.add(curNode.val);
                    if (curNode.left != null || curNode.right != null) {
                        hasNode = true;
                    }
                }
            }
            res.add(curLayer);
            layer = hasNode ? nextLayer : new ArrayList<>();
        }
        return res;
    }


    public static TreeNode example1() {
        return new TreeNode(3, new TreeNode(9), new TreeNode(20, new TreeNode(15), new TreeNode(7)));
    }
    public static TreeNode example2() {
        return new TreeNode(1);
    }
    public static TreeNode example3() {
        return null;
    }
}


/**
 * @lc app=leetcode.cn id=104 lang=java
 *
 * [104] 二叉树的最大深度
 *
 * https://leetcode.cn/problems/maximum-depth-of-binary-tree/description/
 *
 * algorithms
 * Easy (77.12%)
 * Likes:    1658
 * Dislikes: 0
 * Total Accepted:    1.1M
 * Total Submissions: 1.4M
 * Testcase Example:  '[3,9,20,null,null,15,7]'
 *
 * 给定一个二叉树 root ，返回其最大深度。
 * 二叉树的 最大深度 是指从根节点到最远叶子节点的最长路径上的节点数。
 *
 * 示例 1：
 * 输入：root = [3,9,20,null,null,15,7]
 * 输出：3
 *
 * 示例 2：
 * 输入：root = [1,null,2]
 * 输出：2
 *
 * 提示：
 * 树中节点的数量在 [0, 10^4] 区间内。
 * -100 <= Node.val <= 100
 */
class MaximumDepthOfBinaryTree {
    public int maxDepth(TreeNode root) {
        return maxDepth(root, 0);
    }

    public int maxDepth(TreeNode root, int depth) {
        if (root == null) {
            return depth;
        }
        depth += 1;

        return Math.max(maxDepth(root.left, depth), maxDepth(root.right, depth));
    }


    public static TreeNode example1() {
        return new TreeNode(3, new TreeNode(9), new TreeNode(20, new TreeNode(15), new TreeNode(7)));
    }
    public static TreeNode example2() {
        return new TreeNode(1, null, new TreeNode(2));
    }
}


/**
 * @lc app=leetcode.cn id=105 lang=java
 *
 * [105] 从前序与中序遍历序列构造二叉树
 *
 * https://leetcode.cn/problems/construct-binary-tree-from-preorder-and-inorder-traversal/description/
 *
 * algorithms
 * Medium (71.24%)
 * Likes:    2036
 * Dislikes: 0
 * Total Accepted:    518K
 * Total Submissions: 727.1K
 * Testcase Example:  '[3,9,20,15,7]\n[9,3,15,20,7]'
 *
 * 给定两个整数数组 preorder 和 inorder ，其中 preorder 是二叉树的先序遍历， inorder
 * 是同一棵树的中序遍历，请构造二叉树并返回其根节点。
 *
 * 示例 1:
 * 输入: preorder = [3,9,20,15,7], inorder = [9,3,15,20,7]
 * 输出: [3,9,20,null,null,15,7]
 *
 * 示例 2:
 * 输入: preorder = [-1], inorder = [-1]
 * 输出: [-1]
 *
 * 提示:
 * 1 <= preorder.length <= 3000
 * inorder.length == preorder.length
 * -3000 <= preorder[i], inorder[i] <= 3000
 * preorder 和 inorder 均 无重复 元素
 * inorder 均出现在 preorder
 * preorder 保证 为二叉树的前序遍历序列
 * inorder 保证 为二叉树的中序遍历序列
 */
class ConstructBinaryTreeFromPreorderAndInorderTraversal {
    // 存储 inorder 中值到索引的映射
    Map<Integer, Integer> valToIndex = new HashMap<>();

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        for (int i = 0; i < inorder.length; i++) {
            valToIndex.put(inorder[i], i);
        }
        return build(preorder, 0, preorder.length - 1,
                inorder, 0, inorder.length - 1);
    }

    /*
     * 定义：前序遍历数组为 preorder[preStart..preEnd]，
     * 中序遍历数组为 inorder[inStart..inEnd]，
     * 构造这个二叉树并返回该二叉树的根节点
     */
    TreeNode build(int[] preorder, int preStart, int preEnd,
                   int[] inorder, int inStart, int inEnd) {
        if (preStart > preEnd) {
            return null;
        }

        // root 节点对应的值就是前序遍历数组的第一个元素
        int rootVal = preorder[preStart];
        // rootVal 在中序遍历数组中的索引
        int index = valToIndex.get(rootVal);

        int leftSize = index - inStart;

        // 先构造出当前根节点
        TreeNode root = new TreeNode(rootVal);


        // 递归构造左右子树
        root.left = build(preorder, preStart + 1, preStart + leftSize,
                inorder, inStart, index - 1);

        root.right = build(preorder, preStart + leftSize + 1, preEnd,
                inorder, index + 1, inEnd);
        return root;
    }
}


/**
 * @lc app=leetcode.cn id=106 lang=java
 *
 * [106] 从中序与后序遍历序列构造二叉树
 *
 * https://leetcode.cn/problems/construct-binary-tree-from-inorder-and-postorder-traversal/description/
 *
 * algorithms
 * Medium (72.03%)
 * Likes:    1071
 * Dislikes: 0
 * Total Accepted:    299.3K
 * Total Submissions: 415.6K
 * Testcase Example:  '[9,3,15,20,7]\n[9,15,7,20,3]'
 *
 * 给定两个整数数组 inorder 和 postorder ，其中 inorder 是二叉树的中序遍历， postorder
 * 是同一棵树的后序遍历，请你构造并返回这颗 二叉树 。
 *
 * 示例 1:
 * 输入：inorder = [9,3,15,20,7], postorder = [9,15,7,20,3]
 * 输出：[3,9,20,null,null,15,7]
 *
 * 示例 2:
 * 输入：inorder = [-1], postorder = [-1]
 * 输出：[-1]
 *
 * 提示:
 * 1 <= inorder.length <= 3000
 * postorder.length == inorder.length
 * -3000 <= inorder[i], postorder[i] <= 3000
 * inorder 和 postorder 都由 不同 的值组成
 * postorder 中每一个值都在 inorder 中
 * inorder 保证是树的中序遍历
 * postorder 保证是树的后序遍历
 */
class ConstructBinaryTreeFromInorderAndPostorderTraversal {
    // 存储 inorder 中值到索引的映射
    HashMap<Integer, Integer> valToIndex = new HashMap<>();

    public TreeNode buildTree(int[] inorder, int[] postorder) {
        for (int i = 0; i < inorder.length; i++) {
            valToIndex.put(inorder[i], i);
        }
        return build(inorder, 0, inorder.length - 1,
                postorder, 0, postorder.length - 1);
    }

    /*
       定义：
       中序遍历数组为 inorder[inStart..inEnd]，
       后序遍历数组为 postorder[postStart..postEnd]，
       构造这个二叉树并返回该二叉树的根节点
    */
    TreeNode build(int[] inorder, int inStart, int inEnd,
                   int[] postorder, int postStart, int postEnd) {

        if (inStart > inEnd) {
            return null;
        }
        // root 节点对应的值就是后序遍历数组的最后一个元素
        int rootVal = postorder[postEnd];
        // rootVal 在中序遍历数组中的索引
        int index = valToIndex.get(rootVal);
        // 左子树的节点个数
        int leftSize = index - inStart;
        TreeNode root = new TreeNode(rootVal);


        // 递归构造左右子树
        root.left = build(inorder, inStart, index - 1,
                postorder, postStart, postStart + leftSize - 1);

        root.right = build(inorder, index + 1, inEnd,
                postorder, postStart + leftSize, postEnd - 1);
        return root;
    }
}


/**
 * @lc app=leetcode.cn id=107 lang=java
 *
 * [107] 二叉树的层序遍历 II
 *
 * https://leetcode.cn/problems/binary-tree-level-order-traversal-ii/description/
 *
 * algorithms
 * Medium (72.56%)
 * Likes:    708
 * Dislikes: 0
 * Total Accepted:    282.9K
 * Total Submissions: 389.5K
 * Testcase Example:  '[3,9,20,null,null,15,7]'
 *
 * 给你二叉树的根节点 root ，返回其节点值 自底向上的层序遍历 。 （即按从叶子节点所在层到根节点所在的层，逐层从左向右遍历）
 *
 * 示例 1：
 * 输入：root = [3,9,20,null,null,15,7]
 * 输出：[[15,7],[9,20],[3]]
 *
 * 示例 2：
 * 输入：root = [1]
 * 输出：[[1]]
 *
 * 示例 3：
 * 输入：root = []
 * 输出：[]
 *
 * 提示：
 * 树中节点数目在范围 [0, 2000] 内
 * -1000 <= Node.val <= 1000
 */
class BinaryTreeLevelOrderTraversalII {
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) return res;

        List<TreeNode> layer = new ArrayList<>();
        layer.add(root);
        while (!layer.isEmpty()) {
            List<TreeNode> nextLayer = new ArrayList<>();
            boolean hasNode = false;
            List<Integer> curLayer = new ArrayList<>();
            for (TreeNode curNode : layer) {
                if (curNode != null) {
                    nextLayer.add(curNode.left);
                    nextLayer.add(curNode.right);
                    curLayer.add(curNode.val);
                    if (curNode.left != null || curNode.right != null) {
                        hasNode = true;
                    }
                }
            }
            // 与自上而下遍历不同的是，这里每次加到队首
            res.add(0, curLayer);
            layer = hasNode ? nextLayer : new ArrayList<>();
        }
        return res;
    }


    public static TreeNode example1() {
        return new TreeNode(3, new TreeNode(9), new TreeNode(20, new TreeNode(15), new TreeNode(7)));
    }
    public static TreeNode example2() {
        return new TreeNode(1);
    }
    public static TreeNode example3() {
        return null;
    }
}


/**
 * @lc app=leetcode.cn id=108 lang=java
 *
 * [108] 将有序数组转换为二叉搜索树
 *
 * https://leetcode.cn/problems/convert-sorted-array-to-binary-search-tree/description/
 *
 * algorithms
 * Easy (77.50%)
 * Likes:    1363
 * Dislikes: 0
 * Total Accepted:    377.9K
 * Total Submissions: 487.3K
 * Testcase Example:  '[-10,-3,0,5,9]'
 *
 * 给你一个整数数组 nums ，其中元素已经按 升序 排列，请你将其转换为一棵 高度平衡 二叉搜索树。
 * 高度平衡 二叉树是一棵满足「每个节点的左右两个子树的高度差的绝对值不超过 1 」的二叉树。
 *
 * 示例 1：
 * 输入：nums = [-10,-3,0,5,9]
 * 输出：[0,-3,9,-10,null,5]
 * 解释：[0,-10,5,null,-3,null,9] 也将被视为正确答案：
 *
 * 示例 2：
 * 输入：nums = [1,3]
 * 输出：[3,1]
 * 解释：[1,null,3] 和 [3,1] 都是高度平衡二叉搜索树。
 *
 * 提示：
 * 1 <= nums.length <= 10^4
 * -10^4 <= nums[i] <= 10^4
 * nums 按 严格递增 顺序排列
 */
class ConvertSortedArrayToBinarySearchTree {
    public TreeNode sortedArrayToBST(int[] nums) {
        // 数组为 null，直接返回
        if (nums == null) return null;
        return sortedArrayToBST(nums, 0, nums.length - 1);
    }

    public TreeNode sortedArrayToBST(int[] nums, int start, int end) {
        // 判断临界点
        if (start > end) {
            return null;
        }
        // 找到根节点索引
        int rootIndex = start + (end - start) / 2;
        // 构建根节点
        TreeNode root = new TreeNode(nums[rootIndex]);
        root.left = sortedArrayToBST(nums, start, rootIndex - 1);
        root.right = sortedArrayToBST(nums, rootIndex + 1, end);
        return root;
    }
}


/**
 * @lc app=leetcode.cn id=109 lang=java
 *
 * [109] 有序链表转换二叉搜索树
 *
 * https://leetcode.cn/problems/convert-sorted-list-to-binary-search-tree/description/
 *
 * algorithms
 * Medium (76.46%)
 * Likes:    850
 * Dislikes: 0
 * Total Accepted:    149.4K
 * Total Submissions: 195.4K
 * Testcase Example:  '[-10,-3,0,5,9]'
 *
 * 给定一个单链表的头节点  head ，其中的元素 按升序排序 ，将其转换为高度平衡的二叉搜索树。
 * 本题中，一个高度平衡二叉树是指一个二叉树每个节点 的左右两个子树的高度差不超过 1。
 *
 * 示例 1:
 * 输入: head = [-10,-3,0,5,9]
 * 输出: [0,-3,9,-10,null,5]
 * 解释: 一个可能的答案是[0，-3,9，-10,null,5]，它表示所示的高度平衡的二叉搜索树。
 *
 * 示例 2:
 * 输入: head = []
 * 输出: []
 *
 * 提示:
 * head 中的节点数在[0, 2 * 10^4] 范围内
 * -10^5 <= Node.val <= 10^5
 */
class ConvertSortedListToBinarySearchTree {
    public TreeNode sortedListToBST(ListNode head) {
        List<Integer> values = new ArrayList<>();
        // 将有序链表的元素存储到数组中
        while (head != null) {
            values.add(head.val);
            head = head.next;
        }
        // 调用辅助函数，将有序数组转换为平衡的二叉搜索树
        return sortedArrayToBST(values, 0, values.size() - 1);
    }

    private TreeNode sortedArrayToBST(List<Integer> values, int left, int right) {
        // 递归结束条件，左索引大于右索引
        if (left > right) {
            return null;
        }
        // 计算中间索引
        int mid = left + (right - left) / 2;
        // 使用中间元素构建当前子树的根节点
        TreeNode root = new TreeNode(values.get(mid));
        // 递归处理左子数组，构建左子树
        root.left = sortedArrayToBST(values, left, mid - 1);
        // 递归处理右子数组，构建右子树
        root.right = sortedArrayToBST(values, mid + 1, right);
        return root;
    }


    public static ListNode example1() {
        return new ListNode(-10, new ListNode(-3, new ListNode(0, new ListNode(5, new ListNode(9)))));
    }
    public static ListNode example2() {
        return null;
    }
}


/**
 * @lc app=leetcode.cn id=110 lang=java
 *
 * [110] 平衡二叉树
 *
 * https://leetcode.cn/problems/balanced-binary-tree/description/
 *
 * algorithms
 * Easy (57.55%)
 * Likes:    1381
 * Dislikes: 0
 * Total Accepted:    527.8K
 * Total Submissions: 915.9K
 * Testcase Example:  '[3,9,20,null,null,15,7]'
 *
 * 给定一个二叉树，判断它是否是高度平衡的二叉树。
 * 本题中，一棵高度平衡二叉树定义为：
 * 一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过 1 。
 *
 * 示例 1：
 * 输入：root = [3,9,20,null,null,15,7]
 * 输出：true
 *
 * 示例 2：
 * 输入：root = [1,2,2,3,3,null,null,4,4]
 * 输出：false
 *
 * 示例 3：
 * 输入：root = []
 * 输出：true
 *
 * 提示：
 * 树中的节点数在范围 [0, 5000] 内
 * -10^4 <= Node.val <= 10^4
 */
class BalancedBinaryTree {
    public boolean isBalanced(TreeNode root) {
        if (root == null) {
            return true;
        }
        // 判断当前节点的左右子树高度差是否小于等于 1
        if (Math.abs(height(root.left) - height(root.right)) > 1) {
            return false;
        }
        // 递归判断左右子树是否平衡
        return isBalanced(root.left) && isBalanced(root.right);
    }

    // 计算树的高度
    private int height(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + Math.max(height(node.left), height(node.right));
    }


    public static TreeNode example1() {
        return new TreeNode(3, new TreeNode(9), new TreeNode(20, new TreeNode(15), new TreeNode(7)));
    }
    public static TreeNode example2() {
        return new TreeNode(1, new TreeNode(2, new TreeNode(3, new TreeNode(4), new TreeNode(4)), new TreeNode(3)), new TreeNode(2));
    }
    public static TreeNode example3() {
        return null;
    }
}


/**
 * @lc app=leetcode.cn id=111 lang=java
 *
 * [111] 二叉树的最小深度
 *
 * https://leetcode.cn/problems/minimum-depth-of-binary-tree/description/
 *
 * algorithms
 * Easy (52.28%)
 * Likes:    1058
 * Dislikes: 0
 * Total Accepted:    588K
 * Total Submissions: 1.1M
 * Testcase Example:  '[3,9,20,null,null,15,7]'
 *
 * 给定一个二叉树，找出其最小深度。
 * 最小深度是从根节点到最近叶子节点的最短路径上的节点数量。
 * 说明：叶子节点是指没有子节点的节点。
 *
 * 示例 1：
 * 输入：root = [3,9,20,null,null,15,7]
 * 输出：2
 *
 * 示例 2：
 * 输入：root = [2,null,3,null,4,null,5,null,6]
 * 输出：5
 *
 * 提示：
 * 树中节点数的范围在 [0, 10^5] 内
 * -1000 <= Node.val <= 1000
 */
class MinimumDepthOfBinaryTree {
    public int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        if (root.left == null) {
            return 1 + minDepth(root.right);
        }
        if (root.right == null) {
            return 1 + minDepth(root.left);
        }

        return 1 + Math.min(minDepth(root.left), minDepth(root.right));
    }


    public static TreeNode example1() {
        return new TreeNode(3, new TreeNode(9), new TreeNode(20, new TreeNode(15), new TreeNode(7)));
    }
    public static TreeNode example2() {
        return new TreeNode(2, null, new TreeNode(3, null, new TreeNode(4, null, new TreeNode(5, null, new TreeNode(6)))));
    }
}
