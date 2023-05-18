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
// @lc code=start
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
// @lc code=end


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
// @lc code=start
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
// @lc code=end
