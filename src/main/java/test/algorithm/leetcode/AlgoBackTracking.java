package test.algorithm.leetcode;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
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
        // 括号生成
        log.info("GenerateParentheses: {}", new GenerateParentheses().generateParenthesis(3));
        log.info("GenerateParentheses: {}", new GenerateParentheses().generateParenthesis(1));
        // 组合总和
        log.info("CombinationSum: {}", new CombinationSum().combinationSum(new int[] {2,3,6,7}, 7));
        log.info("CombinationSum: {}", new CombinationSum().combinationSum(new int[] {2,3,5}, 8));
        log.info("CombinationSum: {}", new CombinationSum().combinationSum(new int[] {2}, 1));
        // 组合总和 II
        log.info("CombinationSumII: {}", new CombinationSumII().combinationSum2(new int[] {10,1,2,7,6,1,5}, 8));
        log.info("CombinationSumII: {}", new CombinationSumII().combinationSum2(new int[] {2,5,2,1,2}, 5));
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


/**
 * @lc app=leetcode.cn id=39 lang=java
 *
 * [39] 组合总和
 *
 * https://leetcode.cn/problems/combination-sum/description/
 *
 * algorithms
 * Medium (72.47%)
 * Likes:    2446
 * Dislikes: 0
 * Total Accepted:    715.6K
 * Total Submissions: 987.7K
 * Testcase Example:  '[2,3,6,7]\n7'
 *
 * 给你一个 无重复元素 的整数数组 candidates 和一个目标整数 target ，找出 candidates 中可以使数字和为目标数 target
 * 的 所有 不同组合 ，并以列表形式返回。你可以按 任意顺序 返回这些组合。
 * candidates 中的 同一个 数字可以 无限制重复被选取 。如果至少一个数字的被选数量不同，则两种组合是不同的。
 * 对于给定的输入，保证和为 target 的不同组合数少于 150 个。
 *
 * 示例 1：
 * 输入：candidates = [2,3,6,7], target = 7
 * 输出：[[2,2,3],[7]]
 * 解释：
 * 2 和 3 可以形成一组候选，2 + 2 + 3 = 7 。注意 2 可以使用多次。
 * 7 也是一个候选， 7 = 7 。
 * 仅有这两种组合。
 *
 * 示例 2：
 * 输入: candidates = [2,3,5], target = 8
 * 输出: [[2,2,2,2],[2,3,3],[3,5]]
 *
 * 示例 3：
 * 输入: candidates = [2], target = 1
 * 输出: []
 *
 * 提示：
 * 1 <= candidates.length <= 30
 * 2 <= candidates[i] <= 40
 * candidates 的所有元素 互不相同
 * 1 <= target <= 40
 */
// @lc code=start
class CombinationSum {
    List<List<Integer>> res = new LinkedList<>();
    // 记录回溯的路径
    LinkedList<Integer> track = new LinkedList<>();

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        if (candidates.length == 0) {
            return res;
        }
        backtrack(candidates, 0, target, 0);
        return res;
    }

    // 回溯算法主函数
    private void backtrack(int[] candidates, int start, int target, int sum) {
        // 找到目标和
        if (sum == target) {
            res.add(new LinkedList<>(track));
            return;
        }

        // 超过目标和，直接结束
        if (sum > target) {
            return;
        }

        // 回溯算法框架
        for (int i = start; i < candidates.length; i ++) {
            // 选择 candidates[i]
            track.add(candidates[i]);
            sum += candidates[i];
            // 递归遍历下一层回溯树
            backtrack(candidates, i, target, sum);
            // 撤销选择 candidates[i]
            sum -= candidates[i];
            track.removeLast();
        }
    }
}
// @lc code=end


/**
 * @lc app=leetcode.cn id=40 lang=java
 *
 * [40] 组合总和 II
 *
 * https://leetcode.cn/problems/combination-sum-ii/description/
 *
 * algorithms
 * Medium (59.95%)
 * Likes:    1314
 * Dislikes: 0
 * Total Accepted:    417K
 * Total Submissions: 695.7K
 * Testcase Example:  '[10,1,2,7,6,1,5]\n8'
 *
 * 给定一个候选人编号的集合 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。
 * candidates 中的每个数字在每个组合中只能使用 一次 。
 * 注意：解集不能包含重复的组合。
 *
 * 示例 1:
 * 输入: candidates = [10,1,2,7,6,1,5], target = 8,
 * 输出:
 * [
 * [1,1,6],
 * [1,2,5],
 * [1,7],
 * [2,6]
 * ]
 *
 * 示例 2:
 * 输入: candidates = [2,5,2,1,2], target = 5,
 * 输出:
 * [
 * [1,2,2],
 * [5]
 * ]
 *
 * 提示:
 * 1 <= candidates.length <= 100
 * 1 <= candidates[i] <= 50
 * 1 <= target <= 30
 */
// @lc code=start
class CombinationSumII {
    // 结果
    LinkedList<List<Integer>> res = new LinkedList<>();
    // 记录回溯的路径
    LinkedList<Integer> track = new LinkedList<>();

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        if (candidates.length == 0) {
            return res;
        }
        Arrays.sort(candidates);
        backtrack(candidates, 0, target, 0);
        return res;
    }

    // 回溯算法主函数
    private void backtrack(int[] candidates, int start, int target, int sum) {
        // 找到目标和
        if (sum == target) {
            res.add(new LinkedList<>(track));
            return;
        }
        // 超过目标和，直接结束
        if (sum > target) {
            return;
        }

        // 回溯
        // 因为 1 <= candidates[i] <= 50, 所以这里可以设置为 0
        int last = 0;
        for (int i = start; i < candidates.length; i ++) {
            // 不能有元素数值一致的集合
            if (candidates[i] == last) {
                continue;
            }
            last = candidates[i];
            // 选择 candidates[i]
            track.add(candidates[i]);
            sum += candidates[i];
            // 递归遍历下一层回溯树
            backtrack(candidates, i + 1, target, sum);
            // 撤销选择 candidates[i]
            sum -= candidates[i];
            track.removeLast();
        }
    }
}
// @lc code=end
