package test.algorithm.leetcode;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

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
        // 排列序列
        log.info("PermutationSequence: {}", new PermutationSequence().getPermutation(3, 3));
        log.info("PermutationSequence: {}", new PermutationSequence().getPermutation(4, 9));
        log.info("PermutationSequence: {}", new PermutationSequence().getPermutation(3, 1));
        // 组合
        log.info("Combinations: {}", new Combinations().combine(4, 2));
        log.info("Combinations: {}", new Combinations().combine(1, 1));
        // 子集
        log.info("Subsets: {}", new Subsets().subsets(new int[] {1,2,3}));
        log.info("Subsets: {}", new Subsets().subsets(new int[] {0}));
        // 单词搜索
        log.info("WordSearch: {}", new WordSearch().exist(new char[][] {{'A','B','C','E'},{'S','F','C','S'},{'A','D','E','E'}}, "ABCCED"));
        log.info("WordSearch: {}", new WordSearch().exist(new char[][] {{'A','B','C','E'},{'S','F','C','S'},{'A','D','E','E'}}, "SEE"));
        log.info("WordSearch: {}", new WordSearch().exist(new char[][] {{'A','B','C','E'},{'S','F','C','S'},{'A','D','E','E'}}, "ABCB"));
        // 格雷编码
        log.info("GrayCode: {}", new GrayCode().grayCode(2));
        log.info("GrayCode: {}", new GrayCode().grayCode(1));
        // 子集 II
        log.info("SubsetsII: {}", new SubsetsII().subsetsWithDup(new int[] {1,2,2}));
        log.info("SubsetsII: {}", new SubsetsII().subsetsWithDup(new int[] {0}));
        // [93] 复原 IP 地址
        log.info("RestoreIpAddresses: {}", new RestoreIpAddresses().restoreIpAddresses("25525511135"));
        log.info("RestoreIpAddresses: {}", new RestoreIpAddresses().restoreIpAddresses("0000"));
        log.info("RestoreIpAddresses: {}", new RestoreIpAddresses().restoreIpAddresses("101023"));
        // [131] 分割回文串
        log.info("PalindromePartitioning: {}", new PalindromePartitioning().partition("aab"));
        log.info("PalindromePartitioning: {}", new PalindromePartitioning().partition("a"));
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


/**
 * @lc app=leetcode.cn id=60 lang=java
 *
 * [60] 排列序列
 *
 * https://leetcode.cn/problems/permutation-sequence/description/
 *
 * algorithms
 * Hard (53.40%)
 * Likes:    773
 * Dislikes: 0
 * Total Accepted:    128K
 * Total Submissions: 239.7K
 * Testcase Example:  '3\n3'
 *
 * 给出集合 [1,2,3,...,n]，其所有元素共有 n! 种排列。
 * 按大小顺序列出所有排列情况，并一一标记，当 n = 3 时, 所有排列如下：
 * "123"
 * "132"
 * "213"
 * "231"
 * "312"
 * "321"
 * 给定 n 和 k，返回第 k 个排列。
 *
 * 示例 1：
 * 输入：n = 3, k = 3
 * 输出："213"
 *
 * 示例 2：
 * 输入：n = 4, k = 9
 * 输出："2314"
 *
 * 示例 3：
 * 输入：n = 3, k = 1
 * 输出："123"
 *
 * 提示：
 * 1 <= n <= 9
 * 1 <= k <= n!
 */
class PermutationSequence {
    private int cur = 0;
    private String res = "";

    public String getPermutation(int n, int k) {
        boolean[] used = new boolean[n];
        StringBuilder track = new StringBuilder();
        backtrack(n, k, track, used);
        return res;
    }

    private void backtrack(int n, int k, StringBuilder track, boolean[] used) {
        // 已找到 k
        if (res.length() > 0) {
            return;
        }
        // 触发结束条件
        if (track.length() == n) {
            if (++cur >= k) {
                res = track.toString();
            }
            return;
        }
        // 回溯算法主体
        for(int i = 1; i <= n; i++) {
            // 排除不合法选择
            if (used[i - 1]) {
                continue;
            }
            // 选择
            track.append(i);
            used[i - 1] = true;
            // 进入下一层决策树
            backtrack(n, k, track, used);
            // 撤销选择
            track.deleteCharAt(track.length() - 1);
            used[i - 1] = false;
        }
    }
}


/**
 * @lc app=leetcode.cn id=77 lang=java
 *
 * [77] 组合
 *
 * https://leetcode.cn/problems/combinations/description/
 *
 * algorithms
 * Medium (77.11%)
 * Likes:    1413
 * Dislikes: 0
 * Total Accepted:    550.9K
 * Total Submissions: 714.3K
 * Testcase Example:  '4\n2'
 *
 * 给定两个整数 n 和 k，返回范围 [1, n] 中所有可能的 k 个数的组合。
 * 你可以按 任何顺序 返回答案。
 *
 * 示例 1：
 * 输入：n = 4, k = 2
 * 输出：
 * [
 *   [2,4],
 *   [3,4],
 *   [2,3],
 *   [1,2],
 *   [1,3],
 *   [1,4],
 * ]
 *
 * 示例 2：
 * 输入：n = 1, k = 1
 * 输出：[[1]]
 *
 * 提示：
 * 1 <= n <= 20
 * 1 <= k <= n
 */
class Combinations {
    private List<List<Integer>> res = new ArrayList<>();

    public List<List<Integer>> combine(int n, int k) {
        if (n < k) {
            return res;
        }
        Set<Integer> back = new HashSet<>();
        backtrack(n, k, 1, back);
        return res;
    }

    private void backtrack(int n, int k, int cur, Set<Integer> back) {
        // 满足条件
        if (back.size() == k) {
            res.add(new ArrayList<>(back));
        }

        // 回溯
        for (int i = cur; i <= n; i++) {
            // 排除不合法的选择: 已在本次路径中
            if (back.contains(i)) {
                continue;
            }
            // 选择
            back.add(i);
            // 进入下一层决策树
            backtrack(n, k, i + 1, back);
            // 撤销选择
            back.remove(i);
        }
    }
}


/**
 * @lc app=leetcode.cn id=78 lang=java
 *
 * [78] 子集
 *
 * https://leetcode.cn/problems/subsets/description/
 *
 * algorithms
 * Medium (81.11%)
 * Likes:    2069
 * Dislikes: 0
 * Total Accepted:    640.3K
 * Total Submissions: 789.5K
 * Testcase Example:  '[1,2,3]'
 *
 * 给你一个整数数组 nums ，数组中的元素 互不相同 。返回该数组所有可能的子集（幂集）。
 * 解集 不能 包含重复的子集。你可以按 任意顺序 返回解集。
 *
 * 示例 1：
 * 输入：nums = [1,2,3]
 * 输出：[[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]
 *
 * 示例 2：
 * 输入：nums = [0]
 * 输出：[[],[0]]
 *
 * 提示：
 * 1 <= nums.length <= 10
 * -10 <= nums[i] <= 10
 * nums 中的所有元素 互不相同
 */
class Subsets {
    private List<List<Integer>> res = new ArrayList<>();

    public List<List<Integer>> subsets(int[] nums) {
        List<Integer> back = new ArrayList<>();
        backtrack(nums, 0, back);
        return res;
    }

    private void backtrack(int[] nums, int cur, List<Integer> back) {
        // 不需要判断退出条件
        // 加入结果集
        res.add(new ArrayList<>(back));

        // 回溯
        for (int i = cur; i < nums.length; i++) {
            // 选择
            back.add(nums[i]);
            // 进入下一层决策树
            backtrack(nums, i + 1, back);
            // 撤销选择
            back.remove(back.size() - 1);
        }
    }
}


/**
 * @lc app=leetcode.cn id=79 lang=java
 *
 * [79] 单词搜索
 *
 * https://leetcode.cn/problems/word-search/description/
 *
 * algorithms
 * Medium (46.26%)
 * Likes:    1621
 * Dislikes: 0
 * Total Accepted:    434.6K
 * Total Submissions: 939.5K
 * Testcase Example:  '[["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]]\n"ABCCED"'
 *
 * 给定一个 m x n 二维字符网格 board 和一个字符串单词 word 。如果 word 存在于网格中，返回 true ；否则，返回 false。
 * 单词必须按照字母顺序，通过相邻的单元格内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。同一个单元格内的字母不允许被重复使用。
 *
 * 示例 1：
 * 输入：board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word =
 * "ABCCED"
 * 输出：true
 *
 * 示例 2：
 * 输入：board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word =
 * "SEE"
 * 输出：true
 *
 * 示例 3：
 * 输入：board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word =
 * "ABCB"
 * 输出：false
 *
 * 提示：
 * m == board.length
 * n = board[i].length
 * 1 <= m, n <= 6
 * 1 <= word.length <= 15
 * board 和 word 仅由大小写英文字母组成
 *
 * 进阶：你可以使用搜索剪枝的技术来优化解决方案，使其在 board 更大的情况下可以更快解决问题？
 */
class WordSearch {
    private boolean found = false;
    private int[][] dir = new int[][] {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public boolean exist(char[][] board, String word) {
        int m = board.length, n = board[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                dfs(board, i, j, word, 0);
                if (found) {
                    return true;
                }
            }
        }
        return false;
    }

    // 从 (i, j) 开始向四周搜索，试图匹配 word[p..]
    void dfs(char[][] board, int i, int j, String word, int p) {
        if (p == word.length()) {
            // 整个 word 已经被匹配完，找到了一个答案
            found = true;
            return;
        }
        if (found) {
            // 已经找到了一个答案，不用再搜索了
            return;
        }
        int m = board.length, n = board[0].length;
        if (i < 0 || j < 0 || i >= m || j >= n) {
            return;
        }
        if (board[i][j] != word.charAt(p)) {
            return;
        }

        // 已经匹配过的字符，我们给它添一个负号作为标记，避免走回头路
        board[i][j] = (char)(-board[i][j]);
        // word[p] 被 board[i][j] 匹配，开始向四周搜索 word[p+1..]
        for (int k = 0; k < 4; k++) {
            dfs(board, i + dir[k][0], j + dir[k][1], word, p + 1);
        }
        board[i][j] = (char)(-board[i][j]);
    }
}


/**
 * @lc app=leetcode.cn id=89 lang=java
 *
 * [89] 格雷编码
 *
 * https://leetcode.cn/problems/gray-code/description/
 *
 * algorithms
 * Medium (75.52%)
 * Likes:    633
 * Dislikes: 0
 * Total Accepted:    119.6K
 * Total Submissions: 158.3K
 * Testcase Example:  '2'
 *
 * n 位格雷码序列 是一个由 2^n 个整数组成的序列，其中：
 * 每个整数都在范围 [0, 2^n - 1] 内（含 0 和 2^n - 1）
 * 第一个整数是 0
 * 一个整数在序列中出现 不超过一次
 * 每对 相邻 整数的二进制表示 恰好一位不同 ，且
 * 第一个 和 最后一个 整数的二进制表示 恰好一位不同
 *
 * 给你一个整数 n ，返回任一有效的 n 位格雷码序列 。
 *
 * 示例 1：
 * 输入：n = 2
 * 输出：[0,1,3,2]
 * 解释：
 * [0,1,3,2] 的二进制表示是 [00,01,11,10] 。
 * - 00 和 01 有一位不同
 * - 01 和 11 有一位不同
 * - 11 和 10 有一位不同
 * - 10 和 00 有一位不同
 * [0,2,3,1] 也是一个有效的格雷码序列，其二进制表示是 [00,10,11,01] 。
 * - 00 和 10 有一位不同
 * - 10 和 11 有一位不同
 * - 11 和 01 有一位不同
 * - 01 和 00 有一位不同
 *
 * 示例 2：
 * 输入：n = 1
 * 输出：[0,1]
 *
 * 提示：
 * 1 <= n <= 16
 */
class GrayCode {
    public List<Integer> grayCode(int n) {
        List<Integer> res = new ArrayList<>();
        // 初始值为 0
        res.add(0);

        for (int i = 0; i < n; i++) {
            int size = res.size();
            // 将当前结果集的元素逆序添加到结果集中，并在每个元素前面加上 1
            // 如 i = 2 时, 给 0 前面加 1 的操作: 00 -> 100
            // 如 i = 3 时, 给 0 前面加 1 的操作: 000 -> 1000
            for (int j = size - 1; j >= 0; j--) {
                res.add(res.get(j) | 1 << i);
            }
        }
        return res;
    }
}


/**
 * @lc app=leetcode.cn id=90 lang=java
 *
 * [90] 子集 II
 *
 * https://leetcode.cn/problems/subsets-ii/description/
 *
 * algorithms
 * Medium (63.60%)
 * Likes:    1120
 * Dislikes: 0
 * Total Accepted:    309.6K
 * Total Submissions: 486.7K
 * Testcase Example:  '[1,2,2]'
 *
 * 给你一个整数数组 nums ，其中可能包含重复元素，请你返回该数组所有可能的子集（幂集）。
 * 解集 不能 包含重复的子集。返回的解集中，子集可以按 任意顺序 排列。
 *
 * 示例 1：
 * 输入：nums = [1,2,2]
 * 输出：[[],[1],[1,2],[1,2,2],[2],[2,2]]
 *
 * 示例 2：
 * 输入：nums = [0]
 * 输出：[[],[0]]
 *
 * 提示：
 * 1 <= nums.length <= 10
 * -10 <= nums[i] <= 10
 */
class SubsetsII {
    private List<List<Integer>> res = new ArrayList<>();

    public List<List<Integer>> subsetsWithDup(int[] nums) {
        // 排序
        Arrays.sort(nums);
        // 记录「路径」
        List<Integer> track = new ArrayList<>();
        // 回溯
        backtrack(nums, 0, track);
        return res;
    }

    private void backtrack(int[] nums, int start, List<Integer> track) {
        // 加入结果集
        res.add(new ArrayList<>(track));
        // 判断退出条件
        if (start >= nums.length) {
            return;
        }

        // 回溯主体
        for (int i = start; i < nums.length; i++) {
            // 剪枝逻辑，值相同的相邻树枝，只遍历第一条
            if (i > start && nums[i] == nums[i - 1]) {
                continue;
            }
            // 选择
            track.add(nums[i]);
            // 进入下一层决策树
            backtrack(nums, i + 1, track);
            // 撤销选择
            track.remove(track.size() - 1);
        }
    }
}


/**
 * @lc app=leetcode.cn id=93 lang=java
 *
 * [93] 复原 IP 地址
 *
 * https://leetcode.cn/problems/restore-ip-addresses/description/
 *
 * algorithms
 * Medium (58.08%)
 * Likes:    1252
 * Dislikes: 0
 * Total Accepted:    348.3K
 * Total Submissions: 599.3K
 * Testcase Example:  '"25525511135"'
 *
 * 有效 IP 地址 正好由四个整数（每个整数位于 0 到 255 之间组成，且不能含有前导 0），整数之间用 '.' 分隔。
 *
 * 例如："0.1.2.201" 和 "192.168.1.1" 是 有效 IP 地址，但是 "0.011.255.245"、"192.168.1.312"
 * 和 "192.168@1.1" 是 无效 IP 地址。
 *
 * 给定一个只包含数字的字符串 s ，用以表示一个 IP 地址，返回所有可能的有效 IP 地址，这些地址可以通过在 s 中插入 '.' 来形成。
 * 你 不能 重新排序或删除 s 中的任何数字。你可以按 任何 顺序返回答案。
 *
 * 示例 1：
 * 输入：s = "25525511135"
 * 输出：["255.255.11.135","255.255.111.35"]
 *
 * 示例 2：
 * 输入：s = "0000"
 * 输出：["0.0.0.0"]
 *
 * 示例 3：
 * 输入：s = "101023"
 * 输出：["1.0.10.23","1.0.102.3","10.1.0.23","10.10.2.3","101.0.2.3"]
 *
 * 提示：
 * 1 <= s.length <= 20
 * s 仅由数字组成
 */
class RestoreIpAddresses {
    List<String> res = new ArrayList<>();
    List<String> track = new ArrayList<>();

    public List<String> restoreIpAddresses(String s) {
        backtrack(s, 0);
        return res;
    }

    // 回溯算法框架
    void backtrack(String s, int start) {
        if (start == s.length() && track.size() == 4) {
            // base case，走到叶子节点
            // 即整个 s 被成功分割为合法的四部分，记下答案
            res.add(String.join(".", track));
        }
        for (int i = start; i < s.length(); i++) {
            if (!isValid(s, start, i)) {
                // s[start..i] 不是合法的 ip 数字，不能分割
                continue;
            }
            if (track.size() >= 4) {
                // 已经分解成 4 部分了，不能再分解了
                break;
            }
            // s[start..i] 是一个合法的 ip 数字，可以进行分割
            // 做选择，把 s[start..i] 放入路径列表中
            track.add(s.substring(start, i + 1));
            // 进入回溯树的下一层，继续切分 s[i+1..]
            backtrack(s, i + 1);
            // 撤销选择
            track.remove(track.size() - 1);
        }
    }

    // 判断 s[start, end]
    boolean isValid(String s, int start, int end) {
        int length = end - start + 1;

        if (length <= 0 || length > 3) {
            return false;
        }

        // 多于一位数字，但开头是 0，不合法
        if (length > 1 && s.charAt(start) == '0') {
            return false;
        }

        // 数字大于 255，不合法
        if (Integer.parseInt(s.substring(start, start + length)) > 255) {
            return false;
        }

        // 如果只有一位数字，合法
        // 排除了开头是 0 的情况，那么如果是两位数，合法
        // 其他情况，合法
        return true;
    }
}


/**
 * @lc app=leetcode.cn id=131 lang=java
 *
 * [131] 分割回文串
 *
 * https://leetcode.cn/problems/palindrome-partitioning/description/
 *
 * algorithms
 * Medium (73.42%)
 * Likes:    1624
 * Dislikes: 0
 * Total Accepted:    322.8K
 * Total Submissions: 439.6K
 * Testcase Example:  '"aab"'
 *
 * 给你一个字符串 s，请你将 s 分割成一些子串，使每个子串都是 回文串 。返回 s 所有可能的分割方案。
 * 回文串 是正着读和反着读都一样的字符串。
 *
 * 示例 1：
 * 输入：s = "aab"
 * 输出：[["a","a","b"],["aa","b"]]
 *
 * 示例 2：
 * 输入：s = "a"
 * 输出：[["a"]]
 *
 * 提示：
 * 1 <= s.length <= 16
 * s 仅由小写英文字母组成
 */
class PalindromePartitioning {
    private List<List<String>> res = new ArrayList<>();

    public List<List<String>> partition(String s) {
        List<String> track = new ArrayList<>();
        backtrack(s, 0, s.length() - 1, track);
        return res;
    }

    private void backtrack(String s, int start, int end, List<String> track) {
        // 判断结束条件
        if (start > end) {
            res.add(new ArrayList<>(track));
            return;
        }

        // 回溯算法
        for (int i = start; i <= end; i++) {
            // 判断是否是回文字符
            if (!isPalindrome(s, start, i)) {
                continue;
            }
            // 选择
            track.add(s.substring(start, i + 1));
            // 进入下一层决策树
            backtrack(s, i + 1, end, track);
            // 撤销选择
            track.remove(track.size() - 1);
        }
    }

    private boolean isPalindrome(String s, int start, int end) {
        while (start <= end) {
            if (s.charAt(start++) != s.charAt(end--)) {
                return false;
            }
        }
        return true;
    }
}
