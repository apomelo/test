package test.algorithm.leetcode;

import lombok.extern.slf4j.Slf4j;

/**
 * 位操作
 * @author C
 * @date 2023/9/22
 */
@Slf4j
public class AlgoBitManipulation {
    public static void main(String[] args) {
        // [136] 只出现一次的数字
        log.info("SingleNumber: {}", new SingleNumber().singleNumber(new int[] {2,2,1}));
        log.info("SingleNumber: {}", new SingleNumber().singleNumber(new int[] {4,1,2,1,2}));
        log.info("SingleNumber: {}", new SingleNumber().singleNumber(new int[] {1}));
        // [137] 只出现一次的数字 II
        log.info("SingleNumberII: {}", new SingleNumberII().singleNumber(new int[] {2,2,3,2}));
        log.info("SingleNumberII: {}", new SingleNumberII().singleNumber(new int[] {0,1,0,1,0,1,99}));
    }
}


/**
 * @lc app=leetcode.cn id=136 lang=java
 *
 * [136] 只出现一次的数字
 *
 * https://leetcode.cn/problems/single-number/description/
 *
 * algorithms
 * Easy (72.52%)
 * Likes:    2962
 * Dislikes: 0
 * Total Accepted:    950.5K
 * Total Submissions: 1.3M
 * Testcase Example:  '[2,2,1]'
 *
 * 给你一个 非空 整数数组 nums ，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
 * 你必须设计并实现线性时间复杂度的算法来解决此问题，且该算法只使用常量额外空间。
 *
 * 示例 1 ：
 * 输入：nums = [2,2,1]
 * 输出：1
 *
 * 示例 2 ：
 * 输入：nums = [4,1,2,1,2]
 * 输出：4
 *
 * 示例 3 ：
 * 输入：nums = [1]
 * 输出：1
 *
 * 提示：
 * 1 <= nums.length <= 3 * 10^4
 * -3 * 10^4 <= nums[i] <= 3 * 10^4
 * 除了某个元素只出现一次以外，其余每个元素均出现两次。
 */
class SingleNumber {
    public int singleNumber(int[] nums) {
        int res = 0;
        for (int n : nums) {
            res ^= n;
        }
        return res;
    }
}


/**
 * @lc app=leetcode.cn id=137 lang=java
 *
 * [137] 只出现一次的数字 II
 *
 * https://leetcode.cn/problems/single-number-ii/description/
 *
 * algorithms
 * Medium (71.69%)
 * Likes:    1060
 * Dislikes: 0
 * Total Accepted:    160.4K
 * Total Submissions: 223.8K
 * Testcase Example:  '[2,2,3,2]'
 *
 * 给你一个整数数组 nums ，除某个元素仅出现 一次 外，其余每个元素都恰出现 三次 。请你找出并返回那个只出现了一次的元素。
 * 你必须设计并实现线性时间复杂度的算法且使用常数级空间来解决此问题。
 *
 * 示例 1：
 * 输入：nums = [2,2,3,2]
 * 输出：3
 *
 * 示例 2：
 * 输入：nums = [0,1,0,1,0,1,99]
 * 输出：99
 *
 * 提示：
 * 1 <= nums.length <= 3 * 10^4
 * -2^31 <= nums[i] <= 2^31 - 1
 * nums 中，除某个元素仅出现 一次 外，其余每个元素都恰出现 三次
 */
class SingleNumberII {
    public int singleNumber(int[] nums) {
        int ones = 0;  // 用于记录出现一次的数字
        int twos = 0;  // 用于记录出现两次的数字

        for (int num : nums) {
            // 更新 ones：
            // 如果 ones 中已经包含了 num，则将 num 从 ones 中去除（相当于出现两次的数字在 ones 中变为0），
            // 否则将 num 添加到 ones 中。
            ones = (ones ^ num) & ~twos;

            // 更新 twos：
            // 如果 twos 中已经包含了 num，则将 num 从 twos 中去除（相当于出现三次的数字在 twos 中变为0），
            // 否则将 num 添加到 twos 中。
            twos = (twos ^ num) & ~ones;
        }

        return ones; // 返回只出现一次的数字
    }
}
