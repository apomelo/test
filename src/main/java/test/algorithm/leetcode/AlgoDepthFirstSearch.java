package test.algorithm.leetcode;

import lombok.extern.slf4j.Slf4j;

/**
 * 深度优先算法 DFS
 * @author C
 * @date 2023/11/14
 */
@Slf4j
public class AlgoDepthFirstSearch {
    public static void main(String[] args) {
        // [200] 岛屿数量
        log.info("NumberOfIslands: {}", new NumberOfIslands().numIslands(NumberOfIslands.example1()));
        log.info("NumberOfIslands: {}", new NumberOfIslands().numIslands(NumberOfIslands.example2()));
    }
}


/**
 * @lc app=leetcode.cn id=200 lang=java
 *
 * [200] 岛屿数量
 *
 * https://leetcode.cn/problems/number-of-islands/description/
 *
 * algorithms
 * Medium (59.69%)
 * Likes:    2348
 * Dislikes: 0
 * Total Accepted:    728.3K
 * Total Submissions: 1.2M
 * Testcase Example:  '[["1","1","1","1","0"],["1","1","0","1","0"],["1","1","0","0","0"],["0","0","0","0","0"]]'
 *
 * 给你一个由 '1'（陆地）和 '0'（水）组成的的二维网格，请你计算网格中岛屿的数量。
 * 岛屿总是被水包围，并且每座岛屿只能由水平方向和/或竖直方向上相邻的陆地连接形成。
 * 此外，你可以假设该网格的四条边均被水包围。
 *
 * 示例 1：
 * 输入：grid = [
 *   ["1","1","1","1","0"],
 *   ["1","1","0","1","0"],
 *   ["1","1","0","0","0"],
 *   ["0","0","0","0","0"]
 * ]
 * 输出：1
 *
 * 示例 2：
 * 输入：grid = [
 *   ["1","1","0","0","0"],
 *   ["1","1","0","0","0"],
 *   ["0","0","1","0","0"],
 *   ["0","0","0","1","1"]
 * ]
 * 输出：3
 *
 * 提示：
 * m == grid.length
 * n == grid[i].length
 * 1 <= m, n <= 300
 * grid[i][j] 的值为 '0' 或 '1'
 */
class NumberOfIslands {
    int[][] dir = new int[][] {{1,0}, {0,1}, {-1,0}, {0,-1}};

    // 主函数，计算岛屿数量
    public int numIslands(char[][] grid) {
        int res = 0;
        int m = grid.length, n = grid[0].length;
        // 遍历 grid
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '1') {
                    // 每发现一个岛屿，岛屿数量加一
                    res++;
                    // 然后使用 DFS 将岛屿淹了
                    dfs(grid, i, j);
                }
            }
        }
        return res;
    }

    // 从 (i, j) 开始，将与之相邻的陆地都变成海水
    void dfs(char[][] grid, int i, int j) {
        int m = grid.length, n = grid[0].length;
        if (i < 0 || j < 0 || i >= m || j >= n) {
            // 超出索引边界
            return;
        }
        if (grid[i][j] == '0') {
            // 已经是海水了
            return;
        }
        // 将 (i, j) 变成海水
        grid[i][j] = '0';
        // 淹没上下左右的陆地
        for (int[] ints : dir) {
            dfs(grid, i + ints[0], j + ints[1]);
        }
    }

    /**
     * 解法2
     */
    int[][] dir2 = new int[][] {{1,0}, {0,1}, {-1,0}, {0,-1}};
    public int numIslands2(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }

        int res = 0;
        int m = grid.length, n = grid[0].length;
        boolean[][] memo = new boolean[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (dfs2(grid, i, j, memo)) {
                    res++;
                }
            }
        }
        return res;
    }
    private boolean dfs2(char[][] grid, int i, int j, boolean[][] memo) {
        int m = grid.length, n = grid[0].length;
        if (i >= m || i < 0 || j >= n || j < 0) {
            return false;
        }
        // 遍历过
        if (memo[i][j]) {
            return false;
        }

        memo[i][j] = true;
        if (grid[i][j] != '1') {
            return false;
        }
        for (int[] ints : dir2) {
            dfs2(grid, i + ints[0], j + ints[1], memo);
        }
        return true;
    }

    public static char[][] example1() {
        return new char[][] {
                {'1','1','1','1','0'},
                {'1','1','0','1','0'},
                {'1','1','0','0','0'},
                {'0','0','0','0','0'}
        };
    }
    public static char[][] example2() {
        return new char[][] {
                {'1','1','0','0','0'},
                {'1','1','0','0','0'},
                {'0','0','1','0','0'},
                {'0','0','0','1','1'}
        };
    }
}
