package test.algorithm.leetcode;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

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
// @lc code=start
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
// @lc code=end


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
// @lc code=start
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
// @lc code=end


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
// @lc code=start
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
// @lc code=end


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
// @lc code=start
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
// @lc code=end


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
// @lc code=start
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
// @lc code=end


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
// @lc code=start
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
// @lc code=end


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
// @lc code=start
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
// @lc code=end


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
// @lc code=start
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
// @lc code=end
