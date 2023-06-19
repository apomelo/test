package test.algorithm.leetcode;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 数组
 * @author C
 * @date 2023/3/22
 */
@Slf4j
public class AlgoArray {
    public static void main(String[] args) {
        // 下一个排列
        int[] nextPermutationExample1 = new int[] {1,2,3};
        new NextPermutation().nextPermutation(nextPermutationExample1);
        log.info("NextPermutation: {}", nextPermutationExample1);
        int[] nextPermutationExample2 = new int[] {3,2,1};
        new NextPermutation().nextPermutation(nextPermutationExample2);
        log.info("NextPermutation: {}", nextPermutationExample2);
        int[] nextPermutationExample3 = new int[] {1,1,5};
        new NextPermutation().nextPermutation(nextPermutationExample3);
        log.info("NextPermutation: {}", nextPermutationExample3);
        int[] nextPermutationExample4 = new int[] {1,4,3,2};
        new NextPermutation().nextPermutation(nextPermutationExample4);
        log.info("NextPermutation: {}", nextPermutationExample4);
        // 旋转图像
        int[][] rotateImageExample1 = new int[][] {{1,2,3},{4,5,6},{7,8,9}};
        new RotateImage().rotate(rotateImageExample1);
        log.info("RotateImage: {}", (Object) rotateImageExample1);
        int[][] rotateImageExample2 = new int[][] {{5,1,9,11},{2,4,8,10},{13,3,6,7},{15,14,12,16}};
        new RotateImage().rotate(rotateImageExample2);
        log.info("RotateImage: {}", (Object) rotateImageExample2);
        // 螺旋矩阵
        log.info("SpiralMatrix: {}", new SpiralMatrix().spiralOrder(new int[][] {{1,2,3},{4,5,6},{7,8,9}}));
        log.info("SpiralMatrix: {}", new SpiralMatrix().spiralOrder(new int[][] {{1,2,3,4},{5,6,7,8},{9,10,11,12}}));
        // 螺旋矩阵 II
        log.info("SpiralMatrixII: {}", (Object) new SpiralMatrixII().generateMatrix(3));
        log.info("SpiralMatrixII: {}", (Object) new SpiralMatrixII().generateMatrix(1));
        // 加一
        log.info("PlusOne: {}", new PlusOne().plusOne(new int[] {1,2,3}));
        log.info("PlusOne: {}", new PlusOne().plusOne(new int[] {4,3,2,1}));
        log.info("PlusOne: {}", new PlusOne().plusOne(new int[] {0}));
        log.info("PlusOne: {}", new PlusOne().plusOne(new int[] {9}));
    }
}


/**
 * @lc app=leetcode.cn id=31 lang=java
 *
 * [31] 下一个排列
 *
 * https://leetcode.cn/problems/next-permutation/description/
 *
 * algorithms
 * Medium (38.22%)
 * Likes:    2114
 * Dislikes: 0
 * Total Accepted:    413.8K
 * Total Submissions: 1.1M
 * Testcase Example:  '[1,2,3]'
 *
 * 整数数组的一个 排列 就是将其所有成员以序列或线性顺序排列。
 * 例如，arr = [1,2,3] ，以下这些都可以视作 arr 的排列：[1,2,3]、[1,3,2]、[3,1,2]、[2,3,1] 。
 *
 * 整数数组的 下一个排列 是指其整数的下一个字典序更大的排列。更正式地，如果数组的所有排列根据其字典顺序从小到大排列在一个容器中，那么数组的 下一个排列
 * 就是在这个有序容器中排在它后面的那个排列。如果不存在下一个更大的排列，那么这个数组必须重排为字典序最小的排列（即，其元素按升序排列）。
 * 例如，arr = [1,2,3] 的下一个排列是 [1,3,2] 。
 * 类似地，arr = [2,3,1] 的下一个排列是 [3,1,2] 。
 * 而 arr = [3,2,1] 的下一个排列是 [1,2,3] ，因为 [3,2,1] 不存在一个字典序更大的排列。
 *
 * 给你一个整数数组 nums ，找出 nums 的下一个排列。
 * 必须 原地 修改，只允许使用额外常数空间。
 *
 * 示例 1：
 * 输入：nums = [1,2,3]
 * 输出：[1,3,2]
 *
 * 示例 2：
 * 输入：nums = [3,2,1]
 * 输出：[1,2,3]
 *
 * 示例 3：
 * 输入：nums = [1,1,5]
 * 输出：[1,5,1]
 *
 * 提示：
 * 1 <= nums.length <= 100
 * 0 <= nums[i] <= 100
 */
// @lc code=start
class NextPermutation {
    /**
     * 从后向前 查找第一个 相邻升序 的元素对 (i,j)，满足 A[i] < A[j]。此时 [j,end) 必然是降序
     * 在 [j,end) 从后向前 查找第一个满足 A[i] < A[k] 的 k。A[i]、A[k] 分别就是上文所说的「小数」、「大数」
     * 将 A[i] 与 A[k] 交换
     * 可以断定这时 [j,end) 必然是降序，逆置 [j,end)，使其升序
     * 如果在步骤 1 找不到符合的相邻元素对，说明当前 [begin,end) 为一个降序顺序，则直接跳到步骤 4
     * 该方法支持数据重复，且在 C++ STL 中被采用。
     */
    public void nextPermutation(int[] nums) {
        int length = nums.length;
        if (length <= 1) return;

        int i = length - 2;
        int j = length - 1;
        int k = length - 1;

        // find: A[i]<A[j]
        while (i >= 0 && nums[i] >= nums[j]) {
            i--;
            j--;
        }

        // 不是最后一个排列
        if (i >= 0) {
            // find: A[i]<A[k]
            while (nums[i] >= nums[k]) {
                k--;
            }
            // swap A[i], A[k]
            int tmp = nums[i];
            nums[i] = nums[k];
            nums[k] = tmp;
        }

        // reverse A[j:end]
        for (i = j, j = length - 1; i < j; i++, j--) {
            // swap A[i], A[k]
            int tmp = nums[i];
            nums[i] = nums[j];
            nums[j] = tmp;
        }
    }
}
// @lc code=end


/**
 * @lc app=leetcode.cn id=48 lang=java
 *
 * [48] 旋转图像
 *
 * https://leetcode.cn/problems/rotate-image/description/
 *
 * algorithms
 * Medium (74.66%)
 * Likes:    1622
 * Dislikes: 0
 * Total Accepted:    447.8K
 * Total Submissions: 599.5K
 * Testcase Example:  '[[1,2,3],[4,5,6],[7,8,9]]'
 *
 * 给定一个 n × n 的二维矩阵 matrix 表示一个图像。请你将图像顺时针旋转 90 度。
 * 你必须在 原地 旋转图像，这意味着你需要直接修改输入的二维矩阵。请不要 使用另一个矩阵来旋转图像。
 *
 * 示例 1：
 * 输入：matrix = [[1,2,3],[4,5,6],[7,8,9]]
 * 输出：[[7,4,1],[8,5,2],[9,6,3]]
 *
 * 示例 2：
 * 输入：matrix = [[5,1,9,11],[2,4,8,10],[13,3,6,7],[15,14,12,16]]
 * 输出：[[15,13,2,5],[14,3,4,1],[12,6,8,9],[16,7,10,11]]
 *
 * 提示：
 * n == matrix.length == matrix[i].length
 * 1 <= n <= 20
 * -1000 <= matrix[i][j] <= 1000
 */
// @lc code=start
class RotateImage {
    /**
     * 翻转主函数
     */
    public void rotate(int[][] matrix) {
        // 题目已经做了 n == matrix.length == matrix[i].length 的限制，所以不需要判断
        int n = matrix.length;

        // 遍历到中间即可
        for (int i = 0; i < n / 2; i++) {
            // 遍历到 n - i - 1 即可
            for (int j = i; j < n - i - 1; j++) {
                // 上一个位置的横坐标
                int lastI = i;
                // 上一个位置的纵坐标
                int lastJ = j;
                // 上一个位置的数
                int last = matrix[i][j];
                // 翻转 4 次即可
                for (int k = 0; k < 4; k++) {
                    // 获取下一个翻转的位置
                    int[] nextIndex = getNextIndex(n, lastI, lastJ);
                    // 先把下一个位置的数取出来暂存
                    int tmp = matrix[nextIndex[0]][nextIndex[1]];
                    // 把上一个位置的数存入
                    matrix[nextIndex[0]][nextIndex[1]] = last;
                    // 把暂存的下一个位置的数赋值到上一个位置
                    last = tmp;
                    lastI = nextIndex[0];
                    lastJ = nextIndex[1];
                }
            }
        }
    }

    /**
     * 获取下一个翻转的位置
     * 通过数学找规律
     */
    private int[] getNextIndex(int n, int i, int j) {
        return new int[] {j, n - i - 1};
    }
}
// @lc code=end


/**
 * @lc app=leetcode.cn id=54 lang=java
 *
 * [54] 螺旋矩阵
 *
 * https://leetcode.cn/problems/spiral-matrix/description/
 *
 * algorithms
 * Medium (49.28%)
 * Likes:    1376
 * Dislikes: 0
 * Total Accepted:    367.5K
 * Total Submissions: 745.7K
 * Testcase Example:  '[[1,2,3],[4,5,6],[7,8,9]]'
 *
 * 给你一个 m 行 n 列的矩阵 matrix ，请按照 顺时针螺旋顺序 ，返回矩阵中的所有元素。
 *
 * 示例 1：
 * 输入：matrix = [[1,2,3],[4,5,6],[7,8,9]]
 * 输出：[1,2,3,6,9,8,7,4,5]
 *
 * 示例 2：
 * 输入：matrix = [[1,2,3,4],[5,6,7,8],[9,10,11,12]]
 * 输出：[1,2,3,4,8,12,11,10,9,5,6,7]
 *
 * 提示：
 * m == matrix.length
 * n == matrix[i].length
 * 1 <= m, n <= 10
 * -100 <= matrix[i][j] <= 100
 */
// @lc code=start
class SpiralMatrix {
    /**
     * 维护了四个变量，即矩阵的左边界、右边界、上边界和下边界。
     * 在每个循环中，首先遍历上边界上的所有元素，然后遍历右边界上的所有元素。
     * 如果左边界和右边界不相遇并且上边界和下边界不相遇，则遍历下边界和左边界上的所有元素。
     * 然后将四个边界向内缩小，继续下一个循环，直到所有元素都被遍历完为止。
     */
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> res = new ArrayList<>();
        if (matrix == null || matrix.length == 0) {
            return res;
        }
        // 矩阵的行数
        int m = matrix.length;
        // 矩阵的列数
        int n = matrix[0].length;
        // 初始化边界
        int left = 0, right = n - 1, top = 0, bottom = m - 1;
        // 循环条件
        while (left <= right && top <= bottom) {
            // 遍历上边界
            for (int j = left; j <= right; j ++) {
                res.add(matrix[top][j]);
            }
            // 遍历右边界
            for (int i = top + 1; i <= bottom; i ++) {
                res.add(matrix[i][right]);
            }
            // 如果左右边界和上下边界没有相遇，遍历下边界和左边界
            if (left < right && top < bottom) {
                for (int j = right - 1; j >= left; j--) {
                    res.add(matrix[bottom][j]);
                }
                for (int i = bottom - 1; i > top; i--) {
                    res.add(matrix[i][left]);
                }
            }
            // 缩小边界
            left++;
            right--;
            top++;
            bottom--;
        }
        return res;
    }
}
// @lc code=end


/**
 * @lc app=leetcode.cn id=59 lang=java
 *
 * [59] 螺旋矩阵 II
 *
 * https://leetcode.cn/problems/spiral-matrix-ii/description/
 *
 * algorithms
 * Medium (73.17%)
 * Likes:    1055
 * Dislikes: 0
 * Total Accepted:    317.9K
 * Total Submissions: 435.2K
 * Testcase Example:  '3'
 *
 * 给你一个正整数 n ，生成一个包含 1 到 n^2 所有元素，且元素按顺时针顺序螺旋排列的 n x n 正方形矩阵 matrix 。
 *
 * 示例 1：
 * 输入：n = 3
 * 输出：[[1,2,3],[8,9,4],[7,6,5]]
 *
 * 示例 2：
 * 输入：n = 1
 * 输出：[[1]]
 *
 * 提示：
 * 1 <= n <= 20
 */
// @lc code=start
class SpiralMatrixII {
    public int[][] generateMatrix(int n) {
        int[][] res = new int[n][n];
        // 初始化边界
        int left = 0, right = n - 1, top = 0, bottom = n - 1;
        int cur = 1;
        // 循环条件
        while (left <= right && top <= bottom) {
            // 遍历上边界
            for (int j = left; j <= right; j++) {
                res[top][j] = cur++;
            }
            // 遍历右边界
            for (int i = top + 1; i <= bottom; i++) {
                res[i][right] = cur++;
            }
            // 如果左右边界和上下边界没有相遇，遍历下边界和左边界
            if (left < right && top < bottom) {
                // 遍历下边界
                for (int j = right - 1; j >= left; j--) {
                    res[bottom][j] = cur++;
                }
                // 遍历左边界
                for (int i = bottom - 1; i > left; i--) {
                    res[i][left] = cur++;
                }
            }
            // 缩小边界
            left++;
            right--;
            top++;
            bottom--;
        }
        return res;
    }
}
// @lc code=end


/**
 * @lc app=leetcode.cn id=66 lang=java
 *
 * [66] 加一
 *
 * https://leetcode.cn/problems/plus-one/description/
 *
 * algorithms
 * Easy (45.17%)
 * Likes:    1234
 * Dislikes: 0
 * Total Accepted:    635.3K
 * Total Submissions: 1.4M
 * Testcase Example:  '[1,2,3]'
 *
 * 给定一个由 整数 组成的 非空 数组所表示的非负整数，在该数的基础上加一。
 * 最高位数字存放在数组的首位， 数组中每个元素只存储单个数字。
 * 你可以假设除了整数 0 之外，这个整数不会以零开头。
 *
 * 示例 1：
 *
 * 输入：digits = [1,2,3]
 * 输出：[1,2,4]
 * 解释：输入数组表示数字 123。
 *
 * 示例 2：
 * 输入：digits = [4,3,2,1]
 * 输出：[4,3,2,2]
 * 解释：输入数组表示数字 4321。
 *
 * 示例 3：
 * 输入：digits = [0]
 * 输出：[1]
 *
 * 提示：
 * 1 <= digits.length <= 100
 * 0 <= digits[i] <= 9
 */
// @lc code=start
class PlusOne {
    public int[] plusOne(int[] digits) {
        if (digits.length == 0) {
            return digits;
        }
        int radix = 10;
        int carry = 0;
        LinkedList<Integer> res = new LinkedList<>();
        for (int i = digits.length - 1; i >= 0; i--) {
            int cur = digits[i];
            if (i == digits.length - 1) {
                cur ++;
            }
            cur += carry;
            carry = cur / radix;
            res.addFirst(cur % radix);
        }
        if (carry > 0) {
            res.addFirst(carry);
        }
        return res.stream().mapToInt(i -> i).toArray();
    }
}
// @lc code=end
