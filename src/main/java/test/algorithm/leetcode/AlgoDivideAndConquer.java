package test.algorithm.leetcode;

import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 分治算法
 * @author C
 * @date 2023/2/1
 */
@Slf4j
public class AlgoDivideAndConquer {
    public static void main(String[] args) {
        // 合并K个升序链表
        log.info("MergeKSortedLists: {}", new MergeKSortedLists().mergeKLists(MergeKSortedLists.example1()));
        log.info("MergeKSortedLists: {}", new MergeKSortedLists().mergeKLists(MergeKSortedLists.example2()));
        log.info("MergeKSortedLists: {}", new MergeKSortedLists().mergeKLists(MergeKSortedLists.example3()));
        // 最大子数组和
        log.info("MaximumSubarray: {}", new MaximumSubarray().maxSubArray(new int[] {-2,1,-3,4,-1,2,1,-5,4}));
        log.info("MaximumSubarray: {}", new MaximumSubarray().maxSubArray(new int[] {1}));
        log.info("MaximumSubarray: {}", new MaximumSubarray().maxSubArray(new int[] {5,4,-1,7,8}));
    }
}

/**
 * @lc app=leetcode.cn id=23 lang=java
 *
 * [23] 合并K个升序链表
 *
 * https://leetcode.cn/problems/merge-k-sorted-lists/description/
 *
 * algorithms
 * Hard (57.58%)
 * Likes:    2347
 * Dislikes: 0
 * Total Accepted:    607.7K
 * Total Submissions: 1.1M
 * Testcase Example:  '[[1,4,5],[1,3,4],[2,6]]'
 *
 * 给你一个链表数组，每个链表都已经按升序排列。
 * 请你将所有链表合并到一个升序链表中，返回合并后的链表。
 *
 * 示例 1：
 * 输入：lists = [[1,4,5],[1,3,4],[2,6]]
 * 输出：[1,1,2,3,4,4,5,6]
 * 解释：链表数组如下：
 * [
 *   1->4->5,
 *   1->3->4,
 *   2->6
 * ]
 * 将它们合并到一个有序链表中得到。
 * 1->1->2->3->4->4->5->6
 *
 * 示例 2：
 * 输入：lists = []
 * 输出：[]
 *
 * 示例 3：
 * 输入：lists = [[]]
 * 输出：[]
 *
 * 提示：
 * k == lists.length
 * 0 <= k <= 10^4
 * 0 <= lists[i].length <= 500
 * -10^4 <= lists[i][j] <= 10^4
 * lists[i] 按 升序 排列
 * lists[i].length 的总和不超过 10^4
 */
// @lc code=start
class MergeKSortedLists {
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) return null;
        // 虚拟头结点
        ListNode dummy = new ListNode(-1);
        ListNode p = dummy;
        // 优先级队列，最小堆
        PriorityQueue<ListNode> pq = new PriorityQueue<>(lists.length, Comparator.comparingInt(node -> node.val));
        // 将 k 个链表的头结点加入最小堆
        for (ListNode head : lists) {
            if (head != null)
                pq.add(head);
        }

        while (!pq.isEmpty()) {
            // 获取最小节点，接到结果链表中
            ListNode node = pq.poll();
            p.next = node;
            if (node.next != null) {
                pq.add(node.next);
            }
            // p 指针不断前进
            p = p.next;
        }
        return dummy.next;
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

    private static ListNode list1() {
        return new ListNode(1, new ListNode(4, new ListNode(5)));
    }
    private static ListNode list2() {
        return new ListNode(1, new ListNode(3, new ListNode(4)));
    }
    private static ListNode list3() {
        return new ListNode(2, new ListNode(6));
    }
    public static ListNode[] example1() {
        return new ListNode[] {list1(), list2(), list3()};
    }
    public static ListNode[] example2() {
        return null;
    }
    public static ListNode[] example3() {
        return new ListNode[0];
    }
}
// @lc code=end


/**
 * @lc app=leetcode.cn id=53 lang=java
 *
 * [53] 最大子数组和
 *
 * https://leetcode.cn/problems/maximum-subarray/description/
 *
 * algorithms
 * Medium (54.76%)
 * Likes:    6054
 * Dislikes: 0
 * Total Accepted:    1.4M
 * Total Submissions: 2.5M
 * Testcase Example:  '[-2,1,-3,4,-1,2,1,-5,4]'
 *
 * 给你一个整数数组 nums ，请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
 * 子数组 是数组中的一个连续部分。
 *
 * 示例 1：
 * 输入：nums = [-2,1,-3,4,-1,2,1,-5,4]
 * 输出：6
 * 解释：连续子数组 [4,-1,2,1] 的和最大，为 6 。
 *
 * 示例 2：
 * 输入：nums = [1]
 * 输出：1
 *
 * 示例 3：
 * 输入：nums = [5,4,-1,7,8]
 * 输出：23
 *
 * 提示：
 * 1 <= nums.length <= 10^5
 * -10^4 <= nums[i] <= 10^4
 * 进阶：如果你已经实现复杂度为 O(n) 的解法，尝试使用更为精妙的 分治法 求解。
 */
// @lc code=start
class MaximumSubarray {
    public int maxSubArray(int[] nums) {
        int res = Integer.MIN_VALUE >> 1;
        int[] dp = new int[nums.length + 1];
        dp[0] = res;
        for (int i = 0; i < nums.length; i ++) {
            dp[i + 1] = Math.max(dp[i] + nums[i], nums[i]);
            if (dp[i + 1] > res) {
                res = dp[i + 1];
            }
        }
        return res;
    }
}
// @lc code=end

