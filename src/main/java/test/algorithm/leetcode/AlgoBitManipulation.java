package test.algorithm.leetcode;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 位操作
 * @author C
 * @date 2023/9/22
 */
@Slf4j
public class AlgoBitManipulation {
    public static void main(String[] args) {
        // [136] 只出现一次的数字
        log.info("SingleNumber: {}", new SingleNumber().singleNumber(new int[] {2,2,1}));
        log.info("SingleNumber: {}", new SingleNumber().singleNumber(new int[] {4,1,2,1,2}));
        log.info("SingleNumber: {}", new SingleNumber().singleNumber(new int[] {1}));
        // [137] 只出现一次的数字 II
        log.info("SingleNumberII: {}", new SingleNumberII().singleNumber(new int[] {2,2,3,2}));
        log.info("SingleNumberII: {}", new SingleNumberII().singleNumber(new int[] {0,1,0,1,0,1,99}));
        // [169] 多数元素
        log.info("MajorityElement: {}", new MajorityElement().majorityElement(new int[] {3,2,3}));
        log.info("MajorityElement: {}", new MajorityElement().majorityElement(new int[] {2,2,1,1,1,2,2}));
        // [187] 重复的DNA序列
        log.info("RepeatedDnaSequences: {}", new RepeatedDnaSequences().findRepeatedDnaSequences("AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT"));
        log.info("RepeatedDnaSequences: {}", new RepeatedDnaSequences().findRepeatedDnaSequences("AAAAAAAAAAAAA"));
        // [190] 颠倒二进制位
        log.info("ReverseBits: {}", new ReverseBits().reverseBits(0b00000010100101000001111010011100));
        log.info("ReverseBits: {}", new ReverseBits().reverseBits(0b11111111111111111111111111111101));
        // [191] 位1的个数
        log.info("NumberOf1Bits: {}", new NumberOf1Bits().hammingWeight(0b00000000000000000000000000001011));
        log.info("NumberOf1Bits: {}", new NumberOf1Bits().hammingWeight(0b00000000000000000000000010000000));
        log.info("NumberOf1Bits: {}", new NumberOf1Bits().hammingWeight(0b11111111111111111111111111111101));
        // [201] 数字范围按位与
        log.info("BitwiseAndOfNumbersRange: {}", new BitwiseAndOfNumbersRange().rangeBitwiseAnd(5, 7));
        log.info("BitwiseAndOfNumbersRange: {}", new BitwiseAndOfNumbersRange().rangeBitwiseAnd(0, 0));
        log.info("BitwiseAndOfNumbersRange: {}", new BitwiseAndOfNumbersRange().rangeBitwiseAnd(1, 2147483647));
        log.info("BitwiseAndOfNumbersRange: {}", new BitwiseAndOfNumbersRange().rangeBitwiseAnd(2147483646, 2147483647));
    }
}


/**
 * @lc app=leetcode.cn id=136 lang=java
 *
 * [136] 只出现一次的数字
 *
 * https://leetcode.cn/problems/single-number/description/
 *
 * algorithms
 * Easy (72.52%)
 * Likes:    2962
 * Dislikes: 0
 * Total Accepted:    950.5K
 * Total Submissions: 1.3M
 * Testcase Example:  '[2,2,1]'
 *
 * 给你一个 非空 整数数组 nums ，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
 * 你必须设计并实现线性时间复杂度的算法来解决此问题，且该算法只使用常量额外空间。
 *
 * 示例 1 ：
 * 输入：nums = [2,2,1]
 * 输出：1
 *
 * 示例 2 ：
 * 输入：nums = [4,1,2,1,2]
 * 输出：4
 *
 * 示例 3 ：
 * 输入：nums = [1]
 * 输出：1
 *
 * 提示：
 * 1 <= nums.length <= 3 * 10^4
 * -3 * 10^4 <= nums[i] <= 3 * 10^4
 * 除了某个元素只出现一次以外，其余每个元素均出现两次。
 */
class SingleNumber {
    public int singleNumber(int[] nums) {
        int res = 0;
        for (int n : nums) {
            res ^= n;
        }
        return res;
    }
}


/**
 * @lc app=leetcode.cn id=137 lang=java
 *
 * [137] 只出现一次的数字 II
 *
 * https://leetcode.cn/problems/single-number-ii/description/
 *
 * algorithms
 * Medium (71.69%)
 * Likes:    1060
 * Dislikes: 0
 * Total Accepted:    160.4K
 * Total Submissions: 223.8K
 * Testcase Example:  '[2,2,3,2]'
 *
 * 给你一个整数数组 nums ，除某个元素仅出现 一次 外，其余每个元素都恰出现 三次 。请你找出并返回那个只出现了一次的元素。
 * 你必须设计并实现线性时间复杂度的算法且使用常数级空间来解决此问题。
 *
 * 示例 1：
 * 输入：nums = [2,2,3,2]
 * 输出：3
 *
 * 示例 2：
 * 输入：nums = [0,1,0,1,0,1,99]
 * 输出：99
 *
 * 提示：
 * 1 <= nums.length <= 3 * 10^4
 * -2^31 <= nums[i] <= 2^31 - 1
 * nums 中，除某个元素仅出现 一次 外，其余每个元素都恰出现 三次
 */
class SingleNumberII {
    public int singleNumber(int[] nums) {
        int ones = 0;  // 用于记录出现一次的数字
        int twos = 0;  // 用于记录出现两次的数字

        for (int num : nums) {
            // 更新 ones：
            // 如果 ones 中已经包含了 num，则将 num 从 ones 中去除（相当于出现两次的数字在 ones 中变为0），
            // 否则将 num 添加到 ones 中。
            ones = (ones ^ num) & ~twos;

            // 更新 twos：
            // 如果 twos 中已经包含了 num，则将 num 从 twos 中去除（相当于出现三次的数字在 twos 中变为0），
            // 否则将 num 添加到 twos 中。
            twos = (twos ^ num) & ~ones;
        }

        return ones; // 返回只出现一次的数字
    }
}


/**
 * @lc app=leetcode.cn id=169 lang=java
 *
 * [169] 多数元素
 *
 * https://leetcode.cn/problems/majority-element/description/
 *
 * algorithms
 * Easy (66.53%)
 * Likes:    2014
 * Dislikes: 0
 * Total Accepted:    787.9K
 * Total Submissions: 1.2M
 * Testcase Example:  '[3,2,3]'
 *
 * 给定一个大小为 n 的数组 nums ，返回其中的多数元素。多数元素是指在数组中出现次数 大于 ⌊ n/2 ⌋ 的元素。
 * 你可以假设数组是非空的，并且给定的数组总是存在多数元素。
 *
 * 示例 1：
 * 输入：nums = [3,2,3]
 * 输出：3
 *
 * 示例 2：
 * 输入：nums = [2,2,1,1,1,2,2]
 * 输出：2
 *
 * 提示：
 * n == nums.length
 * 1 <= n <= 5 * 10^4
 * -10^9 <= nums[i] <= 10^9
 *
 * 进阶：尝试设计时间复杂度为 O(n)、空间复杂度为 O(1) 的算法解决此问题。
 */
class MajorityElement {
    public int majorityElement(int[] nums) {
        // 我们想寻找的那个众数
        int target = 0;
        // 计数器（类比带电粒子例子中的带电性）
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            if (count == 0) {
                // 当计数器为 0 时，假设 nums[i] 就是众数
                target = nums[i];
                // 众数出现了一次
                count = 1;
            } else if (nums[i] == target) {
                // 如果遇到的是目标众数，计数器累加
                count++;
            } else {
                // 如果遇到的不是目标众数，计数器递减
                count--;
            }
        }
        // 回想带电粒子的例子
        // 此时的 count 必然大于 0，此时的 target 必然就是目标众数
        return target;
    }
}


/**
 * @lc app=leetcode.cn id=187 lang=java
 *
 * [187] 重复的DNA序列
 *
 * https://leetcode.cn/problems/repeated-dna-sequences/description/
 *
 * algorithms
 * Medium (53.50%)
 * Likes:    557
 * Dislikes: 0
 * Total Accepted:    160.4K
 * Total Submissions: 293.7K
 * Testcase Example:  '"AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT"'
 *
 * DNA序列 由一系列核苷酸组成，缩写为 'A', 'C', 'G' 和 'T'.。
 * 例如，"ACGAATTCCG" 是一个 DNA序列 。
 * 在研究 DNA 时，识别 DNA 中的重复序列非常有用。
 * 给定一个表示 DNA序列 的字符串 s ，返回所有在 DNA 分子中出现不止一次的 长度为 10 的序列(子字符串)。
 * 你可以按 任意顺序 返回答案。
 *
 * 示例 1：
 * 输入：s = "AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT"
 * 输出：["AAAAACCCCC","CCCCCAAAAA"]
 *
 * 示例 2：
 * 输入：s = "AAAAAAAAAAAAA"
 * 输出：["AAAAAAAAAA"]
 *
 * 提示：
 * 0 <= s.length <= 10^5
 * s[i]=='A'、'C'、'G' or 'T'
 */
class RepeatedDnaSequences {
    /**
     * 解法1
     * 使用一个哈希表来存储出现的 DNA 序列，每次在哈希表中检查序列是否已经出现，如果已经出现两次，则将其添加到结果集中。
     */
    public List<String> findRepeatedDnaSequences(String s) {
        List<String> res = new ArrayList<>();
        if (s == null || s.length() < 10) {
            return res;
        }

        // 创建一个哈希表，用于存储出现的 DNA 序列
        Map<String, Integer> memo = new HashMap<>();
        for (int i = 10; i <= s.length(); i++) {
            String subStr = s.substring(i - 10, i);
            int count = memo.merge(subStr, 1, Integer::sum);
            // 如果哈希表中已经存在这个 DNA 序列，将其添加到结果集中
            if (count == 2) {
                res.add(subStr);
            }
        }
        return res;
    }

    /**
     * 解法2
     * 使用位操作将 DNA 序列编码为整数，然后使用哈希表 map 来跟踪编码值和出现次数。
     * 通过滑动窗口遍历字符串，更新编码值并检查重复。如果编码值在哈希表中出现两次，将相应的 DNA 子序列添加到结果集中。
     */
    public List<String> findRepeatedDnaSequences2(String s) {
        List<String> result = new ArrayList<>();
        if (s.length() < 10) {
            return result;
        }

        // 创建一个哈希表 map，用于存储 DNA 序列的编码值和出现次数
        Map<Integer, Integer> map = new HashMap<>();

        // 创建一个编码数组 encoding，将 'A', 'C', 'G', 'T' 映射为 0, 1, 2, 3
        int[] encoding = new int[26];
        encoding['A' - 'A'] = 0;
        encoding['C' - 'A'] = 1;
        encoding['G' - 'A'] = 2;
        encoding['T' - 'A'] = 3;

        // 初始化一个哈希编码值 hashCode，用于存储当前滑动窗口内的 DNA 编码
        int hashCode = 0;
        for (int i = 0; i < 10; i++) {
            // 将每个字符的编码值左移 2 位，然后按位或，构建 20 位编码
            hashCode = (hashCode << 2) | encoding[s.charAt(i) - 'A'];
        }
        map.put(hashCode, 1);

        // 遍历字符串，从第 11 位开始，更新哈希编码值并检查重复
        for (int i = 10; i < s.length(); i++) {
            // 更新哈希编码值：左移 2 位并保留最后 20 位，再加上新字符的编码值
            hashCode = ((hashCode << 2) & 0xFFFFF) | encoding[s.charAt(i) - 'A'];

            // 获取当前编码值的出现次数，如果为 1，则将对应的 DNA 子序列添加到结果集中
            int count = map.getOrDefault(hashCode, 0);
            if (count == 1) {
                result.add(s.substring(i - 9, i + 1));
            }

            // 更新哈希表中当前编码值的出现次数
            map.put(hashCode, count + 1);
        }

        return result;
    }
}


/**
 * @lc app=leetcode.cn id=190 lang=java
 *
 * [190] 颠倒二进制位
 *
 * https://leetcode.cn/problems/reverse-bits/description/
 *
 * algorithms
 * Easy (71.99%)
 * Likes:    660
 * Dislikes: 0
 * Total Accepted:    224.6K
 * Total Submissions: 311.8K
 * Testcase Example:  '00000010100101000001111010011100'
 *
 * 颠倒给定的 32 位无符号整数的二进制位。
 *
 * 提示：
 * 请注意，在某些语言（如 Java）中，没有无符号整数类型。
 * 在这种情况下，输入和输出都将被指定为有符号整数类型，并且不应影响您的实现，因为无论整数是有符号的还是无符号的，其内部的二进制表示形式都是相同的。
 * 在 Java 中，编译器使用二进制补码记法来表示有符号整数。因此，在 示例 2 中，输入表示有符号整数 -3，输出表示有符号整数 -1073741825。
 *
 * 示例 1：
 * 输入：n = 00000010100101000001111010011100
 * 输出：964176192 (00111001011110000010100101000000)
 * 解释：输入的二进制串 00000010100101000001111010011100 表示无符号整数 43261596，
 *      因此返回 964176192，其二进制表示形式为 00111001011110000010100101000000。
 *
 * 示例 2：
 * 输入：n = 11111111111111111111111111111101
 * 输出：3221225471 (10111111111111111111111111111111)
 * 解释：输入的二进制串 11111111111111111111111111111101 表示无符号整数 4294967293，
 *      因此返回 3221225471 其二进制表示形式为 10111111111111111111111111111111 。
 *
 * 提示：
 * 输入是一个长度为 32 的二进制字符串
 * 进阶: 如果多次调用这个函数，你将如何优化你的算法？
 */
class ReverseBits {
    public int reverseBits(int n) {
        int result = 0;
        for (int i = 0; i < 32; i++) {
            // 将 n 的最后一位取出，通过位运算放到对应的位置
            result = (result << 1) | (n & 1);
            // 右移 n，准备下一位
            n = n >> 1;
        }
        return result;
    }

    public int reverseBits2(int n) {
        return Integer.reverse(n);
    }

    // Integer.reverse() 内部实现
    public int reverseBits3(int n) {
        // HD, Figure 7-1
        n = (n & 0x55555555) << 1 | (n >>> 1) & 0x55555555;
        n = (n & 0x33333333) << 2 | (n >>> 2) & 0x33333333;
        n = (n & 0x0f0f0f0f) << 4 | (n >>> 4) & 0x0f0f0f0f;
        n = (n << 24) | ((n & 0xff00) << 8) |
                ((n >>> 8) & 0xff00) | (n >>> 24);
        return n;
    }
}


/**
 * @lc app=leetcode.cn id=191 lang=java
 *
 * [191] 位1的个数
 *
 * https://leetcode.cn/problems/number-of-1-bits/description/
 *
 * algorithms
 * Easy (76.71%)
 * Likes:    615
 * Dislikes: 0
 * Total Accepted:    350.6K
 * Total Submissions: 456.9K
 * Testcase Example:  '00000000000000000000000000001011'
 *
 * 编写一个函数，输入是一个无符号整数（以二进制串的形式），返回其二进制表达式中数字位数为 '1' 的个数（也被称为汉明重量）。
 *
 * 提示：
 * 请注意，在某些语言（如 Java）中，没有无符号整数类型。
 * 在这种情况下，输入和输出都将被指定为有符号整数类型，并且不应影响您的实现，因为无论整数是有符号的还是无符号的，其内部的二进制表示形式都是相同的。
 * 在 Java 中，编译器使用二进制补码记法来表示有符号整数。因此，在 示例 3 中，输入表示有符号整数 -3。
 *
 * 示例 1：
 * 输入：n = 00000000000000000000000000001011
 * 输出：3
 * 解释：输入的二进制串 00000000000000000000000000001011 中，共有三位为 '1'。
 *
 * 示例 2：
 * 输入：n = 00000000000000000000000010000000
 * 输出：1
 * 解释：输入的二进制串 00000000000000000000000010000000 中，共有一位为 '1'。
 *
 * 示例 3：
 * 输入：n = 11111111111111111111111111111101
 * 输出：31
 * 解释：输入的二进制串 11111111111111111111111111111101 中，共有 31 位为 '1'。
 *
 * 提示：
 * 输入必须是长度为 32 的 二进制串 。
 *
 * 进阶：
 * 如果多次调用这个函数，你将如何优化你的算法？
 */
class NumberOf1Bits {
    /**
     * n & (n-1) 这个操作是算法中常见的，作用是消除数字 n 的二进制表示中的最后一个 1
     */
    public int hammingWeight(int n) {
        int res = 0;
        while (n != 0) {
            n = n & (n - 1);
            res++;
        }
        return res;
    }

    public int hammingWeight2(int n) {
        int res = 0;
        for (int i = 0; i < 32; i++) {
            if ((n & 1) == 1) {
                res++;
            }
            n >>= 1;
        }
        return res;
    }
}


/**
 * @lc app=leetcode.cn id=201 lang=java
 *
 * [201] 数字范围按位与
 *
 * https://leetcode.cn/problems/bitwise-and-of-numbers-range/description/
 *
 * algorithms
 * Medium (54.19%)
 * Likes:    485
 * Dislikes: 0
 * Total Accepted:    81.2K
 * Total Submissions: 149.7K
 * Testcase Example:  '5\n7'
 *
 * 给你两个整数 left 和 right ，表示区间 [left, right] ，返回此区间内所有数字 按位与 的结果（包含 left 、right 端点）。
 *
 * 示例 1：
 * 输入：left = 5, right = 7
 * 输出：4
 *
 * 示例 2：
 * 输入：left = 0, right = 0
 * 输出：0
 *
 * 示例 3：
 * 输入：left = 1, right = 2147483647
 * 输出：0
 *
 * 提示：
 * 0 <= left <= right <= 2^31 - 1
 */
class BitwiseAndOfNumbersRange {
    /**
     * 方法1：Brian Kernighan 算法
     * Brian Kernighan 算法的关键在于我们每次对 number 和 number−1 之间进行按位与运算后， number 中最右边的 1 会被抹去变成 0。
     */
    public int rangeBitwiseAnd(int left, int right) {
        while (left < right) {
            // 抹去最右边的 1
            right = right & (right - 1);
        }
        return right;
    }

    /**
     * 方法2：位移
     * 将两个数字不断向右移动，直到数字相等，即数字被缩减为它们的公共前缀。
     * 然后，通过将公共前缀向左移动，将零添加到公共前缀的右边以获得最终结果。
     */
    public int rangeBitwiseAnd2(int left, int right) {
        int shift = 0;
        // 找到公共前缀
        while (left < right) {
            left >>= 1;
            right >>= 1;
            shift++;
        }
        return left << shift;
    }
}
