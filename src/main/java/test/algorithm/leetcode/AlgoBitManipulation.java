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
        // [169] 多数元素
        log.info("MajorityElement: {}", new MajorityElement().majorityElement(new int[] {3,2,3}));
        log.info("MajorityElement: {}", new MajorityElement().majorityElement(new int[] {2,2,1,1,1,2,2}));
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


/**
 * @lc app=leetcode.cn id=169 lang=java
 *
 * [169] 多数元素
 *
 * https://leetcode.cn/problems/majority-element/description/
 *
 * algorithms
 * Easy (66.53%)
 * Likes:    2014
 * Dislikes: 0
 * Total Accepted:    787.9K
 * Total Submissions: 1.2M
 * Testcase Example:  '[3,2,3]'
 *
 * 给定一个大小为 n 的数组 nums ，返回其中的多数元素。多数元素是指在数组中出现次数 大于 ⌊ n/2 ⌋ 的元素。
 * 你可以假设数组是非空的，并且给定的数组总是存在多数元素。
 *
 * 示例 1：
 * 输入：nums = [3,2,3]
 * 输出：3
 *
 * 示例 2：
 * 输入：nums = [2,2,1,1,1,2,2]
 * 输出：2
 *
 * 提示：
 * n == nums.length
 * 1 <= n <= 5 * 10^4
 * -10^9 <= nums[i] <= 10^9
 *
 * 进阶：尝试设计时间复杂度为 O(n)、空间复杂度为 O(1) 的算法解决此问题。
 */
class MajorityElement {
    public int majorityElement(int[] nums) {
        // 我们想寻找的那个众数
        int target = 0;
        // 计数器（类比带电粒子例子中的带电性）
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            if (count == 0) {
                // 当计数器为 0 时，假设 nums[i] 就是众数
                target = nums[i];
                // 众数出现了一次
                count = 1;
            } else if (nums[i] == target) {
                // 如果遇到的是目标众数，计数器累加
                count++;
            } else {
                // 如果遇到的不是目标众数，计数器递减
                count--;
            }
        }
        // 回想带电粒子的例子
        // 此时的 count 必然大于 0，此时的 target 必然就是目标众数
        return target;
    }
}
