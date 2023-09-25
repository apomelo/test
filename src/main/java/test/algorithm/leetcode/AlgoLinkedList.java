package test.algorithm.leetcode;

import lombok.extern.slf4j.Slf4j;

import test.algorithm.leetcode.AlgoLinkedList.Node;

/**
 * 链表
 * @author C
 * @date 2023/2/28
 */
@Slf4j
public class AlgoLinkedList {
    public static void main(String[] args) {
        // 合并两个有序链表
        log.info("MergeTwoSortedLists: {}", new MergeTwoSortedLists().mergeTwoLists(MergeTwoSortedLists.example1(), MergeTwoSortedLists.example2()));
        log.info("MergeTwoSortedLists: {}", new MergeTwoSortedLists().mergeTwoLists(MergeTwoSortedLists.example3(), MergeTwoSortedLists.example3()));
        log.info("MergeTwoSortedLists: {}", new MergeTwoSortedLists().mergeTwoLists(MergeTwoSortedLists.example3(), MergeTwoSortedLists.example4()));
        // 两两交换链表中的节点
        log.info("SwapNodesInPairs: {}", new SwapNodesInPairs().swapPairs1(SwapNodesInPairs.example1()));
        log.info("SwapNodesInPairs: {}", new SwapNodesInPairs().swapPairs1(SwapNodesInPairs.example2()));
        log.info("SwapNodesInPairs: {}", new SwapNodesInPairs().swapPairs1(SwapNodesInPairs.example3()));
        log.info("SwapNodesInPairs: {}", new SwapNodesInPairs().swapPairs2(SwapNodesInPairs.example1()));
        // K 个一组翻转链表
        log.info("ReverseNodesInKGroup: {}", new ReverseNodesInKGroup().reverseKGroup(ReverseNodesInKGroup.example1(), 2));
        log.info("ReverseNodesInKGroup: {}", new ReverseNodesInKGroup().reverseKGroup(ReverseNodesInKGroup.example1(), 3));
        log.info("ReverseNodesInKGroup: {}", new ReverseNodesInKGroup().reverseKGroup(ReverseNodesInKGroup.example2(), 2));
        // 旋转链表
        log.info("RotateList: {}", new RotateList().rotateRight(RotateList.example1(), 2));
        log.info("RotateList: {}", new RotateList().rotateRight(RotateList.example2(), 4));
        // 删除排序链表中的重复元素 II
        log.info("RemoveDuplicatesFromSortedListII: {}", new RemoveDuplicatesFromSortedListII().deleteDuplicates(RemoveDuplicatesFromSortedListII.example1()));
        log.info("RemoveDuplicatesFromSortedListII: {}", new RemoveDuplicatesFromSortedListII().deleteDuplicates(RemoveDuplicatesFromSortedListII.example2()));
        log.info("RemoveDuplicatesFromSortedListII: {}", new RemoveDuplicatesFromSortedListII().deleteDuplicates(RemoveDuplicatesFromSortedListII.example3()));
        log.info("RemoveDuplicatesFromSortedListII: {}", new RemoveDuplicatesFromSortedListII().deleteDuplicates(RemoveDuplicatesFromSortedListII.example4()));
        // 删除排序链表中的重复元素
        log.info("RemoveDuplicatesFromSortedList: {}", new RemoveDuplicatesFromSortedList().deleteDuplicates(RemoveDuplicatesFromSortedList.example1()));
        log.info("RemoveDuplicatesFromSortedList: {}", new RemoveDuplicatesFromSortedList().deleteDuplicates(RemoveDuplicatesFromSortedList.example2()));
        // 分隔链表
        log.info("PartitionList: {}", new PartitionList().partition(PartitionList.example1(), 3));
        log.info("PartitionList: {}", new PartitionList().partition(PartitionList.example2(), 2));
        // 反转链表 II
        log.info("ReverseLinkedListII: {}", new ReverseLinkedListII().reverseBetween(ReverseLinkedListII.example1(), 2, 4));
        log.info("ReverseLinkedListII: {}", new ReverseLinkedListII().reverseBetween(ReverseLinkedListII.example2(), 1, 1));
        log.info("ReverseLinkedListII: {}", new ReverseLinkedListII().reverseBetween(ReverseLinkedListII.example3(), 1, 2));
        // [138] 复制带随机指针的链表
        log.info("CopyListWithRandomPointer: {}", new CopyListWithRandomPointer().copyRandomList(CopyListWithRandomPointer.example1()));
        log.info("CopyListWithRandomPointer: {}", new CopyListWithRandomPointer().copyRandomList(CopyListWithRandomPointer.example2()));
        log.info("CopyListWithRandomPointer: {}", new CopyListWithRandomPointer().copyRandomList(CopyListWithRandomPointer.example3()));
    }

    static class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }

        @Override
        public String toString() {
            return random != null
                    ? "[" + val + ", " + random.val + "], " + next
                    : "[" + val + ", null], " + next;
        }
    }
}

/**
 * Definition for singly-linked list.
 */
class ListNode {
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
class SwapNodesInPairs {
    // 解法1
    public ListNode swapPairs1(ListNode head) {
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
        // 一定要加 first.next = null; 这句, 否则会导致环异常:
        // (Method threw 'java.lang.StackOverflowError' exception. Cannot evaluate test.algorithm.leetcode.SwapNodesInPairs$ListNode.toString())
        first.next = null;
        second.next = first;
        // 利用递归定义，将剩下的链表节点两两翻转，接到后面
        first.next = swapPairs2(others);
        // 现在整个链表都成功翻转了，返回新的头结点
        return second;
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

    public static ListNode example1() {
        return new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
    }

    public static ListNode example2() {
        return new ListNode(1, new ListNode(2));
    }
}


/**
 * @lc app=leetcode.cn id=61 lang=java
 *
 * [61] 旋转链表
 *
 * https://leetcode.cn/problems/rotate-list/description/
 *
 * algorithms
 * Medium (41.47%)
 * Likes:    927
 * Dislikes: 0
 * Total Accepted:    314.6K
 * Total Submissions: 759K
 * Testcase Example:  '[1,2,3,4,5]\n2'
 *
 * 给你一个链表的头节点 head ，旋转链表，将链表每个节点向右移动 k 个位置。
 *
 * 示例 1：
 * 输入：head = [1,2,3,4,5], k = 2
 * 输出：[4,5,1,2,3]
 *
 * 示例 2：
 * 输入：head = [0,1,2], k = 4
 * 输出：[2,0,1]
 *
 * 提示：
 * 链表中节点的数目在范围 [0, 500] 内
 * -100 <= Node.val <= 100
 * 0 <= k <= 2 * 10^9
 */
class RotateList {
    public ListNode rotateRight(ListNode head, int k) {
        // 判断链表长度为 0 或 1 的情况（长度为 0 时会导致下面计算翻转长度出现除数为 0）
        if (head == null || head.next == null) {
            return head;
        }
        // 先计算链表长度
        int length = 0;
        ListNode p = head;
        while (p != null) {
            p = p.next;
            length ++;
        }
        // 计算需要翻转的长度
        int rotateLength = k % length;
        // 不需要翻转
        if (rotateLength == 0) {
            return head;
        }

        // 双指针
        ListNode left = head, right = head;
        // 右指针先向前走翻转长度
        for (int i = 0; i < rotateLength; i++) {
            right = right.next;
        }
        // 左右指针同时走，右指针到达末尾
        while (right.next != null) {
            left = left.next;
            right = right.next;
        }

        // 重新拼接链表
        ListNode res = left.next;
        left.next = null;
        right.next = head;

        return res;
    }

    public static ListNode example1() {
        return new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
    }

    public static ListNode example2() {
        return new ListNode(0, new ListNode(1, new ListNode(2)));
    }
}


/**
 * @lc app=leetcode.cn id=82 lang=java
 *
 * [82] 删除排序链表中的重复元素 II
 *
 * https://leetcode.cn/problems/remove-duplicates-from-sorted-list-ii/description/
 *
 * algorithms
 * Medium (53.53%)
 * Likes:    1144
 * Dislikes: 0
 * Total Accepted:    345.5K
 * Total Submissions: 645.3K
 * Testcase Example:  '[1,2,3,3,4,4,5]'
 *
 * 给定一个已排序的链表的头 head ， 删除原始链表中所有重复数字的节点，只留下不同的数字 。返回 已排序的链表 。
 *
 * 示例 1：
 * 输入：head = [1,2,3,3,4,4,5]
 * 输出：[1,2,5]
 *
 * 示例 2：
 * 输入：head = [1,1,1,2,3]
 * 输出：[2,3]
 *
 * 提示：
 * 链表中节点数目在范围 [0, 300] 内
 * -100 <= Node.val <= 100
 * 题目数据保证链表已经按升序 排列
 */
class RemoveDuplicatesFromSortedListII {
    public ListNode deleteDuplicates(ListNode head) {
        // 创建一个虚拟头节点，方便处理头节点重复的情况
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        // 定义前驱节点和当前节点
        ListNode prev = dummy;
        ListNode cur = head;

        while (cur != null) {
            // 判断当前节点是否是重复节点
            if (cur.next != null && cur.val == cur.next.val) {
                // 找到下一个不重复的节点
                while (cur.next != null && cur.val == cur.next.val) {
                    cur = cur.next;
                }
                // 删除重复节点，prev.next 指向下一个不重复的节点
                prev.next = cur.next;
            } else {
                // 当前节点不是重复节点，移动 prev 指针到当前节点
                prev = prev.next;
            }
            // 移动当前节点指针到下一个节点
            cur = cur.next;
        }

        return dummy.next;
    }

    public static ListNode example1() {
        return new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(3, new ListNode(4, new ListNode(4, new ListNode(5)))))));
    }

    public static ListNode example2() {
        return new ListNode(1, new ListNode(1, new ListNode(1, new ListNode(2, new ListNode(3)))));
    }

    public static ListNode example3() {
        return new ListNode(1, new ListNode(1));
    }

    public static ListNode example4() {
        return new ListNode(1);
    }
}


/**
 * @lc app=leetcode.cn id=83 lang=java
 *
 * [83] 删除排序链表中的重复元素
 *
 * https://leetcode.cn/problems/remove-duplicates-from-sorted-list/description/
 *
 * algorithms
 * Easy (53.10%)
 * Likes:    1013
 * Dislikes: 0
 * Total Accepted:    587.3K
 * Total Submissions: 1.1M
 * Testcase Example:  '[1,1,2]'
 *
 * 给定一个已排序的链表的头 head ， 删除所有重复的元素，使每个元素只出现一次 。返回 已排序的链表 。
 *
 * 示例 1：
 * 输入：head = [1,1,2]
 * 输出：[1,2]
 *
 * 示例 2：
 * 输入：head = [1,1,2,3,3]
 * 输出：[1,2,3]
 *
 * 提示：
 * 链表中节点数目在范围 [0, 300] 内
 * -100 <= Node.val <= 100
 * 题目数据保证链表已经按升序 排列
 */
class RemoveDuplicatesFromSortedList {
    public ListNode deleteDuplicates(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode slow = head, fast = head;
        while (fast != null) {
            if (fast.val != slow.val) {
                // nums[slow] = nums[fast];
                slow.next = fast;
                // slow++;
                slow = slow.next;
            }
            // fast++
            fast = fast.next;
        }
        // 断开与后面重复元素的连接
        slow.next = null;
        return head;
    }

    public static ListNode example1() {
        return new ListNode(1, new ListNode(1, new ListNode(2)));
    }

    public static ListNode example2() {
        return new ListNode(1, new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(3)))));
    }
}


/**
 * @lc app=leetcode.cn id=86 lang=java
 *
 * [86] 分隔链表
 *
 * https://leetcode.cn/problems/partition-list/description/
 *
 * algorithms
 * Medium (64.17%)
 * Likes:    729
 * Dislikes: 0
 * Total Accepted:    222.1K
 * Total Submissions: 346K
 * Testcase Example:  '[1,4,3,2,5,2]\n3'
 *
 * 给你一个链表的头节点 head 和一个特定值 x ，请你对链表进行分隔，使得所有 小于 x 的节点都出现在 大于或等于 x 的节点之前。
 * 你应当 保留 两个分区中每个节点的初始相对位置。
 *
 * 示例 1：
 * 输入：head = [1,4,3,2,5,2], x = 3
 * 输出：[1,2,2,4,3,5]
 *
 * 示例 2：
 * 输入：head = [2,1], x = 2
 * 输出：[1,2]
 *
 * 提示：
 * 链表中节点的数目在范围 [0, 200] 内
 * -100 <= Node.val <= 100
 * -200 <= x <= 200
 */
class PartitionList {
    public ListNode partition(ListNode head, int x) {
        ListNode beforeHead = new ListNode(0); // 小于 x 的节点链表的头节点
        ListNode before = beforeHead; // 小于 x 的节点链表的当前节点
        ListNode afterHead = new ListNode(0); // 大于等于 x 的节点链表的头节点
        ListNode after = afterHead; // 大于等于 x 的节点链表的当前节点

        while (head != null) {
            if (head.val < x) {
                before.next = head;
                before = before.next;
            } else {
                after.next = head;
                after = after.next;
            }
            head = head.next;
        }

        after.next = null; // 断开大于等于 x 的节点链表的最后一个节点，避免出现环
        before.next = afterHead.next; // 连接小于 x 的节点链表和大于等于 x 的节点链表

        return beforeHead.next; // 返回新的链表头节点
    }

    public static ListNode example1() {
        return new ListNode(1, new ListNode(4, new ListNode(3, new ListNode(2, new ListNode(5, new ListNode(2))))));
    }

    public static ListNode example2() {
        return new ListNode(2, new ListNode(1));
    }
}


/**
 * @lc app=leetcode.cn id=92 lang=java
 *
 * [92] 反转链表 II
 *
 * https://leetcode.cn/problems/reverse-linked-list-ii/description/
 *
 * algorithms
 * Medium (55.68%)
 * Likes:    1588
 * Dislikes: 0
 * Total Accepted:    410.9K
 * Total Submissions: 737.9K
 * Testcase Example:  '[1,2,3,4,5]\n2\n4'
 *
 * 给你单链表的头指针 head 和两个整数 left 和 right ，其中 left  。请你反转从位置 left 到位置 right 的链表节点，返回
 * 反转后的链表 。
 *
 * 示例 1：
 * 输入：head = [1,2,3,4,5], left = 2, right = 4
 * 输出：[1,4,3,2,5]
 *
 * 示例 2：
 * 输入：head = [5], left = 1, right = 1
 * 输出：[5]
 *
 * 提示：
 * 链表中节点数目为 n
 * 1 <= n <= 500
 * -500 <= Node.val <= 500
 * 1 <= left <= right <= n
 * 进阶： 你可以使用一趟扫描完成反转吗？
 */
class ReverseLinkedListII {
    public ListNode reverseBetween(ListNode head, int left, int right) {
        if (head == null || left >= right) {
            return head;
        }

        ListNode dummy = new ListNode(-1);
        dummy.next = head;

        ListNode preStart = dummy;
        ListNode cur = preStart.next;

        // 移动 cur 指针，直到它指向要反转的部分的起始节点的前一个节点，记为 preStart
        for (int i = 1; i < left; i++) {
            preStart = cur;
            cur = cur.next;
        }

        // preStart 节点不再属于要反转的部分，将其指向 null
        preStart.next = null;
        ListNode start = cur;

        ListNode preEnd = null;
        ListNode next = null;

        // 反转从 left 到 right 的链表部分，同时更新 preEnd 为反转后的尾节点的下一个节点
        for (int i = left; i <= right; i++) {
            next = cur.next;
            cur.next = preEnd;
            preEnd = cur;
            cur = next;
        }

        // 将 preStart 的下一个节点指向反转后的链表的头节点
        preStart.next = preEnd;
        // 将反转后的链表的尾节点的 next 指向 end 节点的下一个节点
        start.next = next;

        return dummy.next;
    }

    public static ListNode example1() {
        return new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
    }

    public static ListNode example2() {
        return new ListNode(5);
    }

    public static ListNode example3() {
        return new ListNode(3, new ListNode(5));
    }
}


/**
 * @lc app=leetcode.cn id=138 lang=java
 *
 * [138] 复制带随机指针的链表
 *
 * https://leetcode.cn/problems/copy-list-with-random-pointer/description/
 *
 * algorithms
 * Medium (66.02%)
 * Likes:    1220
 * Dislikes: 0
 * Total Accepted:    217.8K
 * Total Submissions: 329.9K
 * Testcase Example:  '[[7,null],[13,0],[11,4],[10,2],[1,0]]'
 *
 * 给你一个长度为 n 的链表，每个节点包含一个额外增加的随机指针 random ，该指针可以指向链表中的任何节点或空节点。
 * 构造这个链表的 深拷贝。 深拷贝应该正好由 n 个 全新 节点组成，其中每个新节点的值都设为其对应的原节点的值。新节点的 next 指针和 random
 * 指针也都应指向复制链表中的新节点，并使原链表和复制链表中的这些指针能够表示相同的链表状态。复制链表中的指针都不应指向原链表中的节点 。
 * 例如，如果原链表中有 X 和 Y 两个节点，其中 X.random --> Y 。那么在复制链表中对应的两个节点 x 和 y ，同样有 x.random --> y 。
 * 返回复制链表的头节点。
 *
 * 用一个由 n 个节点组成的链表来表示输入/输出中的链表。每个节点用一个 [val, random_index] 表示：
 * val：一个表示 Node.val 的整数。
 * random_index：随机指针指向的节点索引（范围从 0 到 n-1）；如果不指向任何节点，则为  null 。
 * 你的代码 只 接受原链表的头节点 head 作为传入参数。
 *
 * 示例 1：
 * 输入：head = [[7,null],[13,0],[11,4],[10,2],[1,0]]
 * 输出：[[7,null],[13,0],[11,4],[10,2],[1,0]]
 *
 * 示例 2：
 * 输入：head = [[1,1],[2,1]]
 * 输出：[[1,1],[2,1]]
 *
 * 示例 3：
 * 输入：head = [[3,null],[3,0],[3,null]]
 * 输出：[[3,null],[3,0],[3,null]]
 *
 * 提示：
 * 0 <= n <= 1000
 * -10^4 <= Node.val <= 10^4
 * Node.random 为 null 或指向链表中的节点。
 *
 * 注意：本题与主站 138 题相同：https://leetcode-cn.com/problems/copy-list-with-random-pointer/
 */
class CopyListWithRandomPointer {
    public Node copyRandomList(Node head) {
        if (head == null) {
            return null;
        }
        // 复制链表
        Node p = head;
        while (p != null) {
            Node copy = new Node(p.val);
            Node next = p.next;
            p.next = copy;
            copy.next = next;
            p = next;
        }
        // 复制随机节点
        p = head;
        while (p != null) {
            // 复制随机节点
            p.next.random = p.random == null ? null : p.random.next;
            // 指针前进
            p = p.next.next;
        }
        // 拆分链表
        p = head;
        Node copyHead = new Node(-1);
        Node copyP = copyHead;
        while (p != null) {
            // 连接复制的链表节点
            copyP.next = p.next;
            // 删除复制的链表节点
            p.next = p.next.next;
            // 复制链表指针前进
            copyP = copyP.next;
            copyP.next = null;
            // 原链表指针前进
            p = p.next;
        }
        return copyHead.next;
    }


    public static Node example1() {
        Node node0 = new Node(7);
        Node node1 = new Node(13);
        Node node2 = new Node(11);
        Node node3 = new Node(10);
        Node node4 = new Node(1);
        node0.next = node1;
        node1.next = node2;
        node1.random = node0;
        node2.next = node3;
        node2.random = node4;
        node3.next = node4;
        node3.random = node2;
        node4.random = node0;
        return node0;
    }

    public static Node example2() {
        Node node0 = new Node(1);
        Node node1 = new Node(2);
        node0.next = node1;
        node0.random = node1;
        node1.random = node1;
        return node0;
    }

    public static Node example3() {
        Node node0 = new Node(3);
        Node node1 = new Node(3);
        Node node2 = new Node(3);
        node0.next = node1;
        node1.next = node2;
        node1.random = node0;
        return node0;
    }
}
