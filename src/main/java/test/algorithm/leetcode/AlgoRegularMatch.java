package test.algorithm.leetcode;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * 正则匹配算法
 */
@Slf4j
public class AlgoRegularMatch {
    public static void main(String[] args) {
        // 正则表达式匹配
        log.info("RegularExpressionMatching: {}", new RegularExpressionMatching().isMatch("aa", "a*"));
        log.info("RegularExpressionMatching: {}", new RegularExpressionMatching().isMatch("aac", ".*"));
        log.info("RegularExpressionMatching: {}", new RegularExpressionMatching().isMatch("aac", "aac"));
        // 通配符匹配
        log.info("WildcardMatching: {}", new WildcardMatching().isMatch("aa", "a"));
        log.info("WildcardMatching: {}", new WildcardMatching().isMatch("aa", "*"));
        log.info("WildcardMatching: {}", new WildcardMatching().isMatch("cb", "?a"));
    }
}

/**
 * @lc app=leetcode.cn id=10 lang=java
 *
 * [10] 正则表达式匹配
 *
 * https://leetcode.cn/problems/regular-expression-matching/description/
 *
 * algorithms
 * Hard (31.00%)
 * Likes:    3388
 * Dislikes: 0
 * Total Accepted:    338.3K
 * Total Submissions: 1.1M
 * Testcase Example:  '"aa"\n"a"'
 *
 * 给你一个字符串 s 和一个字符规律 p，请你来实现一个支持 '.' 和 '*' 的正则表达式匹配。
 * '.' 匹配任意单个字符
 * '*' 匹配零个或多个前面的那一个元素
 * 所谓匹配，是要涵盖 整个 字符串 s的，而不是部分字符串。
 *
 * 示例 1：
 * 输入：s = "aa", p = "a"
 * 输出：false
 * 解释："a" 无法匹配 "aa" 整个字符串。
 *
 * 示例 2:
 * 输入：s = "aa", p = "a*"
 * 输出：true
 * 解释：因为 '*' 代表可以匹配零个或多个前面的那一个元素, 在这里前面的元素就是 'a'。因此，字符串 "aa" 可被视为 'a' 重复了一次。
 *
 * 示例 3：
 * 输入：s = "ab", p = ".*"
 * 输出：true
 * 解释：".*" 表示可匹配零个或多个（'*'）任意字符（'.'）。
 *
 * 提示：
 * 1 <= s.length <= 20
 * 1 <= p.length <= 30
 * s 只包含从 a-z 的小写字母。
 * p 只包含从 a-z 的小写字母，以及字符 . 和 *。
 * 保证每次出现字符 * 时，前面都匹配到有效的字符
 */
// @lc code=start
class RegularExpressionMatching {
    // 备忘录，-1 代表还未计算，0 代表 false，1 代表 true
    int[][] memo;

    /**
     * s 和 p 相互匹配的过程大致是，两个指针 i, j 分别在 s 和 p 上移动，如果最后两个指针都能移动到字符串的末尾，那么就匹配成功，反之则匹配失败。
     * 正则表达算法问题只需要把住一个基本点：看 s[i] 和 p[j] 两个字符是否匹配，一切逻辑围绕匹配/不匹配两种情况展开即可。
     * 动态规划算法的核心就是「状态」和「选择」，「状态」无非就是 i 和 j 两个指针的位置，「选择」就是模式串的 p[j] 选择匹配几个字符。
     * dp 函数的定义如下：
     * 若 dp(s, i, p, j) = true，则表示 s[i..] 可以匹配 p[j..]；若 dp(s, i, p, j) = false，则表示 s[i..] 无法匹配 p[j..]。
     */
    public boolean isMatch(String s, String p) {
        int m = s.length(), n = p.length();
        memo = new int[m][n];
        for (int i = 0; i < m; i++) {
            Arrays.fill(memo[i], -1);
        }
        // 指针 i，j 从索引 0 开始移动
        return dp(s, 0, p, 0);
    }

    /* 计算 p[j..] 是否匹配 s[i..] */
    private boolean dp(String s, int i, String p, int j) {
        int m = s.length(), n = p.length();
        // base case
        if (j == n) {
            return i == m;
        }
        if (i == m) {
            if ((n - j) % 2 == 1) {
                return false;
            }
            for (; j + 1 < n; j += 2) {
                if (p.charAt(j + 1) != '*') {
                    return false;
                }
            }
            return true;
        }

        // 查备忘录，防止重复计算
        if (memo[i][j] != -1) {
            return memo[i][j] == 1;
        }

        boolean res = false;
        if (s.charAt(i) == p.charAt(j) || p.charAt(j) == '.') {
            // 下一个字符, 即 j+1 位置是 *
            // p 指针 j 向后移动匹配  (* 可以匹配 1 个前面的字符)
            // 或者尝试匹配 s 中和 i 位置相同的字符 (* 可以匹配 n 个前面的字符)
            if (j < n - 1 && p.charAt(j + 1) == '*') {
                res = dp(s, i, p, j + 2)
                        || dp(s, i + 1, p, j);
            } else {
                // s[i] 和 p[j] 完成匹配
                res = dp(s, i + 1, p, j + 1);
            }
        } else {
            // 下一个字符, 即 j+1 位置是 *
            // p 指针 j 向后移动匹配 (* 可以匹配 0 个前面的字符)
            if (j < n - 1 && p.charAt(j + 1) == '*') {
                res = dp(s, i, p, j + 2);
            } else {
                res = false;
            }
        }
        // 将当前结果记入备忘录
        memo[i][j] = res ? 1 : 0;
        return res;
    }
}


/**
 * @lc app=leetcode.cn id=44 lang=java
 *
 * [44] 通配符匹配
 *
 * https://leetcode.cn/problems/wildcard-matching/description/
 *
 * algorithms
 * Hard (33.81%)
 * Likes:    1034
 * Dislikes: 0
 * Total Accepted:    135.9K
 * Total Submissions: 401.9K
 * Testcase Example:  '"aa"\n"a"'
 *
 * 给你一个输入字符串 (s) 和一个字符模式 (p) ，请你实现一个支持 '?' 和 '*' 匹配规则的通配符匹配：
 * '?' 可以匹配任何单个字符。
 * '*' 可以匹配任意字符序列（包括空字符序列）。
 * 判定匹配成功的充要条件是：字符模式必须能够 完全匹配 输入字符串（而不是部分匹配）。
 *
 * 示例 1：
 * 输入：s = "aa", p = "a"
 * 输出：false
 * 解释："a" 无法匹配 "aa" 整个字符串。
 *
 * 示例 2：
 * 输入：s = "aa", p = "*"
 * 输出：true
 * 解释：'*' 可以匹配任意字符串。
 *
 * 示例 3：
 * 输入：s = "cb", p = "?a"
 * 输出：false
 * 解释：'?' 可以匹配 'c', 但第二个 'a' 无法匹配 'b'。
 *
 * 提示：
 * 0 <= s.length, p.length <= 2000
 * s 仅由小写英文字母组成
 * p 仅由小写英文字母、'?' 或 '*' 组成
 */

// @lc code=start
class WildcardMatching {
    // 备忘录，-1 代表还未计算，0 代表 false，1 代表 true
    private int[][] memo;

    /**
     * 这道题和 10. 正则表达式匹配 几乎一样，这里的 ? 通配符就是第 10 题的 .，这里的 * 就是第 10 题的 .* 组合，
     * 所以一个投机取巧的解法就是把本题中的通配符转化成第 10 题的形式，然后直接套用第 10 题的解法。
     * 如果不投机取巧，照着第 10 题的解法逻辑也可以写出本题的解法，唯一需要注意的是本题的测试用例可能出现很多 * 连续出现的情况，
     * 很容易看出连续多个 * 和一个 * 的通配效果是一样的，所以我们可以提前删除连续的 * 以便提升一些效率。
     */
    public boolean isMatch(String s, String p) {
        if (p.isEmpty()) {
            return s.isEmpty();
        }
        // 将 p 中相邻的 * 去除，以提升效率
        String pp = removeAdjStar(p);
        int m = s.length(), n = pp.length();
        // 备忘录初始化为 -1
        memo = new int[m][n];
        for (int i = 0; i < m; i++) {
            Arrays.fill(memo[i], -1);
        }
        // 执行自顶向下带备忘录的动态规划
        return dp(s, 0, pp, 0);
    }

    // 定义：判断 s[i..] 是否能被 p[j..] 匹配
    private boolean dp(String s, int i, String p, int j) {
        // base case
        if (j == p.length()) {
            return i == s.length();
        }
        if (i == s.length()) {
            for (int k = j; k < p.length(); k++) {
                if (p.charAt(k) != '*') {
                    return false;
                }
            }
            return true;
        }
        // 查备忘录，防止重复计算
        if (memo[i][j] != -1) {
            return memo[i][j] == 1;
        }

        boolean res = false;
        if (s.charAt(i) == p.charAt(j) || p.charAt(j) == '?') {
            // s[i] 和 p[j] 完成匹配
            res = dp(s, i + 1, p, j + 1);
        } else if (p.charAt(j) == '*') {
            // s[i] 和 p[j] 不匹配，但 p[j] 是通配符 *
            // 可以匹配 0 个或多个 s 中的字符，
            // 只要有一种情况能够完成匹配即可
            res = dp(s, i + 1, p, j)
                    || dp(s, i, p, j + 1);
        }
        // 将 s[i] 和 p[j] 的匹配结果存储备忘录
        memo[i][j] = res ? 1 : 0;

        return res;
    }

    // 删除相邻的 * 号，返回删除后的字符
    private String removeAdjStar(String p) {
        if (p.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(p.charAt(0));
        for (int i = 1; i < p.length(); i++) {
            if (p.charAt(i) == '*' && p.charAt(i - 1) == '*') {
                continue;
            }
            sb.append(p.charAt(i));
        }
        return sb.toString();
    }
}
// @lc code=end
