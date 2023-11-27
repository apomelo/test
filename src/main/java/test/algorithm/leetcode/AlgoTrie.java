package test.algorithm.leetcode;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 前缀树
 * @author C
 * @date 2023/2/1
 */
@Slf4j
public class AlgoTrie {
    public static void main(String[] args) {
        // [212] 单词搜索 II
        log.info("WordSearchII: {}", new WordSearchII().findWords(new char[][]{{'o','a','a','n'},{'e','t','a','e'},{'i','h','k','r'},{'i','f','l','v'}}, new String[]{"oath","pea","eat","rain"}));
        log.info("WordSearchII: {}", new WordSearchII().findWords(new char[][]{{'a','b'},{'c','d'}}, new String[]{"abcb"}));
    }
}


/**
 * @lc app=leetcode.cn id=212 lang=java
 *
 * [212] 单词搜索 II
 *
 * https://leetcode.cn/problems/word-search-ii/description/
 *
 * algorithms
 * Hard (43.52%)
 * Likes:    815
 * Dislikes: 0
 * Total Accepted:    97.9K
 * Total Submissions: 225.2K
 * Testcase Example:  '[["o","a","a","n"],["e","t","a","e"],["i","h","k","r"],["i","f","l","v"]]\n' +
  '["oath","pea","eat","rain"]'
 *
 * 给定一个 m x n 二维字符网格 board 和一个单词（字符串）列表 words， 返回所有二维网格上的单词 。
 * 单词必须按照字母顺序，通过 相邻的单元格内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。
 * 同一个单元格内的字母在一个单词中不允许被重复使用。
 *
 * 示例 1：
 * 输入：board =
 * [["o","a","a","n"],["e","t","a","e"],["i","h","k","r"],["i","f","l","v"]],
 * words = ["oath","pea","eat","rain"]
 * 输出：["eat","oath"]
 *
 * 示例 2：
 * 输入：board = [["a","b"],["c","d"]], words = ["abcb"]
 * 输出：[]
 *
 * 提示：
 * m == board.length
 * n == board[i].length
 * 1 <= m, n <= 12
 * board[i][j] 是一个小写英文字母
 * 1 <= words.length <= 3 * 10^4
 * 1 <= words[i].length <= 10
 * words[i] 由小写英文字母组成
 * words 中的所有字符串互不相同
 */
class WordSearchII {
    /**
     * 方法1
     * 使用 Trie 树和深度优先搜索（DFS）来查找二维字符网格中的单词
     */
    public List<String> findWords(char[][] board, String[] words) {
        List<String> result = new ArrayList<>();
        TrieNode root = buildTrie(words); // 构建 Trie 树

        // 遍历二维字符网格
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                dfs(board, i, j, root, result); // 从每个位置开始进行 DFS 搜索
            }
        }

        return result;
    }

    // 深度优先搜索（DFS）函数
    private void dfs(char[][] board, int i, int j, TrieNode node, List<String> result) {
        char c = board[i][j];
        if (c == '#' || node.children[c - 'a'] == null) {
            return;
        }

        node = node.children[c - 'a'];
        if (node.word != null) {
            result.add(node.word); // 找到单词，添加到结果列表中
            node.word = null; // 避免重复添加
        }

        board[i][j] = '#'; // 标记访问过的字符

        // 继续向四个方向进行深度搜索
        if (i > 0) dfs(board, i - 1, j, node, result);
        if (j > 0) dfs(board, i, j - 1, node, result);
        if (i < board.length - 1) dfs(board, i + 1, j, node, result);
        if (j < board[0].length - 1) dfs(board, i, j + 1, node, result);

        board[i][j] = c; // 恢复原始字符
    }

    // 构建 Trie 树
    private TrieNode buildTrie(String[] words) {
        TrieNode root = new TrieNode();
        for (String word : words) {
            TrieNode node = root;
            for (char c : word.toCharArray()) {
                int index = c - 'a';
                if (node.children[index] == null) {
                    node.children[index] = new TrieNode();
                }
                node = node.children[index];
            }
            node.word = word; // 将单词存储在 Trie 树的末尾节点
        }
        return root;
    }

    class TrieNode {
        TrieNode[] children;
        String word; // 保存单词的完整内容

        public TrieNode() {
            children = new TrieNode[26];
        }
    }

    /**
     * 方法2 (运行超时)
     * 遍历 + 回溯
     */
    private int[][] dir2 = new int[][] {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

    public List<String> findWords2(char[][] board, String[] words) {
        List<String> res = new ArrayList<>();
        int m = board.length, n = board[0].length;
        boolean[][] track = new boolean[m][n];
        for (String word : words) {
            boolean find = false;
            for (int i = 0; i < m && !find; i++) {
                for (int j = 0; j < n && !find; j++) {
                    if (findWord2(board, word, 0, i, j, track)) {
                        find = true;
                        res.add(word);
                    }
                }
            }
        }
        return res;
    }

    public boolean findWord2(char[][] board, String word, int index, int i, int j, boolean[][] track) {
        // 找到
        if (index >= word.length()) {
            return true;
        }

        int m = board.length, n = board[0].length;
        // 判断不合法情况
        if (i < 0 || i >= m || j < 0 || j >= n || track[i][j]) {
            return false;
        }
        boolean find = false;
        // 添加走过的路径
        track[i][j] = true;
        // 进入下一决策树
        if (board[i][j] == word.charAt(index)) {
            for (int k = 0; k < dir2.length && !find; k++) {
                if (findWord2(board, word, index + 1, i + dir2[k][0], j + dir2[k][1], track)) {
                    find = true;
                }
            }
        }
        // 回溯
        track[i][j] = false;
        return find;
    }
}
