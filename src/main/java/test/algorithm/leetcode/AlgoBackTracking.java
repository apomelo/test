package test.algorithm.leetcode;

import lombok.extern.slf4j.Slf4j;

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
