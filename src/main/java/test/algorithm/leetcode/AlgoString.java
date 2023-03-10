package test.algorithm.leetcode;

import lombok.extern.slf4j.Slf4j;

/**
 * 字符串
 * @author C
 * @date 2023/2/1
 */
@Slf4j
public class AlgoString {
    public static void main(java.lang.String[] args) {
        // 模板
        log.info("LongestCommonPrefix: {}", new LongestCommonPrefix().longestCommonPrefix(new String[]{"flower","flow","flight"}));
        log.info("LongestCommonPrefix: {}", new LongestCommonPrefix().longestCommonPrefix(new String[]{"dog","racecar","car"}));
        log.info("LongestCommonPrefix: {}", new LongestCommonPrefix().longestCommonPrefix(new String[]{"ab", "a"}));
        log.info("FindTheIndexOfTheFirstOccurrenceInAString: {}", new FindTheIndexOfTheFirstOccurrenceInAString().strStr("sadbutsad", "sad"));
        log.info("FindTheIndexOfTheFirstOccurrenceInAString: {}", new FindTheIndexOfTheFirstOccurrenceInAString().strStr("leetcode", "leeto"));
        log.info("FindTheIndexOfTheFirstOccurrenceInAString: {}", new FindTheIndexOfTheFirstOccurrenceInAString().strStr("mississippi", "issipi"));
    }
}

/**
 * @lc app=leetcode.cn id=14 lang=java
 *
 * [14] 最长公共前缀
 *
 * https://leetcode.cn/problems/longest-common-prefix/description/
 *
 * algorithms
 * Easy (43.19%)
 * Likes:    2629
 * Dislikes: 0
 * Total Accepted:    1M
 * Total Submissions: 2.4M
 * Testcase Example:  '["flower","flow","flight"]'
 *
 * 编写一个函数来查找字符串数组中的最长公共前缀。
 * 如果不存在公共前缀，返回空字符串 ""。
 *
 * 示例 1：
 * 输入：strs = ["flower","flow","flight"]
 * 输出："fl"
 *
 * 示例 2：
 * 输入：strs = ["dog","racecar","car"]
 * 输出：""
 * 解释：输入不存在公共前缀。
 *
 * 提示：
 * 1 <= strs.length <= 200
 * 0 <= strs[i].length <= 200
 * strs[i] 仅由小写英文字母组成
 */
// @lc code=start
class LongestCommonPrefix {
    public String longestCommonPrefix(String[] strs) {
        StringBuilder stringBuilder = new StringBuilder();
        int index = 0;
        boolean run = true;
        while (run) {
            if (index >= strs[0].length()) {
                run = false;
                break;
            }
            char c = strs[0].charAt(index);
            for (String str : strs) {
                if (index >= str.length() || str.charAt(index) != c) {
                    run = false;
                    break;
                }
            }
            if (run) {
                index++;
                stringBuilder.append(c);
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 第二种方法
     */
    public String longestCommonPrefix2(String[] strs) {
        int m = strs.length;
        // 以第一行的列数为基准
        int n = strs[0].length();
        for (int col = 0; col < n; col++) {
            for (int row = 1; row < m; row++) {
                String thisStr = strs[row], prevStr = strs[row - 1];
                // 判断每个字符串的 col 索引是否都相同
                if (col >= thisStr.length() || col >= prevStr.length() ||
                        thisStr.charAt(col) != prevStr.charAt(col)) {
                    // 发现不匹配的字符，只有 strs[row][0..col-1] 是公共前缀
                    return strs[row].substring(0, col);
                }
            }
        }
        return strs[0];
    }
}
// @lc code=end


/**
 * @lc app=leetcode.cn id=28 lang=java
 *
 * [28] 找出字符串中第一个匹配项的下标
 *
 * https://leetcode.cn/problems/find-the-index-of-the-first-occurrence-in-a-string/description/
 *
 * algorithms
 * Medium (42.01%)
 * Likes:    1774
 * Dislikes: 0
 * Total Accepted:    809K
 * Total Submissions: 1.9M
 * Testcase Example:  '"sadbutsad"\n"sad"'
 *
 * 给你两个字符串 haystack 和 needle ，请你在 haystack 字符串中找出 needle 字符串的第一个匹配项的下标（下标从 0
 * 开始）。如果 needle 不是 haystack 的一部分，则返回  -1 。
 *
 * 示例 1：
 * 输入：haystack = "sadbutsad", needle = "sad"
 * 输出：0
 * 解释："sad" 在下标 0 和 6 处匹配。
 * 第一个匹配项的下标是 0 ，所以返回 0 。
 *
 * 示例 2：
 * 输入：haystack = "leetcode", needle = "leeto"
 * 输出：-1
 * 解释："leeto" 没有在 "leetcode" 中出现，所以返回 -1 。
 *
 * 提示：
 * 1 <= haystack.length, needle.length <= 10^4
 * haystack 和 needle 仅由小写英文字符组成
 */

// @lc code=start
class FindTheIndexOfTheFirstOccurrenceInAString {
    public int strStr(String haystack, String needle) {
        int hayStackLength = haystack.length();
        int needleLength = needle.length();
        // 主字符串最大遍历位置
        int max = hayStackLength - needleLength;
        if (max < 0) return -1;

        char first = needle.charAt(0);
        // 遍历主字符串
        for (int i = 0; i <= max; i ++) {
            // 查找匹配的第一个字符串
            if (haystack.charAt(i) != first) {
                while (++i < hayStackLength && haystack.charAt(i) != first);
            }
            // 遍历表里后面字符串是否匹配
            if (i <= max) {
                int j = i + 1;
                int end = i + needleLength;
                for (int k = 1; j < end && haystack.charAt(j) == needle.charAt(k); j++, k++);
                if (j == end) {
                    return i;
                }
            }
        }
        return -1;
    }
}
// @lc code=end
