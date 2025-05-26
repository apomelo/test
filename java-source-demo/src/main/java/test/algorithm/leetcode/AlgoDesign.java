package test.algorithm.leetcode;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * 设计
 * @author C
 * @date 2023/2/1
 */
@Slf4j
public class AlgoDesign {
    public static void main(String[] args) {
        // [146] LRU 缓存
        LRUCache.example1();
        LRUCache.example2();
        LRUCache2.example1();
        LRUCache2.example2();
        // [155] 最小栈
        MinStack.example1();
        // [208] 实现 Trie (前缀树)
        Trie.example1();
        // [211] 添加与搜索单词 - 数据结构设计
        WordDictionary.example1();
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
@Slf4j
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


    public static void example1() {
        LRUCache lruCache = new LRUCache(2);
        lruCache.put(1, 1); // 缓存是 {1=1}
        lruCache.put(2, 2); // 缓存是 {1=1, 2=2}
        log.info("{}", lruCache.get(1));    // 返回 1
        lruCache.put(3, 3); // 该操作会使得关键字 2 作废，缓存是 {1=1, 3=3}
        log.info("{}", lruCache.get(2));    // 返回 -1 (未找到)
        lruCache.put(4, 4); // 该操作会使得关键字 1 作废，缓存是 {4=4, 3=3}
        log.info("{}", lruCache.get(1));    // 返回 -1 (未找到)
        log.info("{}", lruCache.get(3));    // 返回 3
        log.info("{}", lruCache.get(4));    // 返回 4
        lruCache.put(4, 5); // 该操作会更新缓存，缓存是 {4=5, 3=3}
        log.info("{}", lruCache.get(4));    // 返回 5
    }
    public static void example2() {
        LRUCache lruCache = new LRUCache(1);
        lruCache.put(1, 1); // 缓存是 {1=1}
        lruCache.put(2, 2); // 缓存是 {1=1, 2=2}
        log.info("{}", lruCache.get(1));    // 返回 -1
        lruCache.put(3, 3); // 该操作会使得关键字 2 作废，缓存是 {1=1, 3=3}
        log.info("{}", lruCache.get(2));    // 返回 -1 (未找到)
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
        LRUCache2 lruCache2 = new LRUCache2(2);
        lruCache2.put(1, 1); // 缓存是 {1=1}
        lruCache2.put(2, 2); // 缓存是 {1=1, 2=2}
        log.info("{}", lruCache2.get(1));    // 返回 1
        lruCache2.put(3, 3); // 该操作会使得关键字 2 作废，缓存是 {1=1, 3=3}
        log.info("{}", lruCache2.get(2));    // 返回 -1 (未找到)
        lruCache2.put(4, 4); // 该操作会使得关键字 1 作废，缓存是 {4=4, 3=3}
        log.info("{}", lruCache2.get(1));    // 返回 -1 (未找到)
        log.info("{}", lruCache2.get(3));    // 返回 3
        log.info("{}", lruCache2.get(4));    // 返回 4
        lruCache2.put(4, 5); // 该操作会更新缓存，缓存是 {4=5, 3=3}
        log.info("{}", lruCache2.get(4));    // 返回 5
    }
    public static void example2() {
        LRUCache2 lruCache2 = new LRUCache2(1);
        lruCache2.put(1, 1); // 缓存是 {1=1}
        lruCache2.put(2, 2); // 缓存是 {1=1, 2=2}
        log.info("{}", lruCache2.get(1));    // 返回 -1
        lruCache2.put(3, 3); // 该操作会使得关键字 2 作废，缓存是 {1=1, 3=3}
        log.info("{}", lruCache2.get(2));    // 返回 -1 (未找到)
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


/**
 * @lc app=leetcode.cn id=208 lang=java
 *
 * [208] 实现 Trie (前缀树)
 *
 * https://leetcode.cn/problems/implement-trie-prefix-tree/description/
 *
 * algorithms
 * Medium (71.91%)
 * Likes:    1553
 * Dislikes: 0
 * Total Accepted:    292.1K
 * Total Submissions: 406.3K
 * Testcase Example:  '["Trie","insert","search","search","startsWith","insert","search"]\n' +
  '[[],["apple"],["apple"],["app"],["app"],["app"],["app"]]'
 *
 * Trie（发音类似 "try"）或者说 前缀树 是一种树形数据结构，用于高效地存储和检索字符串数据集中的键。
 * 这一数据结构有相当多的应用情景，例如自动补完和拼写检查。
 *
 * 请你实现 Trie 类：
 * Trie() 初始化前缀树对象。
 * void insert(String word) 向前缀树中插入字符串 word 。
 * boolean search(String word) 如果字符串 word 在前缀树中，返回 true（即，在检索之前已经插入）；否则，返回 false 。
 * boolean startsWith(String prefix) 如果之前已经插入的字符串 word 的前缀之一为 prefix ，返回 true ；否则，返回 false 。
 *
 * 示例：
 * 输入
 * ["Trie", "insert", "search", "search", "startsWith", "insert", "search"]
 * [[], ["apple"], ["apple"], ["app"], ["app"], ["app"], ["app"]]
 * 输出
 * [null, null, true, false, true, null, true]
 * 解释
 * Trie trie = new Trie();
 * trie.insert("apple");
 * trie.search("apple");   // 返回 True
 * trie.search("app");     // 返回 False
 * trie.startsWith("app"); // 返回 True
 * trie.insert("app");
 * trie.search("app");     // 返回 True
 *
 * 提示：
 * 1 <= word.length, prefix.length <= 2000
 * word 和 prefix 仅由小写英文字母组成
 * insert、search 和 startsWith 调用次数 总计 不超过 3 * 10^4 次
 */
@Slf4j
class Trie {
    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public void insert(String word) {
        TrieNode node = root;
        for (char ch : word.toCharArray()) {
            if (!node.containsKey(ch)) {
                node.put(ch, new TrieNode());
            }
            node = node.get(ch);
        }
        node.setEnd();
    }

    public boolean search(String word) {
        TrieNode node = searchPrefix(word);
        return node != null && node.isEnd();
    }

    private TrieNode searchPrefix(String word) {
        TrieNode node = root;
        for (char ch : word.toCharArray()) {
            if (node.containsKey(ch)) {
                node = node.get(ch);
            } else {
                return null;
            }
        }
        return node;
    }

    public boolean startsWith(String prefix) {
        return searchPrefix(prefix) != null;
    }


    /**
     * TrieNode 表示 Trie 的节点。
     * 每个节点有一个大小为 26 的 links 数组，用来存储指向子节点的引用，R 是字符集的大小（这里为小写字母 a-z）。
     * isEnd 标记是否是一个单词的结束。
     *
     * 以下是一个示例前缀树的结构，用于存储字符串集合 ["apple", "apply", "abc", "api", "book"]
     *           (root)
     *           /    \
     *         a       b
     *        / \       \
     *       p   b       o
     *      / \   \       \
     *     p   i   c       o
     *    /                 \
     *   l                   k
     *  / \
     * e   y
     */
    class TrieNode {
        private TrieNode[] links;
        private final int R = 26;
        private boolean isEnd;

        public TrieNode() {
            links = new TrieNode[R];
        }

        public boolean containsKey(char ch) {
            return links[ch - 'a'] != null;
        }

        public TrieNode get(char ch) {
            return links[ch - 'a'];
        }

        public void put(char ch, TrieNode node) {
            links[ch - 'a'] = node;
        }

        public void setEnd() {
            isEnd = true;
        }

        public boolean isEnd() {
            return isEnd;
        }
    }


    public static void example1() {
        Trie trie = new Trie();
        trie.insert("apple");
        log.info("{}", trie.search("apple"));   // 返回 True
        log.info("{}", trie.search("app"));     // 返回 False
        log.info("{}", trie.startsWith("app"));       // 返回 True
        trie.insert("app");
        log.info("{}", trie.search("app"));     // 返回 True
    }
}


/**
 * @lc app=leetcode.cn id=211 lang=java
 *
 * [211] 添加与搜索单词 - 数据结构设计
 *
 * https://leetcode.cn/problems/design-add-and-search-words-data-structure/description/
 *
 * algorithms
 * Medium (49.64%)
 * Likes:    531
 * Dislikes: 0
 * Total Accepted:    79.4K
 * Total Submissions: 159.9K
 * Testcase Example:  '["WordDictionary","addWord","addWord","addWord","search","search","search","search"]\n' +
  '[[],["bad"],["dad"],["mad"],["pad"],["bad"],[".ad"],["b.."]]'
 *
 * 请你设计一个数据结构，支持 添加新单词 和 查找字符串是否与任何先前添加的字符串匹配 。
 * 实现词典类 WordDictionary ：
 * WordDictionary() 初始化词典对象
 * void addWord(word) 将 word 添加到数据结构中，之后可以对它进行匹配
 * bool search(word) 如果数据结构中存在字符串与 word 匹配，则返回 true ；否则，返回  false 。
 * word 中可能包含一些 '.' ，每个 . 都可以表示任何一个字母。
 *
 * 示例：
 * 输入：
 * ["WordDictionary","addWord","addWord","addWord","search","search","search","search"]
 * [[],["bad"],["dad"],["mad"],["pad"],["bad"],[".ad"],["b.."]]
 * 输出：
 * [null,null,null,null,false,true,true,true]
 * 解释：
 * WordDictionary wordDictionary = new WordDictionary();
 * wordDictionary.addWord("bad");
 * wordDictionary.addWord("dad");
 * wordDictionary.addWord("mad");
 * wordDictionary.search("pad"); // 返回 False
 * wordDictionary.search("bad"); // 返回 True
 * wordDictionary.search(".ad"); // 返回 True
 * wordDictionary.search("b.."); // 返回 True
 *
 * 提示：
 * 1 <= word.length <= 25
 * addWord 中的 word 由小写英文字母组成
 * search 中的 word 由 '.' 或小写英文字母组成
 * 最多调用 10^4 次 addWord 和 search
 */
@Slf4j
class WordDictionary {
    private TrieNode root;

    public WordDictionary() {
        root = new TrieNode();
    }

    public void addWord(String word) {
        TrieNode node = root;
        for (char ch : word.toCharArray()) {
            if (node.children[ch - 'a'] == null) {
                node.children[ch - 'a'] = new TrieNode();
            }
            node = node.children[ch - 'a'];
        }
        node.isEnd = true;
    }

    public boolean search(String word) {
        return searchWord(word, root, 0);
    }

    private boolean searchWord(String word, TrieNode node, int index) {
        if (index == word.length()) {
            return node.isEnd;
        }

        char ch = word.charAt(index);
        if (ch == '.') {
            for (TrieNode child : node.children) {
                if (child != null && searchWord(word, child, index + 1)) {
                    return true;
                }
            }
            return false;
        } else {
            TrieNode child = node.children[ch - 'a'];
            return child != null && searchWord(word, child, index + 1);
        }
    }

    class TrieNode {
        TrieNode[] children;
        boolean isEnd;

        public TrieNode() {
            children = new TrieNode[26];
            isEnd = false;
        }
    }

    public static void example1() {
        WordDictionary wordDictionary = new WordDictionary();
        wordDictionary.addWord("bad");
        wordDictionary.addWord("dad");
        wordDictionary.addWord("mad");
        log.info("{}", wordDictionary.search("pad")); // 返回 False
        log.info("{}", wordDictionary.search("bad")); // 返回 True
        log.info("{}", wordDictionary.search(".ad")); // 返回 True
        log.info("{}", wordDictionary.search("b..")); // 返回 True
    }
}
