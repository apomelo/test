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
        // 全排列
        log.info("Permutations: {}", new Permutations().permute(new int[] {1,2,3}));
        log.info("Permutations: {}", new Permutations().permute(new int[] {0,1}));
        log.info("Permutations: {}", new Permutations().permute(new int[] {1}));
        // 全排列 II
        log.info("PermutationsII: {}", new PermutationsII().permuteUnique(new int[] {1,1,2}));
        log.info("PermutationsII: {}", new PermutationsII().permuteUnique(new int[] {1,2,3}));
        // N 皇后
        log.info("NQueens: {}", new NQueens().solveNQueens(4));
        log.info("NQueens: {}", new NQueens().solveNQueens(1));
        // N 皇后 II
        log.info("NQueensII: {}", new NQueensII().totalNQueens(4));
        log.info("NQueensII: {}", new NQueensII().totalNQueens(1));
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


/**
 * @lc app=leetcode.cn id=46 lang=java
 *
 * [46] 全排列
 *
 * https://leetcode.cn/problems/permutations/description/
 *
 * algorithms
 * Medium (78.87%)
 * Likes:    2534
 * Dislikes: 0
 * Total Accepted:    852.7K
 * Total Submissions: 1.1M
 * Testcase Example:  '[1,2,3]'
 *
 * 给定一个不含重复数字的数组 nums ，返回其 所有可能的全排列 。你可以 按任意顺序 返回答案。
 *
 * 示例 1：
 * 输入：nums = [1,2,3]
 * 输出：[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
 *
 * 示例 2：
 * 输入：nums = [0,1]
 * 输出：[[0,1],[1,0]]
 *
 * 示例 3：
 * 输入：nums = [1]
 * 输出：[[1]]
 *
 * 提示：
 * 1 <= nums.length <= 6
 * -10 <= nums[i] <= 10
 * nums 中的所有整数 互不相同
 */
// @lc code=start
class Permutations {
    List<List<Integer>> res = new ArrayList<>();

    /**
     * 主函数，输入一组不重复的数字，返回它们的全排列
     */
    public List<List<Integer>> permute(int[] nums) {
        // 记录「路径」
        LinkedList<Integer> track = new LinkedList<>();
        // 「路径」中的元素会被标记为 true，避免重复使用
        boolean[] used = new boolean[nums.length];
        backtrack(nums, track, used);
        return res;
    }

    /**
     * 路径：记录在 track 中
     * 选择列表：nums 中不存在于 track 的那些元素（used[i] 为 false）
     * 结束条件：nums 中的元素全都在 track 中出现
     */
    private void backtrack(int[] nums, LinkedList<Integer> track, boolean[] used) {
        // 触发结束条件
        if (track.size() == nums.length) {
            res.add(new ArrayList<>(track));
            return;
        }

        // 回溯主体
        for (int i = 0; i < nums.length; i++) {
            // 排除不合法的选择
            if (used[i]) {
                // nums[i] 已经在 track 中，跳过
                continue;
            }
            // 选择
            track.add(nums[i]);
            used[i] = true;
            // 进入下一层决策树
            backtrack(nums, track, used);
            // 撤销选择
            track.removeLast();
            used[i] = false;
        }
    }
}
// @lc code=end


/**
 * @lc app=leetcode.cn id=47 lang=java
 *
 * [47] 全排列 II
 *
 * https://leetcode.cn/problems/permutations-ii/description/
 *
 * algorithms
 * Medium (65.48%)
 * Likes:    1356
 * Dislikes: 0
 * Total Accepted:    450.2K
 * Total Submissions: 687.4K
 * Testcase Example:  '[1,1,2]'
 *
 * 给定一个可包含重复数字的序列 nums ，按任意顺序 返回所有不重复的全排列。
 *
 * 示例 1：
 * 输入：nums = [1,1,2]
 * 输出：
 * [[1,1,2],
 *  [1,2,1],
 *  [2,1,1]]
 *
 * 示例 2：
 * 输入：nums = [1,2,3]
 * 输出：[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
 *
 * 提示：
 * 1 <= nums.length <= 8
 * -10 <= nums[i] <= 10
 */
// @lc code=start
class PermutationsII {
    List<List<Integer>> res = new ArrayList<>();
    /**
     * 主函数，输入一组 可以 重复的数字，返回它们的全排列
     */
    public List<List<Integer>> permuteUnique(int[] nums) {
        // 记录「路径」
        LinkedList<Integer> track = new LinkedList<>();
        // 「路径」中的元素会被标记为 true，避免重复使用
        boolean[] used = new boolean[nums.length];
        Arrays.sort(nums);
        backtrack(nums, track, used);
        return res;
    }

    /**
     * 路径：记录在 track 中
     * 选择列表：nums 中不存在于 track 的那些元素（used[i] 为 false）
     * 结束条件：nums 中的元素全都在 track 中出现
     */
    private void backtrack(int[] nums, LinkedList<Integer> track, boolean[] used) {
        // 触发结束条件
        if (track.size() == nums.length) {
            res.add(new ArrayList<>(track));
            return;
        }

        // 回溯算法主体
        int last = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            // 排除不合法的选择
            // nums[i] 已经在 track 中，跳过
            // 和上一个数相同，跳过
            if (used[i] || nums[i] == last) {
                continue;
            }
            last = nums[i];
            // 选择
            track.add(nums[i]);
            used[i] = true;
            // 进入下一层决策树
            backtrack(nums, track, used);
            // 撤销选择
            track.removeLast();
            used[i] = false;
        }
    }
}
// @lc code=end


/**
 * @lc app=leetcode.cn id=51 lang=java
 *
 * [51] N 皇后
 *
 * https://leetcode.cn/problems/n-queens/description/
 *
 * algorithms
 * Hard (74.16%)
 * Likes:    1755
 * Dislikes: 0
 * Total Accepted:    303.5K
 * Total Submissions: 409.4K
 * Testcase Example:  '4'
 *
 * 按照国际象棋的规则，皇后可以攻击与之处在同一行或同一列或同一斜线上的棋子。
 * n 皇后问题 研究的是如何将 n 个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击。
 * 给你一个整数 n ，返回所有不同的 n 皇后问题 的解决方案。
 * 每一种解法包含一个不同的 n 皇后问题 的棋子放置方案，该方案中 'Q' 和 '.' 分别代表了皇后和空位。
 *
 * 示例 1：
 * 输入：n = 4
 * 输出：[[".Q..","...Q","Q...","..Q."],["..Q.","Q...","...Q",".Q.."]]
 * 解释：如上所示，4 皇后问题存在两个不同的解法。
 *
 * 示例 2：
 * 输入：n = 1
 * 输出：[["Q"]]
 *
 * 提示：
 * 1 <= n <= 9
 */
// @lc code=start
class NQueens {
    List<List<String>> res = new ArrayList<>();

    /**
     * 输入棋盘边长 n，返回所有合法的放置
     */
    public List<List<String>> solveNQueens(int n) {
        // '.' 表示空，'Q' 表示皇后，初始化空棋盘。
        List<String> board = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append('.');
        }
        for (int i = 0; i < n; i++) {
            board.add(sb.toString());
        }
        backtrack(board, 0);
        return res;
    }

    /**
     * 路径：board 中小于 row 的那些行都已经成功放置了皇后
     * 选择列表：第 row 行的所有列都是放置皇后的选择
     * 结束条件：row 超过 board 的最后一行
     */
    private void backtrack(List<String> board, int row) {
        if (row == board.size()) {
            res.add(new ArrayList<>(board));
            return;
        }

        int n = board.get(row).length();
        for (int col = 0; col < n; col++) {
            // 排除不合法选择
            if (!isValid(board, row, col)) {
                continue;
            }
            // 选择
            char[] arr = board.get(row).toCharArray();
            arr[col] = 'Q';
            board.set(row, String.valueOf(arr));
            // 进入下一层决策树
            backtrack(board, row + 1);
            // 撤销选择
            arr[col] = '.';
            board.set(row, String.valueOf(arr));
        }
    }

    /**
     * 是否可以在 board[row][col] 放置皇后
     */
    private boolean isValid(List<String> board, int row, int col) {
        int n = board.size();
        // 检查列是否有皇后互相冲突 (检查到board[row][col]位置)
        for (int i = 0; i <= row; i++) {
            if (board.get(i).charAt(col) == 'Q') {
                return false;
            }
        }
        // 检查左上方是否有皇后相互冲突 (上面已检查过board[row][col]位置)
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
            if (board.get(i).charAt(j) == 'Q') {
                return false;
            }
        }
        // 检查右上方是否有皇后互相冲突 (上面已检查过board[row][col]位置)
        for (int i = row - 1, j = col + 1; i >= 0 && j < n; i--, j++) {
            if (board.get(i).charAt(j) == 'Q') {
                return false;
            }
        }
        return true;
    }
}
// @lc code=end


/*
 * @lc app=leetcode.cn id=52 lang=java
 *
 * [52] N 皇后 II
 *
 * https://leetcode.cn/problems/n-queens-ii/description/
 *
 * algorithms
 * Hard (82.42%)
 * Likes:    431
 * Dislikes: 0
 * Total Accepted:    119K
 * Total Submissions: 144.4K
 * Testcase Example:  '4'
 *
 * n 皇后问题 研究的是如何将 n 个皇后放置在 n × n 的棋盘上，并且使皇后彼此之间不能相互攻击。
 * 给你一个整数 n ，返回 n 皇后问题 不同的解决方案的数量。
 *
 * 示例 1：
 * 输入：n = 4
 * 输出：2
 * 解释：如上图所示，4 皇后问题存在两个不同的解法。
 *
 * 示例 2：
 * 输入：n = 1
 * 输出：1
 *
 * 提示：
 * 1 <= n <= 9
 */
// @lc code=start
class NQueensII {
    private int count = 0;
    public int totalNQueens(int n) {
        // 初始化路径
        boolean[][] track = new boolean[n][n];
        backtrack(track, 0);
        return count;
    }

    /**
     * 路径：track 中小于 row 的那些行都已经成功放置了皇后
     * 选择列表：第 row 行的所有列都是放置皇后的选择
     * 结束条件：row 超过 track 的最后一行
     */
    private void backtrack(boolean[][] track, int row) {
        int n = track[0].length;
        // 触发结束条件
        if (row == track.length) {
            count ++;
            return;
        }

        // 回溯主体
        for (int col = 0; col < n; col++) {
            // 排除不合法的选择
            if (!isValid(track, row, col)) {
                continue;
            }
            // 选择
            track[row][col] = true;
            // 进入下一层决策树
            backtrack(track, row + 1);
            // 撤销选择
            track[row][col] = false;
        }
    }

    private boolean isValid(boolean[][] track, int row, int col) {
        int n = track[0].length;
        // 检查列是否有皇后互相冲突 (检查到track[row][col]位置)
        for (int i = 0; i <= row; i ++) {
            if (track[i][col]) {
                return false;
            }
        }
        // 检查左上方是否有皇后相互冲突 (上面已检查过track[row][col]位置)
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
            if (track[i][j]) {
                return false;
            }
        }
        // 检查右上方是否有皇后互相冲突 (上面已检查过track[row][col]位置)
        for (int i = row - 1, j = col + 1; i >= 0 && j < n; i--, j++) {
            if (track[i][j]) {
                return false;
            }
        }
        return true;
    }
}
// @lc code=end
