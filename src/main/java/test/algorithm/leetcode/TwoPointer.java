package test.algorithm.leetcode;

import lombok.extern.slf4j.Slf4j;

/**
 * 双指针算法
 */
@Slf4j
public class TwoPointer {
    public static void main(String[] args) {
        log.info("ContainerWithMostWater: {}", new ContainerWithMostWater().maxArea(new int[] {1,8,6,2,5,4,8,3,7}));
        log.info("ContainerWithMostWater: {}", new ContainerWithMostWater().maxArea(new int[] {1,1}));
    }
}

/**
 * @lc app=leetcode.cn id=11 lang=java
 *
 * [11] 盛最多水的容器
 *
 * https://leetcode.cn/problems/container-with-most-water/description/
 *
 * algorithms
 * Medium (60.70%)
 * Likes:    4017
 * Dislikes: 0
 * Total Accepted:    899.3K
 * Total Submissions: 1.5M
 * Testcase Example:  '[1,8,6,2,5,4,8,3,7]'
 *
 * 给定一个长度为 n 的整数数组 height 。有 n 条垂线，第 i 条线的两个端点是 (i, 0) 和 (i, height[i]) 。
 *
 * 找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
 *
 * 返回容器可以储存的最大水量。
 *
 * 说明：你不能倾斜容器。
 *
 * 示例 1：
 * 输入：[1,8,6,2,5,4,8,3,7]
 * 输出：49
 * 解释：图中垂直线代表输入数组 [1,8,6,2,5,4,8,3,7]。在此情况下，容器能够容纳水（表示为蓝色部分）的最大值为 49。
 *
 * 示例 2：
 * 输入：height = [1,1]
 * 输出：1
 *
 * 提示：
 * n == height.length
 * 2 <= n <= 10^5
 * 0 <= height[i] <= 10^4
 */

// @lc code=start
class ContainerWithMostWater {
    public int maxArea(int[] height) {
        int start = 0;
        int end = height.length - 1;
        int res = 0;
        while (start < end) {
            int startH = height[start];
            int endH = height[end];
            int h = Math.min(startH, endH);
            int area = h * (end - start);
            res = Math.max(res, area);
            if (startH < endH) {
                start ++;
            } else {
                end --;
            }
        }
        return res;
    }
}


