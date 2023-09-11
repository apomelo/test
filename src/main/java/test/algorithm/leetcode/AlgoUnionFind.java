package test.algorithm.leetcode;

import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;

/**
 * Union Find 排序
 * @author C
 * @date 2023/2/1
 */
@Slf4j
public class AlgoUnionFind {
    public static void main(String[] args) {
        // [128] 最长连续序列
        log.info("longestConsecutive: {}", new LongestConsecutiveSequence().longestConsecutive(new int[] {100,4,200,1,3,2}));
        log.info("longestConsecutive: {}", new LongestConsecutiveSequence().longestConsecutive(new int[] {0,3,7,2,5,8,4,6,0,1}));
        // [130] 被围绕的区域
        char[][] surroundedRegionsExample1 = SurroundedRegions.example1();
        new SurroundedRegions().solve(surroundedRegionsExample1);
        log.info("SurroundedRegions: {}", (Object) surroundedRegionsExample1);
        char[][] surroundedRegionsExample2 = SurroundedRegions.example2();
        new SurroundedRegions().solve(surroundedRegionsExample2);
        log.info("SurroundedRegions: {}", (Object) surroundedRegionsExample2);
    }
}


/**
 * @lc app=leetcode.cn id=128 lang=java
 *
 * [128] 最长连续序列
 *
 * https://leetcode.cn/problems/longest-consecutive-sequence/description/
 *
 * algorithms
 * Medium (54.29%)
 * Likes:    1806
 * Dislikes: 0
 * Total Accepted:    439.9K
 * Total Submissions: 813.6K
 * Testcase Example:  '[100,4,200,1,3,2]'
 *
 * 给定一个未排序的整数数组 nums ，找出数字连续的最长序列（不要求序列元素在原数组中连续）的长度。
 * 请你设计并实现时间复杂度为 O(n) 的算法解决此问题。
 *
 * 示例 1：
 * 输入：nums = [100,4,200,1,3,2]
 * 输出：4
 * 解释：最长数字连续序列是 [1, 2, 3, 4]。它的长度为 4。
 *
 * 示例 2：
 * 输入：nums = [0,3,7,2,5,8,4,6,0,1]
 * 输出：9
 *
 * 提示：
 * 0 <= nums.length <= 10^5
 * -10^9 <= nums[i] <= 10^9
 */
class LongestConsecutiveSequence {
    /**
     * 利用 hash 空间换时间
     */
    public int longestConsecutive(int[] nums) {
        Set<Integer> dict = new HashSet<>();
        for (int num : nums) {
            dict.add(num);
        }

        int res = 0;
        for (int num : dict) {
            // 不是连续序列的第一个
            if (dict.contains(num - 1)) {
                continue;
            }
            // 遍历后续子序列
            int curLength = 1;
            while (dict.contains(++num)) {
                curLength++;
            }
            // 得到最大长度
            res = Math.max(curLength, res);
        }
        return res;
    }
}


/**
 * @lc app=leetcode.cn id=130 lang=java
 *
 * [130] 被围绕的区域
 *
 * https://leetcode.cn/problems/surrounded-regions/description/
 *
 * algorithms
 * Medium (46.27%)
 * Likes:    1014
 * Dislikes: 0
 * Total Accepted:    241.1K
 * Total Submissions: 520.8K
 * Testcase Example:  '[["X","X","X","X"],["X","O","O","X"],["X","X","O","X"],["X","O","X","X"]]'
 *
 * 给你一个 m x n 的矩阵 board ，由若干字符 'X' 和 'O' ，找到所有被 'X' 围绕的区域，并将这些区域里所有的 'O' 用 'X' 填充。
 *
 * 示例 1：
 * 输入：board =
 * [["X","X","X","X"],["X","O","O","X"],["X","X","O","X"],["X","O","X","X"]]
 * 输出：[["X","X","X","X"],["X","X","X","X"],["X","X","X","X"],["X","O","X","X"]]
 * 解释：被围绕的区间不会存在于边界上，换句话说，任何边界上的 'O' 都不会被填充为 'X'。 任何不在边界上，或不与边界上的 'O' 相连的 'O'
 * 最终都会被填充为 'X'。如果两个元素在水平或垂直方向相邻，则称它们是“相连”的。
 *
 * 示例 2：
 * 输入：board = [["X"]]
 * 输出：[["X"]]
 *
 * 提示：
 * m == board.length
 * n == board[i].length
 * 1 <= m, n <= 200
 * board[i][j] 为 'X' 或 'O'
 */
class SurroundedRegions {
    /**
     * 方法1
     * 首先标记与边界相连的 'O' 为特殊字符（如 ''），然后遍历整个矩阵，将剩余的 'O' 修改为 'X'，最后将特殊字符（''）恢复为 'O'
     */
    public void solve(char[][] board) {
        if (board == null || board.length == 0) {
            return;
        }

        int rows = board.length;
        int cols = board[0].length;

        // 遍历第一行和最后一行，将连接到边界的 'O' 标记为特殊字符，如 '*'
        for (int i = 0; i < rows; i++) {
            if (board[i][0] == 'O') {
                dfs(board, i, 0);
            }
            if (board[i][cols - 1] == 'O') {
                dfs(board, i, cols - 1);
            }
        }

        // 遍历第一列和最后一列，将连接到边界的 'O' 标记为特殊字符，如 '*'
        for (int j = 0; j < cols; j++) {
            if (board[0][j] == 'O') {
                dfs(board, 0, j);
            }
            if (board[rows - 1][j] == 'O') {
                dfs(board, rows - 1, j);
            }
        }

        // 遍历整个矩阵，将 'O' 改为 'X'，将 '*' 恢复为 'O'
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (board[i][j] == 'O') {
                    board[i][j] = 'X';
                } else if (board[i][j] == '*') {
                    board[i][j] = 'O';
                }
            }
        }
    }

    private void dfs(char[][] board, int row, int col) {
        int rows = board.length;
        int cols = board[0].length;

        if (row < 0 || row >= rows || col < 0 || col >= cols || board[row][col] != 'O') {
            return;
        }
        // 跳过已标记过的位置，避免重复标记
        if (board[row][col] == '*') {
            return;
        }

        board[row][col] = '*';

        // 上下左右四个方向继续DFS
        dfs(board, row - 1, col);
        dfs(board, row + 1, col);
        dfs(board, row, col - 1);
        dfs(board, row, col + 1);
    }

    /**
     * 方法2
     * 把所有靠边的 O 和一个虚拟节点 dummy 进行连通。
     * 然后再遍历整个 board，那些和 dummy 不连通的 O 就是被围绕的区域，需要被替换。
     */
    public void solve2(char[][] board) {
        if (board.length == 0) return;

        int m = board.length;
        int n = board[0].length;
        // 给 dummy 留一个额外位置
        UF uf = new UF(m * n + 1);
        int dummy = m * n;
        // 将首列和末列的 O 与 dummy 连通
        for (int i = 0; i < m; i++) {
            if (board[i][0] == 'O')
                uf.union(i * n, dummy);
            if (board[i][n - 1] == 'O')
                uf.union(i * n + n - 1, dummy);
        }
        // 将首行和末行的 O 与 dummy 连通
        for (int j = 0; j < n; j++) {
            if (board[0][j] == 'O')
                uf.union(j, dummy);
            if (board[m - 1][j] == 'O')
                uf.union(n * (m - 1) + j, dummy);
        }
        // 方向数组 d 是上下左右搜索的常用手法
        int[][] d = new int[][]{{1, 0}, {0, 1}, {0, -1}, {-1, 0}};
        for (int i = 1; i < m - 1; i++)
            for (int j = 1; j < n - 1; j++)
                if (board[i][j] == 'O')
                    // 将此 O 与上下左右的 O 连通
                    for (int k = 0; k < 4; k++) {
                        int x = i + d[k][0];
                        int y = j + d[k][1];
                        if (board[x][y] == 'O')
                            uf.union(x * n + y, i * n + j);
                    }
        // 所有不和 dummy 连通的 O，都要被替换
        for (int i = 1; i < m - 1; i++)
            for (int j = 1; j < n - 1; j++)
                if (!uf.connected(dummy, i * n + j))
                    board[i][j] = 'X';
    }

    class UF {
        // 记录连通分量个数
        private int count;
        // 存储若干棵树
        private int[] parent;
        // 记录树的“重量”
        private int[] size;

        public UF(int n) {
            this.count = n;
            parent = new int[n];
            size = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        /* 将 p 和 q 连通 */
        public void union(int p, int q) {
            int rootP = find(p);
            int rootQ = find(q);
            if (rootP == rootQ)
                return;

            // 小树接到大树下面，较平衡
            if (size[rootP] > size[rootQ]) {
                parent[rootQ] = rootP;
                size[rootP] += size[rootQ];
            } else {
                parent[rootP] = rootQ;
                size[rootQ] += size[rootP];
            }
            count--;
        }

        /* 判断 p 和 q 是否互相连通 */
        public boolean connected(int p, int q) {
            int rootP = find(p);
            int rootQ = find(q);
            // 处于同一棵树上的节点，相互连通
            return rootP == rootQ;
        }

        /* 返回节点 x 的根节点 */
        private int find(int x) {
            while (parent[x] != x) {
                // 进行路径压缩
                parent[x] = parent[parent[x]];
                x = parent[x];
            }
            return x;
        }

        public int count() {
            return count;
        }
    }


    public static char[][] example1() {
        return new char[][] {{'X','X','X','X'},{'X','O','O','X'},{'X','X','O','X'},{'X','O','X','X'}};
    }
    public static char[][] example2() {
        return new char[][] {{'X'}};
    }
}
