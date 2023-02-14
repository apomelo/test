package test.algorithm.leetcode;

import lombok.extern.slf4j.Slf4j;

/**
 * 模板
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
