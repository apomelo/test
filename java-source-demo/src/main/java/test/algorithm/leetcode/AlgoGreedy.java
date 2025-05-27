package test.algorithm.leetcode;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * 贪婪算法
 * @author C
 * @date 2023/4/27
 */
@Slf4j
public class AlgoGreedy {
    public static void main(String[] args) {
        // 跳跃游戏 II
        log.info("JumpGameII: {}", new JumpGameII().jump(new int[] {2,3,1,1,4}));
        log.info("JumpGameII: {}", new JumpGameII().jump(new int[] {2,3,0,1,4}));
        // 跳跃游戏
        log.info("JumpGame: {}", new JumpGame().canJump(new int[] {2,3,1,1,4}));
        log.info("JumpGame: {}", new JumpGame().canJump(new int[] {3,2,1,0,4}));
        log.info("JumpGame: {}", new JumpGame().canJump(new int[] {0,2,3}));
        // [134] 加油站
        log.info("GasStation: {}", new GasStation().canCompleteCircuit(new int[] {1,2,3,4,5}, new int[] {3,4,5,1,2}));
        log.info("GasStation: {}", new GasStation().canCompleteCircuit(new int[] {2,3,4}, new int[] {3,4,3}));
        // [134] 加油站
        log.info("Candy: {}", new Candy().candy(new int[] {1,0,2}));
        log.info("Candy: {}", new Candy().candy(new int[] {1,2,2}));
    }
}


/**
 * @lc app=leetcode.cn id=45 lang=java
 *
 * [45] 跳跃游戏 II
 *
 * https://leetcode.cn/problems/jump-game-ii/description/
 *
 * algorithms
 * Medium (45.19%)
 * Likes:    2079
 * Dislikes: 0
 * Total Accepted:    483.7K
 * Total Submissions: 1.1M
 * Testcase Example:  '[2,3,1,1,4]'
 *
 * 给定一个长度为 n 的 0 索引整数数组 nums。初始位置为 nums[0]。
 * 每个元素 nums[i] 表示从索引 i 向前跳转的最大长度。换句话说，如果你在 nums[i] 处，你可以跳转到任意 nums[i + j] 处:
 * 0 <= j <= nums[i] 
 * i + j < n
 * 返回到达 nums[n - 1] 的最小跳跃次数。生成的测试用例可以到达 nums[n - 1]。
 *
 * 示例 1:
 * 输入: nums = [2,3,1,1,4]
 * 输出: 2
 * 解释: 跳到最后一个位置的最小跳跃数是 2。
 * 从下标为 0 跳到下标为 1 的位置，跳 1 步，然后跳 3 步到达数组的最后一个位置。
 *
 * 示例 2:
 * 输入: nums = [2,3,0,1,4]
 * 输出: 2
 *
 * 提示:
 * 1 <= nums.length <= 10^4
 * 0 <= nums[i] <= 1000
 * 题目保证可以到达 nums[n-1]
 */
class JumpGameII {
    /**
     * 方法 1:
     */
    public int jump(int[] nums) {
        int n = nums.length;
        // 上一次跳跃的最远距离
        int end = 0;
        // 所有选择 [i..end] 中能够跳到的最远距离, 即筛选出下一次可以跳的最远距离
        int farthest = 0;
        // 跳跃次数
        int jumps = 0;
        for (int i = 0; i < n - 1; i++) {
            // 不需要关心两次最远距离之间应该跳到哪一格, 只需要筛选出下一次可以跳的最远距离
            farthest = Math.max(nums[i] + i, farthest);
            // 只有到上一次最远距离 end 的时候才更新
            if (end == i) {
                jumps++;
                end = farthest;
            }
        }
        return jumps;
    }

    /**
     * 方法 2:
     * 遍历并记录能到当前位置的最小值
     */
    public int jump2(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        // 初始化为 Integer.MAX_VALUE
        Arrays.fill(dp, Integer.MAX_VALUE);
        // 第一个位置需要跳跃 0 步
        dp[0] = 0;
        for (int i = 0; i < n - 1; i ++) {
            // 跳跃到当前格最小步数
            int curMinStep = dp[i];
            // 当前格可跳跃的最大步数
            int goMax = nums[i];
            // 尝试跳跃
            for (int j = 1; j <= goMax && i + j < n; j ++) {
                // 需要跳跃一步才能到下一目的
                if (dp[i + j] > curMinStep + 1) {
                    dp[i + j] = curMinStep + 1;
                }
            }
        }
        return dp[n - 1];
    }
}


/**
 * @lc app=leetcode.cn id=55 lang=java
 *
 * [55] 跳跃游戏
 *
 * https://leetcode.cn/problems/jump-game/description/
 *
 * algorithms
 * Medium (43.60%)
 * Likes:    2320
 * Dislikes: 0
 * Total Accepted:    708.5K
 * Total Submissions: 1.6M
 * Testcase Example:  '[2,3,1,1,4]'
 *
 * 给定一个非负整数数组 nums ，你最初位于数组的 第一个下标 。
 * 数组中的每个元素代表你在该位置可以跳跃的最大长度。
 * 判断你是否能够到达最后一个下标。
 *
 * 示例 1：
 * 输入：nums = [2,3,1,1,4]
 * 输出：true
 * 解释：可以先跳 1 步，从下标 0 到达下标 1, 然后再从下标 1 跳 3 步到达最后一个下标。
 *
 * 示例 2：
 * 输入：nums = [3,2,1,0,4]
 * 输出：false
 * 解释：无论怎样，总会到达下标为 3 的位置。但该下标的最大跳跃长度是 0 ， 所以永远不可能到达最后一个下标。
 *
 * 提示：
 * 1 <= nums.length <= 3 * 10^4
 * 0 <= nums[i] <= 10^5
 */
class JumpGame {
    /**
     * 方法 1
     */
    public boolean canJump(int[] nums) {
        int n = nums.length;
        // 当前能够到达的最远距离
        int farthest = 0;
        for (int i = 0; i < n - 1; i++) {
            // 不断计算能跳到的最远距离
            farthest = Math.max(farthest, i + nums[i]);
            // 如果最远距离小于等于当前位置，无法继续向前跳跃，返回false
            if (farthest <= i) {
                return false;
            }
        }
        // 判断最远距离是否大于等于数组的长度减1
        return farthest >= n - 1;
    }

    /**
     * 方法 2
     */
    public boolean canJump2(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        // 初始化为 -1 表示不可达
        Arrays.fill(dp, -1);
        // 第一个位置需要跳跃 0 步, 并且只有一个位置时要返回 true
        dp[0] = 0;
        for (int i = 0; i < n - 1; i ++) {
            // 跳跃到当前格最小步数
            int curMinStep = dp[i];
            // 如果当前格子不可达则跳过
            if (curMinStep < 0) {
                continue;
            }
            // 当前格可跳跃的最大步数
            int goMax = nums[i];
            // 尝试跳跃
            for (int j = 1; j <= goMax && i + j < n; j ++) {
                // 需要跳跃一步才能到下一目的
                if (dp[i + j] > curMinStep + 1 || dp[i + j] < 0) {
                    dp[i + j] = curMinStep + 1;
                }
            }
        }
        return dp[n - 1] >= 0;
    }
}


/**
 * @lc app=leetcode.cn id=134 lang=java
 *
 * [134] 加油站
 *
 * https://leetcode.cn/problems/gas-station/description/
 *
 * algorithms
 * Medium (50.34%)
 * Likes:    1394
 * Dislikes: 0
 * Total Accepted:    282.1K
 * Total Submissions: 561.5K
 * Testcase Example:  '[1,2,3,4,5]\n[3,4,5,1,2]'
 *
 * 在一条环路上有 n 个加油站，其中第 i 个加油站有汽油 gas[i] 升。
 * 你有一辆油箱容量无限的的汽车，从第 i 个加油站开往第 i+1 个加油站需要消耗汽油 cost[i] 升。你从其中的一个加油站出发，开始时油箱为空。
 * 给定两个整数数组 gas 和 cost ，如果你可以按顺序绕环路行驶一周，则返回出发时加油站的编号，否则返回 -1 。如果存在解，则 保证 它是 唯一的。
 *
 * 示例 1:
 * 输入: gas = [1,2,3,4,5], cost = [3,4,5,1,2]
 * 输出: 3
 * 解释:
 * 从 3 号加油站(索引为 3 处)出发，可获得 4 升汽油。此时油箱有 = 0 + 4 = 4 升汽油
 * 开往 4 号加油站，此时油箱有 4 - 1 + 5 = 8 升汽油
 * 开往 0 号加油站，此时油箱有 8 - 2 + 1 = 7 升汽油
 * 开往 1 号加油站，此时油箱有 7 - 3 + 2 = 6 升汽油
 * 开往 2 号加油站，此时油箱有 6 - 4 + 3 = 5 升汽油
 * 开往 3 号加油站，你需要消耗 5 升汽油，正好足够你返回到 3 号加油站。
 * 因此，3 可为起始索引。
 *
 * 示例 2:
 * 输入: gas = [2,3,4], cost = [3,4,3]
 * 输出: -1
 * 解释:
 * 你不能从 0 号或 1 号加油站出发，因为没有足够的汽油可以让你行驶到下一个加油站。
 * 我们从 2 号加油站出发，可以获得 4 升汽油。 此时油箱有 = 0 + 4 = 4 升汽油
 * 开往 0 号加油站，此时油箱有 4 - 3 + 2 = 3 升汽油
 * 开往 1 号加油站，此时油箱有 3 - 3 + 3 = 3 升汽油
 * 你无法返回 2 号加油站，因为返程需要消耗 4 升汽油，但是你的油箱只有 3 升汽油。
 * 因此，无论怎样，你都不可能绕环路行驶一周。
 *
 * 提示:
 * gas.length == n
 * cost.length == n
 * 1 <= n <= 10^5
 * 0 <= gas[i], cost[i] <= 10^4
 */
class GasStation {
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int n = gas.length;
        // 相当于图像中的坐标点和最低点
        int sum = 0, minSum = 0;
        int start = 0;
        for (int i = 0; i < n; i++) {
            sum += gas[i] - cost[i];
            if (sum < minSum) {
                // 这里要注意！！！
                // 经过第 i 个站点后，使 sum 到达新低
                // 所以站点 i + 1 就是最低点（起点）
                start = i + 1;
                minSum = sum;
            }
        }
        if (sum < 0) {
            // 总油量小于总的消耗，无解
            return -1;
        }
        // 环形数组特性
        return start == n ? 0 : start;
    }
}


/**
 * @lc app=leetcode.cn id=135 lang=java
 *
 * [135] 分发糖果
 *
 * https://leetcode.cn/problems/candy/description/
 *
 * algorithms
 * Hard (50.09%)
 * Likes:    1336
 * Dislikes: 0
 * Total Accepted:    248.4K
 * Total Submissions: 495.9K
 * Testcase Example:  '[1,0,2]'
 *
 * n 个孩子站成一排。给你一个整数数组 ratings 表示每个孩子的评分。
 * 你需要按照以下要求，给这些孩子分发糖果：
 * 每个孩子至少分配到 1 个糖果。
 * 相邻两个孩子评分更高的孩子会获得更多的糖果。
 * 请你给每个孩子分发糖果，计算并返回需要准备的 最少糖果数目 。
 *
 * 示例 1：
 * 输入：ratings = [1,0,2]
 * 输出：5
 * 解释：你可以分别给第一个、第二个、第三个孩子分发 2、1、2 颗糖果。
 *
 * 示例 2：
 * 输入：ratings = [1,2,2]
 * 输出：4
 * 解释：你可以分别给第一个、第二个、第三个孩子分发 1、2、1 颗糖果。
 *      第三个孩子只得到 1 颗糖果，这满足题面中的两个条件。
 *
 * 提示：
 * n == ratings.length
 * 1 <= n <= 2 * 10^4
 * 0 <= ratings[i] <= 2 * 10^4
 */
class Candy {
    public int candy(int[] ratings) {
        int n = ratings.length;
        int[] candies = new int[n];
        // 初始化每个孩子都有一个糖果
        Arrays.fill(candies, 1);

        // 从左向右遍历，保证右边高评分的孩子比左边多一个糖果
        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1]) {
                candies[i] = candies[i - 1] + 1;
            }
        }

        // 从右向左遍历，处理左边高评分的孩子，确保比右边多一个糖果
        for (int i = n - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                // 注意: 这里用 max 判断
                candies[i] = Math.max(candies[i], candies[i + 1] + 1);
            }
        }

        // 计算总糖果数
        int totalCandies = 0;
        for (int candy : candies) {
            totalCandies += candy;
        }
        return totalCandies;
    }
}
