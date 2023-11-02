package test.algorithm.leetcode;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * 动态规划
 * @author C
 * @date 2023/2/1
 */
@Slf4j
public class AlgoDynamicProgramming {
    public static void main(String[] args) {
        // 最长有效括号
        log.info("LongestValidParentheses: {}", new LongestValidParentheses().longestValidParentheses("(()"));
        log.info("LongestValidParentheses: {}", new LongestValidParentheses().longestValidParentheses(")()())"));
        log.info("LongestValidParentheses: {}", new LongestValidParentheses().longestValidParentheses(""));
        // 不同路径
        log.info("UniquePaths: {}", new UniquePaths().uniquePaths(3, 7));
        log.info("UniquePaths: {}", new UniquePaths().uniquePaths(3, 2));
        log.info("UniquePaths: {}", new UniquePaths().uniquePaths(7, 3));
        log.info("UniquePaths: {}", new UniquePaths().uniquePaths(3, 3));
        // 不同路径 II
        log.info("UniquePathsII: {}", new UniquePathsII().uniquePathsWithObstacles(new int[][]{{0,0,0},{0,1,0},{0,0,0}}));
        log.info("UniquePathsII: {}", new UniquePathsII().uniquePathsWithObstacles(new int[][]{{0,1},{0,0}}));
        log.info("UniquePathsII: {}", new UniquePathsII().uniquePathsWithObstacles(new int[][]{{0,0}}));
        // 最小路径和
        log.info("MinimumPathSum: {}", new MinimumPathSum().minPathSum(new int[][]{{1,3,1},{1,5,1},{4,2,1}}));
        log.info("MinimumPathSum: {}", new MinimumPathSum().minPathSum(new int[][]{{1,2,3},{4,5,6}}));
        // 爬楼梯
        log.info("ClimbingStairs: {}", new ClimbingStairs().climbStairs(2));
        log.info("ClimbingStairs: {}", new ClimbingStairs().climbStairs(3));
        // 编辑距离
        log.info("EditDistance: {}", new EditDistance().minDistance("horse", "ros"));
        log.info("EditDistance: {}", new EditDistance().minDistance("intention", "execution"));
        // 解码方法
        log.info("DecodeWays: {}", new DecodeWays().numDecodings("12"));
        log.info("DecodeWays: {}", new DecodeWays().numDecodings("226"));
        log.info("DecodeWays: {}", new DecodeWays().numDecodings("06"));
        // [97] 交错字符串
        log.info("InterleavingString: {}", new InterleavingString().isInterleave("aabcc", "dbbca", "aadbbcbcac"));
        log.info("InterleavingString: {}", new InterleavingString().isInterleave("aabcc", "dbbca", "aadbbbaccc"));
        log.info("InterleavingString: {}", new InterleavingString().isInterleave("", "", ""));
        // [115] 不同的子序列
        log.info("DistinctSubsequences: {}", new DistinctSubsequences().numDistinct("rabbbit", "rabbit"));
        log.info("DistinctSubsequences: {}", new DistinctSubsequences().numDistinct("babgbag", "bag"));
        // [120] 三角形最小路径和
        log.info("Triangle: {}", new Triangle().minimumTotal(Triangle.example1()));
        log.info("Triangle: {}", new Triangle().minimumTotal(Triangle.example2()));
        // [121] 买卖股票的最佳时机
        log.info("BestTimeToBuyAndSellStock: {}", new BestTimeToBuyAndSellStock().maxProfit(new int[] {7,1,5,3,6,4}));
        log.info("BestTimeToBuyAndSellStock: {}", new BestTimeToBuyAndSellStock().maxProfit(new int[] {7,6,4,3,1}));
        // [122] 买卖股票的最佳时机 II
        log.info("BestTimeToBuyAndSellStockII: {}", new BestTimeToBuyAndSellStockII().maxProfit(new int[] {7,1,5,3,6,4}));
        log.info("BestTimeToBuyAndSellStockII: {}", new BestTimeToBuyAndSellStockII().maxProfit(new int[] {1,2,3,4,5}));
        log.info("BestTimeToBuyAndSellStockII: {}", new BestTimeToBuyAndSellStockII().maxProfit(new int[] {7,6,4,3,1}));
        // [123] 买卖股票的最佳时机 III
        log.info("BestTimeToBuyAndSellStockIII: {}", new BestTimeToBuyAndSellStockIII().maxProfit(new int[] {3,3,5,0,0,3,1,4}));
        log.info("BestTimeToBuyAndSellStockIII: {}", new BestTimeToBuyAndSellStockIII().maxProfit(new int[] {1,2,3,4,5}));
        log.info("BestTimeToBuyAndSellStockIII: {}", new BestTimeToBuyAndSellStockIII().maxProfit(new int[] {7,6,4,3,1}));
        log.info("BestTimeToBuyAndSellStockIII: {}", new BestTimeToBuyAndSellStockIII().maxProfit(new int[] {1}));
        // [132] 分割回文串 II
        log.info("PalindromePartitioningII: {}", new PalindromePartitioningII().minCut("aab"));
        log.info("PalindromePartitioningII: {}", new PalindromePartitioningII().minCut("a"));
        log.info("PalindromePartitioningII: {}", new PalindromePartitioningII().minCut("ab"));
        // [139] 单词拆分
        log.info("WordBreak: {}", new WordBreak().wordBreak("leetcode", Arrays.asList("leet", "code")));
        log.info("WordBreak: {}", new WordBreak().wordBreak("applepenapple", Arrays.asList("apple", "pen")));
        log.info("WordBreak: {}", new WordBreak().wordBreak("catsandog", Arrays.asList("cats", "dog", "sand", "and", "cat")));
        // [140] 单词拆分 II
        log.info("WordBreakII: {}", new WordBreakII().wordBreak("catsanddog", Arrays.asList("cat","cats","and","sand","dog")));
        log.info("WordBreakII: {}", new WordBreakII().wordBreak("pineapplepenapple", Arrays.asList("apple","pen","applepen","pine","pineapple")));
        log.info("WordBreakII: {}", new WordBreakII().wordBreak("catsandog", Arrays.asList("cats","dog","sand","and","cat")));
        // [152] 乘积最大子数组
        log.info("MaximumProductSubarray: {}", new MaximumProductSubarray().maxProduct(new int[] {2,3,-2,4}));
        log.info("MaximumProductSubarray: {}", new MaximumProductSubarray().maxProduct(new int[] {-2,0,-1}));
        // [174] 地下城游戏
        log.info("DungeonGame: {}", new DungeonGame().calculateMinimumHP(new int[][] {{-2,-3,3}, {-5,-10,1},{10,30,-5}}));
        log.info("DungeonGame: {}", new DungeonGame().calculateMinimumHP(new int[][] {{0}}));
    }
}


/**
 * @lc app=leetcode.cn id=32 lang=java
 *
 * [32] 最长有效括号
 *
 * https://leetcode.cn/problems/longest-valid-parentheses/description/
 *
 * algorithms
 * Hard (37.11%)
 * Likes:    2200
 * Dislikes: 0
 * Total Accepted:    358K
 * Total Submissions: 964.3K
 * Testcase Example:  '"(()"'
 *
 * 给你一个只包含 '(' 和 ')' 的字符串，找出最长有效（格式正确且连续）括号子串的长度。
 *
 * 示例 1：
 * 输入：s = "(()"
 * 输出：2
 * 解释：最长有效括号子串是 "()"
 *
 * 示例 2：
 * 输入：s = ")()())"
 * 输出：4
 * 解释：最长有效括号子串是 "()()"
 *
 * 示例 3：
 * 输入：s = ""
 * 输出：0
 *
 * 提示：
 * 0
 * s[i] 为 '(' 或 ')'
 */
class LongestValidParentheses {
    public int longestValidParentheses(String s) {
        if (s == null || s.length() == 0) return 0;
        Stack<Integer> stack = new Stack<>();
        // 记录以 s[i-1] 结尾的最长合法括号子串长度
        int[] dp = new int[s.length() + 1];

        for (int i = 0; i < s.length(); i ++) {
            if (s.charAt(i) == '(') {
                // 遇到左括号，记录索引
                stack.push(i);
                // 左括号不可能是合法括号子串的结尾
                dp[i + 1] = 0;
            } else {
                // 遇到右括号
                if (!stack.isEmpty()) {
                    // 配对的左括号对应索引
                    int leftIndex = stack.pop();
                    // 以这个右括号结尾的最长子串长度
                    int len = i + 1 - leftIndex + dp[leftIndex];
                    dp[i + 1] = len;
                } else {
                    // 没有配对的左括号
                    dp[i + 1] = 0;
                }
            }
        }
        // 计算最长子串的长度
        int res = 0;
        for (int j : dp) {
            res = Math.max(res, j);
        }
        return res;
    }
}


/**
 * @lc app=leetcode.cn id=62 lang=java
 *
 * [62] 不同路径
 *
 * https://leetcode.cn/problems/unique-paths/description/
 *
 * algorithms
 * Medium (67.72%)
 * Likes:    1774
 * Dislikes: 0
 * Total Accepted:    623.1K
 * Total Submissions: 919.9K
 * Testcase Example:  '3\n7'
 *
 * 一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为 “Start” ）。
 * 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为 “Finish” ）。
 * 问总共有多少条不同的路径？
 *
 * 示例 1：
 * 输入：m = 3, n = 7
 * 输出：28
 *
 * 示例 2：
 * 输入：m = 3, n = 2
 * 输出：3
 * 解释：
 * 从左上角开始，总共有 3 条路径可以到达右下角。
 * 1. 向右 -> 向下 -> 向下
 * 2. 向下 -> 向下 -> 向右
 * 3. 向下 -> 向右 -> 向下
 *
 * 示例 3：
 * 输入：m = 7, n = 3
 * 输出：28
 *
 * 示例 4：
 * 输入：m = 3, n = 3
 * 输出：6
 *
 * 提示：
 * 1 <= m, n <= 100
 * 题目数据保证答案小于等于 2 * 10^9
 */
class UniquePaths {
    public int uniquePaths(int m, int n) {
        if (m <= 1 || n <= 1) {
            return 1;
        }
        int[][] dp = new int[m][n];
        // 初始化状态
        // 初始化第一列
        for (int i = 0; i < m; i++) {
            dp[i][0] = 1;
        }
        // 初始化第一行
        Arrays.fill(dp[0], 1);
        // 状态转移
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }
        return dp[m - 1][n - 1];
    }
}


/**
 * @lc app=leetcode.cn id=63 lang=java
 *
 * [63] 不同路径 II
 *
 * https://leetcode.cn/problems/unique-paths-ii/description/
 *
 * algorithms
 * Medium (41.02%)
 * Likes:    1056
 * Dislikes: 0
 * Total Accepted:    374.7K
 * Total Submissions: 912.7K
 * Testcase Example:  '[[0,0,0],[0,1,0],[0,0,0]]'
 *
 * 一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为 “Start” ）。
 * 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为 “Finish”）。
 * 现在考虑网格中有障碍物。那么从左上角到右下角将会有多少条不同的路径？
 * 网格中的障碍物和空位置分别用 1 和 0 来表示。
 *
 * 示例 1：
 * 输入：obstacleGrid = [[0,0,0],[0,1,0],[0,0,0]]
 * 输出：2
 * 解释：3x3 网格的正中间有一个障碍物。
 * 从左上角到右下角一共有 2 条不同的路径：
 * 1. 向右 -> 向右 -> 向下 -> 向下
 * 2. 向下 -> 向下 -> 向右 -> 向右
 *
 * 示例 2：
 * 输入：obstacleGrid = [[0,1],[0,0]]
 * 输出：1
 *
 * 提示：
 * m == obstacleGrid.length
 * n == obstacleGrid[i].length
 * 1 <= m, n <= 100
 * obstacleGrid[i][j] 为 0 或 1
 */
class UniquePathsII {
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        // 排除格子为空的情况
        if (obstacleGrid == null || obstacleGrid.length == 0 || obstacleGrid[0].length == 0) {
            return 0;
        }
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        int[][] dp = new int[m][n];
        // 初始化第一个位置
        dp[0][0] = obstacleGrid[0][0] == 1 ? 0 : 1;
        // 初始化第一列
        for (int i = 1; i < m; i++) {
            dp[i][0] = dp[i - 1][0] == 0 ? 0 : (obstacleGrid[i][0] == 1 ? 0 : 1);
        }
        // 初始化第一行
        for (int j = 1; j < n; j++) {
            dp[0][j] = dp[0][j - 1] == 0 ? 0 : (obstacleGrid[0][j] == 1 ? 0 : 1);
        }
        // 状态转移
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (obstacleGrid[i][j] == 1) {
                    dp[i][j] = 0;
                } else {
                    dp[i][j] = dp[i][j - 1] + dp[i - 1][j];
                }
            }
        }
        return dp[m - 1][n - 1];
    }
}


/**
 * @lc app=leetcode.cn id=64 lang=java
 *
 * [64] 最小路径和
 *
 * https://leetcode.cn/problems/minimum-path-sum/description/
 *
 * algorithms
 * Medium (69.49%)
 * Likes:    1508
 * Dislikes: 0
 * Total Accepted:    486.3K
 * Total Submissions: 699.7K
 * Testcase Example:  '[[1,3,1],[1,5,1],[4,2,1]]'
 *
 * 给定一个包含非负整数的 m x n 网格 grid ，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。
 * 说明：每次只能向下或者向右移动一步。
 *
 * 示例 1：
 * 输入：grid = [[1,3,1],[1,5,1],[4,2,1]]
 * 输出：7
 * 解释：因为路径 1→3→1→1→1 的总和最小。
 *
 * 示例 2：
 * 输入：grid = [[1,2,3],[4,5,6]]
 * 输出：12
 *
 * 提示：
 * m == grid.length
 * n == grid[i].length
 * 1 <= m, n <= 200
 * 0 <= grid[i][j] <= 200
 */
class MinimumPathSum {
    public int minPathSum(int[][] grid) {
        // 排除格子为空的情况
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }

        int m = grid.length;
        int n = grid[0].length;
        int[][] dp = new int[m + 1][n + 1];
        // 初始化第一个位置
        dp[0][0] = grid[0][0];
        // 初始化第一列
        for (int i = 1; i < m; i++) {
            dp[i][0] = dp[i - 1][0] + grid[i][0];
        }
        // 初始化第一行
        for (int j = 1; j < n; j++) {
            dp[0][j] = dp[0][j - 1] + grid[0][j];
        }
        // 状态转移
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = Math.min(dp[i][j - 1], dp[i - 1][j]) + grid[i][j];
            }
        }
        return dp[m - 1][n - 1];
    }
}


/**
 * @lc app=leetcode.cn id=70 lang=java
 *
 * [70] 爬楼梯
 *
 * https://leetcode.cn/problems/climbing-stairs/description/
 *
 * algorithms
 * Easy (54.05%)
 * Likes:    3073
 * Dislikes: 0
 * Total Accepted:    1.2M
 * Total Submissions: 2.2M
 * Testcase Example:  '2'
 *
 * 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
 * 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
 *
 * 示例 1：
 * 输入：n = 2
 * 输出：2
 * 解释：有两种方法可以爬到楼顶。
 * 1. 1 阶 + 1 阶
 * 2. 2 阶
 *
 * 示例 2：
 * 输入：n = 3
 * 输出：3
 * 解释：有三种方法可以爬到楼顶。
 * 1. 1 阶 + 1 阶 + 1 阶
 * 2. 1 阶 + 2 阶
 * 3. 2 阶 + 1 阶
 *
 * 提示：
 * 1 <= n <= 45
 */
class ClimbingStairs {
    public int climbStairs(int n) {
        if (n <= 2) {
            return n;
        }
        int[] dp = new int[n + 1];
        dp[1] = 1;
        dp[2] = 2;
        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }
}


/**
 * @lc app=leetcode.cn id=72 lang=java
 *
 * [72] 编辑距离
 *
 * https://leetcode.cn/problems/edit-distance/description/
 *
 * algorithms
 * Hard (62.79%)
 * Likes:    2999
 * Dislikes: 0
 * Total Accepted:    373.3K
 * Total Submissions: 594.5K
 * Testcase Example:  '"horse"\n"ros"'
 *
 * 给你两个单词 word1 和 word2， 请返回将 word1 转换成 word2 所使用的最少操作数  。
 * 你可以对一个单词进行如下三种操作：
 * 插入一个字符
 * 删除一个字符
 * 替换一个字符
 *
 * 示例 1：
 * 输入：word1 = "horse", word2 = "ros"
 * 输出：3
 * 解释：
 * horse -> rorse (将 'h' 替换为 'r')
 * rorse -> rose (删除 'r')
 * rose -> ros (删除 'e')
 *
 * 示例 2：
 * 输入：word1 = "intention", word2 = "execution"
 * 输出：5
 * 解释：
 * intention -> inention (删除 't')
 * inention -> enention (将 'i' 替换为 'e')
 * enention -> exention (将 'n' 替换为 'x')
 * exention -> exection (将 'n' 替换为 'c')
 * exection -> execution (插入 'u')
 *
 * 提示：
 * 0 <= word1.length, word2.length <= 500
 * word1 和 word2 由小写英文字母组成
 */
class EditDistance {
    public int minDistance(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();

        // 创建一个二维数组dp，用于存储最小编辑距离
        // 表示将 word1 的前 i 个字符转换为 word2 的前 j 个字符所需的最小编辑距离
        int[][] dp = new int[m + 1][n + 1];

        // 初始化边界条件
        // 当 word2 为空字符串时，将 word1 转换为空字符串需要删除 word1 的字符，所以 dp[i][0] 初始化为 i。
        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }
        // 当 word1 为空字符串时，将其转换为 word2 需要插入 word2 的字符，所以 dp[0][j] 初始化为 j。
        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }

        // 动态规划求解最小编辑距离
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                // 如果当前字符相等，不需要进行编辑操作，继承前一个状态的最小编辑距离
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    // 如果当前字符不相等，考虑三种编辑操作的情况（插入、删除、替换）
                    int insert = dp[i][j - 1] + 1;  // 插入操作
                    int delete = dp[i - 1][j] + 1;  // 删除操作
                    int replace = dp[i - 1][j - 1] + 1;  // 替换操作
                    dp[i][j] = Math.min(Math.min(insert, delete), replace);
                }
            }
        }

        // 返回最终的最小编辑距离
        return dp[m][n];
    }
}


/**
 * @lc app=leetcode.cn id=91 lang=java
 *
 * [91] 解码方法
 *
 * https://leetcode.cn/problems/decode-ways/description/
 *
 * algorithms
 * Medium (33.15%)
 * Likes:    1413
 * Dislikes: 0
 * Total Accepted:    275K
 * Total Submissions: 828.6K
 * Testcase Example:  '"12"'
 *
 * 一条包含字母 A-Z 的消息通过以下映射进行了 编码 ：
 * 'A' -> "1"
 * 'B' -> "2"
 * ...
 * 'Z' -> "26"
 * 要 解码 已编码的消息，所有数字必须基于上述映射的方法，反向映射回字母（可能有多种方法）。例如，"11106" 可以映射为：
 * "AAJF" ，将消息分组为 (1 1 10 6)
 * "KJF" ，将消息分组为 (11 10 6)
 *
 * 注意，消息不能分组为  (1 11 06) ，因为 "06" 不能映射为 "F" ，这是由于 "6" 和 "06" 在映射中并不等价。
 * 给你一个只含数字的 非空 字符串 s ，请计算并返回 解码 方法的 总数 。
 * 题目数据保证答案肯定是一个 32 位 的整数。
 *
 * 示例 1：
 * 输入：s = "12"
 * 输出：2
 * 解释：它可以解码为 "AB"（1 2）或者 "L"（12）。
 *
 * 示例 2：
 * 输入：s = "226"
 * 输出：3
 * 解释：它可以解码为 "BZ" (2 26), "VF" (22 6), 或者 "BBF" (2 2 6) 。
 *
 * 示例 3：
 * 输入：s = "06"
 * 输出：0
 * 解释："06" 无法映射到 "F" ，因为存在前导零（"6" 和 "06" 并不等价）。
 *
 * 提示：
 * 1 <= s.length <= 100
 * s 只包含数字，并且可能包含前导零。
 */
class DecodeWays {
    public int numDecodings(String s) {
        int n = s.length();
        if (n < 1) {
            return 0;
        }

        // 定义：dp[i] 表示 s[0..i-1] 的解码方式数量
        int[] dp = new int[n + 1];
        // base case: s 只有一个字符的情况
        dp[0] = 1; // 注意这里初始化为 1
        dp[1] = s.charAt(0) == '0' ? 0 : 1;

        // 注意 dp 数组和 s 之间的索引偏移一位
        for (int i = 2; i <= n; i++) {
            char c = s.charAt(i-1), d = s.charAt(i-2);
            if (c >= '1' && c <= '9') {
                // 1. s[i] 本身可以作为一个字母
                dp[i] += dp[i - 1];
            }
            if (d == '1' || (d == '2' && c <= '6')) {
                // 2. s[i] 和 s[i - 1] 结合起来表示一个字母
                dp[i] += dp[i - 2];
            }
        }

        return dp[n];
    }
}


/**
 * @lc app=leetcode.cn id=97 lang=java
 *
 * [97] 交错字符串
 *
 * https://leetcode.cn/problems/interleaving-string/description/
 *
 * algorithms
 * Medium (44.65%)
 * Likes:    888
 * Dislikes: 0
 * Total Accepted:    119.1K
 * Total Submissions: 266.7K
 * Testcase Example:  '"aabcc"\n"dbbca"\n"aadbbcbcac"'
 *
 * 给定三个字符串 s1、s2、s3，请你帮忙验证 s3 是否是由 s1 和 s2 交错 组成的。
 * 两个字符串 s 和 t 交错 的定义与过程如下，其中每个字符串都会被分割成若干 非空 子字符串：
 * s = s1 + s2 + ... + sn
 * t = t1 + t2 + ... + tm
 * |n - m| <= 1
 * 交错 是 s1 + t1 + s2 + t2 + s3 + t3 + ... 或者 t1 + s1 + t2 + s2 + t3 + s3 +
 * ...
 *
 * 注意：a + b 意味着字符串 a 和 b 连接。
 *
 * 示例 1：
 * 输入：s1 = "aabcc", s2 = "dbbca", s3 = "aadbbcbcac"
 * 输出：true
 *
 * 示例 2：
 * 输入：s1 = "aabcc", s2 = "dbbca", s3 = "aadbbbaccc"
 * 输出：false
 *
 * 示例 3：
 * 输入：s1 = "", s2 = "", s3 = ""
 * 输出：true
 *
 * 提示：
 * 0 <= s1.length, s2.length <= 100
 * 0 <= s3.length <= 200
 * s1、s2、和 s3 都由小写英文字母组成
 *
 * 进阶：您能否仅使用 O(s2.length) 额外的内存空间来解决它?
 */
class InterleavingString {
    public boolean isInterleave(String s1, String s2, String s3) {
        int m = s1.length();
        int n = s2.length();
        // 如果长度对不上，必然不可能
        if (m + n != s3.length()) {
            return false;
        }

        // 备忘录，其中 -1 代表未计算，0 代表 false，1 代表 true
        byte[][] memo = new byte[m + 1][n + 1];
        for (byte[] row : memo) {
            Arrays.fill(row, (byte) -1);
        }
        return dp(s1, 0, s2, 0, s3, memo);
    }

    // 定义：计算 s1[i..] 和 s2[j..] 是否能组合出 s3[i+j..]
    private boolean dp(String s1, int i, String s2, int j, String s3, byte[][] memo) {
        int k = i + j;
        // base case，s3 构造完成
        if (k == s3.length()) {
            return true;
        }
        // 查备忘录，如果已经计算过，直接返回
        if (memo[i][j] != -1) {
            return memo[i][j] == 1;
        }

        boolean res = false;
        // 如果，s1[i] 可以匹配 s3[k]，那么填入 s1[i] 试一下
        if (i < s1.length() && s1.charAt(i) == s3.charAt(k)) {
            res = dp(s1, i + 1, s2, j, s3, memo);
        }
        // 如果，s2[j] 可以匹配，那么填入 s2[j] 试一下
        if (j < s2.length() && s2.charAt(j) == s3.charAt(k)) {
            res |= dp(s1, i, s2, j + 1, s3, memo);
        }
        // 如果 s1[i] 和 s2[j] 都匹配不了，则返回 false
        // 将结果存入备忘录
        memo[i][j] = (byte) (res ? 1 : 0);
        return res;
    }
}


/**
 * @lc app=leetcode.cn id=115 lang=java
 *
 * [115] 不同的子序列
 *
 * https://leetcode.cn/problems/distinct-subsequences/description/
 *
 * algorithms
 * Hard (52.21%)
 * Likes:    1106
 * Dislikes: 0
 * Total Accepted:    147.9K
 * Total Submissions: 283.5K
 * Testcase Example:  '"rabbbit"\n"rabbit"'
 *
 * 给你两个字符串 s 和 t ，统计并返回在 s 的 子序列 中 t 出现的个数。
 * 题目数据保证答案符合 32 位带符号整数范围。
 *
 * 示例 1：
 * 输入：s = "rabbbit", t = "rabbit"
 * 输出：3
 * 解释：
 * 如下所示, 有 3 种可以从 s 中得到 "rabbit" 的方案。
 * rabbbit
 * rabbbit
 * rabbbit
 *
 * 示例 2：
 * 输入：s = "babgbag", t = "bag"
 * 输出：5
 * 解释：
 * 如下所示, 有 5 种可以从 s 中得到 "bag" 的方案。
 * babgbag
 * babgbag
 * babgbag
 * babgbag
 * babgbag
 *
 * 提示：
 * 1 <= s.length, t.length <= 1000
 * s 和 t 由英文字母组成
 */
class DistinctSubsequences {
    int[][] memo;

    public int numDistinct(String s, String t) {
        memo = new int[s.length()][t.length()];
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }
        return dp(s, 0, t, 0);
    }

    // 定义：该函数返回 s[i..] 中的子序列 t[j..] 的数量
    int dp(String s, int i, String t, int j) {
        int m = s.length(), n = t.length();
        if (j == n) {
            // 子序列全部匹配完成
            return 1;
        }
        if (n - j > m - i) {
            // 待匹配子序列的长度不应该比字符串的长度还要短
            return 0;
        }
        if (memo[i][j] != -1) {
            // 已经计算过对应状态
            return memo[i][j];
        }
        int res = 0;
        // 状态转移方程
        /*
        站在 s 的视角：
        我们的原问题是求 s[0..] 中不同子序列 t[0..]，可以先看看 s[0] 是否能匹配 t[0]，如果不可以，那没得说，原问题就转化为让 s[1..] 去匹配 t[0..]；
        但如果 s[0] 可以匹配 t[0]，那么又有两种情况，这两种情况是累加的关系：
        1、让 s[0] 匹配 t[0]，那么原问题转化为让 s[1..] 去匹配 t[1..]。
        2、不让 s[0] 匹配 t[0]，那么原问题转化为让 s[1..] 去匹配 t[0..]。
        比如 s = "aab", t = "ab"，就有两种匹配方式：a_b 和 _ab。
         */
        if (s.charAt(i) == t.charAt(j)) {
            res += dp(s, i + 1, t, j + 1) + dp(s, i + 1, t, j);
        } else {
            res += dp(s, i + 1, t, j);
        }
        memo[i][j] = res;
        return res;
    }
}


/**
 * @lc app=leetcode.cn id=120 lang=java
 *
 * [120] 三角形最小路径和
 *
 * https://leetcode.cn/problems/triangle/description/
 *
 * algorithms
 * Medium (68.66%)
 * Likes:    1252
 * Dislikes: 0
 * Total Accepted:    304.2K
 * Total Submissions: 443K
 * Testcase Example:  '[[2],[3,4],[6,5,7],[4,1,8,3]]'
 *
 * 给定一个三角形 triangle ，找出自顶向下的最小路径和。
 * 每一步只能移动到下一行中相邻的结点上。相邻的结点 在这里指的是 下标 与 上一层结点下标 相同或者等于 上一层结点下标 + 1
 * 的两个结点。也就是说，如果正位于当前行的下标 i ，那么下一步可以移动到下一行的下标 i 或 i + 1 。
 *
 * 示例 1：
 * 输入：triangle = [[2],[3,4],[6,5,7],[4,1,8,3]]
 * 输出：11
 * 解释：如下面简图所示：
 *    2
 *   3 4
 *  6 5 7
 * 4 1 8 3
 * 自顶向下的最小路径和为 11（即，2 + 3 + 5 + 1 = 11）。
 *
 * 示例 2：
 * 输入：triangle = [[-10]]
 * 输出：-10
 *
 * 提示：
 * 1 <= triangle.length <= 200
 * triangle[0].length == 1
 * triangle[i].length == triangle[i - 1].length + 1
 * -10^4 <= triangle[i][j] <= 10^4
 *
 * 进阶：
 * 你可以只使用 O(n) 的额外空间（n 为三角形的总行数）来解决这个问题吗？
 */
class Triangle {
    public int minimumTotal(List<List<Integer>> triangle) {
        int n = triangle.size();
        int[] dp = new int[n];

        // 初始化 dp 数组为最后一行的元素值
        for (int i = 0; i < n; i++) {
            dp[i] = triangle.get(n - 1).get(i);
        }

        // 从倒数第二行开始迭代计算最短路径和
        for (int i = n - 2; i >= 0; i--) {
            for (int j = 0; j <= i; j++) {
                dp[j] = triangle.get(i).get(j) + Math.min(dp[j], dp[j + 1]);
            }
        }

        return dp[0];
    }

    public static List<List<Integer>> example1() {
        return Arrays.asList(Collections.singletonList(2), Arrays.asList(3,4), Arrays.asList(6,5,7), Arrays.asList(4,1,8,3));
    }
    public static List<List<Integer>> example2() {
        return Collections.singletonList(Collections.singletonList(-10));
    }
}


/**
 * @lc app=leetcode.cn id=121 lang=java
 *
 * [121] 买卖股票的最佳时机
 *
 * https://leetcode.cn/problems/best-time-to-buy-and-sell-stock/description/
 *
 * algorithms
 * Easy (57.93%)
 * Likes:    3122
 * Dislikes: 0
 * Total Accepted:    1.2M
 * Total Submissions: 2M
 * Testcase Example:  '[7,1,5,3,6,4]'
 *
 * 给定一个数组 prices ，它的第 i 个元素 prices[i] 表示一支给定股票第 i 天的价格。
 * 你只能选择 某一天 买入这只股票，并选择在 未来的某一个不同的日子 卖出该股票。设计一个算法来计算你所能获取的最大利润。
 * 返回你可以从这笔交易中获取的最大利润。如果你不能获取任何利润，返回 0 。
 *
 * 示例 1：
 * 输入：[7,1,5,3,6,4]
 * 输出：5
 * 解释：在第 2 天（股票价格 = 1）的时候买入，在第 5 天（股票价格 = 6）的时候卖出，最大利润 = 6-1 = 5 。
 *      注意利润不能是 7-1 = 6, 因为卖出价格需要大于买入价格；同时，你不能在买入前卖出股票。
 *
 * 示例 2：
 * 输入：prices = [7,6,4,3,1]
 * 输出：0
 * 解释：在这种情况下, 没有交易完成, 所以最大利润为 0。
 *
 * 提示：
 * 1 <= prices.length <= 10^5
 * 0 <= prices[i] <= 10^4
 */
class BestTimeToBuyAndSellStock {
    /**
     * 股票系列问题状态定义：
     * dp[i][k][0 or 1]
     * 0 <= i <= n - 1, 1 <= k <= K
     * n 为天数，大 K 为交易数的上限，0 和 1 代表是否持有股票。
     *
     * 股票系列问题通用状态转移方程：
     * dp[i][k][0] = max(dp[i-1][k][0], dp[i-1][k][1] + prices[i])
     *               max( 今天选择 rest,        今天选择 sell       )
     *
     * dp[i][k][1] = max(dp[i-1][k][1], dp[i-1][k-1][0] - prices[i])
     *               max( 今天选择 rest,         今天选择 buy         )
     *
     * 通用 base case：
     * dp[-1][...][0] = dp[...][0][0] = 0
     * dp[-1][...][1] = dp[...][0][1] = -infinity
     *
     * 特化到 k = 1 的情况，状态转移方程和 base case 如下：
     * 状态转移方程：
     * dp[i][0] = max(dp[i-1][0], dp[i-1][1] + prices[i])
     * dp[i][1] = max(dp[i-1][1], -prices[i])
     *
     * base case：
     * dp[i][0] = 0;
     * dp[i][1] = -prices[i];
     */
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }

        int n = prices.length;
        int[][] dp = new int[n][2];
        // base case
        dp[0][0] = 0;
        dp[0][1] = -prices[0];
        // 状态转移方程
        for (int i = 1; i < n; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            dp[i][1] = Math.max(dp[i - 1][1], -prices[i]);
        }
        return dp[n - 1][0];
    }
}


/**
 * @lc app=leetcode.cn id=122 lang=java
 *
 * [122] 买卖股票的最佳时机 II
 *
 * https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-ii/description/
 *
 * algorithms
 * Medium (72.23%)
 * Likes:    2230
 * Dislikes: 0
 * Total Accepted:    916.4K
 * Total Submissions: 1.3M
 * Testcase Example:  '[7,1,5,3,6,4]'
 *
 * 给你一个整数数组 prices ，其中 prices[i] 表示某支股票第 i 天的价格。
 * 在每一天，你可以决定是否购买和/或出售股票。你在任何时候 最多 只能持有 一股 股票。你也可以先购买，然后在 同一天 出售。
 * 返回 你能获得的 最大 利润 。
 *
 * 示例 1：
 * 输入：prices = [7,1,5,3,6,4]
 * 输出：7
 * 解释：在第 2 天（股票价格 = 1）的时候买入，在第 3 天（股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5 - 1 = 4 。
 * 随后，在第 4 天（股票价格 = 3）的时候买入，在第 5 天（股票价格 = 6）的时候卖出, 这笔交易所能获得利润 = 6 - 3 = 3 。
 *      总利润为 4 + 3 = 7 。
 *
 * 示例 2：
 * 输入：prices = [1,2,3,4,5]
 * 输出：4
 * 解释：在第 1 天（股票价格 = 1）的时候买入，在第 5 天 （股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5 - 1 = 4 。
 * 总利润为 4 。
 *
 * 示例 3：
 * 输入：prices = [7,6,4,3,1]
 * 输出：0
 * 解释：在这种情况下, 交易无法获得正利润，所以不参与交易可以获得最大利润，最大利润为 0 。
 *
 * 提示：
 * 1 <= prices.length <= 3 * 10^4
 * 0 <= prices[i] <= 10^4
 */
class BestTimeToBuyAndSellStockII {
    /**
     * 股票系列问题状态定义：
     * dp[i][k][0 or 1]
     * 0 <= i <= n - 1, 1 <= k <= K
     * n 为天数，大 K 为交易数的上限，0 和 1 代表是否持有股票。
     *
     * 股票系列问题通用状态转移方程：
     * dp[i][k][0] = max(dp[i-1][k][0], dp[i-1][k][1] + prices[i])
     *               max( 今天选择 rest,        今天选择 sell       )
     *
     * dp[i][k][1] = max(dp[i-1][k][1], dp[i-1][k-1][0] - prices[i])
     *               max( 今天选择 rest,         今天选择 buy         )
     *
     * 通用 base case：
     * dp[-1][...][0] = dp[...][0][0] = 0
     * dp[-1][...][1] = dp[...][0][1] = -infinity
     *
     * 特化到 k 无限制的情况，状态转移方程如下：
     * dp[i][0] = max(dp[i-1][0], dp[i-1][1] + prices[i])
     * dp[i][1] = max(dp[i-1][1], dp[i-1][0] - prices[i])
     */
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }

        int n = prices.length;
        int[][] dp = new int[n][2];
        // base case
        dp[0][0] = 0;
        dp[0][1] = -prices[0];
        for (int i = 1; i < n; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);
        }
        return dp[n - 1][0];
    }

    /**
     * 解法2：滑动窗口
     */
    public int maxProfit2(int[] prices) {
        int n = prices.length;
        int res = 0;
        for (int i = 1; i < n; i++) {
            int price = prices[i] - prices[i - 1];
            if (price > 0) {
                res += price;
            }
        }
        return res;
    }
}


/**
 * @lc app=leetcode.cn id=123 lang=java
 *
 * [123] 买卖股票的最佳时机 III
 *
 * https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-iii/description/
 *
 * algorithms
 * Hard (59.15%)
 * Likes:    1503
 * Dislikes: 0
 * Total Accepted:    274.7K
 * Total Submissions: 464.1K
 * Testcase Example:  '[3,3,5,0,0,3,1,4]'
 *
 * 给定一个数组，它的第 i 个元素是一支给定的股票在第 i 天的价格。
 * 设计一个算法来计算你所能获取的最大利润。你最多可以完成 两笔 交易。
 *
 * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
 *
 * 示例 1:
 *
 * 输入：prices = [3,3,5,0,0,3,1,4]
 * 输出：6
 * 解释：在第 4 天（股票价格 = 0）的时候买入，在第 6 天（股票价格 = 3）的时候卖出，这笔交易所能获得利润 = 3-0 = 3 。
 * 随后，在第 7 天（股票价格 = 1）的时候买入，在第 8 天 （股票价格 = 4）的时候卖出，这笔交易所能获得利润 = 4-1 = 3 。
 *
 * 示例 2：
 * 输入：prices = [1,2,3,4,5]
 * 输出：4
 * 解释：在第 1 天（股票价格 = 1）的时候买入，在第 5 天 （股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。
 * 注意你不能在第 1 天和第 2 天接连购买股票，之后再将它们卖出。  
 * 因为这样属于同时参与了多笔交易，你必须在再次购买前出售掉之前的股票。
 *
 * 示例 3：
 * 输入：prices = [7,6,4,3,1]
 * 输出：0
 * 解释：在这个情况下, 没有交易完成, 所以最大利润为 0。
 *
 * 示例 4：
 * 输入：prices = [1]
 * 输出：0
 *
 * 提示：
 * 1 <= prices.length <= 10^5
 * 0 <= prices[i] <= 10^5
 */
class BestTimeToBuyAndSellStockIII {
    /**
     * 股票系列问题状态定义：
     * dp[i][k][0 or 1]
     * 0 <= i <= n - 1, 1 <= k <= K
     * n 为天数，大 K 为交易数的上限，0 和 1 代表是否持有股票。
     *
     * 股票系列问题通用状态转移方程：
     * dp[i][k][0] = max(dp[i-1][k][0], dp[i-1][k][1] + prices[i])
     *               max( 今天选择 rest,        今天选择 sell       )
     *
     * dp[i][k][1] = max(dp[i-1][k][1], dp[i-1][k-1][0] - prices[i])
     *               max( 今天选择 rest,         今天选择 buy         )
     *
     * 通用 base case：
     * dp[-1][...][0] = dp[...][0][0] = 0
     * dp[-1][...][1] = dp[...][0][1] = -infinity
     */
    public int maxProfit(int[] prices) {
        int maxK = 2, n = prices.length;
        int[][][] dp = new int[n][maxK + 1][2];

        for (int i = 0; i < n; i++) {
            for (int k = maxK; k > 0; k--) {
                if (i == 0) {
                    // 处理 base case
                    dp[i][k][0] = 0;
                    dp[i][k][1] = -prices[i];
                    continue;
                }
                dp[i][k][0] = Math.max(dp[i - 1][k][0], dp[i - 1][k][1] + prices[i]);
                dp[i][k][1] = Math.max(dp[i - 1][k][1], dp[i - 1][k - 1][0] - prices[i]);
            }
        }

        // 穷举了 n × max_k × 2 个状态，正确。
        return dp[n - 1][maxK][0];
    }
}


/**
 * @lc app=leetcode.cn id=132 lang=java
 *
 * [132] 分割回文串 II
 *
 * https://leetcode.cn/problems/palindrome-partitioning-ii/description/
 *
 * algorithms
 * Hard (49.89%)
 * Likes:    704
 * Dislikes: 0
 * Total Accepted:    80.8K
 * Total Submissions: 161.9K
 * Testcase Example:  '"aab"'
 *
 * 给你一个字符串 s，请你将 s 分割成一些子串，使每个子串都是回文。
 * 返回符合要求的 最少分割次数 。
 *
 * 示例 1：
 * 输入：s = "aab"
 * 输出：1
 * 解释：只需一次分割就可将 s 分割成 ["aa","b"] 这样两个回文子串。
 *
 * 示例 2：
 * 输入：s = "a"
 * 输出：0
 *
 * 示例 3：
 * 输入：s = "ab"
 * 输出：1
 *
 * 提示：
 * 1 <= s.length <= 2000
 * s 仅由小写英文字母组成
 */
class PalindromePartitioningII {
    public int minCut(String s) {
        int n = s.length();
        boolean[][] isPalindrome = new boolean[n][n];

        // dp[i] 表示 s[0:i] 子串的最小分割次数
        int[] dp = new int[n];

        for (int i = 0; i < n; i++) {
            int minCut = i; // 最坏情况下，每个字符都分割一次
            for (int j = 0; j <= i; j++) {
                // 判断 s[j:i] 是否是回文串
                if (isPalindrome(s, j, i, isPalindrome)) {
                    isPalindrome[j][i] = true;
                    if (j == 0) {
                        // s[0:i] 本身是回文串，不需要分割
                        minCut = 0;
                    } else {
                        // 更新最小分割次数
                        minCut = Math.min(minCut, dp[j - 1] + 1);
                    }
                }
            }
            dp[i] = minCut;
        }

        return dp[n - 1];
    }

    private boolean isPalindrome(String s, int start, int end, boolean[][] isPalindrome) {
        return s.charAt(start) == s.charAt(end) && (end - start <= 1 || isPalindrome[start + 1][end - 1]);
    }
}


/**
 * @lc app=leetcode.cn id=139 lang=java
 *
 * [139] 单词拆分
 *
 * https://leetcode.cn/problems/word-break/description/
 *
 * algorithms
 * Medium (54.41%)
 * Likes:    2292
 * Dislikes: 0
 * Total Accepted:    485.6K
 * Total Submissions: 892.1K
 * Testcase Example:  '"leetcode"\n["leet","code"]'
 *
 * 给你一个字符串 s 和一个字符串列表 wordDict 作为字典。请你判断是否可以利用字典中出现的单词拼接出 s 。
 * 注意：不要求字典中出现的单词全部都使用，并且字典中的单词可以重复使用。
 *
 * 示例 1：
 * 输入: s = "leetcode", wordDict = ["leet", "code"]
 * 输出: true
 * 解释: 返回 true 因为 "leetcode" 可以由 "leet" 和 "code" 拼接成。
 *
 * 示例 2：
 * 输入: s = "applepenapple", wordDict = ["apple", "pen"]
 * 输出: true
 * 解释: 返回 true 因为 "applepenapple" 可以由 "apple" "pen" "apple" 拼接成。
 * 注意，你可以重复使用字典中的单词。
 *
 * 示例 3：
 * 输入: s = "catsandog", wordDict = ["cats", "dog", "sand", "and", "cat"]
 * 输出: false
 *
 * 提示：
 * 1 <= s.length <= 300
 * 1 <= wordDict.length <= 1000
 * 1 <= wordDict[i].length <= 20
 * s 和 wordDict[i] 仅由小写英文字母组成
 * wordDict 中的所有字符串 互不相同
 */
class WordBreak {
    /**
     * 用于记录状态 （s[start] -> s[end] 是否可以拼成）
     * 0 表示没有计算过 1 表示可以 -1 表示不可以
     */
    private int[] dp;

    public boolean wordBreak(String s, List<String> wordDict) {
        if (s == null || s.isEmpty()) {
            return false;
        }
        dp = new int[s.length()];
        return wordBreak(s, wordDict, 0);
    }

    private boolean wordBreak(String s, List<String> wordDict, int start) {
        int length = s.length();
        // 判断结束条件
        if (start >= length) {
            return true;
        }
        // 判断是否计算过
        if (dp[start] != 0) {
            return dp[start] == 1;
        }
        // 回溯算法
        for (String word : wordDict) {
            // 不相等直接跳过
            if (!s.startsWith(word, start)) {
                continue;
            }
            // 进入下一层决策树并记录状态
            if (wordBreak(s, wordDict, start + word.length())) {
                dp[start] = 1;
                return true;
            }
        }
        // 都不成功记录失败
        dp[start] = -1;
        return false;
    }

    /**
     * 方法2： 回溯方法，回进行大量计算导致超时
     */
    private boolean res2;
    public boolean wordBreak2(String s, List<String> wordDict) {
        if (s == null || s.isEmpty()) {
            return false;
        }
        wordBreak2(s, wordDict, 0);
        return res2;
    }
    private void wordBreak2(String s, List<String> wordDict, int start) {
        int length = s.length();
        if (res2 || start >= length) {
            res2 = true;
            return;
        }

        for (String word : wordDict) {
            // 判断是否匹配
            if (!s.startsWith(word, start)) {
                continue;
            }
            // 进入下一层决策树
            wordBreak2(s, wordDict, start + word.length());
        }
    }
}


/**
 * @lc app=leetcode.cn id=140 lang=java
 *
 * [140] 单词拆分 II
 *
 * https://leetcode.cn/problems/word-break-ii/description/
 *
 * algorithms
 * Hard (57.68%)
 * Likes:    720
 * Dislikes: 0
 * Total Accepted:    92.9K
 * Total Submissions: 160.9K
 * Testcase Example:  '"catsanddog"\n["cat","cats","and","sand","dog"]'
 *
 * 给定一个字符串 s 和一个字符串字典 wordDict ，在字符串 s 中增加空格来构建一个句子，使得句子中所有的单词都在词典中。以任意顺序
 * 返回所有这些可能的句子。
 * 注意：词典中的同一个单词可能在分段中被重复使用多次。
 *
 * 示例 1：
 * 输入:s = "catsanddog", wordDict = ["cat","cats","and","sand","dog"]
 * 输出:["cats and dog","cat sand dog"]
 *
 * 示例 2：
 * 输入:s = "pineapplepenapple", wordDict =
 * ["apple","pen","applepen","pine","pineapple"]
 * 输出:["pine apple pen apple","pineapple pen apple","pine applepen apple"]
 * 解释: 注意你可以重复使用字典中的单词。
 *
 * 示例 3：
 * 输入:s = "catsandog", wordDict = ["cats","dog","sand","and","cat"]
 * 输出:[]
 *
 * 提示：
 * 1 <= s.length <= 20
 * 1 <= wordDict.length <= 1000
 * 1 <= wordDict[i].length <= 10
 * s 和 wordDict[i] 仅有小写英文字母组成
 * wordDict 中所有字符串都 不同
 */
class WordBreakII {
    // 算法1 记录 s[i...] 的路径
    List<String> res = new LinkedList<>();
    // 记录回溯路径
    LinkedList<String> track = new LinkedList<>();

    public List<String> wordBreak(String s, List<String> wordDict) {
        // 根据函数定义，判断 s[0..] 是否能够被拼出
        backtrack(s, 0, wordDict);
        return res;
    }

    // 回溯算法框架
    void backtrack(String s, int i, List<String> wordDict) {
        // base case，整个 s 都被拼出来了
        if (i == s.length()) {
            res.add(String.join(" ", track));
            return;
        }
        if (i > s.length()) {
            return;
        }

        // 遍历所有单词，尝试匹配 s[i..] 的前缀
        for (String word : wordDict) {
            int len = word.length();
            // 单词太长，跳过
            if (i + len > s.length()) {
                continue;
            }
            // 无法匹配，跳过
            String subStr = s.substring(i, i + len);
            if (!subStr.equals(word)) {
                continue;
            }
            // s[i..] 的前缀被 word 匹配，做选择
            track.addLast(word);
            backtrack(s, i + len, wordDict);
            // 撤销选择
            track.removeLast();
        }
    }

    // 算法2 记录 s[start] -> s[end] 所有能拼成的字符串
    /**
     * 用于记录状态 （s[start] -> s[end] 是否可以拼成）
     * 0 表示没有计算过 1 表示可以 -1 表示不可以
     */
    private int[] dp;
    /**
     * 用于记录 s[start] -> s[end] 可拼成的所有字符串
     */
    private Map<Integer, List<String>> memo = new HashMap<>();

    public List<String> wordBreak2(String s, List<String> wordDict) {
        if (s == null || s.isEmpty()) {
            return new ArrayList<>();
        }
        dp = new int[s.length()];
        wordBreak2(s, wordDict, 0);
        return memo.getOrDefault(0, new ArrayList<>());
    }

    private boolean wordBreak2(String s, List<String> wordDict, int start) {
        int length = s.length();
        // 判断结束条件
        if (start >= length) {
            return true;
        }
        // 判断是否计算过
        if (dp[start] != 0) {
            return dp[start] == 1;
        }
        // 回溯算法
        for (String word : wordDict) {
            // 不相等直接跳过
            if (!s.startsWith(word, start)) {
                continue;
            }
            // 进入下一层决策树并记录状态
            int len = word.length();
            if (wordBreak2(s, wordDict, start + len)) {
                // 成功
                dp[start] = 1;
                // 是否是最后一个单词
                List<String> list = memo.get(start + len);
                if (list == null) {
                    memo.computeIfAbsent(start, k -> new ArrayList<>()).add(word);
                } else {
                    list.forEach(subStr -> {
                        memo.computeIfAbsent(start, k -> new ArrayList<>()).add(word + " " + subStr);
                    });
                }
            }
        }
        // 都不成功记录失败
        if (dp[start] == 0) {
            dp[start] = -1;
        }
        return dp[start] == 1;
    }
}


/**
 * @lc app=leetcode.cn id=152 lang=java
 *
 * [152] 乘积最大子数组
 *
 * https://leetcode.cn/problems/maximum-product-subarray/description/
 *
 * algorithms
 * Medium (43.12%)
 * Likes:    2121
 * Dislikes: 0
 * Total Accepted:    384.1K
 * Total Submissions: 890.8K
 * Testcase Example:  '[2,3,-2,4]'
 *
 * 给你一个整数数组 nums ，请你找出数组中乘积最大的非空连续子数组（该子数组中至少包含一个数字），并返回该子数组所对应的乘积。
 * 测试用例的答案是一个 32-位 整数。
 * 子数组 是数组的连续子序列。
 *
 * 示例 1:
 * 输入: nums = [2,3,-2,4]
 * 输出: 6
 * 解释: 子数组 [2,3] 有最大乘积 6。
 *
 * 示例 2:
 * 输入: nums = [-2,0,-1]
 * 输出: 0
 * 解释: 结果不能为 2, 因为 [-2,-1] 不是子数组。
 *
 * 提示:
 * 1 <= nums.length <= 2 * 10^4
 * -10 <= nums[i] <= 10
 * nums 的任何前缀或后缀的乘积都 保证 是一个 32-位 整数
 */
class MaximumProductSubarray {
    public int maxProduct(int[] nums) {
        if (nums == null) {
            return 0;
        }

        int length = nums.length;
        // dp[i][0] 表示以 i 为终点的子数组最大乘积
        // dp[i][1] 表示以 i 为终点的子数组最小乘积
        int[][] dp = new int[length][2];
        // base case
        dp[0][0] = nums[0];
        dp[0][1] = nums[0];
        // 最大子数组乘积
        int res = nums[0];
        for (int i = 1; i < length; i++) {
            int a = nums[i] * dp[i - 1][0];
            int b = nums[i] * dp[i - 1][1];
            dp[i][0] = Math.max(nums[i], Math.max(a, b));
            dp[i][1] = Math.min(nums[i], Math.min(a, b));
            res = Math.max(res, dp[i][0]);
        }
        return res;
    }
}


/**
 * @lc app=leetcode.cn id=174 lang=java
 *
 * [174] 地下城游戏
 *
 * https://leetcode.cn/problems/dungeon-game/description/
 *
 * algorithms
 * Hard (48.65%)
 * Likes:    792
 * Dislikes: 0
 * Total Accepted:    68.1K
 * Total Submissions: 139.9K
 * Testcase Example:  '[[-2,-3,3],[-5,-10,1],[10,30,-5]]'
 *
 * 恶魔们抓住了公主并将她关在了地下城 dungeon 的 右下角 。地下城是由 m x n 个房间组成的二维网格。我们英勇的骑士最初被安置在 左上角
 * 的房间里，他必须穿过地下城并通过对抗恶魔来拯救公主。
 * 骑士的初始健康点数为一个正整数。如果他的健康点数在某一时刻降至 0 或以下，他会立即死亡。
 * 有些房间由恶魔守卫，因此骑士在进入这些房间时会失去健康点数（若房间里的值为负整数，则表示骑士将损失健康点数）；其他房间要么是空的（房间里的值为
 * 0），要么包含增加骑士健康点数的魔法球（若房间里的值为正整数，则表示骑士将增加健康点数）。
 * 为了尽快解救公主，骑士决定每次只 向右 或 向下 移动一步。
 * 返回确保骑士能够拯救到公主所需的最低初始健康点数。
 *
 * 注意：任何房间都可能对骑士的健康点数造成威胁，也可能增加骑士的健康点数，包括骑士进入的左上角房间以及公主被监禁的右下角房间。
 *
 * 示例 1：
 * 输入：dungeon = [[-2,-3,3],[-5,-10,1],[10,30,-5]]
 * 输出：7
 * 解释：如果骑士遵循最佳路径：右 -> 右 -> 下 -> 下 ，则骑士的初始健康点数至少为 7 。
 *
 * 示例 2：
 * 输入：dungeon = [[0]]
 * 输出：1
 *
 * 提示：
 * m == dungeon.length
 * n == dungeon[i].length
 * 1 <= m, n <= 200
 * -1000 <= dungeon[i][j] <= 1000
 */
class DungeonGame {
    /**
     * dp 函数的定义： 从 grid[i][j] 到达终点（右下角）所需的最少生命值是 dp(grid, i, j)。
     * 我们想求 dp(0, 0)，那就应该试图通过 dp(i, j+1) 和 dp(i+1, j) 推导出 dp(i, j)，这样才能不断逼近 base case，正确进行状态转移。
     * 状态转移方程：
     * int res = min(
     *     dp(i + 1, j),
     *     dp(i, j + 1)
     * ) - grid[i][j];
     * dp(i, j) = res <= 0 ? 1 : res;
     */

    // 备忘录，消除重叠子问题
    int[][] memo;

    public int calculateMinimumHP(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        // 备忘录中都初始化为 -1
        memo = new int[m][n];
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }

        return dp(grid, 0, 0);
    }

    /* 定义：从 (i, j) 到达右下角，需要的初始血量至少是多少 */
    int dp(int[][] grid, int i, int j) {
        int m = grid.length;
        int n = grid[0].length;
        // base case
        if (i == m - 1 && j == n - 1) {
            return grid[i][j] >= 0 ? 1 : -grid[i][j] + 1;
        }
        if (i == m || j == n) {
            return Integer.MAX_VALUE;
        }
        // 避免重复计算
        if (memo[i][j] != -1) {
            return memo[i][j];
        }
        // 状态转移逻辑
        int res = Math.min(
                dp(grid, i, j + 1),
                dp(grid, i + 1, j)
        ) - grid[i][j];
        // 骑士的生命值至少为 1
        memo[i][j] = res <= 0 ? 1 : res;

        return memo[i][j];
    }
}
