package test.algorithm.leetcode;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 字符串
 *
 * @author C
 * @date 2023/2/1
 */
@Slf4j
public class AlgoString {
    public static void main(java.lang.String[] args) {
        // 最长公共前缀
        log.info("LongestCommonPrefix: {}", new LongestCommonPrefix().longestCommonPrefix(new String[]{"flower", "flow", "flight"}));
        log.info("LongestCommonPrefix: {}", new LongestCommonPrefix().longestCommonPrefix(new String[]{"dog", "racecar", "car"}));
        log.info("LongestCommonPrefix: {}", new LongestCommonPrefix().longestCommonPrefix(new String[]{"ab", "a"}));
        // 找出字符串中第一个匹配项的下标
        log.info("FindTheIndexOfTheFirstOccurrenceInAString: {}", new FindTheIndexOfTheFirstOccurrenceInAString().strStr("sadbutsad", "sad"));
        log.info("FindTheIndexOfTheFirstOccurrenceInAString: {}", new FindTheIndexOfTheFirstOccurrenceInAString().strStr("leetcode", "leeto"));
        log.info("FindTheIndexOfTheFirstOccurrenceInAString: {}", new FindTheIndexOfTheFirstOccurrenceInAString().strStr("mississippi", "issipi"));
        // 串联所有单词的子串
        log.info("SubstringWithConcatenationOfAllWords: {}", new SubstringWithConcatenationOfAllWords().findSubstring("barfoothefoobarman", new String[]{"foo", "bar"}));
        log.info("SubstringWithConcatenationOfAllWords: {}", new SubstringWithConcatenationOfAllWords().findSubstring("wordgoodgoodgoodbestword", new String[]{"word", "good", "best", "word"}));
        log.info("SubstringWithConcatenationOfAllWords: {}", new SubstringWithConcatenationOfAllWords().findSubstring("barfoofoobarthefoobarman", new String[]{"bar", "foo", "the"}));
        // 外观数列
        log.info("CountAndSay: {}", new CountAndSay().countAndSay(1));
        log.info("CountAndSay: {}", new CountAndSay().countAndSay(4));
        // 字符串相乘
        log.info("MultiplyStrings: {}", new MultiplyStrings().multiply("2", "3"));
        log.info("MultiplyStrings: {}", new MultiplyStrings().multiply("123", "456"));
    }
}

/**
 * @lc app=leetcode.cn id=14 lang=java
 * <p>
 * [14] 最长公共前缀
 * <p>
 * https://leetcode.cn/problems/longest-common-prefix/description/
 * <p>
 * algorithms
 * Easy (43.19%)
 * Likes:    2629
 * Dislikes: 0
 * Total Accepted:    1M
 * Total Submissions: 2.4M
 * Testcase Example:  '["flower","flow","flight"]'
 * <p>
 * 编写一个函数来查找字符串数组中的最长公共前缀。
 * 如果不存在公共前缀，返回空字符串 ""。
 * <p>
 * 示例 1：
 * 输入：strs = ["flower","flow","flight"]
 * 输出："fl"
 * <p>
 * 示例 2：
 * 输入：strs = ["dog","racecar","car"]
 * 输出：""
 * 解释：输入不存在公共前缀。
 * <p>
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
 * <p>
 * [28] 找出字符串中第一个匹配项的下标
 * <p>
 * https://leetcode.cn/problems/find-the-index-of-the-first-occurrence-in-a-string/description/
 * <p>
 * algorithms
 * Medium (42.01%)
 * Likes:    1774
 * Dislikes: 0
 * Total Accepted:    809K
 * Total Submissions: 1.9M
 * Testcase Example:  '"sadbutsad"\n"sad"'
 * <p>
 * 给你两个字符串 haystack 和 needle ，请你在 haystack 字符串中找出 needle 字符串的第一个匹配项的下标（下标从 0
 * 开始）。如果 needle 不是 haystack 的一部分，则返回  -1 。
 * <p>
 * 示例 1：
 * 输入：haystack = "sadbutsad", needle = "sad"
 * 输出：0
 * 解释："sad" 在下标 0 和 6 处匹配。
 * 第一个匹配项的下标是 0 ，所以返回 0 。
 * <p>
 * 示例 2：
 * 输入：haystack = "leetcode", needle = "leeto"
 * 输出：-1
 * 解释："leeto" 没有在 "leetcode" 中出现，所以返回 -1 。
 * <p>
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
        for (int i = 0; i <= max; i++) {
            // 查找匹配的第一个字符串
            if (haystack.charAt(i) != first) {
                while (++i < hayStackLength && haystack.charAt(i) != first) ;
            }
            // 遍历表里后面字符串是否匹配
            if (i <= max) {
                int j = i + 1;
                int end = i + needleLength;
                for (int k = 1; j < end && haystack.charAt(j) == needle.charAt(k); j++, k++) ;
                if (j == end) {
                    return i;
                }
            }
        }
        return -1;
    }
}
// @lc code=end


/**
 * @lc app=leetcode.cn id=30 lang=java
 *
 * [30] 串联所有单词的子串
 *
 * https://leetcode.cn/problems/substring-with-concatenation-of-all-words/description/
 *
 * algorithms
 * Hard (39.60%)
 * Likes:    894
 * Dislikes: 0
 * Total Accepted:    155.6K
 * Total Submissions: 393.1K
 * Testcase Example:  '"barfoothefoobarman"\n["foo","bar"]'
 *
 * 给定一个字符串 s 和一个字符串数组 words。 words 中所有字符串 长度相同。
 * s 中的 串联子串 是指一个包含  words 中所有字符串以任意顺序排列连接起来的子串。
 *
 * 例如，如果 words = ["ab","cd","ef"]， 那么 "abcdef"， "abefcd"，"cdabef"，
 * "cdefab"，"efabcd"， 和 "efcdab" 都是串联子串。 "acdbef" 不是串联子串，因为他不是任何 words 排列的连接。
 * 返回所有串联字串在 s 中的开始索引。你可以以 任意顺序 返回答案。
 *
 * 示例 1：
 * 输入：s = "barfoothefoobarman", words = ["foo","bar"]
 * 输出：[0,9]
 * 解释：因为 words.length == 2 同时 words[i].length == 3，连接的子字符串的长度必须为 6。
 * 子串 "barfoo" 开始位置是 0。它是 words 中以 ["bar","foo"] 顺序排列的连接。
 * 子串 "foobar" 开始位置是 9。它是 words 中以 ["foo","bar"] 顺序排列的连接。
 * 输出顺序无关紧要。返回 [9,0] 也是可以的。
 *
 * 示例 2：
 * 输入：s = "wordgoodgoodgoodbestword", words = ["word","good","best","word"]
 * 输出：[]
 * 解释：因为 words.length == 4 并且 words[i].length == 4，所以串联子串的长度必须为 16。
 * s 中没有子串长度为 16 并且等于 words 的任何顺序排列的连接。
 * 所以我们返回一个空数组。
 *
 * 示例 3：
 * 输入：s = "barfoofoobarthefoobarman", words = ["bar","foo","the"]
 * 输出：[6,9,12]
 * 解释：因为 words.length == 3 并且 words[i].length == 3，所以串联子串的长度必须为 9。
 * 子串 "foobarthe" 开始位置是 6。它是 words 中以 ["foo","bar","the"] 顺序排列的连接。
 * 子串 "barthefoo" 开始位置是 9。它是 words 中以 ["bar","the","foo"] 顺序排列的连接。
 * 子串 "thefoobar" 开始位置是 12。它是 words 中以 ["the","foo","bar"] 顺序排列的连接。
 *
 * 提示：
 * 1 <= s.length <= 10^4
 * 1 <= words.length <= 5000
 * 1 <= words[i].length <= 30
 * words[i] 和 s 由小写英文字母组成
 */
// @lc code=start
class SubstringWithConcatenationOfAllWords {
    /**
     * 记 words 的长度为 m, words 中每个单词的长度为 n, s 的长度为 ls。首先需要将 s 划分为单词组,
     * 每个单词的大小均为 n(首尾除外)。这样的划分方法有 n 种, 即先删去前 i(i=0~n-1)个字母后,
     * 将剩下的字母进行划分, 如果未尾有不到 n 个字母也删去。对这种划分得到的单词数组分别使用滑动窗口对
     * words 进行类似于「字母异位词」的搜寻。
     * 划分成单词组后, 一个窗口包含 s 中前 m 个单词, 用一个哈希表 differ 表示窗口中单词频次和 words 中单词
     * 频次之差。初始化 differ 时, 出现在窗口中的单词, 每出现一次, 相应的值增加 1, 出现在 words 中的单
     * 词, 每出现一次, 相应的值减少 1。然后将窗口右移, 右侧会加入一个单词, 左侧会移出一个单词, 并对
     * differ 做相应的更新。窗口移动时, 若出现 differ 中值不为 0 的键的数量为 0, 则表示这个窗口中的单词频次
     * 和 words 中单词频次相同, 窗口的左端点是一个待求的起始位置。划分的方法有 n 种, 做 n 次滑动窗口后,
     * 即可找到所有的起始位置。
     */
    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> res = new ArrayList<>();
        // 所有单词的个数
        int num = words.length;
        // 每个单词的长度（是相同的）
        int wordLen = words[0].length();
        // 字符串长度
        int stringLen = s.length();

        for (int i = 0; i < wordLen; i++) {
            // 遍历的长度超过了整个字符串的长度，退出循环
            if (i + num * wordLen > stringLen) {
                break;
            }
            // differ表示窗口中的单词频次和words中的单词频次之差
            Map<String, Integer> differ = new HashMap<>();
            // 初始化窗口，窗口长度为num * wordLen,依次计算窗口里每个切分的单词的频次
            for (int j = 0; j < num; j++) {
                String word = s.substring(i + j * wordLen, i + (j + 1) * wordLen);
                differ.put(word, differ.getOrDefault(word, 0) + 1);
            }
            // 遍历words中的word，对窗口里每个单词计算差值
            for (String word : words) {
                differ.put(word, differ.getOrDefault(word, 0) - 1);
                // 差值为0时，移除掉这个word
                if (differ.get(word) == 0) {
                    differ.remove(word);
                }
            }
            // 开始滑动窗口
            for (int start = i; start < stringLen - num * wordLen + 1; start += wordLen) {
                if (start != i) {
                    // 右边的单词滑进来
                    String word = s.substring(start + (num - 1) * wordLen, start + num * wordLen);
                    differ.put(word, differ.getOrDefault(word, 0) + 1);
                    if (differ.get(word) == 0) {
                        differ.remove(word);
                    }
                    // 左边的单词滑出去
                    word = s.substring(start - wordLen, start);
                    differ.put(word, differ.getOrDefault(word, 0) - 1);
                    if (differ.get(word) == 0) {
                        differ.remove(word);
                    }
                    word = s.substring(start - wordLen, start);
                }
                // 窗口匹配的单词数等于words中对应的单词数
                if (differ.isEmpty()) {
                    res.add(start);
                }
            }
        }
        return res;
    }
}
// @lc code=end


/**
 * @lc app=leetcode.cn id=38 lang=java
 *
 * [38] 外观数列
 *
 * https://leetcode.cn/problems/count-and-say/description/
 *
 * algorithms
 * Medium (60.34%)
 * Likes:    1006
 * Dislikes: 0
 * Total Accepted:    334.4K
 * Total Submissions: 554.2K
 * Testcase Example:  '1'
 *
 * 给定一个正整数 n ，输出外观数列的第 n 项。
 * 「外观数列」是一个整数序列，从数字 1 开始，序列中的每一项都是对前一项的描述。
 * 你可以将其视作是由递归公式定义的数字字符串序列：
 * countAndSay(1) = "1"
 * countAndSay(n) 是对 countAndSay(n-1) 的描述，然后转换成另一个数字字符串。
 * 前五项如下：
 * 1.     1
 * 2.     11
 * 3.     21
 * 4.     1211
 * 5.     111221
 * 第一项是数字 1
 * 描述前一项，这个数是 1 即 “ 一 个 1 ”，记作 "11"
 * 描述前一项，这个数是 11 即 “ 二 个 1 ” ，记作 "21"
 * 描述前一项，这个数是 21 即 “ 一 个 2 + 一 个 1 ” ，记作 "1211"
 * 描述前一项，这个数是 1211 即 “ 一 个 1 + 一 个 2 + 二 个 1 ” ，记作 "111221"
 * 要描述一个数字字符串，首先要将字符串分割为 最小 数量的组，每个组都由连续的最多 相同字符
 * 组成。然后对于每个组，先描述字符的数量，然后描述字符，形成一个描述组。要将描述转换为数字字符串，先将每组中的字符数量用数字替换，再将所有描述组连接起来。
 *
 * 示例 1：
 * 输入：n = 1
 * 输出："1"
 * 解释：这是一个基本样例。
 *
 * 示例 2：
 * 输入：n = 4
 * 输出："1211"
 * 解释：
 * countAndSay(1) = "1"
 * countAndSay(2) = 读 "1" = 一 个 1 = "11"
 * countAndSay(3) = 读 "11" = 二 个 1 = "21"
 * countAndSay(4) = 读 "21" = 一 个 2 + 一 个 1 = "12" + "11" = "1211"
 *
 * 提示：
 * 1 <= n <= 30
 */
// @lc code=start
class CountAndSay {
    public String countAndSay(int n) {
        if (n == 1) return "1";
        String strN1 = countAndSay(n - 1);
        return countAndSay(strN1);
    }

    public String countAndSay(String strN) {
        // 左右指针
        int left = 0;
        int right = 0;
        char leftVal = strN.charAt(0);
        StringBuilder sb = new StringBuilder();
        for (char c : strN.toCharArray()) {
            // 新字符不相等,
            if (c != leftVal) {
                // 加入相等字符的数量和值
                sb.append(right - left).append(leftVal);
                // 把左指针移到新字符
                left = right;
                // 左指针的值等于新字符
                leftVal = c;
            }
            // 右指针每次都移动到下 1 格
            right ++;
        }
        // 最后一段字符一定相等, 所以需要再循环外加入最后一段字符
        sb.append(right - left).append(leftVal);
        return sb.toString();
    }
}
// @lc code=end


/**
 * @lc app=leetcode.cn id=43 lang=java
 *
 * [43] 字符串相乘
 *
 * https://leetcode.cn/problems/multiply-strings/description/
 *
 * algorithms
 * Medium (44.53%)
 * Likes:    1196
 * Dislikes: 0
 * Total Accepted:    294.8K
 * Total Submissions: 662.4K
 * Testcase Example:  '"2"\n"3"'
 *
 * 给定两个以字符串形式表示的非负整数 num1 和 num2，返回 num1 和 num2 的乘积，它们的乘积也表示为字符串形式。
 * 注意：不能使用任何内置的 BigInteger 库或直接将输入转换为整数。
 *
 * 示例 1:
 * 输入: num1 = "2", num2 = "3"
 * 输出: "6"
 *
 * 示例 2:
 * 输入: num1 = "123", num2 = "456"
 * 输出: "56088"
 *
 * 提示：
 * 1 <= num1.length, num2.length <= 200
 * num1 和 num2 只能由数字组成。
 * num1 和 num2 都不包含任何前导零，除了数字0本身。
 */
// @lc code=start
class MultiplyStrings {
    public String multiply(String num1, String num2) {
        int m = num1.length();
        int n = num2.length();
        // 结果最多为 m + n 位数
        int[] res = new int[m + n];
        // 从个位数开始逐位相乘
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                int mul = (num1.charAt(i) - '0') * (num2.charAt(j) - '0');
                // 乘积在 res 对应的索引位置
                int p1 = i + j;     // 高位
                int p2 = i + j + 1; // 低位
                int sum = res[p2] + mul;
                res[p1] += sum / 10;
                res[p2] = sum % 10;
            }
        }
        // 将计算结果转化成字符串
        // 结果前缀可能存的 0（未使用的位）
        int i = 0;
        while (i < res.length && res[i] == 0) {
            i++;
        }
        StringBuilder sb = new StringBuilder();
        for (; i < res.length; i++) {
            sb.append(res[i]);
        }
        String str = sb.toString();
        return str.length() == 0 ? "0" : str;
    }
}
// @lc code=end
