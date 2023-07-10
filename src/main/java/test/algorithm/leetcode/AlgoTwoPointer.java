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
        // 盛最多水的容器
        log.info("ContainerWithMostWater: {}", new ContainerWithMostWater().maxArea(new int[] {1,8,6,2,5,4,8,3,7}));
        log.info("ContainerWithMostWater: {}", new ContainerWithMostWater().maxArea(new int[] {1,1}));
        // 三数之和
        log.info("ThreeSum: {}", new ThreeSum().threeSum(new int[] {-1,0,1,2,-1,-4}));
        log.info("ThreeSum: {}", new ThreeSum().threeSum(new int[] {0,1,1}));
        log.info("ThreeSum: {}", new ThreeSum().threeSum(new int[] {0,0,0}));
        // 最接近的三数之和
        log.info("ThreeSumClosest: {}", new ThreeSumClosest().threeSumClosest(new int[] {-1,2,1,-4}, 1));
        log.info("ThreeSumClosest: {}", new ThreeSumClosest().threeSumClosest(new int[] {0,0,0}, 1));
        log.info("ThreeSumClosest: {}", new ThreeSumClosest().threeSumClosest(new int[] {1,1,-1}, 0));
        log.info("ThreeSumClosest: {}", new ThreeSumClosest().threeSumClosest(new int[] {0,3,97,102,200}, 300));
        // 四数之和
        log.info("FourSum: {}", new FourSum().fourSum(new int[] {1,0,-1,0,-2,2}, 0));
        log.info("FourSum: {}", new FourSum().fourSum(new int[] {2,2,2,2,2}, 8));
        // 删除链表的倒数第 N 个结点
        log.info("RemoveNthNodeFromEndOfList: {}", new RemoveNthNodeFromEndOfList().removeNthFromEnd(RemoveNthNodeFromEndOfList.example1(), 2));
        log.info("RemoveNthNodeFromEndOfList: {}", new RemoveNthNodeFromEndOfList().removeNthFromEnd(RemoveNthNodeFromEndOfList.example2(), 1));
        log.info("RemoveNthNodeFromEndOfList: {}", new RemoveNthNodeFromEndOfList().removeNthFromEnd(RemoveNthNodeFromEndOfList.example3(), 1));
        // 删除有序数组中的重复项
        log.info("RemoveDuplicatesFromSortedArray: {}", new RemoveDuplicatesFromSortedArray().removeDuplicates(new int[] {1,1,2}));
        log.info("RemoveDuplicatesFromSortedArray: {}", new RemoveDuplicatesFromSortedArray().removeDuplicates(new int[] {0,0,1,1,1,2,2,3,3,4}));
        // 移除元素
        log.info("RemoveElement: {}", new RemoveElement().removeElement(new int[] {3,2,2,3}, 3));
        log.info("RemoveElement: {}", new RemoveElement().removeElement(new int[] {0,1,2,2,3,0,4,2}, 2));
        // 接雨水
        log.info("TrappingRainWater: {}", new TrappingRainWater().trap(new int[] {0,1,0,2,1,0,1,3,2,1,2,1}));
        log.info("TrappingRainWater: {}", new TrappingRainWater().trap(new int[] {4,2,0,3,2,5}));
        log.info("TrappingRainWater: {}", new TrappingRainWater().trap(new int[] {3,0,4,0,5}));
        // 颜色分类
        int[] sortColorsExample1 = new int[] {2,0,2,1,1,0};
        new SortColors().sortColors(sortColorsExample1);
        log.info("SortColors: {}", sortColorsExample1);
        int[] sortColorsExample2 = new int[] {2,0,1};
        new SortColors().sortColors(sortColorsExample2);
        log.info("SortColors: {}", sortColorsExample2);
        // 删除有序数组中的重复项 II
        log.info("RemoveDuplicatesFromSortedArrayII: {}", new RemoveDuplicatesFromSortedArrayII().removeDuplicates(new int[] {1,1,1,2,2,3}));
        log.info("RemoveDuplicatesFromSortedArrayII: {}", new RemoveDuplicatesFromSortedArrayII().removeDuplicates(new int[] {0,0,1,1,1,1,2,3,3}));
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


/**
 * @lc app=leetcode.cn id=26 lang=java
 *
 * [26] 删除有序数组中的重复项
 *
 * https://leetcode.cn/problems/remove-duplicates-from-sorted-array/description/
 *
 * algorithms
 * Easy (54.67%)
 * Likes:    3053
 * Dislikes: 0
 * Total Accepted:    1.4M
 * Total Submissions: 2.6M
 * Testcase Example:  '[1,1,2]'
 *
 * 给你一个 升序排列 的数组 nums ，请你 原地 删除重复出现的元素，使每个元素 只出现一次 ，返回删除后数组的新长度。元素的 相对顺序 应该保持
 * 一致 。
 * 由于在某些语言中不能改变数组的长度，所以必须将结果放在数组nums的第一部分。更规范地说，如果在删除重复项之后有 k 个元素，那么 nums 的前 k
 * 个元素应该保存最终结果。
 * 将最终结果插入 nums 的前 k 个位置后返回 k 。
 * 不要使用额外的空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成。
 *
 * 判题标准:
 * 系统会用下面的代码来测试你的题解:
 * int[] nums = [...]; // 输入数组
 * int[] expectedNums = [...]; // 长度正确的期望答案
 * int k = removeDuplicates(nums); // 调用
 * assert k == expectedNums.length;
 * for (int i = 0; i < k; i++) {
 *     assert nums[i] == expectedNums[i];
 * }
 * 如果所有断言都通过，那么您的题解将被 通过。
 *
 * 示例 1：
 * 输入：nums = [1,1,2]
 * 输出：2, nums = [1,2,_]
 * 解释：函数应该返回新的长度 2 ，并且原数组 nums 的前两个元素被修改为 1, 2 。不需要考虑数组中超出新长度后面的元素。
 *
 * 示例 2：
 * 输入：nums = [0,0,1,1,1,2,2,3,3,4]
 * 输出：5, nums = [0,1,2,3,4]
 * 解释：函数应该返回新的长度 5 ， 并且原数组 nums 的前五个元素被修改为 0, 1, 2, 3, 4
 * 。不需要考虑数组中超出新长度后面的元素。
 *
 * 提示：
 * 1 <= nums.length <= 3 * 10^4
 * -10^4 <= nums[i] <= 10^4
 * nums 已按 升序 排列
 */
// @lc code=start
class RemoveDuplicatesFromSortedArray {
    public int removeDuplicates(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int slow = 0, fast = 0;
        while (fast < nums.length) {
            if (nums[slow] != nums[fast]) {
                slow ++;
                // 维护 nums[0..slow] 无重复
                nums[slow] = nums[fast];
            }
            fast ++;
        }
        // 数组长度为索引 + 1
        return slow + 1;
    }
}
// @lc code=end


/**
 * @lc app=leetcode.cn id=27 lang=java
 *
 * [27] 移除元素
 *
 * https://leetcode.cn/problems/remove-element/description/
 *
 * algorithms
 * Easy (59.41%)
 * Likes:    1697
 * Dislikes: 0
 * Total Accepted:    978.4K
 * Total Submissions: 1.6M
 * Testcase Example:  '[3,2,2,3]\n3'
 *
 * 给你一个数组 nums 和一个值 val，你需要 原地 移除所有数值等于 val 的元素，并返回移除后数组的新长度。
 * 不要使用额外的数组空间，你必须仅使用 O(1) 额外空间并 原地 修改输入数组。
 * 元素的顺序可以改变。你不需要考虑数组中超出新长度后面的元素。
 *
 * 说明:
 * 为什么返回数值是整数，但输出的答案是数组呢?
 * 请注意，输入数组是以「引用」方式传递的，这意味着在函数里修改输入数组对于调用者是可见的。
 * 你可以想象内部操作如下:
 * // nums 是以“引用”方式传递的。也就是说，不对实参作任何拷贝
 * int len = removeElement(nums, val);
 * // 在函数里修改输入数组对于调用者是可见的。
 * // 根据你的函数返回的长度, 它会打印出数组中 该长度范围内 的所有元素。
 * for (int i = 0; i < len; i++) {
 *     print(nums[i]);
 * }
 *
 * 示例 1：
 * 输入：nums = [3,2,2,3], val = 3
 * 输出：2, nums = [2,2]
 * 解释：函数应该返回新的长度 2, 并且 nums 中的前两个元素均为 2。你不需要考虑数组中超出新长度后面的元素。例如，函数返回的新长度为 2 ，而
 * nums = [2,2,3,3] 或 nums = [2,2,0,0]，也会被视作正确答案。
 *
 * 示例 2：
 * 输入：nums = [0,1,2,2,3,0,4,2], val = 2
 * 输出：5, nums = [0,1,4,0,3]
 * 解释：函数应该返回新的长度 5, 并且 nums 中的前五个元素为 0, 1, 3, 0,
 * 4。注意这五个元素可为任意顺序。你不需要考虑数组中超出新长度后面的元素。
 *
 * 提示：
 * 0 <= nums.length <= 100
 * 0 <= nums[i] <= 50
 * 0 <= val <= 100
 */
// @lc code=start
class RemoveElement {
    public int removeElement(int[] nums, int val) {
        if (nums == null || nums.length == 0) return 0;
        int slow = 0, fast = 0;
        while (fast < nums.length) {
            if (nums[fast] != val) {
                nums[slow] = nums[fast];
                slow ++;
            }
            fast ++;
        }
        return slow;
    }
}
// @lc code=end


/**
 * @lc app=leetcode.cn id=42 lang=java
 *
 * [42] 接雨水
 *
 * https://leetcode.cn/problems/trapping-rain-water/description/
 *
 * algorithms
 * Hard (62.58%)
 * Likes:    4276
 * Dislikes: 0
 * Total Accepted:    672.5K
 * Total Submissions: 1.1M
 * Testcase Example:  '[0,1,0,2,1,0,1,3,2,1,2,1]'
 *
 * 给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
 *
 * 示例 1：
 * 输入：height = [0,1,0,2,1,0,1,3,2,1,2,1]
 * 输出：6
 * 解释：上面是由数组 [0,1,0,2,1,0,1,3,2,1,2,1] 表示的高度图，在这种情况下，可以接 6 个单位的雨水（蓝色部分表示雨水）。
 *
 * 示例 2：
 * 输入：height = [4,2,0,3,2,5]
 * 输出：9
 *
 * 提示：
 * n == height.length
 * 1 <= n <= 2 * 10^4
 * 0 <= height[i] <= 10^5
 */
// @lc code=start
class TrappingRainWater {
    public int trap(int[] height) {
        if (height == null || height.length < 3) {
            return 0;
        }

        int n = height.length;
        int res = 0;
        // 数组充当备忘录
        int[] lMax = new int[n];
        int[] rMax = new int[n];
        // 初始化 base case
        lMax[0] = height[0];
        rMax[n - 1] = height[n - 1];
        // 从左向右计算 l_max
        for (int i = 1; i < n; i++) {
            lMax[i] = Math.max(height[i], lMax[i - 1]);
        }
        // 从右向左计算 r_max
        for (int i = n - 2; i >= 0; i--) {
            rMax[i] = Math.max(height[i], rMax[i + 1]);
        }
        // 计算答案
        for (int i = 1; i < n - 1; i++) {
            res += Math.min(lMax[i], rMax[i]) - height[i];
        }
        return res;
    }
}
// @lc code=end


/**
 * @lc app=leetcode.cn id=75 lang=java
 *
 * [75] 颜色分类
 *
 * https://leetcode.cn/problems/sort-colors/description/
 *
 * algorithms
 * Medium (60.41%)
 * Likes:    1611
 * Dislikes: 0
 * Total Accepted:    542.9K
 * Total Submissions: 898.5K
 * Testcase Example:  '[2,0,2,1,1,0]'
 *
 * 给定一个包含红色、白色和蓝色、共 n 个元素的数组 nums ，原地对它们进行排序，使得相同颜色的元素相邻，并按照红色、白色、蓝色顺序排列。
 * 我们使用整数 0、 1 和 2 分别表示红色、白色和蓝色。
 * 必须在不使用库内置的 sort 函数的情况下解决这个问题。
 *
 * 示例 1：
 * 输入：nums = [2,0,2,1,1,0]
 * 输出：[0,0,1,1,2,2]
 *
 * 示例 2：
 * 输入：nums = [2,0,1]
 * 输出：[0,1,2]
 *
 * 提示：
 * n == nums.length
 * 1 <= n <= 300
 * nums[i] 为 0、1 或 2
 *
 * 进阶：
 * 你能想出一个仅使用常数空间的一趟扫描算法吗？
 */
// @lc code=start
class SortColors {
    public void sortColors(int[] nums) {
        // 定义左指针和右指针
        int left = 0, right = nums.length - 1;
        // 当前指针初始值为0
        int cur = 0;
        // 只要当前指针不超过右指针，就继续处理 (注意边界是 <=)
        while (cur <= right) {
            if (nums[cur] == 0) {
                // 将当前指针指向的元素与左指针指向的元素交换，并将左指针和当前指针均加1
                swap(nums, cur++, left++);
            } else if (nums[cur] == 2) {
                // 将当前指针指向的元素与右指针指向的元素交换，并将右指针减1 (不移动当前指针)
                swap(nums, cur, right--);
            } else {
                // 将当前指针加1
                cur++;
            }
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
// @lc code=end


/**
 * @lc app=leetcode.cn id=80 lang=java
 *
 * [80] 删除有序数组中的重复项 II
 *
 * https://leetcode.cn/problems/remove-duplicates-from-sorted-array-ii/description/
 *
 * algorithms
 * Medium (61.65%)
 * Likes:    815
 * Dislikes: 0
 * Total Accepted:    241.6K
 * Total Submissions: 391.9K
 * Testcase Example:  '[1,1,1,2,2,3]'
 *
 * 给你一个有序数组 nums ，请你 原地 删除重复出现的元素，使得出现次数超过两次的元素只出现两次 ，返回删除后数组的新长度。
 * 不要使用额外的数组空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成。
 *
 * 说明：
 * 为什么返回数值是整数，但输出的答案是数组呢？
 * 请注意，输入数组是以「引用」方式传递的，这意味着在函数里修改输入数组对于调用者是可见的。
 * 你可以想象内部操作如下:
 * // nums 是以“引用”方式传递的。也就是说，不对实参做任何拷贝
 * int len = removeDuplicates(nums);
 * // 在函数里修改输入数组对于调用者是可见的。
 * // 根据你的函数返回的长度, 它会打印出数组中 该长度范围内 的所有元素。
 * for (int i = 0; i < len; i++) {
 *     print(nums[i]);
 * }
 *
 * 示例 1：
 * 输入：nums = [1,1,1,2,2,3]
 * 输出：5, nums = [1,1,2,2,3]
 * 解释：函数应返回新长度 length = 5, 并且原数组的前五个元素被修改为 1, 1, 2, 2, 3 。
 * 不需要考虑数组中超出新长度后面的元素。
 *
 * 示例 2：
 * 输入：nums = [0,0,1,1,1,1,2,3,3]
 * 输出：7, nums = [0,0,1,1,2,3,3]
 * 解释：函数应返回新长度 length = 7, 并且原数组的前五个元素被修改为 0, 0, 1, 1, 2, 3, 3 。
 * 不需要考虑数组中超出新长度后面的元素。
 *
 * 提示：
 * 1 <= nums.length <= 3 * 10^4
 * -10^4 <= nums[i] <= 10^4
 * nums 已按升序排列
 */
// @lc code=start
class RemoveDuplicatesFromSortedArrayII {
    public int removeDuplicates(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        // 快慢指针，维护 nums[0..slow] 为结果子数组
        int slow = 0, fast = 0;
        // 记录一个元素重复的次数
        int count = 0;
        while (fast < nums.length) {
            // 当遇到不同元素和一个元素重复次数不到 2 次时
            if (nums[fast] != nums[slow]
                    || (slow < fast && count < 2)) {
                slow++;
                nums[slow] = nums[fast];
            }
            fast++;
            count++;
            // 遇到不同的元素
            if (fast < nums.length && nums[fast] != nums[fast - 1]) {
                count = 0;
            }
        }
        // 数组长度为索引 + 1
        return slow + 1;
    }
}
// @lc code=end
