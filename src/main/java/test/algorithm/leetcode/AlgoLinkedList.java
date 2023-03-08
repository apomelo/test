package test.algorithm.leetcode;

import lombok.extern.slf4j.Slf4j;

/**
 * 链表
 * @author C
 * @date 2023/2/28
 */
@Slf4j
public class AlgoLinkedList {
    public static void main(String[] args) {
        // 模板
        log.info("MergeTwoSortedLists: {}", new MergeTwoSortedLists().mergeTwoLists(MergeTwoSortedLists.example1(), MergeTwoSortedLists.example2()));
        log.info("MergeTwoSortedLists: {}", new MergeTwoSortedLists().mergeTwoLists(MergeTwoSortedLists.example3(), MergeTwoSortedLists.example3()));
        log.info("MergeTwoSortedLists: {}", new MergeTwoSortedLists().mergeTwoLists(MergeTwoSortedLists.example3(), MergeTwoSortedLists.example4()));
        log.info("SwapNodesInPairs: {}", new SwapNodesInPairs().swapPairs(SwapNodesInPairs.example1()));
        log.info("SwapNodesInPairs: {}", new SwapNodesInPairs().swapPairs(SwapNodesInPairs.example2()));
        log.info("SwapNodesInPairs: {}", new SwapNodesInPairs().swapPairs(SwapNodesInPairs.example3()));
        log.info("ReverseNodesInKGroup: {}", new ReverseNodesInKGroup().reverseKGroup(ReverseNodesInKGroup.example1(), 2));
        log.info("ReverseNodesInKGroup: {}", new ReverseNodesInKGroup().reverseKGroup(ReverseNodesInKGroup.example1(), 3));
        log.info("ReverseNodesInKGroup: {}", new ReverseNodesInKGroup().reverseKGroup(ReverseNodesInKGroup.example2(), 2));
    }
}

/**
 * @lc app=leetcode.cn id=21 lang=java
 *
 * [21] 合并两个有序链表
 *
 * https://leetcode.cn/problems/merge-two-sorted-lists/description/
 *
 * algorithms
 * Easy (66.53%)
 * Likes:    2938
 * Dislikes: 0
 * Total Accepted:    1.3M
 * Total Submissions: 2M
 * Testcase Example:  '[1,2,4]\n[1,3,4]'
 *
 * 将两个升序链表合并为一个新的 升序 链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
 *
 * 示例 1：
 * 输入：l1 = [1,2,4], l2 = [1,3,4]
 * 输出：[1,1,2,3,4,4]
 *
 * 示例 2：
 * 输入：l1 = [], l2 = []
 * 输出：[]
 *
 * 示例 3：
 * 输入：l1 = [], l2 = [0]
 * 输出：[0]
 *
 * 提示：
 * 两个链表的节点数目范围是 [0, 50]
 * -100 <= Node.val <= 100
 * l1 和 l2 均按 非递减顺序 排列
 */

// @lc code=start
class MergeTwoSortedLists {
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        // 虚拟头结点
        ListNode dummy = new ListNode(-1);
        ListNode p = dummy;
        ListNode p1 = list1;
        ListNode p2 = list2;
        while (p1 != null && p2 != null) {
            // 比较 p1 和 p2 两个指针
            // 将值较小的的节点接到 p 指针
            if (p1.val < p2.val) {
                p.next = p1;
                p1 = p1.next;
            } else {
                p.next = p2;
                p2 = p2.next;
            }
            // p 指针不断前进
            p = p.next;
        }
        if (p1 != null) {
            p.next = p1;
        }
        if (p2 != null) {
            p.next = p2;
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

    public static ListNode example1() {
        return new ListNode(1, new ListNode(2, new ListNode(4)));
    }
    public static ListNode example2() {
        return new ListNode(1, new ListNode(3, new ListNode(4)));
    }
    public static ListNode example3() {
        return null;
    }
    public static ListNode example4() {
        return new ListNode(0);
    }
}
// @lc code=end


/**
 * @lc app=leetcode.cn id=24 lang=java
 *
 * [24] 两两交换链表中的节点
 *
 * https://leetcode.cn/problems/swap-nodes-in-pairs/description/
 *
 * algorithms
 * Medium (71.30%)
 * Likes:    1741
 * Dislikes: 0
 * Total Accepted:    588.3K
 * Total Submissions: 825.1K
 * Testcase Example:  '[1,2,3,4]'
 *
 * 给你一个链表，两两交换其中相邻的节点，并返回交换后链表的头节点。你必须在不修改节点内部的值的情况下完成本题（即，只能进行节点交换）。
 *
 * 示例 1：
 * 输入：head = [1,2,3,4]
 * 输出：[2,1,4,3]
 *
 * 示例 2：
 * 输入：head = []
 * 输出：[]
 *
 * 示例 3：
 * 输入：head = [1]
 * 输出：[1]
 *
 * 提示：
 * 链表中节点的数目在范围 [0, 100] 内
 * 0 <= Node.val <= 100
 */
// @lc code=start
class SwapNodesInPairs {
    // 解法1
    public ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode dummy = new ListNode(-1);
        ListNode p = dummy;
        ListNode p1 = head;
        int index = 1;
        while (p1 != null) {
            if (index % 2 == 1) {
                p.next = p1;
                p1 = p1.next;
                // 指针停留
            } else {
                ListNode temp = p.next;
                // 断开环
                temp.next = null;
                p.next = p1;
                p1 = p1.next;
                p.next.next = temp;
                // 指针移动 2 位
                p = p.next.next;
            }
            index ++;
        }
        return dummy.next;
    }

    // 解法2: 递归
    public ListNode swapPairs2(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode first = head;
        ListNode second = head.next;
        ListNode others = head.next.next;
        // 先把前两个元素翻转
        // 此处会导致环异常
        second.next = first;
        // 利用递归定义，将剩下的链表节点两两翻转，接到后面
        first.next = swapPairs(others);
        // 现在整个链表都成功翻转了，返回新的头结点
        return second;
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
        return new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4))));
    }
    public static ListNode example2() {
        return null;
    }
    public static ListNode example3() {
        return new ListNode(1);
    }
}
// @lc code=end


/**
 * @lc app=leetcode.cn id=25 lang=java
 *
 * [25] K 个一组翻转链表
 *
 * https://leetcode.cn/problems/reverse-nodes-in-k-group/description/
 *
 * algorithms
 * Hard (67.75%)
 * Likes:    1940
 * Dislikes: 0
 * Total Accepted:    439.6K
 * Total Submissions: 649K
 * Testcase Example:  '[1,2,3,4,5]\n2'
 *
 * 给你链表的头节点 head ，每 k 个节点一组进行翻转，请你返回修改后的链表。
 * k 是一个正整数，它的值小于或等于链表的长度。如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。
 * 你不能只是单纯的改变节点内部的值，而是需要实际进行节点交换。
 *
 * 示例 1：
 * 输入：head = [1,2,3,4,5], k = 2
 * 输出：[2,1,4,3,5]
 *
 * 示例 2：
 * 输入：head = [1,2,3,4,5], k = 3
 * 输出：[3,2,1,4,5]
 *
 * 提示：
 * 链表中的节点数目为 n
 * 1 <= k <= n <= 5000
 * 0 <= Node.val <= 1000
 *
 * 进阶：你可以设计一个只用 O(1) 额外内存空间的算法解决此问题吗？
 */
// @lc code=start
class ReverseNodesInKGroup {
    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || head.next == null || k <= 1) return head;
        ListNode first = head;
        ListNode other = first.next;
        // 遍历剩余节点是否足够 k 个, 并找出下一段头结点
        for (int i = 1; i < k; i++) {
            if (other == null) {
                return first;
            }
            other = other.next;
        }

        // 遍历并翻转 k 个节点
        ListNode p1 = null;
        ListNode p2 = first;
        ListNode p3 = first.next;
        for (int i = 0; i < k; i++) {
            p2.next = p1;
            // 后移前 2 个指针
            p1 = p2;
            p2 = p3;
            // 后移第 3 个指针时判断是否为空
            if (p3 == null) {
                break;
            }
            p3 = p3.next;
        }
        first.next = reverseKGroup(other, k);
        return p1;
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
        return new ListNode(1, new ListNode(2));
    }
}
// @lc code=end
