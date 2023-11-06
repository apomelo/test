package test.algorithm.leetcode;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * hash表
 * @author C
 * @date 2023/11/20
 */
@Slf4j
public class AlgoHashTable {
    public static void main(String[] args) {
        // [205] 同构字符串
        log.info("IsomorphicStrings: {}", new IsomorphicStrings().isIsomorphic("egg", "add"));
        log.info("IsomorphicStrings: {}", new IsomorphicStrings().isIsomorphic("foo", "bar"));
        log.info("IsomorphicStrings: {}", new IsomorphicStrings().isIsomorphic("paper", "title"));
    }
}


/**
 * @lc app=leetcode.cn id=205 lang=java
 *
 * [205] 同构字符串
 *
 * https://leetcode.cn/problems/isomorphic-strings/description/
 *
 * algorithms
 * Easy (49.48%)
 * Likes:    666
 * Dislikes: 0
 * Total Accepted:    230.2K
 * Total Submissions: 469.3K
 * Testcase Example:  '"egg"\n"add"'
 *
 * 给定两个字符串 s 和 t ，判断它们是否是同构的。
 * 如果 s 中的字符可以按某种映射关系替换得到 t ，那么这两个字符串是同构的。
 * 每个出现的字符都应当映射到另一个字符，同时不改变字符的顺序。不同字符不能映射到同一个字符上，相同字符只能映射到同一个字符上，字符可以映射到自己本身。
 *
 * 示例 1:
 * 输入：s = "egg", t = "add"
 * 输出：true
 *
 * 示例 2：
 * 输入：s = "foo", t = "bar"
 * 输出：false
 *
 * 示例 3：
 * 输入：s = "paper", t = "title"
 * 输出：true
 *
 * 提示：
 * 1 <= s.length <= 5 * 10^4
 * t.length == s.length
 * s 和 t 由任意有效的 ASCII 字符组成
 */
class IsomorphicStrings {
    public boolean isIsomorphic(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        Map<Character, Character> map1 = new HashMap<>();
        Map<Character, Character> map2 = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char c1 = s.charAt(i);
            char c2 = t.charAt(i);
            Character src2 = map1.computeIfAbsent(c1, k -> c2);
            if (src2 != c2) {
                return false;
            }
            Character src1 = map2.computeIfAbsent(c2, k -> c1);
            if (src1 != c1) {
                return false;
            }
        }
        return true;
    }
}
