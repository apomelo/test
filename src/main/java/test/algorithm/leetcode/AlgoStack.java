package test.algorithm.leetcode;

import lombok.extern.slf4j.Slf4j;

/**
 * 栈
 * @author C
 * @date 2023/2/24
 */
@Slf4j
public class AlgoStack {
    public static void main(String[] args) {
        // 模板
        log.info("template: {}", new AlgoStack());
    }
}

/**
 * @lc app=leetcode.cn id=20 lang=java
 *
 * [20] 有效的括号
 *
 * https://leetcode.cn/problems/valid-parentheses/description/
 *
 * algorithms
 * Easy (44.36%)
 * Likes:    3741
 * Dislikes: 0
 * Total Accepted:    1.4M
 * Total Submissions: 3.1M
 * Testcase Example:  '"()"'
 *
 * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。
 * 有效字符串需满足：
 * 左括号必须用相同类型的右括号闭合。
 * 左括号必须以正确的顺序闭合。
 * 每个右括号都有一个对应的相同类型的左括号。
 *
 * 示例 1：
 * 输入：s = "()"
 * 输出：true
 *
 * 示例 2：
 * 输入：s = "()[]{}"
 * 输出：true
 *
 * 示例 3：
 * 输入：s = "(]"
 * 输出：false
 *
 * 提示：
 * 1 <= s.length <= 10^4
 * s 仅由括号 '()[]{}' 组成
 */
// @lc code=start
class ValidParentheses {
    public boolean validParentheses(String s) {
        return false;
    }
}
// @lc code=end
