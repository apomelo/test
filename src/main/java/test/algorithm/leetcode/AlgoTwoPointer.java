package test.algorithm.leetcode;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 双指针算法
 */
@Slf4j
public class AlgoTwoPointer {
    public static void main(String[] args) {
        log.info("ContainerWithMostWater: {}", new ContainerWithMostWater().maxArea(new int[] {1,8,6,2,5,4,8,3,7}));
        log.info("ContainerWithMostWater: {}", new ContainerWithMostWater().maxArea(new int[] {1,1}));
        log.info("ThreeSum: {}", new ThreeSum().threeSum(new int[] {-1,0,1,2,-1,-4}));
        log.info("ThreeSum: {}", new ThreeSum().threeSum(new int[] {0,1,1}));
        log.info("ThreeSum: {}", new ThreeSum().threeSum(new int[] {0,0,0}));
        log.info("ThreeSumClosest: {}", new ThreeSumClosest().threeSumClosest(new int[] {-1,2,1,-4}, 1));
        log.info("ThreeSumClosest: {}", new ThreeSumClosest().threeSumClosest(new int[] {0,0,0}, 1));
        log.info("ThreeSumClosest: {}", new ThreeSumClosest().threeSumClosest(new int[] {1,1,-1}, 0));
        log.info("ThreeSumClosest: {}", new ThreeSumClosest().threeSumClosest(new int[] {0,3,97,102,200}, 300));
        log.info("FourSum: {}", new FourSum().fourSum(new int[] {1,0,-1,0,-2,2}, 0));
        log.info("FourSum: {}", new FourSum().fourSum(new int[] {2,2,2,2,2}, 8));
        log.info("RemoveNthNodeFromEndOfList: {}", new RemoveNthNodeFromEndOfList().removeNthFromEnd(RemoveNthNodeFromEndOfList.example1(), 2));
        log.info("RemoveNthNodeFromEndOfList: {}", new RemoveNthNodeFromEndOfList().removeNthFromEnd(RemoveNthNodeFromEndOfList.example2(), 1));
        log.info("RemoveNthNodeFromEndOfList: {}", new RemoveNthNodeFromEndOfList().removeNthFromEnd(RemoveNthNodeFromEndOfList.example3(), 1));
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

/**
 * @lc app=leetcode.cn id=15 lang=java
 *
 * [15] 三数之和
 *
 * https://leetcode.cn/problems/3sum/description/
 *
 * algorithms
 * Medium (36.39%)
 * Likes:    5424
 * Dislikes: 0
 * Total Accepted:    1.2M
 * Total Submissions: 3.3M
 * Testcase Example:  '[-1,0,1,2,-1,-4]'
 *
 * 给你一个整数数组 nums ，判断是否存在三元组 [nums[i], nums[j], nums[k]] 满足 i != j、i != k 且 j !=
 * k ，同时还满足 nums[i] + nums[j] + nums[k] == 0 。请
 * 你返回所有和为 0 且不重复的三元组。
 * 注意：答案中不可以包含重复的三元组。
 *
 * 示例 1：
 * 输入：nums = [-1,0,1,2,-1,-4]
 * 输出：[[-1,-1,2],[-1,0,1]]
 * 解释：
 * nums[0] + nums[1] + nums[2] = (-1) + 0 + 1 = 0 。
 * nums[1] + nums[2] + nums[4] = 0 + 1 + (-1) = 0 。
 * nums[0] + nums[3] + nums[4] = (-1) + 2 + (-1) = 0 。
 * 不同的三元组是 [-1,0,1] 和 [-1,-1,2] 。
 * 注意，输出的顺序和三元组的顺序并不重要。
 *
 * 示例 2：
 * 输入：nums = [0,1,1]
 * 输出：[]
 * 解释：唯一可能的三元组和不为 0 。
 *
 * 示例 3：
 * 输入：nums = [0,0,0]
 * 输出：[[0,0,0]]
 * 解释：唯一可能的三元组和为 0 。
 *
 * 提示：
 * 3 <= nums.length <= 3000
 * -10^5 <= nums[i] <= 10^5
 */
// @lc code=start
class ThreeSum {
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        return nSum(nums, 3, 0, 0);
    }

    private List<List<Integer>> nSum(int[] nums, int n, int target, int start) {
        List<List<Integer>> res = new ArrayList<>();
        int length = nums.length;
        if (n > length || n < 2 || start >= length) {
            return res;
        }
        if (n == 2) {
            int left = start, right = length - 1;
            while (left < right) {
                int small = nums[left], big = nums[right];
                int sum = small + big;
                if (sum == target) {
                    res.add(new ArrayList<>(Arrays.asList(small, big)));
                    while (left < right && nums[left] == small) left ++;
                    while (left < right && nums[right] == big) right --;
                } else if (sum < target) {
                    while (left < right && nums[left] == small) left ++;
                } else {
                    while (left < right && nums[right] == big) right --;
                }
            }
        } else {
            int curIndex = start;
            while (curIndex < length) {
                int curNum = nums[curIndex];
                List<List<Integer>> subRes = nSum(nums, n - 1, target - curNum, curIndex + 1);
                subRes.forEach(list -> {
                    list.add(curNum);
                    res.add(list);
                });
                while (curIndex < length && nums[curIndex] == curNum) curIndex ++;
            }
        }
        return res;
    }
}
// @lc code=end

/**
 * @lc app=leetcode.cn id=16 lang=java
 *
 * [16] 最接近的三数之和
 *
 * https://leetcode.cn/problems/3sum-closest/description/
 *
 * algorithms
 * Medium (45.21%)
 * Likes:    1328
 * Dislikes: 0
 * Total Accepted:    438.2K
 * Total Submissions: 969.4K
 * Testcase Example:  '[-1,2,1,-4]\n1'
 *
 * 给你一个长度为 n 的整数数组 nums 和 一个目标值 target。请你从 nums 中选出三个整数，使它们的和与 target 最接近。
 * 返回这三个数的和。
 * 假定每组输入只存在恰好一个解。
 *
 * 示例 1：
 * 输入：nums = [-1,2,1,-4], target = 1
 * 输出：2
 * 解释：与 target 最接近的和是 2 (-1 + 2 + 1 = 2) 。
 *
 * 示例 2：
 * 输入：nums = [0,0,0], target = 1
 * 输出：0
 *
 * 提示：
 * 3 <= nums.length <= 1000
 * -1000 <= nums[i] <= 1000
 * -10^4 <= target <= 10^4
 */
// @lc code=start
class ThreeSumClosest {
    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        List<List<Integer>> lists = nSum(nums, 3, Integer.MAX_VALUE, target, 0);
        return lists.isEmpty() ? 0 : lists.get(0).stream().mapToInt(Integer::intValue).sum();
    }

    private List<List<Integer>> nSum(int[] nums, int n, int closedDiffer, int target, int start) {
        List<List<Integer>> res = new ArrayList<>();
        int length = nums.length;
        if (n > length || n < 2 || start >= length) {
            return res;
        }
        if (n == 2) {
            int left = start, right = length - 1;
            while (left < right) {
                int small = nums[left];
                int big = nums[right];
                int sum = small + big;
                int differ = Math.abs(subtract(sum, target));
                // 判断最小差值
                if (differ <= closedDiffer) {
                    // 比最小差值小则更新差值
                    if (differ < closedDiffer) {
                        res.clear();
                        closedDiffer = differ;
                    }
                    res.add(new ArrayList<>(Arrays.asList(small, big)));
                }
                if (sum < target) {
                    while (left < right && nums[left] == small) left ++;
                } else {
                    while (right > left && nums[right] == big) right --;
                }
            }
        } else {
            int curIndex = start;
            while (curIndex < length) {
                int curNum = nums[curIndex];
                // 这里 closedDiffer 是定量，不需要减 curNum
                List<List<Integer>> lists = nSum(nums, n - 1, closedDiffer, target - curNum, curIndex + 1);
                if (lists.size() > 0) {
                    // 判断最小差值
                    int sum = lists.get(0).stream().mapToInt(Integer::intValue).sum() + curNum;
                    int differ = Math.abs(subtract(sum, target));
                    if (differ <= closedDiffer) {
                        // 比最小差值小则更新差值
                        if (differ < closedDiffer) {
                            res.clear();
                            closedDiffer = differ;
                        }
                        lists.forEach(list -> {
                            list.add(curNum);
                            res.add(list);
                        });
                    }
                }
                while (curIndex < length && nums[curIndex] == curNum) curIndex ++;
            }
        }
        // 最终返回最小差值的列表
        return res;
    }

    private int subtract(int x, int y) {
        int r = x - y;
        if (((x ^ y) & (x ^ r)) < 0) {
            return Integer.MAX_VALUE;
        }
        return r;
    }

    /**
     * 第二种解法（非nSum）
     */
    public int threeSumClosest2(int[] nums, int target) {
        if (nums.length < 3) {
            return 0;
        }
        // 别忘了要先排序数组
        Arrays.sort(nums);
        // 记录三数之和与目标值的偏差
        int delta = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length - 2; i++) {
            // 固定 nums[i] 为三数之和中的第一个数，
            // 然后对 nums[i+1..] 搜索接近 target - nums[i] 的两数之和
            int sum = nums[i] + twoSumClosest2(nums, i + 1, target - nums[i]);
            if (Math.abs(delta) > Math.abs(target - sum)) {
                delta = target - sum;
            }
        }
        return target - delta;
    }
    // 在 nums[start..] 搜索最接近 target 的两数之和
    private int twoSumClosest2(int[] nums, int start, int target) {
        int lo = start, hi = nums.length - 1;
        // 记录两数之和与目标值的偏差
        int delta = Integer.MAX_VALUE;
        while (lo < hi) {
            int sum = nums[lo] + nums[hi];
            if (Math.abs(delta) > Math.abs(target - sum)) {
                delta = target - sum;
            }
            if (sum < target) {
                lo++;
            } else {
                hi--;
            }
        }
        return target - delta;
    }
}
// @lc code=end


/**
 * @lc app=leetcode.cn id=18 lang=java
 *
 * [18] 四数之和
 *
 * https://leetcode.cn/problems/4sum/description/
 *
 * algorithms
 * Medium (37.55%)
 * Likes:    1505
 * Dislikes: 0
 * Total Accepted:    417.6K
 * Total Submissions: 1.1M
 * Testcase Example:  '[1,0,-1,0,-2,2]\n0'
 *
 * 给你一个由 n 个整数组成的数组 nums ，和一个目标值 target 。请你找出并返回满足下述全部条件且不重复的四元组 [nums[a],
 * nums[b], nums[c], nums[d]] （若两个四元组元素一一对应，则认为两个四元组重复）：
 * 0 <= a, b, c, d < n
 * a、b、c 和 d 互不相同
 * nums[a] + nums[b] + nums[c] + nums[d] == target
 * 你可以按 任意顺序 返回答案 。
 *
 * 示例 1：
 * 输入：nums = [1,0,-1,0,-2,2], target = 0
 * 输出：[[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]
 *
 * 示例 2：
 * 输入：nums = [2,2,2,2,2], target = 8
 * 输出：[[2,2,2,2]]
 *
 * 提示：
 * 1 <= nums.length <= 200
 * -10^9 <= nums[i] <= 10^9
 * -10^9 <= target <= 10^9
 */

// @lc code=start
class FourSum {
    public List<List<Integer>> fourSum(int[] nums, int target) {
        Arrays.sort(nums);
        return nSum(nums, 4, target, 0);
    }

    private List<List<Integer>> nSum(int[] nums, int n, int target, int start) {
        List<List<Integer>> res = new ArrayList<>();
        // 判断边界
        int length = nums.length;
        if (n > length || n < 2 || start >= length) {
            return res;
        }

        if (n == 2) {
            int left = start, right = length - 1;
            while (left < right) {
                int small = nums[left], big = nums[right];
                // 这道题两数相加不会溢出，不需要判断
                int sum = small + big;
                if (sum == target) {
                    res.add(new ArrayList<>(Arrays.asList(small, big)));
                    while (left < right && nums[left] == small) left ++;
                    while (left < right && nums[right] == big) right --;
                } else if (sum < target) {
                    while (left < right && nums[left] == small) left ++;
                } else {
                    while (left < right && nums[right] == big) right --;
                }
            }
        } else {
            int curIndex = start;
            while (curIndex < length) {
                int curNum = nums[curIndex];
                // 这道题相减可能溢出
                List<List<Integer>> subRes = subtractOverflow(target, curNum) ? new ArrayList<>() : nSum(nums, n - 1, target - curNum, curIndex + 1);
                subRes.forEach(list -> {
                    list.add(curNum);
                    res.add(list);
                });
                while (curIndex < length && nums[curIndex] == curNum) curIndex ++;
            }
        }
        return res;
    }

    private boolean subtractOverflow(int x, int y) {
        int r = x - y;
        return ((x ^ y) & (x ^ r)) < 0;
    }
}
// @lc code=end

/**
 * @lc app=leetcode.cn id=19 lang=java
 *
 * [19] 删除链表的倒数第 N 个结点
 *
 * https://leetcode.cn/problems/remove-nth-node-from-end-of-list/description/
 *
 * algorithms
 * Medium (45.15%)
 * Likes:    2413
 * Dislikes: 0
 * Total Accepted:    1M
 * Total Submissions: 2.3M
 * Testcase Example:  '[1,2,3,4,5]\n2'
 *
 * 给你一个链表，删除链表的倒数第 n 个结点，并且返回链表的头结点。
 *
 * 示例 1：
 * 输入：head = [1,2,3,4,5], n = 2
 * 输出：[1,2,3,5]
 *
 * 示例 2：
 * 输入：head = [1], n = 1
 * 输出：[]
 *
 * 示例 3：
 * 输入：head = [1,2], n = 1
 * 输出：[1]
 *
 * 提示：
 * 链表中结点的数目为 sz
 * 1 <= sz <= 30
 * 0 <= Node.val <= 100
 * 1 <= n <= sz
 *
 * 进阶：你能尝试使用一趟扫描实现吗？
 */
// @lc code=start
class RemoveNthNodeFromEndOfList {

    public ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null || n == 0) {
            return null;
        }
        ListNode newHead = new ListNode(-1, head);
        ListNode left = newHead;
        ListNode right = newHead;
        for (int i = 0; i < n; i ++) {
            if (right.next == null) {
                return null;
            }
            right = right.next;
        }
        while (right.next != null) {
            left = left.next;
            right = right.next;
        }
        left.next = left.next.next;
        return newHead.next;
    }

    // 解法2
    public ListNode removeNthFromEnd2(ListNode head, int n) {
        // 虚拟头结点
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        // 删除倒数第 n 个，要先找倒数第 n + 1 个节点
        ListNode x = findFromEnd(dummy, n + 1);
        // 删掉倒数第 n 个节点
        x.next = x.next.next;
        return dummy.next;
    }

    // 返回链表的倒数第 k 个节点
    ListNode findFromEnd(ListNode head, int k) {
        ListNode p1 = head;
        // p1 先走 k 步
        for (int i = 0; i < k; i++) {
            p1 = p1.next;
        }
        ListNode p2 = head;
        // p1 和 p2 同时走 n - k 步
        // 注意: 因为传入的是 n + 1, 所以这里用 p1 != null 判断, 如果传入的是 n, 这里应该判断 p1.next != null
        while (p1 != null) {
            p2 = p2.next;
            p1 = p1.next;
        }
        // p2 现在指向第 n - k 个节点
        return p2;
    }

    /**
     * Definition for singly-linked list.
     */
    private static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }

        @Override
        public String toString() {
            return val + ", " + next;
        }
    }

    public static ListNode example1() {
        return new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
    }
    public static ListNode example2() {
        return new ListNode(1);
    }
    public static ListNode example3() {
        return new ListNode(1, new ListNode(2));
    }
}
// @lc code=end
