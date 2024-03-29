package test.algorithm.leetcode;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;

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
        // 二进制求和
        log.info("AddBinary: {}", new AddBinary().addBinary("11", "1"));
        log.info("AddBinary: {}", new AddBinary().addBinary("1010", "1011"));
        // [150] 逆波兰表达式求值
        log.info("EvaluateReversePolishNotation: {}", new EvaluateReversePolishNotation().evalRPN(new String[] {"2","1","+","3","*"}));
        log.info("EvaluateReversePolishNotation: {}", new EvaluateReversePolishNotation().evalRPN(new String[] {"4","13","5","/","+"}));
        log.info("EvaluateReversePolishNotation: {}", new EvaluateReversePolishNotation().evalRPN(new String[] {"10","6","9","3","+","-11","*","/","*","17","+","5","+"}));
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


/**
 * @lc app=leetcode.cn id=67 lang=java
 *
 * [67] 二进制求和
 *
 * https://leetcode.cn/problems/add-binary/description/
 *
 * algorithms
 * Easy (53.01%)
 * Likes:    1032
 * Dislikes: 0
 * Total Accepted:    326.1K
 * Total Submissions: 615.5K
 * Testcase Example:  '"11"\n"1"'
 *
 * 给你两个二进制字符串 a 和 b ，以二进制字符串的形式返回它们的和。
 *
 * 示例 1：
 * 输入:a = "11", b = "1"
 * 输出："100"
 *
 * 示例 2：
 * 输入：a = "1010", b = "1011"
 * 输出："10101"
 *
 * 提示：
 * 1 <= a.length, b.length <= 10^4
 * a 和 b 仅由字符 '0' 或 '1' 组成
 * 字符串如果不是 "0" ，就不含前导零
 */
class AddBinary {
    public String addBinary(String a, String b) {
        int radix = 2;
        int carry = 0;
        // 用队列存本位计算的结果
        LinkedList<Integer> list = new LinkedList<>();
        // a, b 两个字符串的指针
        int i = a.length() - 1;
        int j = b.length() - 1;
        while (i >= 0 || j >= 0) {
            // 按位依次相加
            int cur = carry;
            if (i >= 0) {
                cur += a.charAt(i) - '0';
                i--;
            }
            if (j >= 0) {
                cur += b.charAt(j) - '0';
                j--;
            }
            carry = cur / radix;
            list.addFirst(cur % radix);
        }
        // 最后还有进位，插入队列头
        if (carry > 0) {
            list.addFirst(carry);
        }
        // 转换为字符串
        StringBuilder res = new StringBuilder();
        list.forEach(res::append);
        return res.toString();
    }
}


/*
 * @lc app=leetcode.cn id=150 lang=java
 *
 * [150] 逆波兰表达式求值
 *
 * https://leetcode.cn/problems/evaluate-reverse-polish-notation/description/
 *
 * algorithms
 * Medium (53.00%)
 * Likes:    816
 * Dislikes: 0
 * Total Accepted:    310K
 * Total Submissions: 584K
 * Testcase Example:  '["2","1","+","3","*"]'
 *
 * 给你一个字符串数组 tokens ，表示一个根据 逆波兰表示法 表示的算术表达式。
 * 请你计算该表达式。返回一个表示表达式值的整数。
 *
 * 注意：
 * 有效的算符为 '+'、'-'、'*' 和 '/' 。
 * 每个操作数（运算对象）都可以是一个整数或者另一个表达式。
 * 两个整数之间的除法总是 向零截断 。
 * 表达式中不含除零运算。
 * 输入是一个根据逆波兰表示法表示的算术表达式。
 * 答案及所有中间计算结果可以用 32 位 整数表示。
 *
 * 示例 1：
 * 输入：tokens = ["2","1","+","3","*"]
 * 输出：9
 * 解释：该算式转化为常见的中缀算术表达式为：((2 + 1) * 3) = 9
 *
 * 示例 2：
 * 输入：tokens = ["4","13","5","/","+"]
 * 输出：6
 * 解释：该算式转化为常见的中缀算术表达式为：(4 + (13 / 5)) = 6
 *
 * 示例 3：
 * 输入：tokens = ["10","6","9","3","+","-11","*","/","*","17","+","5","+"]
 * 输出：22
 * 解释：该算式转化为常见的中缀算术表达式为：
 *   ((10 * (6 / ((9 + 3) * -11))) + 17) + 5
 * = ((10 * (6 / (12 * -11))) + 17) + 5
 * = ((10 * (6 / -132)) + 17) + 5
 * = ((10 * 0) + 17) + 5
 * = (0 + 17) + 5
 * = 17 + 5
 * = 22
 *
 * 提示：
 * 1 <= tokens.length <= 10^4
 * tokens[i] 是一个算符（"+"、"-"、"*" 或 "/"），或是在范围 [-200, 200] 内的一个整数
 *
 * 逆波兰表达式：
 * 逆波兰表达式是一种后缀表达式，所谓后缀就是指算符写在后面。
 * 平常使用的算式则是一种中缀表达式，如 ( 1 + 2 ) * ( 3 + 4 ) 。
 * 该算式的逆波兰表达式写法为 ( ( 1 2 + ) ( 3 4 + ) * ) 。
 * 逆波兰表达式主要有以下两个优点：
 * 去掉括号后表达式无歧义，上式即便写成 1 2 + 3 4 + * 也可以依据次序计算出正确结果。
 * 适合用栈操作运算：遇到数字则入栈；遇到算符则取出栈顶两个数字进行计算，并将结果压入栈中
 */
class EvaluateReversePolishNotation {
    public int evalRPN(String[] tokens) {
        Stack<Integer> stack = new Stack<>();
        for (String s : tokens) {
            switch (s) {
                case "+":
                    stack.push(stack.pop() + stack.pop());
                    break;
                case "-":
                    int sub = stack.pop();
                    stack.push(stack.pop() - sub);
                    break;
                case "*":
                    stack.push(stack.pop() * stack.pop());
                    break;
                case "/":
                    int div = stack.pop();
                    stack.push(stack.pop() / div);
                    break;
                default:
                    stack.push(Integer.parseInt(s));
                    break;
            }
        }
        return stack.pop();
    }
}
