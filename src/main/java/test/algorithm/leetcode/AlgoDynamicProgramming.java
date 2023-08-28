package test.algorithm.leetcode;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Stack;

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
