package test.algorithm.leetcode;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * 广度优先算法 BFS
 * @author C
 * @date 2023/9/6
 */
@Slf4j
public class AlgoBreadthFirstSearch {
    public static void main(String[] args) {
        // [126] 单词接龙 II
        log.info("WordLadderII: {}", new WordLadderII().findLadders("hit", "cog", Arrays.asList("hot","dot","dog","lot","log","cog")));
        log.info("WordLadderII: {}", new WordLadderII().findLadders("hit", "cog", Arrays.asList("hot","dot","dog","lot","log")));
        // [127] 单词接龙
        log.info("WordLadder: {}", new WordLadder().ladderLength("hit", "cog", Arrays.asList("hot","dot","dog","lot","log","cog")));
        log.info("WordLadder: {}", new WordLadder().ladderLength("hit", "cog", Arrays.asList("hot","dot","dog","lot","log")));
    }
}


/**
 * @lc app=leetcode.cn id=126 lang=java
 *
 * [126] 单词接龙 II
 *
 * https://leetcode.cn/problems/word-ladder-ii/description/
 *
 * algorithms
 * Hard (37.61%)
 * Likes:    675
 * Dislikes: 0
 * Total Accepted:    55.6K
 * Total Submissions: 148K
 * Testcase Example:  '"hit"\n"cog"\n["hot","dot","dog","lot","log","cog"]'
 *
 * 按字典 wordList 完成从单词 beginWord 到单词 endWord 转化，一个表示此过程的 转换序列 是形式上像 beginWord ->
 * s1 -> s2 -> ... -> sk 这样的单词序列，并满足：
 * 1. 每对相邻的单词之间仅有单个字母不同。
 * 2. 转换过程中的每个单词 si（1 <= i <= k）必须是字典 wordList 中的单词。注意，beginWord 不必是字典 wordList中的单词。
 * 3. sk == endWord
 *
 * 给你两个单词 beginWord 和 endWord ，以及一个字典 wordList 。请你找出并返回所有从 beginWord 到 endWord
 * 的 最短转换序列 ，如果不存在这样的转换序列，返回一个空列表。每个序列都应该以单词列表 [beginWord, s1, s2, ..., sk]
 * 的形式返回。
 *
 * 示例 1：
 * 输入：beginWord = "hit", endWord = "cog", wordList =
 * ["hot","dot","dog","lot","log","cog"]
 * 输出：[["hit","hot","dot","dog","cog"],["hit","hot","lot","log","cog"]]
 * 解释：存在 2 种最短的转换序列：
 * "hit" -> "hot" -> "dot" -> "dog" -> "cog"
 * "hit" -> "hot" -> "lot" -> "log" -> "cog"
 *
 * 示例 2：
 * 输入：beginWord = "hit", endWord = "cog", wordList =
 * ["hot","dot","dog","lot","log"]
 * 输出：[]
 * 解释：endWord "cog" 不在字典 wordList 中，所以不存在符合要求的转换序列。
 *
 * 提示：
 * 1 <= beginWord.length <= 5
 * endWord.length == beginWord.length
 * 1 <= wordList.length <= 500
 * wordList[i].length == beginWord.length
 * beginWord、endWord 和 wordList[i] 由小写英文字母组成
 * beginWord != endWord
 * wordList 中的所有单词 互不相同
 */
class WordLadderII {
    /**
     * 广度优先 (breadth-first-search)
     * 本题要求的是最短转换序列，看到最短首先想到的就是广度优先搜索。
     * 根据单词转换规则：把每个单词都抽象为一个顶点，如果两个单词可以只改变一个字母进行转换，那么说明它们之间有一条双向边。
     * 因此我们只需要把满足转换条件的点相连，就形成了一张图。
     *
     * 基于该图，我们以 “hit" 为图的起点， 以 “cog" 为终点进行广度优先搜索，寻找 “hit" 到 “cog" 的最短路径。
     *
     * 从一个单词出发，修改每一位字符，将它修改成为 ‘a’ 到 ‘z’ 中的所有字符，看看修改以后是不是在题目中给出的单词列表中；
     * 有一些边的关系，由于不是最短路径上的边，不可以被记录下来。为此，我们为扩展出的单词记录附加的属性：层数。
     * 即下面代码中的 steps 。如果当前的单词扩散出去得到的单词的层数在以前出现过，则不应该记录这样的边的关系。
     */
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        List<List<String>> res = new ArrayList<>();
        // 因为需要快速判断扩展出的单词是否在 wordList 里，因此需要将 wordList 存入哈希表，这里命名为「字典」
        Set<String> dict = new HashSet<>(wordList);
        // 特殊用例判断
        if (!dict.contains(endWord)) {
            return res;
        }

        dict.remove(beginWord);

        // 第 1 步：广度优先搜索建图
        // 记录扩展出的单词是在第几次扩展的时候得到的，key：单词，value：在广度优先搜索的第几层
        Map<String, Integer> steps = new HashMap<>();
        steps.put(beginWord, 0);
        // 记录了单词是从哪些单词扩展而来，key：单词，value：单词列表，这些单词可以变换到 key ，它们是一对多关系
        Map<String, List<String>> from = new HashMap<>();
        int step = 1;
        boolean found = false;
        Queue<String> queue = new ArrayDeque<>();
        queue.offer(beginWord);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String currWord = queue.poll();
                char[] charArray = currWord.toCharArray();
                // 将每一位替换成 26 个小写英文字母
                for (int j = 0; j < charArray.length; j++) {
                    char origin = charArray[j];
                    for (char c = 'a'; c <= 'z'; c++) {
                        charArray[j] = c;
                        String nextWord = String.valueOf(charArray);
                        if (steps.containsKey(nextWord) && step == steps.get(nextWord)) {
                            // 给下一层节点添加当前节点为邻居（构建从终点到起点的单向路径图）
                            from.get(nextWord).add(currWord);
                        }
                        if (!dict.contains(nextWord)) {
                            continue;
                        }
                        // 如果从一个单词扩展出来的单词以前遍历过，距离一定更远，为了避免搜索到已经遍历到，且距离更远的单词，需要将它从 dict 中删除
                        dict.remove(nextWord);
                        // 这一层扩展出的单词进入队列
                        queue.offer(nextWord);

                        // 给下一层节点添加当前节点为邻居（构建从终点到起点的单向路径图）
                        // 记录 nextWord 从 currWord 而来
                        from.computeIfAbsent(nextWord, k -> new ArrayList<>()).add(currWord);
                        // 记录 nextWord 的 step
                        steps.put(nextWord, step);
                        // 下一层找到终点节点，继续遍历当前层找到其他路径，因为是第一次找到所以路径最短
                        if (nextWord.equals(endWord)) {
                            found = true;
                        }
                    }
                    charArray[j] = origin;
                }
            }
            step++;
            if (found) {
                break;
            }
        }

        // 第 2 步：回溯找到所有解，从 endWord 恢复到 beginWord ，所以每次尝试操作 path 列表的头部
        if (found) {
            Deque<String> path = new ArrayDeque<>();
            path.add(endWord);
            backtrack(from, path, beginWord, endWord, res);
        }
        return res;
    }

    public void backtrack(Map<String, List<String>> from, Deque<String> path, String beginWord, String cur, List<List<String>> res) {
        if (cur.equals(beginWord)) {
            res.add(new ArrayList<>(path));
            return;
        }
        for (String precursor : from.get(cur)) {
            path.addFirst(precursor);
            backtrack(from, path, beginWord, precursor, res);
            path.removeFirst();
        }
    }
}


/**
 * @lc app=leetcode.cn id=127 lang=java
 *
 * [127] 单词接龙
 *
 * https://leetcode.cn/problems/word-ladder/description/
 *
 * algorithms
 * Hard (48.26%)
 * Likes:    1285
 * Dislikes: 0
 * Total Accepted:    191.9K
 * Total Submissions: 397.7K
 * Testcase Example:  '"hit"\n"cog"\n["hot","dot","dog","lot","log","cog"]'
 *
 * 字典 wordList 中从单词 beginWord 和 endWord 的 转换序列 是一个按下述规格形成的序列
 * beginWord -> s1 -> s2 -> ... -> sk：
 * 每一对相邻的单词只差一个字母。
 * 对于 1 <= i <= k 时，每个 si 都在 wordList 中。注意， beginWord 不需要在 wordList 中。
 * sk == endWord
 * 给你两个单词 beginWord 和 endWord 和一个字典 wordList ，返回 从 beginWord 到 endWord 的 最短转换序列
 * 中的 单词数目 。如果不存在这样的转换序列，返回 0 。
 *
 * 示例 1：
 * 输入：beginWord = "hit", endWord = "cog", wordList =
 * ["hot","dot","dog","lot","log","cog"]
 * 输出：5
 * 解释：一个最短转换序列是 "hit" -> "hot" -> "dot" -> "dog" -> "cog", 返回它的长度 5。
 *
 * 示例 2：
 * 输入：beginWord = "hit", endWord = "cog", wordList =
 * ["hot","dot","dog","lot","log"]
 * 输出：0
 * 解释：endWord "cog" 不在字典中，所以无法进行转换。
 *
 * 提示：
 * 1 <= beginWord.length <= 10
 * endWord.length == beginWord.length
 * 1 <= wordList.length <= 5000
 * wordList[i].length == beginWord.length
 * beginWord、endWord 和 wordList[i] 由小写英文字母组成
 * beginWord != endWord
 * wordList 中的所有字符串 互不相同
 */
class WordLadder {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        // 因为需要快速判断扩展出的单词是否在 wordList 里，因此需要将 wordList 存入哈希表，这里命名为「字典」
        Set<String> dict = new HashSet<>(wordList);
        // 判断特殊情况
        if (!dict.contains(endWord)) {
            return 0;
        }
        dict.remove(beginWord);

        // 第 1 步：广度优先搜索建图
        // 记录扩展出的单词是在第几次扩展的时候得到的，key：单词，value：在广度优先搜索的第几层
        Map<String, Integer> steps = new HashMap<>();
        steps.put(beginWord, 1);
        // 记录了单词是从哪些单词扩展而来，key：单词，value：单词列表，这些单词可以变换到 key ，它们是一对多关系
        Map<String, List<String>> from = new HashMap<>();
        int step = 2;
        Queue<String> queue = new ArrayDeque<>();
        queue.offer(beginWord);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String currWord = queue.poll();
                char[] charArray = currWord.toCharArray();
                // 将每一位替换成 26 个小写英文字母
                for (int j = 0; j < charArray.length; j++) {
                    char origin = charArray[j];
                    for (char c = 'a'; c <= 'z'; c++) {
                        charArray[j] = c;
                        String nextWord = String.valueOf(charArray);
                        if (steps.containsKey(nextWord) && step == steps.get(nextWord)) {
                            // 给下一层节点添加当前节点为邻居（构建从终点到起点的单向路径图）
                            from.get(nextWord).add(currWord);
                        }
                        if (!dict.contains(nextWord)) {
                            continue;
                        }
                        // 如果从一个单词扩展出来的单词以前遍历过，距离一定更远，为了避免搜索到已经遍历到，且距离更远的单词，需要将它从 dict 中删除
                        dict.remove(nextWord);
                        // 这一层扩展出的单词进入队列
                        queue.offer(nextWord);

                        // 给下一层节点添加当前节点为邻居（构建从终点到起点的单向路径图）
                        // 记录 nextWord 从 currWord 而来
                        from.computeIfAbsent(nextWord, k -> new ArrayList<>()).add(currWord);
                        // 记录 nextWord 的 step
                        steps.put(nextWord, step);
                        // 下一层找到终点节点，因为是第一次找到所以路径最短，直接返回
                        if (nextWord.equals(endWord)) {
                            return step;
                        }
                    }
                    charArray[j] = origin;
                }
            }
            step++;
        }
        return 0;
    }
}
