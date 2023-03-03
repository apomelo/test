package test.algorithm.leetcode;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 回溯
 * @author C
 * @date 2023/2/16
 */
@Slf4j
public class AlgoBackTracking {
    public static void main(String[] args) {
        // 电话号码的字母组合
        log.info("LetterCombinationsOfAPhoneNumber: {}", new LetterCombinationsOfAPhoneNumber().letterCombinations("23"));
        log.info("LetterCombinationsOfAPhoneNumber: {}", new LetterCombinationsOfAPhoneNumber().letterCombinations(""));
        log.info("LetterCombinationsOfAPhoneNumber: {}", new LetterCombinationsOfAPhoneNumber().letterCombinations("2"));
        log.info("GenerateParentheses: {}", new GenerateParentheses().generateParenthesis(3));
        log.info("GenerateParentheses: {}", new GenerateParentheses().generateParenthesis(1));
    }
}

/**
 * @lc app=leetcode.cn id=17 lang=java
 *
 * [17] 电话号码的字母组合
 *
 * https://leetcode.cn/problems/letter-combinations-of-a-phone-number/description/
 *
 * algorithms
 * Medium (58.04%)
 * Likes:    2303
 * Dislikes: 0
 * Total Accepted:    634.7K
 * Total Submissions: 1.1M
 * Testcase Example:  '"23"'
 *
 * 给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。答案可以按 任意顺序 返回。
 * 给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。
 *
 * 示例 1：
 * 输入：digits = "23"
 * 输出：["ad","ae","af","bd","be","bf","cd","ce","cf"]
 *
 * 示例 2：
 * 输入：digits = ""
 * 输出：[]
 *
 * 示例 3：
 * 输入：digits = "2"
 * 输出：["a","b","c"]
 *
 * 提示：
 * 0 <= digits.length <= 4
 * digits[i] 是范围 ['2', '9'] 的一个数字。
 */
// @lc code=start
class LetterCombinationsOfAPhoneNumber {
    // 每个数字到字母的映射
    String[] mapping = new String[] {
            "", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"
    };

    List<String> res = new LinkedList<>();

    public List<String> letterCombinations(String digits) {
        if (digits.isEmpty()) {
            return res;
        }
        // 从 digits[0] 开始进行回溯
        backtrack(digits, 0, new StringBuilder());
        return res;
    }

    // 回溯算法主函数
    private void backtrack(String digits, int start, StringBuilder sb) {
        if (digits.length() == sb.length()) {
            // 到达回溯树底部
            res.add(sb.toString());
            return;
        }

        // 回溯算法框架
        for (int i = start; i < digits.length(); i ++) {
            int digit = digits.charAt(i) - '0';
            for (char c : mapping[digit].toCharArray()) {
                // 做选择
                sb.append(c);
                // 递归下一层回溯树
                backtrack(digits, i + 1, sb);
                // 撤销选择
                sb.deleteCharAt(sb.length() - 1);
            }
        }
    }
}
// @lc code=end

/**
 * @lc app=leetcode.cn id=22 lang=java
 *
 * [22] 括号生成
 *
 * https://leetcode.cn/problems/generate-parentheses/description/
 *
 * algorithms
 * Medium (77.59%)
 * Likes:    3103
 * Dislikes: 0
 * Total Accepted:    656K
 * Total Submissions: 845.7K
 * Testcase Example:  '3'
 *
 * 数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。
 *
 * 示例 1：
 * 输入：n = 3
 * 输出：["((()))","(()())","(())()","()(())","()()()"]
 *
 * 示例 2：
 * 输入：n = 1
 * 输出：["()"]
 *
 * 提示：
 * 1 <= n <= 8
 */
// @lc code=start
class GenerateParentheses {
    public List<String> generateParenthesis(int n) {
        List<String> res = new ArrayList<>();
        StringBuilder track = new StringBuilder();
        backtrack(n, n, track, res);
        return res;
    }

    /**
     * 回溯算法
     * @param left 剩余的左括号数量
     * @param right 剩余的右括号数量
     * @param track 回溯字符串
     * @param res 存储符合条件的字符串
     */
    private void backtrack(int left, int right, StringBuilder track, List<String> res) {
        // 若左括号剩下的多，说明不合法
        if (left > right) {
            return;
        }
        // 数量小于 0 不合法
        if (left < 0 || right < 0) {
            return;
        }
        // 当所有括号都恰好用完时，得到一个合法的括号组合
        if (left == 0 && right == 0) {
            res.add(track.toString());
            return;
        }

        // 尝试放一个左括号
        track.append('('); // 选择
        backtrack(left - 1, right, track, res);
        track.deleteCharAt(track.length() - 1); // 撤消选择

        // 尝试放一个右括号
        track.append(')'); // 选择
        backtrack(left, right - 1, track, res);
        track.deleteCharAt(track.length() - 1); // 撤消选择
    }
}
// @lc code=end
