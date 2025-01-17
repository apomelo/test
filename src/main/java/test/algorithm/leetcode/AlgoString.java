package test.algorithm.leetcode;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 字符串
 *
 * @author C
 * @date 2023/2/1
 */
@Slf4j
public class AlgoString {
    public static void main(java.lang.String[] args) {
        // [14] 最长公共前缀
        log.info("LongestCommonPrefix: {}", new LongestCommonPrefix().longestCommonPrefix(new String[]{"flower", "flow", "flight"}));
        log.info("LongestCommonPrefix: {}", new LongestCommonPrefix().longestCommonPrefix(new String[]{"dog", "racecar", "car"}));
        log.info("LongestCommonPrefix: {}", new LongestCommonPrefix().longestCommonPrefix(new String[]{"ab", "a"}));
        // [28] 找出字符串中第一个匹配项的下标
        log.info("FindTheIndexOfTheFirstOccurrenceInAString: {}", new FindTheIndexOfTheFirstOccurrenceInAString().strStr("sadbutsad", "sad"));
        log.info("FindTheIndexOfTheFirstOccurrenceInAString: {}", new FindTheIndexOfTheFirstOccurrenceInAString().strStr("leetcode", "leeto"));
        log.info("FindTheIndexOfTheFirstOccurrenceInAString: {}", new FindTheIndexOfTheFirstOccurrenceInAString().strStr("mississippi", "issipi"));
        // [30] 串联所有单词的子串
        log.info("SubstringWithConcatenationOfAllWords: {}", new SubstringWithConcatenationOfAllWords().findSubstring("barfoothefoobarman", new String[]{"foo", "bar"}));
        log.info("SubstringWithConcatenationOfAllWords: {}", new SubstringWithConcatenationOfAllWords().findSubstring("wordgoodgoodgoodbestword", new String[]{"word", "good", "best", "word"}));
        log.info("SubstringWithConcatenationOfAllWords: {}", new SubstringWithConcatenationOfAllWords().findSubstring("barfoofoobarthefoobarman", new String[]{"bar", "foo", "the"}));
        // [38] 外观数列
        log.info("CountAndSay: {}", new CountAndSay().countAndSay(1));
        log.info("CountAndSay: {}", new CountAndSay().countAndSay(4));
        // [43] 字符串相乘
        log.info("MultiplyStrings: {}", new MultiplyStrings().multiply("2", "3"));
        log.info("MultiplyStrings: {}", new MultiplyStrings().multiply("123", "456"));
        // [49] 字母异位词分组
        log.info("GroupAnagrams: {}", new GroupAnagrams().groupAnagrams(new String[] {"eat", "tea", "tan", "ate", "nat", "bat"}));
        log.info("GroupAnagrams: {}", new GroupAnagrams().groupAnagrams(new String[] {}));
        log.info("GroupAnagrams: {}", new GroupAnagrams().groupAnagrams(new String[] {"a"}));
        // [58] 最后一个单词的长度
        log.info("LengthOfLastWord: {}", new LengthOfLastWord().lengthOfLastWord("Hello World"));
        log.info("LengthOfLastWord: {}", new LengthOfLastWord().lengthOfLastWord("   fly me   to   the moon  "));
        log.info("LengthOfLastWord: {}", new LengthOfLastWord().lengthOfLastWord("luffy is still joyboy"));
        // [68] 文本左右对齐
        log.info("TextJustification: {}", new TextJustification().fullJustify(new String[] {"This", "is", "an", "example", "of", "text", "justification."}, 16));
        log.info("TextJustification: {}", new TextJustification().fullJustify(new String[] {"What","must","be","acknowledgment","shall","be"}, 16));
        log.info("TextJustification: {}", new TextJustification().fullJustify(new String[] {"Science","is","what","we","understand","well","enough","to","explain","to","a","computer.","Art","is","everything","else","we","do"}, 20));
        // [71] 简化路径
        log.info("SimplifyPath: {}", new SimplifyPath().simplifyPath("/home/"));
        log.info("SimplifyPath: {}", new SimplifyPath().simplifyPath("/../"));
        log.info("SimplifyPath: {}", new SimplifyPath().simplifyPath("/home//foo/"));
        log.info("SimplifyPath: {}", new SimplifyPath().simplifyPath("/a/./b/../../c/"));
        // [76] 最小覆盖子串
        log.info("MinimumWindowSubstring: {}", new MinimumWindowSubstring().minWindow("ADOBECODEBANC", "ABC"));
        log.info("MinimumWindowSubstring: {}", new MinimumWindowSubstring().minWindow("a", "a"));
        log.info("MinimumWindowSubstring: {}", new MinimumWindowSubstring().minWindow("a", "aa"));
        // [87] 扰乱字符串
        log.info("ScrambleString: {}", new ScrambleString().isScramble("great", "rgeat"));
        log.info("ScrambleString: {}", new ScrambleString().isScramble("abcde", "caebd"));
        log.info("ScrambleString: {}", new ScrambleString().isScramble("a", "a"));
        // [151] 反转字符串中的单词
        log.info("ReverseWordsInAString: {}", new ReverseWordsInAString().reverseWords("the sky is blue"));
        log.info("ReverseWordsInAString: {}", new ReverseWordsInAString().reverseWords("  hello world  "));
        log.info("ReverseWordsInAString: {}", new ReverseWordsInAString().reverseWords("a good   example"));
        // [165] 比较版本号
        log.info("CompareVersionNumbers: {}", new CompareVersionNumbers().compareVersion("1.01", "1.001"));
        log.info("CompareVersionNumbers: {}", new CompareVersionNumbers().compareVersion("1.0", "1.0.0"));
        log.info("CompareVersionNumbers: {}", new CompareVersionNumbers().compareVersion("0.1", "1.1"));
        // [214] 最短回文串
        log.info("ShortestPalindrome: {}", new ShortestPalindrome().shortestPalindrome("aacecaaa"));
        log.info("ShortestPalindrome: {}", new ShortestPalindrome().shortestPalindrome("abcd"));
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


/**
 * @lc app=leetcode.cn id=49 lang=java
 *
 * [49] 字母异位词分组
 *
 * https://leetcode.cn/problems/group-anagrams/description/
 *
 * algorithms
 * Medium (67.82%)
 * Likes:    1479
 * Dislikes: 0
 * Total Accepted:    457.7K
 * Total Submissions: 675.3K
 * Testcase Example:  '["eat","tea","tan","ate","nat","bat"]'
 *
 * 给你一个字符串数组，请你将 字母异位词 组合在一起。可以按任意顺序返回结果列表。
 * 字母异位词 是由重新排列源单词的字母得到的一个新单词，所有源单词中的字母通常恰好只用一次。
 *
 * 示例 1:
 * 输入: strs = ["eat", "tea", "tan", "ate", "nat", "bat"]
 * 输出: [["bat"],["nat","tan"],["ate","eat","tea"]]
 *
 * 示例 2:
 * 输入: strs = [""]
 * 输出: [[""]]
 *
 * 示例 3:
 * 输入: strs = ["a"]
 * 输出: [["a"]]
 *
 * 提示：
 * 1 <= strs.length <= 10^4
 * 0 <= strs[i].length <= 100
 * strs[i] 仅包含小写字母
 */
class GroupAnagrams {
    public List<List<String>> groupAnagrams(String[] strs) {
        // 编码到分组的映射
        Map<String, List<String>> codeToGroup = new HashMap<>();
        for (String s : strs) {
            // 对字符串进行编码
            String code = encode(s);
            // 把编码相同的字符串放在一起
            codeToGroup.computeIfAbsent(code, k -> new LinkedList<>()).add(s);
        }

        // 返回结果
        return new LinkedList<>(codeToGroup.values());
    }

    /**
     * 利用每个字符的出现次数进行编码
     */
    public String encode(String s) {
        char[] count = new char[26];
        for (char c : s.toCharArray()) {
            int delta = c - 'a';
            count[delta]++;
        }
        return new String(count);
    }
}


/**
 * @lc app=leetcode.cn id=58 lang=java
 *
 * [58] 最后一个单词的长度
 *
 * https://leetcode.cn/problems/length-of-last-word/description/
 *
 * algorithms
 * Easy (42.55%)
 * Likes:    588
 * Dislikes: 0
 * Total Accepted:    431.5K
 * Total Submissions: 1M
 * Testcase Example:  '"Hello World"'
 *
 * 给你一个字符串 s，由若干单词组成，单词前后用一些空格字符隔开。返回字符串中 最后一个 单词的长度。
 * 单词 是指仅由字母组成、不包含任何空格字符的最大子字符串。
 *
 * 示例 1：
 * 输入：s = "Hello World"
 * 输出：5
 * 解释：最后一个单词是“World”，长度为5。
 *
 * 示例 2：
 * 输入：s = "   fly me   to   the moon  "
 * 输出：4
 * 解释：最后一个单词是“moon”，长度为4。
 *
 * 示例 3：
 * 输入：s = "luffy is still joyboy"
 * 输出：6
 * 解释：最后一个单词是长度为6的“joyboy”。
 *
 * 提示：
 * 1 <= s.length <= 10^4
 * s 仅有英文字母和空格 ' ' 组成
 * s 中至少存在一个单词
 */
class LengthOfLastWord {
    public int lengthOfLastWord(String s) {
        String[] split = s.trim().split("\\s+");
        int n = split.length;
        return split[n - 1].length();
    }
}


/**
 * @lc app=leetcode.cn id=68 lang=java
 *
 * [68] 文本左右对齐
 *
 * https://leetcode.cn/problems/text-justification/description/
 *
 * algorithms
 * Hard (52.23%)
 * Likes:    345
 * Dislikes: 0
 * Total Accepted:    54.7K
 * Total Submissions: 104.7K
 * Testcase Example:  '["This", "is", "an", "example", "of", "text", "justification."]\n16'
 *
 * 给定一个单词数组 words 和一个长度 maxWidth ，重新排版单词，使其成为每行恰好有 maxWidth 个字符，且左右两端对齐的文本。
 * 你应该使用 “贪心算法” 来放置给定的单词；也就是说，尽可能多地往每行中放置单词。必要时可用空格 ' ' 填充，使得每行恰好有 maxWidth
 * 个字符。
 * 要求尽可能均匀分配单词间的空格数量。如果某一行单词间的空格不能均匀分配，则左侧放置的空格数要多于右侧的空格数。
 * 文本的最后一行应为左对齐，且单词之间不插入额外的空格。
 *
 * 注意:
 * 单词是指由非空格字符组成的字符序列。
 * 每个单词的长度大于 0，小于等于 maxWidth。
 * 输入单词数组 words 至少包含一个单词。
 *
 * 示例 1:
 * 输入: words = ["This", "is", "an", "example", "of", "text", "justification."],
 * maxWidth = 16
 * 输出:
 * [
 * "This    is    an",
 * "example  of text",
 * "justification.  "
 * ]
 *
 * 示例 2:
 * 输入:words = ["What","must","be","acknowledgment","shall","be"], maxWidth = 16
 * 输出:
 * [
 * "What   must   be",
 * "acknowledgment  ",
 * "shall be        "
 * ]
 * 解释: 注意最后一行的格式应为 "shall be    " 而不是 "shall     be",
 * 因为最后一行应为左对齐，而不是左右两端对齐。
 *      第二行同样为左对齐，这是因为这行只包含一个单词。
 *
 * 示例 3:
 * 输入:words =
 * ["Science","is","what","we","understand","well","enough","to","explain","to","a","computer.","Art","is","everything","else","we","do"]，maxWidth
 * = 20
 * 输出:
 * [
 * "Science  is  what we",
 * "understand      well",
 * "enough to explain to",
 * "a  computer.  Art is",
 * "everything  else  we",
 * "do                  "
 * ]
 *
 * 提示:
 * 1 <= words.length <= 300
 * 1 <= words[i].length <= 20
 * words[i] 由小写英文字母和符号组成
 * 1 <= maxWidth <= 100
 * words[i].length <= maxWidth
 */
class TextJustification {
    public List<String> fullJustify(String[] words, int maxWidth) {
        List<String> res = new ArrayList<>();
        int p = 0;
        while (p < words.length) {
            // 找到最多能放多少单词
            int[] maxNum = findMaxNum(words, maxWidth, p);
            // 单词数量
            int wordNum = maxNum[0];
            // 当前行的字符总长度
            int wordWidth = maxNum[1];
            // 当前行剩余的空格槽数量
            int spaceNum = maxWidth - wordWidth;
            // 当前行
            StringBuilder line = new StringBuilder(words[p]);
            // 当前行只有一个单词或已经是最后一行（左对齐）
            if (wordNum == 1 || p + wordNum == words.length) {
                for (int i = p + 1; i < p + wordNum; i++) {
                    addSpace(line, 1);
                    spaceNum--;
                    line.append(words[i]);
                }
                // 在当前行末尾添加剩余的空格
                addSpace(line, spaceNum);
            } else {
                // 每个空格槽应分配的空格数
                int eachSpaceNum = spaceNum / (wordNum - 1);
                // 需要额外分配的空格数
                int extSpaceNum = spaceNum % (wordNum - 1);
                for (int i = p + 1; i < p + wordNum; i++) {
                    addSpace(line, eachSpaceNum);
                    // 均匀分配额外的空格
                    if (extSpaceNum > 0) {
                        addSpace(line, 1);
                        extSpaceNum--;
                    }
                    line.append(words[i]);
                }
            }
            res.add(line.toString());
            p += wordNum;
        }
        return res;
    }

    private int[] findMaxNum(String[] words, int maxWidth, int p) {
        // 当前行的单词数量
        int wordNum = 0;
        // 当前行的字符总长度
        int wordWidth = 0;
        // 尽可能多地将单词加入当前行，直到达到最大宽度
        for (int i = p; i < words.length; i++) {
            int curLength = words[i].length();
            if (wordWidth + curLength + wordNum > maxWidth) {
                break;
            }
            // 当前行的单词数量 +1
            wordNum ++;
            // 当前行的字符总长度
            wordWidth += curLength;
        }
        return new int[] {wordNum, wordWidth};
    }

    private void addSpace(StringBuilder sb, int num) {
        for (int i = 0; i < num; i++) {
            sb.append(" ");
        }
    }
}


/**
 * @lc app=leetcode.cn id=71 lang=java
 *
 * [71] 简化路径
 *
 * https://leetcode.cn/problems/simplify-path/description/
 *
 * algorithms
 * Medium (44.19%)
 * Likes:    608
 * Dislikes: 0
 * Total Accepted:    183.8K
 * Total Submissions: 416K
 * Testcase Example:  '"/home/"'
 *
 * 给你一个字符串 path ，表示指向某一文件或目录的 Unix 风格 绝对路径 （以 '/' 开头），请你将其转化为更加简洁的规范路径。
 * 在 Unix 风格的文件系统中，一个点（.）表示当前目录本身；此外，两个点 （..）
 * 表示将目录切换到上一级（指向父目录）；两者都可以是复杂相对路径的组成部分。任意多个连续的斜杠（即，'//'）都被视为单个斜杠 '/' 。
 * 对于此问题，任何其他格式的点（例如，'...'）均被视为文件/目录名称。
 *
 * 请注意，返回的 规范路径 必须遵循下述格式：
 * 始终以斜杠 '/' 开头。
 * 两个目录名之间必须只有一个斜杠 '/' 。
 * 最后一个目录名（如果存在）不能 以 '/' 结尾。
 * 此外，路径仅包含从根目录到目标文件或目录的路径上的目录（即，不含 '.' 或 '..'）。
 *
 * 返回简化后得到的 规范路径 。
 *
 * 示例 1：
 * 输入：path = "/home/"
 * 输出："/home"
 * 解释：注意，最后一个目录名后面没有斜杠。
 *
 * 示例 2：
 * 输入：path = "/../"
 * 输出："/"
 * 解释：从根目录向上一级是不可行的，因为根目录是你可以到达的最高级。
 *
 * 示例 3：
 * 输入：path = "/home//foo/"
 * 输出："/home/foo"
 * 解释：在规范路径中，多个连续斜杠需要用一个斜杠替换。
 *
 * 示例 4：
 * 输入：path = "/a/./b/../../c/"
 * 输出："/c"
 *
 * 提示：
 * 1 <= path.length <= 3000
 * path 由英文字母，数字，'.'，'/' 或 '_' 组成。
 * path 是一个有效的 Unix 风格绝对路径。
 */
class SimplifyPath {
    public String simplifyPath(String path) {
        Stack<String> stk = new Stack<>();
        String regex = "/";
        String[] parts = path.split(regex);
        // 借助栈计算最终的文件夹路径
        for (String part : parts) {
            if (part.isEmpty() || part.equals(".")) {
                continue;
            }
            if (part.equals("..")) {
                if (!stk.isEmpty()) stk.pop();
                continue;
            }
            stk.push(part);
        }
        // 栈中存储的文件夹组成路径
        String res = "";
        while (!stk.isEmpty()) {
            // 先进栈的路径在前面
            res = "/" + stk.pop() + res;
        }
        return res.isEmpty() ? "/" : res;
    }
}


/**
 * @lc app=leetcode.cn id=76 lang=java
 *
 * [76] 最小覆盖子串
 *
 * https://leetcode.cn/problems/minimum-window-substring/description/
 *
 * algorithms
 * Hard (45.19%)
 * Likes:    2550
 * Dislikes: 0
 * Total Accepted:    432.2K
 * Total Submissions: 956.3K
 * Testcase Example:  '"ADOBECODEBANC"\n"ABC"'
 *
 * 给你一个字符串 s 、一个字符串 t 。返回 s 中涵盖 t 所有字符的最小子串。如果 s 中不存在涵盖 t 所有字符的子串，则返回空字符串 ""。
 *
 * 注意：
 * 对于 t 中重复字符，我们寻找的子字符串中该字符数量必须不少于 t 中该字符数量。
 * 如果 s 中存在这样的子串，我们保证它是唯一的答案。
 *
 * 示例 1：
 * 输入：s = "ADOBECODEBANC", t = "ABC"
 * 输出："BANC"
 * 解释：最小覆盖子串 "BANC" 包含来自字符串 t 的 'A'、'B' 和 'C'。
 *
 * 示例 2：
 * 输入：s = "a", t = "a"
 * 输出："a"
 * 解释：整个字符串 s 是最小覆盖子串。
 *
 * 示例 3:
 * 输入: s = "a", t = "aa"
 * 输出: ""
 * 解释: t 中两个字符 'a' 均应包含在 s 的子串中，
 * 因此没有符合条件的子字符串，返回空字符串。
 *
 * 提示：
 * m == s.length
 * n == t.length
 * 1 <= m, n <= 10^5
 * s 和 t 由英文字母组成
 *
 * 进阶：你能设计一个在 o(m+n) 时间内解决此问题的算法吗？
 */
class MinimumWindowSubstring {
    public String minWindow(String s, String t) {
        Map<Character, Integer> need = new HashMap<>();
        Map<Character, Integer> window = new HashMap<>();

        for (char c : t.toCharArray()) {
            need.merge(c, 1, Integer::sum);
        }

        int left = 0, right = 0;
        // 满足出现次数的字符个数
        int valid = 0;
        // 记录最小覆盖子串的起始索引及长度
        int start = 0, len = Integer.MAX_VALUE;

        while (right < s.length()) {
            // c 是将移入窗口的字符
            char c = s.charAt(right);
            // 右移窗口
            right++;
            // 进行窗口内数据的一系列更新
            if (need.containsKey(c)) {
                window.merge(c, 1, Integer::sum);
                if (window.get(c).equals(need.get(c))) {
                    valid++;
                }
            }

            // 判断左侧窗口是否要收缩
            while (valid == need.size()) {
                // 在这里更新最小覆盖子串
                if (right - left < len) {
                    start = left;
                    len = right - left;
                }
                // d 是将移出窗口的字符
                char d = s.charAt(left);
                // 左移窗口
                left++;
                // 进行窗口内数据的一系列更新
                if (need.containsKey(d)) {
                    if (window.get(d).equals(need.get(d))) {
                        valid--;
                    }
                    window.merge(d, -1, Integer::sum);
                }
            }
        }

        // 返回最小覆盖子串
        return len == Integer.MAX_VALUE ? "" : s.substring(start, start + len);
    }
}


/**
 * @lc app=leetcode.cn id=87 lang=java
 *
 * [87] 扰乱字符串
 *
 * https://leetcode.cn/problems/scramble-string/description/
 *
 * algorithms
 * Hard (47.35%)
 * Likes:    526
 * Dislikes: 0
 * Total Accepted:    58.2K
 * Total Submissions: 122.8K
 * Testcase Example:  '"great"\n"rgeat"'
 *
 * 使用下面描述的算法可以扰乱字符串 s 得到字符串 t ：
 * 如果字符串的长度为 1 ，算法停止
 * 如果字符串的长度 > 1 ，执行下述步骤：
 * 在一个随机下标处将字符串分割成两个非空的子字符串。即，如果已知字符串 s ，则可以将其分成两个子字符串 x 和 y ，且满足 s = x + y。
 * 随机 决定是要「交换两个子字符串」还是要「保持这两个子字符串的顺序不变」。即，在执行这一步骤之后，s 可能是 s = x + y 或者 s = y + x 。
 * 在 x 和 y 这两个子字符串上继续从步骤 1 开始递归执行此算法。
 *
 * 给你两个 长度相等 的字符串 s1 和 s2，判断 s2 是否是 s1 的扰乱字符串。如果是，返回 true ；否则，返回 false 。
 *
 * 示例 1：
 * 输入：s1 = "great", s2 = "rgeat"
 * 输出：true
 * 解释：s1 上可能发生的一种情形是：
 * "great" --> "gr/eat" // 在一个随机下标处分割得到两个子字符串
 * "gr/eat" --> "gr/eat" // 随机决定：「保持这两个子字符串的顺序不变」
 * "gr/eat" --> "g/r / e/at" // 在子字符串上递归执行此算法。两个子字符串分别在随机下标处进行一轮分割
 * "g/r / e/at" --> "r/g / e/at" // 随机决定：第一组「交换两个子字符串」，第二组「保持这两个子字符串的顺序不变」
 * "r/g / e/at" --> "r/g / e/ a/t" // 继续递归执行此算法，将 "at" 分割得到 "a/t"
 * "r/g / e/ a/t" --> "r/g / e/ a/t" // 随机决定：「保持这两个子字符串的顺序不变」
 * 算法终止，结果字符串和 s2 相同，都是 "rgeat"
 * 这是一种能够扰乱 s1 得到 s2 的情形，可以认为 s2 是 s1 的扰乱字符串，返回 true
 *
 * 示例 2：
 * 输入：s1 = "abcde", s2 = "caebd"
 * 输出：false
 *
 * 示例 3：
 * 输入：s1 = "a", s2 = "a"
 * 输出：true
 *
 * 提示：
 * s1.length == s2.length
 * 1 <= s1.length <= 30
 * s1 和 s2 由小写英文字母组成
 */
class ScrambleString {
    /**
     * 解法1: 递归
     */
    public boolean isScramble(String s1, String s2) {
        // 使用记忆化技术，保存中间结果，避免重复计算
        Map<String, Boolean> memo = new HashMap<>();
        return isScrambleHelper(s1, s2, memo);
    }

    private boolean isScrambleHelper(String s1, String s2, Map<String, Boolean> memo) {
        // 判断两个字符串是否相等
        if (s1.equals(s2)) {
            return true;
        }
        int n = s1.length();
        // 判断两个字符串长度是否相等
        if (n != s2.length()) {
            return false;
        }
        // 判断两个字符串中字符的种类和数量是否一致
        int charNum = 26;
        int[] count = new int[charNum];
        for (int i = 0; i < n; i++) {
            count[s1.charAt(i) - 'a']++;
            count[s2.charAt(i) - 'a']--;
        }
        for (int i = 0; i < charNum; i++) {
            if (count[i] != 0) {
                return false;
            }
        }
        // 判断是否已经计算过该结果
        String key = s1 + "#" + s2;
        if (memo.containsKey(key)) {
            return memo.get(key);
        }
        // 递归判断所有可能的切割位置
        for (int i = 1; i < n; i++) {
            // 不交换的情况
            if (isScrambleHelper(s1.substring(0, i), s2.substring(0, i), memo)
                    && isScrambleHelper(s1.substring(i), s2.substring(i), memo)) {
                memo.put(key, true);
                return true;
            }
            // 交换的情况
            if (isScrambleHelper(s1.substring(0, i), s2.substring(n - i), memo)
                    && isScrambleHelper(s1.substring(i), s2.substring(0, n - i), memo)) {
                memo.put(key, true);
                return true;
            }
        }
        memo.put(key, false);
        return false;
    }

    /**
     * 解法2: 动态规划
     */
    public boolean isScramble2(String s1, String s2) {
        int n = s1.length();
        if (n != s2.length()) {
            return false;
        }

        // dp[i][j][k] 表示 s1 从 i 开始长度为 k 的子串是否能扰乱成 s2 从 j 开始长度为 k 的子串
        boolean[][][] dp = new boolean[n][n][n + 1];

        // 初始化单个字符的情况
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                dp[i][j][1] = s1.charAt(i) == s2.charAt(j);
            }
        }

        // 枚举子串长度
        for (int len = 2; len <= n; len++) {
            // 枚举 s1 的起点位置
            for (int i = 0; i <= n - len; i++) {
                // 枚举 s2 的起点位置
                for (int j = 0; j <= n - len; j++) {
                    // 枚举切割位置
                    for (int k = 1; k < len; k++) {
                        // 不交换的情况
                        if (dp[i][j][k] && dp[i + k][j + k][len - k]) {
                            dp[i][j][len] = true;
                            break;
                        }
                        // 交换的情况
                        if (dp[i][j + len - k][k] && dp[i + k][j][len - k]) {
                            dp[i][j][len] = true;
                            break;
                        }
                    }
                }
            }
        }

        return dp[0][0][n];
    }
}


/**
 * @lc app=leetcode.cn id=151 lang=java
 *
 * [151] 反转字符串中的单词
 *
 * https://leetcode.cn/problems/reverse-words-in-a-string/description/
 *
 * algorithms
 * Medium (52.89%)
 * Likes:    1041
 * Dislikes: 0
 * Total Accepted:    457.8K
 * Total Submissions: 865.6K
 * Testcase Example:  '"the sky is blue"'
 *
 * 给你一个字符串 s ，请你反转字符串中 单词 的顺序。
 * 单词 是由非空格字符组成的字符串。s 中使用至少一个空格将字符串中的 单词 分隔开。
 * 返回 单词 顺序颠倒且 单词 之间用单个空格连接的结果字符串。
 * 注意：输入字符串 s中可能会存在前导空格、尾随空格或者单词间的多个空格。返回的结果字符串中，单词间应当仅用单个空格分隔，且不包含任何额外的空格。
 *
 * 示例 1：
 * 输入：s = "the sky is blue"
 * 输出："blue is sky the"
 *
 * 示例 2：
 * 输入：s = "  hello world  "
 * 输出："world hello"
 * 解释：反转后的字符串中不能存在前导空格和尾随空格。
 *
 * 示例 3：
 * 输入：s = "a good   example"
 * 输出："example good a"
 * 解释：如果两个单词间有多余的空格，反转后的字符串需要将单词间的空格减少到仅有一个。
 *
 * 提示：
 * 1 <= s.length <= 10^4
 * s 包含英文大小写字母、数字和空格 ' '
 * s 中 至少存在一个 单词
 *
 * 进阶：如果字符串在你使用的编程语言中是一种可变数据类型，请尝试使用 O(1) 额外空间复杂度的 原地 解法。
 */
class ReverseWordsInAString {
    public String reverseWords(String s) {
        String[] split = s.split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (int i = split.length - 1; i >= 0; i--) {
            sb.append(split[i]).append(" ");
        }
        return sb.toString().trim();
    }
}


/**
 * @lc app=leetcode.cn id=165 lang=java
 *
 * [165] 比较版本号
 *
 * https://leetcode.cn/problems/compare-version-numbers/description/
 *
 * algorithms
 * Medium (51.92%)
 * Likes:    393
 * Dislikes: 0
 * Total Accepted:    160.1K
 * Total Submissions: 308.4K
 * Testcase Example:  '"1.01"\n"1.001"'
 *
 * 给你两个版本号 version1 和 version2 ，请你比较它们。
 *
 * 版本号由一个或多个修订号组成，各修订号由一个 '.' 连接。每个修订号由 多位数字 组成，可能包含 前导零。
 * 每个版本号至少包含一个字符。修订号从左到右编号，下标从 0 开始，最左边的修订号下标为 0 ，下一个修订号下标为 1 ，以此类推。
 * 例如，2.5.33和 0.1 都是有效的版本号。
 * 比较版本号时，请按从左到右的顺序依次比较它们的修订号。比较修订号时，只需比较 忽略任何前导零后的整数值 。也就是说，修订号 1 和修订号 001 相等 。
 * 如果版本号没有指定某个下标处的修订号，则该修订号视为 0 。例如，版本 1.0 小于版本 1.1 ，因为它们下标为 0 的修订号相同，而下标为 1
 * 的修订号分别为 0 和 1 ，0 < 1 。
 *
 * 返回规则如下：
 * 如果 version1 > version2 返回 1，
 * 如果 version1 < version2 返回 -1，
 * 除此之外返回 0。
 *
 * 示例 1：
 * 输入：version1 = "1.01", version2 = "1.001"
 * 输出：0
 * 解释：忽略前导零，"01" 和 "001" 都表示相同的整数 "1"
 *
 * 示例 2：
 * 输入：version1 = "1.0", version2 = "1.0.0"
 * 输出：0
 * 解释：version1 没有指定下标为 2 的修订号，即视为 "0"
 *
 * 示例 3：
 * 输入：version1 = "0.1", version2 = "1.1"
 * 输出：-1
 * 解释：version1 中下标为 0 的修订号是 "0"，version2 中下标为 0 的修订号是 "1" 。0 < 1，所以 version1 < version2
 *
 * 提示：
 * 1 <= version1.length, version2.length <= 500
 * version1 和 version2 仅包含数字和 '.'
 * version1 和 version2 都是 有效版本号
 * version1 和 version2 的所有修订号都可以存储在 32 位整数 中
 */
class CompareVersionNumbers {
    public int compareVersion(String version1, String version2) {
        // 分割版本号字符串，使用点号作为分隔符
        String[] parts1 = version1.split("\\.");
        String[] parts2 = version2.split("\\.");

        int length = Math.max(parts1.length, parts2.length);

        // 遍历每一位版本号
        for (int i = 0; i < length; i++) {
            // 将未定义的部分视为 0
            int num1 = (i < parts1.length) ? Integer.parseInt(parts1[i]) : 0;
            int num2 = (i < parts2.length) ? Integer.parseInt(parts2[i]) : 0;

            if (num1 > num2) {
                return 1;  // version1 大于 version2
            } else if (num1 < num2) {
                return -1; // version1 小于 version2
            }
        }

        return 0;  // 两个版本号相等
    }
}


/*
 * @lc app=leetcode.cn id=214 lang=java
 *
 * [214] 最短回文串
 *
 * https://leetcode.cn/problems/shortest-palindrome/description/
 *
 * algorithms
 * Hard (40.25%)
 * Likes:    566
 * Dislikes: 0
 * Total Accepted:    49.2K
 * Total Submissions: 122.2K
 * Testcase Example:  '"aacecaaa"'
 *
 * 给定一个字符串 s，你可以通过在字符串前面添加字符将其转换为回文串。找到并返回可以用这种方式转换的最短回文串。
 *
 * 示例 1：
 * 输入：s = "aacecaaa"
 * 输出："aaacecaaa"
 *
 * 示例 2：
 * 输入：s = "abcd"
 * 输出："dcbabcd"
 *
 * 提示：
 * 0 <= s.length <= 5 * 10^4
 * s 仅由小写英文字母组成
 */
class ShortestPalindrome {
    public String shortestPalindrome(String s) {
        // 反转字符串
        String rev = new StringBuilder(s).reverse().toString();
        // 构建 s 和反转字符串的连接
        String str = s + "#" + rev;
        // 构建部分匹配表
        int[] table = buildTable(str);

        // 找到最长回文前缀的长度，剩下的部分即为需要在 s 前添加的字符
        return rev.substring(0, s.length() - table[table.length - 1]) + s;
    }

    // 构建部分匹配表
    private int[] buildTable(String s) {
        int[] table = new int[s.length()];
        int index = 0;
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(index) == s.charAt(i)) {
                table[i] = table[i - 1] + 1;
                index++;
            } else {
                index = table[i - 1];
                while (index > 0 && s.charAt(index) != s.charAt(i)) {
                    index = table[index - 1];
                }
                if (s.charAt(index) == s.charAt(i)) {
                    index++;
                }
                table[i] = index;
            }
        }
        return table;
    }
}
