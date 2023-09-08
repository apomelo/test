package test.algorithm.leetcode;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
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
        // 矩阵置零
        int[][] setMatrixZeroesExample1 = new int[][] {{1,1,1},{1,0,1},{1,1,1}};
        new SetMatrixZeroes().setZeroes(setMatrixZeroesExample1);
        log.info("SetMatrixZeroes: {}", (Object) setMatrixZeroesExample1);
        int[][] setMatrixZeroesExample2 = new int[][] {{0,1,2,0},{3,4,5,2},{1,3,1,5}};
        new SetMatrixZeroes().setZeroes(setMatrixZeroesExample2);
        log.info("SetMatrixZeroes: {}", (Object) setMatrixZeroesExample2);
        // [118] 杨辉三角
        log.info("PascalsTriangle: {}", new PascalsTriangle().generate(5));
        log.info("PascalsTriangle: {}", new PascalsTriangle().generate(1));
        // [119] 杨辉三角 II
        log.info("PascalsTriangleII: {}", new PascalsTriangleII().getRow(3));
        log.info("PascalsTriangleII: {}", new PascalsTriangleII().getRow(0));
        log.info("PascalsTriangleII: {}", new PascalsTriangleII().getRow(1));
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


/**
 * @lc app=leetcode.cn id=73 lang=java
 *
 * [73] 矩阵置零
 *
 * https://leetcode.cn/problems/set-matrix-zeroes/description/
 *
 * algorithms
 * Medium (63.14%)
 * Likes:    887
 * Dislikes: 0
 * Total Accepted:    255.1K
 * Total Submissions: 403.9K
 * Testcase Example:  '[[1,1,1],[1,0,1],[1,1,1]]'
 *
 * 给定一个 m x n 的矩阵，如果一个元素为 0 ，则将其所在行和列的所有元素都设为 0 。请使用 原地 算法。
 *
 * 示例 1：
 * 输入：matrix = [[1,1,1],[1,0,1],[1,1,1]]
 * 输出：[[1,0,1],[0,0,0],[1,0,1]]
 *
 * 示例 2：
 * 输入：matrix = [[0,1,2,0],[3,4,5,2],[1,3,1,5]]
 * 输出：[[0,0,0,0],[0,4,5,0],[0,3,1,0]]
 *
 * 提示：
 * m == matrix.length
 * n == matrix[0].length
 * 1 <= m, n <= 200
 * -2^31 <= matrix[i][j] <= 2^31 - 1
 *
 * 进阶：
 * 一个直观的解决方案是使用  O(mn) 的额外空间，但这并不是一个好的解决方案。
 * 一个简单的改进方案是使用 O(m + n) 的额外空间，但这仍然不是最好的解决方案。
 * 你能想出一个仅使用常量空间的解决方案吗？
 */
class SetMatrixZeroes {
    /**
     * 方法1: 用第一行和第一列作为标记
     */
    public void setZeroes(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        boolean rowZero = false;
        boolean colZero = false;

        // 检查第一行是否有0
        for (int j = 0; j < n; j++) {
            if (matrix[0][j] == 0) {
                rowZero = true;
                break;
            }
        }
        // 检查第一列是否有0
        for (int i = 0; i < m; i++) {
            if (matrix[i][0] == 0) {
                colZero = true;
                break;
            }
        }

        // 遍历矩阵，将对应的第一行和第一列的元素设置为0作为标记
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }
        // 根据第一行和第一列的标记，将对应的行和列设置为0
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }
        }
        // 根据标记，将第一行和第一列设置为0
        if (rowZero) {
            for (int j = 0; j < n; j++) {
                matrix[0][j] = 0;
            }
        }
        if (colZero) {
            for (int i = 0; i < m; i++) {
                matrix[i][0] = 0;
            }
        }
    }

    /**
     * 方法2: 存储哪一行哪一列需要清零
     */
    public void setZeroes2(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        // 创建两个数组来记录哪些行和列需要被设置为0
        boolean[] rowZeroes = new boolean[m];
        boolean[] colZeroes = new boolean[n];

        // 遍历矩阵，将为0的行和列记录下来
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    rowZeroes[i] = true;
                    colZeroes[j] = true;
                }
            }
        }

        // 根据记录的信息，将对应的行和列设置为0
        for (int i = 0; i < m; i++) {
            if (rowZeroes[i]) {
                for (int j = 0; j < n; j++) {
                    matrix[i][j] = 0;
                }
            }
        }
        for (int j = 0; j < n; j++) {
            if (colZeroes[j]) {
                for (int i = 0; i < m; i++) {
                    matrix[i][j] = 0;
                }
            }
        }
    }
}


/**
 * @lc app=leetcode.cn id=118 lang=java
 *
 * [118] 杨辉三角
 *
 * https://leetcode.cn/problems/pascals-triangle/description/
 *
 * algorithms
 * Easy (75.51%)
 * Likes:    1049
 * Dislikes: 0
 * Total Accepted:    436.6K
 * Total Submissions: 578.2K
 * Testcase Example:  '5'
 *
 * 给定一个非负整数 numRows，生成「杨辉三角」的前 numRows 行。
 * 在「杨辉三角」中，每个数是它左上方和右上方的数的和。
 *
 * 示例 1:
 * 输入: numRows = 5
 * 输出: [[1],[1,1],[1,2,1],[1,3,3,1],[1,4,6,4,1]]
 *
 * 示例 2:
 * 输入: numRows = 1
 * 输出: [[1]]
 *
 * 提示:
 * 1 <= numRows <= 30
 */
class PascalsTriangle {
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> res = new ArrayList<>();
        if (numRows <= 0) return res;

        // 先把第一层装进去作为 base case
        res.add(Collections.singletonList(1));
        // 开始一层一层生成，装入 res
        for (int i = 1; i < numRows; i++) {
            List<Integer> lastRow = res.get(res.size() - 1);
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < i + 1; j++) {
                int left = j - 1 < 0 ? 0 : lastRow.get(j - 1);
                int right = j >= lastRow.size() ? 0 : lastRow.get(j);
                row.add(left + right);
            }
            res.add(row);
        }
        return res;
    }
}


/**
 * @lc app=leetcode.cn id=119 lang=java
 *
 * [119] 杨辉三角 II
 *
 * https://leetcode.cn/problems/pascals-triangle-ii/description/
 *
 * algorithms
 * Easy (68.94%)
 * Likes:    504
 * Dislikes: 0
 * Total Accepted:    280.3K
 * Total Submissions: 406.5K
 * Testcase Example:  '3'
 *
 * 给定一个非负索引 rowIndex，返回「杨辉三角」的第 rowIndex 行。
 * 在「杨辉三角」中，每个数是它左上方和右上方的数的和。
 *
 * 示例 1:
 * 输入: rowIndex = 3
 * 输出: [1,3,3,1]
 *
 * 示例 2:
 * 输入: rowIndex = 0
 * 输出: [1]
 *
 * 示例 3:
 * 输入: rowIndex = 1
 * 输出: [1,1]
 *
 * 提示:
 * 0 <= rowIndex <= 33
 * 进阶：
 * 你可以优化你的算法到 O(rowIndex) 空间复杂度吗？
 */
class PascalsTriangleII {
    public List<Integer> getRow(int rowIndex) {
        List<Integer> res = new ArrayList<>();
        if (rowIndex < 0) return res;

        // base case
        res.add(1);
        // 遍历计算出下一行
        for (int i = 1; i <= rowIndex; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < i + 1; j++) {
                int left = j - 1 < 0 ? 0 : res.get(j - 1);
                int right = j >= res.size() ? 0 : res.get(j);
                row.add(left + right);
            }
            res = row;
        }
        return res;
    }
}
