package test.algorithm.leetcode;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * 数学 字符串
 * @author C
 * @date 2023/1/31
 */
@Slf4j
public class AlgoMathString {
    public static void main(String[] args) {
        // 整数转罗马数字
        log.info("IntegerToRoman: {}", new IntegerToRoman().intToRoman(58));
        log.info("IntegerToRoman: {}", new IntegerToRoman().intToRoman(1994));
        log.info("IntegerToRoman: {}", new IntegerToRoman().intToRoman(2004));
        // 罗马数字转整数
        log.info("RomanToInteger: {}", new RomanToInteger().romanToInt("LVIII"));
        log.info("RomanToInteger: {}", new RomanToInteger().romanToInt("MCMXCIV"));
        log.info("RomanToInteger: {}", new RomanToInteger().romanToInt("MMIV"));
    }
}

/**
 * @lc app=leetcode.cn id=12 lang=java
 *
 * [12] 整数转罗马数字
 *
 * https://leetcode.cn/problems/integer-to-roman/description/
 *
 * algorithms
 * Medium (66.23%)
 * Likes:    1033
 * Dislikes: 0
 * Total Accepted:    355.7K
 * Total Submissions: 537K
 * Testcase Example:  '3'
 *
 * 罗马数字包含以下七种字符：I，V，X，L，C，D和M。
 * 字符          数值
 * I             1
 * V             5
 * X             10
 * L             50
 * C             100
 * D             500
 * M             1000
 *
 * 例如， 罗马数字 2 写做II，即为两个并列的 1。12 写做XII，即为X+II。 27 写做XXVII, 即为XX+V+
 * II。
 * 通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做IIII，而是IV。数字 1 在数字 5 的左边，所表示的数等于大数
 * 5 减小数 1 得到的数值 4 。同样地，数字 9 表示为IX。这个特殊的规则只适用于以下六种情况：
 * I可以放在V(5) 和X(10) 的左边，来表示 4 和 9。
 * X可以放在L(50) 和C(100) 的左边，来表示 40 和90。
 * C可以放在D(500) 和M(1000) 的左边，来表示400 和900。
 *
 * 给你一个整数，将其转为罗马数字。
 *
 * 示例1:
 * 输入:num = 3
 * 输出: "III"
 *
 * 示例2:
 * 输入:num = 4
 * 输出: "IV"
 *
 * 示例3:
 * 输入:num = 9
 * 输出: "IX"
 *
 * 示例4:
 * 输入:num = 58
 * 输出: "LVIII"
 * 解释: L = 50, V = 5, III = 3.
 *
 * 示例5:
 * 输入:num = 1994
 * 输出: "MCMXCIV"
 * 解释: M = 1000, CM = 900, XC = 90, IV = 4.
 *
 * 提示：
 * 1
 */
// @lc code=start
class IntegerToRoman {
    public String intToRoman(int num) {
        // 映射表
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        // 匹配逻辑
        StringBuilder roman = new StringBuilder();
        for (int i = 0; i < values.length; ++i) {
            int value = values[i];
            String symbol = symbols[i];
            // 满足条件时循环匹配该字符
            while (num >= value) {
                num -= value;
                roman.append(symbol);
            }
            if (num == 0) {
                break;
            }
        }
        return roman.toString();
    }
}
// @lc code=end

/**
 * @lc app=leetcode.cn id=13 lang=java
 *
 * [13] 罗马数字转整数
 *
 * https://leetcode.cn/problems/roman-to-integer/description/
 *
 * algorithms
 * Easy (62.29%)
 * Likes:    2214
 * Dislikes: 0
 * Total Accepted:    766K
 * Total Submissions: 1.2M
 * Testcase Example:  '"III"'
 *
 * 罗马数字包含以下七种字符:I，V，X，L，C，D和M。
 * 字符          数值
 * I             1
 * V             5
 * X             10
 * L             50
 * C             100
 * D             500
 * M             1000
 *
 * 例如， 罗马数字 2 写做II，即为两个并列的 1 。12 写做XII，即为X+II。 27 写做XXVII, 即为XX+V
 * +II。
 * 通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做IIII，而是IV。数字 1 在数字 5 的左边，所表示的数等于大数
 * 5 减小数 1 得到的数值 4 。同样地，数字 9 表示为IX。这个特殊的规则只适用于以下六种情况：
 * I可以放在V(5) 和X(10) 的左边，来表示 4 和 9。
 * X可以放在L(50) 和C(100) 的左边，来表示 40 和90。
 * C可以放在D(500) 和M(1000) 的左边，来表示400 和900。
 *
 * 给定一个罗马数字，将其转换成整数。
 *
 * 示例1:
 * 输入:s = "III"
 * 输出: 3
 *
 * 示例2:
 * 输入:s = "IV"
 * 输出: 4
 *
 * 示例3:
 * 输入:s = "IX"
 * 输出: 9
 *
 * 示例4:
 * 输入:s = "LVIII"
 * 输出: 58
 * 解释: L = 50, V= 5, III = 3.
 *
 * 示例5:
 * 输入:s = "MCMXCIV"
 * 输出: 1994
 * 解释: M = 1000, CM = 900, XC = 90, IV = 4.
 *
 * 提示：
 * 1 <= s.length <= 15
 * s 仅含字符 ('I', 'V', 'X', 'L', 'C', 'D', 'M')
 * 题目数据保证 s 是一个有效的罗马数字，且表示整数在范围 [1, 3999] 内
 * 题目所给测试用例皆符合罗马数字书写规则，不会出现跨位等情况。
 * IL 和 IM 这样的例子并不符合题目要求，49 应该写作 XLIX，999 应该写作 CMXCIX 。
 * 关于罗马数字的详尽书写规则，可以参考 罗马数字 - Mathematics 。
 */

// @lc code=start
class RomanToInteger {
    public int romanToInt(String s) {
        Map<Character, Integer> symbolValues = new HashMap<Character, Integer>() {{
            put('I', 1);
            put('V', 5);
            put('X', 10);
            put('L', 50);
            put('C', 100);
            put('D', 500);
            put('M', 1000);
        }};
        int res = 0;
        int n = s.length();
        // 可以将罗马字符的每个字符视作一个单独的值，若一个数字右侧的数字比它大，则将该数字的符号取反
        for (int i = 0; i < n; i ++) {
            int value = symbolValues.get(s.charAt(i));
            if (i < n - 1 && value < symbolValues.get(s.charAt(i + 1))) {
                res -= value;
            } else {
                res += value;
            }
        }
        return res;
    }
}
// @lc code=end
