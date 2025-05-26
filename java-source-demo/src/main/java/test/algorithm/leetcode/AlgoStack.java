package test.algorithm.leetcode;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * 栈
 * @author C
 * @date 2023/2/24
 */
@Slf4j
public class AlgoStack {
    public static void main(String[] args) {
        // 有效的括号
        log.info("ValidParentheses: {}", new ValidParentheses().isValid("()[]{}"));
        log.info("ValidParentheses: {}", new ValidParentheses().isValid("(]"));
        log.info("ValidParentheses: {}", new ValidParentheses().isValid("([)]"));
        // 柱状图中最大的矩形
        log.info("LargestRectangleInHistogram: {}", new LargestRectangleInHistogram().largestRectangleArea(new int[] {2,1,5,6,2,3}));
        log.info("LargestRectangleInHistogram: {}", new LargestRectangleInHistogram().largestRectangleArea(new int[] {2,4}));
        // 柱状图中最大的矩形
        log.info("MaximalRectangle: {}", new MaximalRectangle().maximalRectangle(new char[][] {{'1','0','1','0','0'}, {'1','0','1','1','1'},{'1','1','1','1','1'},{'1','0','0','1','0'}}));
        log.info("MaximalRectangle: {}", new MaximalRectangle().maximalRectangle(new char[][] {{}}));
        log.info("MaximalRectangle: {}", new MaximalRectangle().maximalRectangle(new char[][] {{'0'}}));
        log.info("MaximalRectangle: {}", new MaximalRectangle().maximalRectangle(new char[][] {{'1'}}));
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
class ValidParentheses {
    public boolean isValid(String s) {
        Map<Character, Character> maping = new HashMap<Character, Character>() {{
            put('(', ')');
            put('{', '}');
            put('[', ']');
        }};
        Stack<Character> stack = new Stack<>();
        for (char c : s.toCharArray()) {
            if (stack.empty()) {
                stack.push(c);
            } else {
                Character peek = stack.peek();
                Character mapC = maping.get(peek);
                if (mapC != null && mapC == c) {
                    stack.pop();
                } else {
                    stack.push(c);
                }
            }
        }
        return stack.empty();
    }
}


/**
 * @lc app=leetcode.cn id=84 lang=java
 *
 * [84] 柱状图中最大的矩形
 *
 * https://leetcode.cn/problems/largest-rectangle-in-histogram/description/
 *
 * algorithms
 * Hard (45.00%)
 * Likes:    2482
 * Dislikes: 0
 * Total Accepted:    349.6K
 * Total Submissions: 776.7K
 * Testcase Example:  '[2,1,5,6,2,3]'
 *
 * 给定 n 个非负整数，用来表示柱状图中各个柱子的高度。每个柱子彼此相邻，且宽度为 1 。
 * 求在该柱状图中，能够勾勒出来的矩形的最大面积。
 *
 * 示例 1:
 * 输入：heights = [2,1,5,6,2,3]
 * 输出：10
 * 解释：最大的矩形为图中红色区域，面积为 10
 *
 * 示例 2：
 * 输入： heights = [2,4]
 * 输出： 4
 *
 * 提示：
 * 1 <= heights.length <=10^5
 * 0 <= heights[i] <= 10^4
 */
class LargestRectangleInHistogram {
    public int largestRectangleArea(int[] heights) {
        int n = heights.length;
        int[] left = new int[n]; // 记录每个柱形条左边第一个小于它的柱形条的索引
        int[] right = new int[n]; // 记录每个柱形条右边第一个小于它的柱形条的索引

        Stack<Integer> stack = new Stack<>(); // 单调递增栈，存储柱形条的索引

        // 遍历数组，找到每个柱形条左边第一个小于它的柱形条的索引
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && heights[stack.peek()] >= heights[i]) {
                stack.pop();
            }
            left[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }

        stack.clear(); // 清空栈，用于下一次遍历

        // 遍历数组，找到每个柱形条右边第一个小于它的柱形条的索引
        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && heights[stack.peek()] >= heights[i]) {
                stack.pop();
            }
            right[i] = stack.isEmpty() ? n : stack.peek();
            stack.push(i);
        }

        int maxArea = 0; // 记录最大面积

        // 计算每个柱形条作为矩形的宽度，并计算对应的面积
        for (int i = 0; i < n; i++) {
            int width = right[i] - left[i] - 1; // 矩形的宽度为右边界索引减去左边界索引减一
            int area = heights[i] * width; // 计算矩形的面积
            maxArea = Math.max(maxArea, area); // 更新最大面积
        }

        return maxArea;
    }
}


/**
 * @lc app=leetcode.cn id=85 lang=java
 *
 * [85] 最大矩形
 *
 * https://leetcode.cn/problems/maximal-rectangle/description/
 *
 * algorithms
 * Hard (54.69%)
 * Likes:    1547
 * Dislikes: 0
 * Total Accepted:    178.5K
 * Total Submissions: 326.4K
 * Testcase Example:  '[["1","0","1","0","0"],["1","0","1","1","1"],["1","1","1","1","1"],["1","0","0","1","0"]]'
 *
 * 给定一个仅包含 0 和 1 、大小为 rows x cols 的二维二进制矩阵，找出只包含 1 的最大矩形，并返回其面积。
 *
 * 示例 1：
 * 输入：matrix =
 * [["1","0","1","0","0"],
 *  ["1","0","1","1","1"],
 *  ["1","1","1","1","1"],
 *  ["1","0","0","1","0"]]
 * 输出：6
 * 解释：最大矩形如上图所示。
 *
 * 示例 2：
 * 输入：matrix = []
 * 输出：0
 *
 * 示例 3：
 * 输入：matrix = [["0"]]
 * 输出：0
 *
 * 示例 4：
 * 输入：matrix = [["1"]]
 * 输出：1
 *
 * 示例 5：
 * 输入：matrix = [["0","0"]]
 * 输出：0
 *
 * 提示：
 * rows == matrix.length
 * cols == matrix[0].length
 * 1 <= row, cols <= 200
 * matrix[i][j] 为 '0' 或 '1'
 */
class MaximalRectangle {
    public int maximalRectangle(char[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }

        int rows = matrix.length;
        int cols = matrix[0].length;
        int[] heights = new int[cols];
        int maxArea = 0;

        // 遍历每一行
        for (int row = 0; row < rows; row++) {
            // 更新 heights 数组
            for (int col = 0; col < cols; col++) {
                if (matrix[row][col] == '1') {
                    heights[col]++;
                } else {
                    heights[col] = 0;
                }
            }
            // 计算当前行 heights 数组对应的最大矩形面积
            maxArea = Math.max(maxArea, largestRectangleArea(heights));
        }

        return maxArea;
    }

    private int largestRectangleArea(int[] heights) {
        int n = heights.length;
        int[] left = new int[n]; // 记录每个柱形条左边第一个小于它的柱形条的索引
        int[] right = new int[n]; // 记录每个柱形条右边第一个小于它的柱形条的索引

        Stack<Integer> stack = new Stack<>(); // 单调递增栈，存储柱形条的索引

        // 遍历数组，找到每个柱形条左边第一个小于它的柱形条的索引
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && heights[stack.peek()] >= heights[i]) {
                stack.pop();
            }
            left[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }

        stack.clear(); // 清空栈，用于下一次遍历

        // 遍历数组，找到每个柱形条右边第一个小于它的柱形条的索引
        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && heights[stack.peek()] >= heights[i]) {
                stack.pop();
            }
            right[i] = stack.isEmpty() ? n : stack.peek();
            stack.push(i);
        }

        int maxArea = 0; // 记录最大面积

        // 计算每个柱形条作为矩形的宽度，并计算对应的面积
        for (int i = 0; i < n; i++) {
            int width = right[i] - left[i] - 1; // 矩形的宽度为右边界索引减去左边界索引减一
            int area = heights[i] * width; // 计算矩形的面积
            maxArea = Math.max(maxArea, area); // 更新最大面积
        }

        return maxArea;
    }
}
