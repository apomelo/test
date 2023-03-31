package test.algorithm.leetcode;

import lombok.extern.slf4j.Slf4j;

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
// @lc code=start
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
// @lc code=end
