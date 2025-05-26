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
        // [217] 存在重复元素
        log.info("ContainsDuplicate: {}", new ContainsDuplicate().containsDuplicate(new int[] {1,2,3,1}));
        log.info("ContainsDuplicate: {}", new ContainsDuplicate().containsDuplicate(new int[] {1,2,3,4}));
        log.info("ContainsDuplicate: {}", new ContainsDuplicate().containsDuplicate(new int[] {1,1,1,3,3,4,3,2,4,2}));
        // [219] 存在重复元素 II
        log.info("ContainsDuplicateII: {}", new ContainsDuplicateII().containsNearbyDuplicate(new int[] {1,2,3,1}, 3));
        log.info("ContainsDuplicateII: {}", new ContainsDuplicateII().containsNearbyDuplicate(new int[] {1,0,1,1}, 1));
        log.info("ContainsDuplicateII: {}", new ContainsDuplicateII().containsNearbyDuplicate(new int[] {1,2,3,1,2,3}, 2));
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


/**
 * @lc app=leetcode.cn id=217 lang=java
 *
 * [217] 存在重复元素
 *
 * https://leetcode.cn/problems/contains-duplicate/description/
 *
 * algorithms
 * Easy (55.00%)
 * Likes:    1068
 * Dislikes: 0
 * Total Accepted:    832.3K
 * Total Submissions: 1.5M
 * Testcase Example:  '[1,2,3,1]'
 *
 * 给你一个整数数组 nums 。如果任一值在数组中出现 至少两次 ，返回 true ；如果数组中每个元素互不相同，返回 false 。
 *
 * 示例 1：
 * 输入：nums = [1,2,3,1]
 * 输出：true
 *
 * 示例 2：
 * 输入：nums = [1,2,3,4]
 * 输出：false
 *
 * 示例 3：
 * 输入：nums = [1,1,1,3,3,4,3,2,4,2]
 * 输出：true
 *
 * 提示：
 * 1 <= nums.length <= 10^5
 * -10^9 <= nums[i] <= 10^9
 */
class ContainsDuplicate {
    public boolean containsDuplicate(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int n : nums) {
            Integer merge = map.merge(n, 1, Integer::sum);
            if (merge > 1) {
                return true;
            }
        }
        return false;
    }
}


/**
 * @lc app=leetcode.cn id=219 lang=java
 *
 * [219] 存在重复元素 II
 *
 * https://leetcode.cn/problems/contains-duplicate-ii/description/
 *
 * algorithms
 * Easy (45.10%)
 * Likes:    670
 * Dislikes: 0
 * Total Accepted:    265.2K
 * Total Submissions: 586.3K
 * Testcase Example:  '[1,2,3,1]\n3'
 *
 * 给你一个整数数组 nums 和一个整数 k ，判断数组中是否存在两个 不同的索引 i 和 j ，
 * 满足 nums[i] == nums[j] 且 abs(i - j) <= k 。如果存在，返回 true ；否则，返回 false 。
 *
 * 示例 1：
 * 输入：nums = [1,2,3,1], k = 3
 * 输出：true
 *
 * 示例 2：
 * 输入：nums = [1,0,1,1], k = 1
 * 输出：true
 *
 * 示例 3：
 * 输入：nums = [1,2,3,1,2,3], k = 2
 * 输出：false
 *
 * 提示：
 * 1 <= nums.length <= 10^5
 * -10^9 <= nums[i] <= 10^9
 * 0 <= k <= 10^5
 */
class ContainsDuplicateII {
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            if (map.containsKey(num) && i - map.get(num) <= k) {
                return true;
            }
            map.put(num, i);
        }
        return false;
    }
}
