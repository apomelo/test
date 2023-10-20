package test.algorithm.leetcode;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Stack;

/**
 * 设计
 * @author C
 * @date 2023/2/1
 */
@Slf4j
public class AlgoDesign {
    public static void main(String[] args) {
        // [146] LRU 缓存
        LRUCache2.example1();
        LRUCache2.example2();
        // [155] 最小栈
        MinStack.example1();
    }
}


/**
 * @lc app=leetcode.cn id=146 lang=java
 *
 * [146] LRU 缓存
 *
 * https://leetcode.cn/problems/lru-cache/description/
 *
 * algorithms
 * Medium (53.48%)
 * Likes:    2965
 * Dislikes: 0
 * Total Accepted:    545.6K
 * Total Submissions: 1M
 * Testcase Example:  '["LRUCache","put","put","get","put","get","put","get","get","get"]\n' +
  '[[2],[1,1],[2,2],[1],[3,3],[2],[4,4],[1],[3],[4]]'
 *
 * 请你设计并实现一个满足 LRU (最近最少使用) 缓存 约束的数据结构。
 * 实现 LRUCache 类：
 * LRUCache(int capacity) 以 正整数 作为容量 capacity 初始化 LRU 缓存
 * int get(int key) 如果关键字 key 存在于缓存中，则返回关键字的值，否则返回 -1 。
 * void put(int key, int value) 如果关键字 key 已经存在，则变更其数据值 value ；如果不存在，则向缓存中插入该组
 * key-value 。如果插入操作导致关键字数量超过 capacity ，则应该 逐出 最久未使用的关键字。
 * 函数 get 和 put 必须以 O(1) 的平均时间复杂度运行。
 *
 * 示例：
 * 输入
 * ["LRUCache", "put", "put", "get", "put", "get", "put", "get", "get", "get"]
 * [[2], [1, 1], [2, 2], [1], [3, 3], [2], [4, 4], [1], [3], [4]]
 * 输出
 * [null, null, null, 1, null, -1, null, -1, 3, 4]
 * 解释
 * LRUCache lRUCache = new LRUCache(2);
 * lRUCache.put(1, 1); // 缓存是 {1=1}
 * lRUCache.put(2, 2); // 缓存是 {1=1, 2=2}
 * lRUCache.get(1);    // 返回 1
 * lRUCache.put(3, 3); // 该操作会使得关键字 2 作废，缓存是 {1=1, 3=3}
 * lRUCache.get(2);    // 返回 -1 (未找到)
 * lRUCache.put(4, 4); // 该操作会使得关键字 1 作废，缓存是 {4=4, 3=3}
 * lRUCache.get(1);    // 返回 -1 (未找到)
 * lRUCache.get(3);    // 返回 3
 * lRUCache.get(4);    // 返回 4
 *
 * 提示：
 * 1 <= capacity <= 3000
 * 0 <= key <= 10000
 * 0 <= value <= 10^5
 * 最多调用 2 * 10^5 次 get 和 put
 */
// 解法1： 使用 LinkedHashMap
class LRUCache {
    int cap;
    LinkedHashMap<Integer, Integer> cache = new LinkedHashMap<>();
    public LRUCache(int capacity) {
        this.cap = capacity;
    }

    public int get(int key) {
        if (!cache.containsKey(key)) {
            return -1;
        }
        // 将 key 变为最近使用
        makeRecently(key);
        return cache.get(key);
    }

    public void put(int key, int val) {
        if (cache.containsKey(key)) {
            // 修改 key 的值
            cache.put(key, val);
            // 将 key 变为最近使用
            makeRecently(key);
            return;
        }

        if (cache.size() >= this.cap) {
            // 链表头部就是最久未使用的 key
            int oldestKey = cache.keySet().iterator().next();
            cache.remove(oldestKey);
        }
        // 将新的 key 添加链表尾部
        cache.put(key, val);
    }

    private void makeRecently(int key) {
        int val = cache.get(key);
        // 删除 key，重新插入到队尾
        cache.remove(key);
        cache.put(key, val);
    }
}
// 解法2： 使用自定义链表 + HashMap
@Slf4j
class LRUCache2 {
    private int maxCapacity;
    private Map<Integer, Integer> memo = new HashMap<>();
    // 最近最少使用的元素前的虚拟节点
    private LruNode head = new LruNode(-1);
    // 最近使用的元素
    private LruNode tail = head;
    public LRUCache2(int capacity) {
        this.maxCapacity = capacity;
    }

    public int get(int key) {
        Integer val = memo.get(key);
        if (val == null) {
            return -1;
        }
        refreshLruList(key);
        return val;
    }

    public void put(int key, int value) {
        if (memo.containsKey(key)) {
            memo.put(key, value);
            refreshLruList(key);
        } else {
            if (memo.size() >= maxCapacity) {
                // 排出最近最少使用的元素
                LruNode node = popLruNode();
                memo.remove(node.val);
            }
            memo.put(key, value);
            pushLruNode(key);
        }
    }

    private void refreshLruList(int key) {
        // 是最近使用的元素，不需要刷新，直接返回
        if (tail.val == key) {
            return;
        }
        LruNode p = head;
        while (p.next.val != key) {
            p = p.next;
        }
        LruNode node = p.next;
        popNextLruNode(p);
        pushLruNode(node);
    }

    private void pushLruNode(int val) {
        LruNode node = new LruNode(val);
        pushLruNode(node);
    }

    private void pushLruNode(LruNode node) {
        tail.next = node;
        tail = tail.next;
        if (head.next == null) {
            head.next = tail;
        }
    }

    private LruNode popLruNode() {
        return popNextLruNode(head);
    }

    private LruNode popNextLruNode(LruNode node) {
        LruNode next = node.next;
        node.next = node.next.next;
        next.next = null;
        return next;
    }

    private static class LruNode {
        int val;
        LruNode next;
        public LruNode(int val) {
            this.val = val;
        }
    }


    public static void example1() {
        LRUCache2 lRUCache2 = new LRUCache2(2);
        lRUCache2.put(1, 1); // 缓存是 {1=1}
        lRUCache2.put(2, 2); // 缓存是 {1=1, 2=2}
        lRUCache2.get(1);    // 返回 1
        log.info("{}", lRUCache2.get(1));
        lRUCache2.put(3, 3); // 该操作会使得关键字 2 作废，缓存是 {1=1, 3=3}
        lRUCache2.get(2);    // 返回 -1 (未找到)
        log.info("{}", lRUCache2.get(2));
        lRUCache2.put(4, 4); // 该操作会使得关键字 1 作废，缓存是 {4=4, 3=3}
        lRUCache2.get(1);    // 返回 -1 (未找到)
        log.info("{}", lRUCache2.get(1));
        lRUCache2.get(3);    // 返回 3
        log.info("{}", lRUCache2.get(3));
        lRUCache2.get(4);    // 返回 4
        log.info("{}", lRUCache2.get(4));
        lRUCache2.put(4, 5); // 该操作会更新缓存，缓存是 {4=5, 3=3}
        lRUCache2.get(4);    // 返回 5
        log.info("{}", lRUCache2.get(4));
    }
    public static void example2() {
        LRUCache2 lRUCache2 = new LRUCache2(1);
        lRUCache2.put(1, 1); // 缓存是 {1=1}
        lRUCache2.put(2, 2); // 缓存是 {1=1, 2=2}
        lRUCache2.get(1);    // 返回 -1
        log.info("{}", lRUCache2.get(1));
        lRUCache2.put(3, 3); // 该操作会使得关键字 2 作废，缓存是 {1=1, 3=3}
        lRUCache2.get(2);    // 返回 -1 (未找到)
        log.info("{}", lRUCache2.get(2));
    }
}


/**
 * @lc app=leetcode.cn id=155 lang=java
 *
 * [155] 最小栈
 *
 * https://leetcode.cn/problems/min-stack/description/
 *
 * algorithms
 * Medium (59.19%)
 * Likes:    1667
 * Dislikes: 0
 * Total Accepted:    514.5K
 * Total Submissions: 869.3K
 * Testcase Example:  '["MinStack","push","push","push","getMin","pop","top","getMin"]\n' +
  '[[],[-2],[0],[-3],[],[],[],[]]'
 *
 * 设计一个支持 push ，pop ，top 操作，并能在常数时间内检索到最小元素的栈。
 * 实现 MinStack 类:
 * MinStack() 初始化堆栈对象。
 * void push(int val) 将元素val推入堆栈。
 * void pop() 删除堆栈顶部的元素。
 * int top() 获取堆栈顶部的元素。
 * int getMin() 获取堆栈中的最小元素。
 * 
 * 示例 1:
 * 输入：
 * ["MinStack","push","push","push","getMin","pop","top","getMin"]
 * [[],[-2],[0],[-3],[],[],[],[]]
 * 输出：
 * [null,null,null,null,-3,null,0,-2]
 * 解释：
 * MinStack minStack = new MinStack();
 * minStack.push(-2);
 * minStack.push(0);
 * minStack.push(-3);
 * minStack.getMin();   --> 返回 -3.
 * minStack.pop();
 * minStack.top();      --> 返回 0.
 * minStack.getMin();   --> 返回 -2.
 * 
 * 提示：
 * -2^31 <= val <= 2^31 - 1
 * pop、top 和 getMin 操作总是在 非空栈 上调用
 * push, pop, top, and getMin最多被调用 3 * 10^4 次
 */
@Slf4j
class MinStack {
    // 记录栈中的所有元素
    Stack<Integer> stack = new Stack<>();
    // 阶段性记录栈中的最小元素
    Stack<Integer> minStack = new Stack<>();

    public MinStack() {
    }

    public void push(int val) {
        stack.push(val);
        // 维护 minStack 栈顶为全栈最小元素
        if (minStack.isEmpty() || val <= minStack.peek()) {
            minStack.push(val);
        }
    }

    public void pop() {
        int val = stack.pop();
        // 维护 minStk 栈顶为全栈最小元素
        if (minStack.peek() == val) {
            minStack.pop();
        }
    }

    public int top() {
        return stack.peek();
    }

    public int getMin() {
        return minStack.peek();
    }


    public static void example1() {
        MinStack minStack = new MinStack();
        minStack.push(-2);
        minStack.push(0);
        minStack.push(-3);
        log.info("{}", minStack.getMin()); // 返回 -3.
        minStack.pop();
        log.info("{}", minStack.top()); // 返回 0.
        log.info("{}", minStack.getMin()); // 返回 -2.
    }
}
