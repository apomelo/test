package test.algorithm.leetcode;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

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
