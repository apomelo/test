package test.algorithm.leetcode;

import lombok.extern.slf4j.Slf4j;

/**
 * 数学
 *
 * @author C
 * @date 2023/2/1
 */
@Slf4j
public class AlgoMath {
    public static void main(String[] args) {
        // 两数相除
        log.info("DivideTwoIntegers: {}", new DivideTwoIntegers().divide1(10, 3));
        log.info("DivideTwoIntegers: {}", new DivideTwoIntegers().divide1(7, -3));
        log.info("DivideTwoIntegers: {}", new DivideTwoIntegers().divide2(10, 3));
        log.info("DivideTwoIntegers: {}", new DivideTwoIntegers().divide2(7, -3));
        // 缺失的第一个正数
        log.info("FirstMissingPositive: {}", new FirstMissingPositive().firstMissingPositive(new int[] {1,2,0}));
        log.info("FirstMissingPositive: {}", new FirstMissingPositive().firstMissingPositive(new int[] {3,4,-1,1}));
        log.info("FirstMissingPositive: {}", new FirstMissingPositive().firstMissingPositive(new int[] {7,8,9,11,12}));
        // Pow(x, n)
        log.info("PowxN: {}", new PowxN().myPow(2.00000, 10));
        log.info("PowxN: {}", new PowxN().myPow(2.10000, 3));
        log.info("PowxN: {}", new PowxN().myPow(2.00000, -2));
        // 有效数字
        log.info("ValidNumber: {}", new ValidNumber().isNumber("0"));
        log.info("ValidNumber: {}", new ValidNumber().isNumber("e"));
        log.info("ValidNumber: {}", new ValidNumber().isNumber("."));
        // x 的平方根
        log.info("Sqrtx: {}", new Sqrtx().mySqrt(4));
        log.info("Sqrtx: {}", new Sqrtx().mySqrt(8));
    }
}


/**
 * @lc app=leetcode.cn id=29 lang=java
 * <p>
 * [29] 两数相除
 * <p>
 * https://leetcode.cn/problems/divide-two-integers/description/
 * <p>
 * algorithms
 * Medium (22.20%)
 * Likes:    1077
 * Dislikes: 0
 * Total Accepted:    201.7K
 * Total Submissions: 908.9K
 * Testcase Example:  '10\n3'
 * <p>
 * 给你两个整数，被除数 dividend 和除数 divisor。将两数相除，要求 不使用 乘法、除法和取余运算。
 * 整数除法应该向零截断，也就是截去（truncate）其小数部分。例如，8.345 将被截断为 8 ，-2.7335 将被截断至 -2 。
 * 返回被除数 dividend 除以除数 divisor 得到的 商 。
 * 注意：假设我们的环境只能存储 32 位 有符号整数，其数值范围是 [−2^31,  2^31 − 1] 。本题中，如果商 严格大于 2^31 − 1
 * ，则返回 2^31 − 1 ；如果商 严格小于 -2^31 ，则返回 -2^31 。
 * <p>
 * 示例 1:
 * 输入: dividend = 10, divisor = 3
 * 输出: 3
 * 解释: 10/3 = 3.33333.. ，向零截断后得到 3 。
 * <p>
 * 示例 2:
 * 输入: dividend = 7, divisor = -3
 * 输出: -2
 * 解释: 7/-3 = -2.33333.. ，向零截断后得到 -2 。
 * <p>
 * 提示：
 * -2^31 <= dividend, divisor <= 2^31 - 1
 * divisor != 0
 */
class DivideTwoIntegers {
    /**
     * 使用二分查找法计算商：
     * 在每次循环中，找到当前可以被除的最大倍数。通过不断左移除数，即乘以2的方式，找到最大的倍数，使得该倍数不超过被除数。
     * 更新被除数：减去当前的最大倍数。
     * 更新结果：将当前倍数加到结果上。
     * 重复上述步骤，直到被除数小于除数。
     */
    public int divide1(int dividend, int divisor) {
        // 处理特殊情况
        if (divisor == 0) {
            throw new ArithmeticException("Division by zero");
        }

        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            return Integer.MAX_VALUE;
        }

        // 判断结果的正负号
        boolean isNegative = (dividend < 0 && divisor > 0) || (dividend > 0 && divisor < 0);

        // 将被除数和除数都转换为负数，以避免溢出问题
        long absDividend = Math.abs((long) dividend);
        long absDivisor = Math.abs((long) divisor);

        int result = 0;
        while (absDividend >= absDivisor) {
            // 找到当前可以被除的最大的倍数
            long temp = absDivisor; // 当前除数的值
            int multiple = 1; // 当前倍数的值
            // 不断左移除数，即乘以2，找到最大的倍数
            while (absDividend >= (temp << 1)) {
                temp <<= 1;
                multiple <<= 1;
            }

            // 更新被除数和结果
            absDividend -= temp; // 更新被除数，减去当前的最大倍数
            result += multiple; // 更新结果，将当前倍数加到结果上
        }

        // 根据正负号返回结果
        return isNegative ? -result : result;
    }

    public int divide2(int dividend, int divisor) {
        // 考虑被除数为最小值的情况
        if (dividend == Integer.MIN_VALUE) {
            if (divisor == 1) {
                return Integer.MIN_VALUE;
            }
            if (divisor == -1) {
                return Integer.MAX_VALUE;
            }
        }
        // 考虑除数为最小值的情况
        if (divisor == Integer.MIN_VALUE) {
            return dividend == Integer.MIN_VALUE ? 1 : 0;
        }
        // 考虑被除数为 0 的情况
        if (dividend == 0) {
            return 0;
        }

        // 一般情况，使用二分查找
        // 将所有的正数取相反数，这样就只需要考虑一种情况
        boolean rev = false;
        if (dividend > 0) {
            dividend = -dividend;
            rev = !rev;
        }
        if (divisor > 0) {
            divisor = -divisor;
            rev = !rev;
        }

        int left = 1, right = Integer.MAX_VALUE, ans = 0;
        while (left <= right) {
            // 注意溢出，并且不能使用除法
            int mid = left + ((right - left) >> 1);
            boolean check = quickAdd(divisor, mid, dividend);
            if (check) {
                ans = mid;
                // 注意溢出
                if (mid == Integer.MAX_VALUE) {
                    break;
                }
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return rev ? -ans : ans;
    }

    // 快速乘
    private boolean quickAdd(int y, int z, int x) {
        // x 和 y 是负数，z 是正数
        // 需要判断 z * y >= x 是否成立
        int result = 0, add = y;
        while (z != 0) {
            if ((z & 1) != 0) {
                // 需要保证 result + add >= x
                if (result < x - add) {
                    return false;
                }
                result += add;
            }
            if (z != 1) {
                // 需要保证 add + add >= x
                if (add < x - add) {
                    return false;
                }
                add += add;
            }
            // 不能使用除法
            z >>= 1;
        }
        return true;
    }
}


/**
 * @lc app=leetcode.cn id=41 lang=java
 *
 * [41] 缺失的第一个正数
 *
 * https://leetcode.cn/problems/first-missing-positive/description/
 *
 * algorithms
 * Hard (42.94%)
 * Likes:    1799
 * Dislikes: 0
 * Total Accepted:    287.8K
 * Total Submissions: 670.2K
 * Testcase Example:  '[1,2,0]'
 *
 * 给你一个未排序的整数数组 nums ，请你找出其中没有出现的最小的正整数。
 * 请你实现时间复杂度为 O(n) 并且只使用常数级别额外空间的解决方案。
 *
 * 示例 1：
 * 输入：nums = [1,2,0]
 * 输出：3
 *
 * 示例 2：
 * 输入：nums = [3,4,-1,1]
 * 输出：2
 *
 * 示例 3：
 * 输入：nums = [7,8,9,11,12]
 * 输出：1
 *
 * 提示：
 * 1 <= nums.length <= 5 * 10^5
 * -2^31 <= nums[i] <= 2^31 - 1
 */
class FirstMissingPositive {
    /**
     * 方法1: 原地 hash 法
     * 对于一个长度为 N 的数组，其中没有出现的最小正整数只能在 [1,N+1] 中。
     * 这是因为如果 [1,N] 都出现了，那么答案是 N+1，否则答案是 [1,N] 中没有出现的最小正整数。
     * 这样一来，我们将所有在 [1,N] 范围内的数放入哈希表，就可以得到最终的答案。
     * 算法的流程如下：
     * 1. 我们将数组中所有小于等于 0 的数修改为  N+1；
     * 2. 我们遍历数组中的每一个数 x，它可能已经被打了标记，因此原本对应的数为 ∣x∣，其中 ∣∣ 为绝对值符号。
     *    如果 ∣x∣∈[1,N]，那么我们给数组中的第 ∣x∣−1 个位置的数添加一个负号。注意如果它已经有负号，不需要重复添加；
     * 3. 在遍历完成之后，如果数组中的每一个数都是负数，那么答案是 N+1，否则答案是第一个正数的位置加 1。
     */
    public int firstMissingPositive(int[] nums) {
        int n = nums.length;
        // 将数组中所有小于等于 0 的数修改为  N+1
        for (int i = 0; i < n; i ++) {
            if (nums[i] <= 0) {
                nums[i] = n + 1;
            }
        }
        // 在索引为 nums[i] 上面打上标记
        for (int i = 0; i < n; i ++) {
            // 因为数组中数字可能重复, 所以可能重复打标记, 需要保证标记之后是负数
            int num = Math.abs(nums[i]);
            if (num <= n) {
                nums[num - 1] = -Math.abs(nums[num - 1]);
            }
        }
        // 找到第一个正数索引, 因为上面是在索引 nums[i] - 1 位置打标记, 所以这里应该是索引 + 1
        for (int i = 0; i < n; i ++) {
            if (nums[i] > 0) {
                return i + 1;
            }
        }
        return n + 1;
    }

    /**
     * 方法2: 置换
     * 对数组进行一次遍历，对于遍历到的数 x=nums[i]，如果 x∈[1,N]，我们就知道 x 应当出现在数组中的 x−1 的位置，
     * 因此交换 nums[i] 和 nums[x−1]，这样 x 就出现在了正确的位置。在完成交换后，新的 nums[i] 可能还在 [1,N] 的范围内，
     * 我们需要继续进行交换操作，直到 x ∉ [1,N].
     * 上面的方法可能会陷入死循环。如果 nums[i] 恰好与 nums[x−1] 相等，那么就会无限交换下去。
     * 此时我们有 nums[i]=x=nums[x−1]，说明 x 已经出现在了正确的位置。因此我们可以跳出循环，开始遍历下一个数。
     * 每次的交换操作都会使得某一个数交换到正确的位置，因此交换的次数最多为 N，整个方法的时间复杂度为 O(N)。
     */
    public int firstMissingPositive2(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n; ++i) {
            while (nums[i] > 0 && nums[i] <= n && nums[nums[i] - 1] != nums[i]) {
                int temp = nums[nums[i] - 1];
                nums[nums[i] - 1] = nums[i];
                nums[i] = temp;
            }
        }
        for (int i = 0; i < n; ++i) {
            if (nums[i] != i + 1) {
                return i + 1;
            }
        }
        return n + 1;
    }
}


/**
 * @lc app=leetcode.cn id=50 lang=java
 *
 * [50] Pow(x, n)
 *
 * https://leetcode.cn/problems/powx-n/description/
 *
 * algorithms
 * Medium (38.02%)
 * Likes:    1171
 * Dislikes: 0
 * Total Accepted:    373.2K
 * Total Submissions: 981.8K
 * Testcase Example:  '2.00000\n10'
 *
 * 实现 pow(x, n) ，即计算 x 的整数 n 次幂函数（即，x^n^ ）。
 *
 * 示例 1：
 * 输入：x = 2.00000, n = 10
 * 输出：1024.00000
 *
 * 示例 2：
 * 输入：x = 2.10000, n = 3
 * 输出：9.26100
 *
 * 示例 3：
 * 输入：x = 2.00000, n = -2
 * 输出：0.25000
 * 解释：2^-2 = 1/2^2 = 1/4 = 0.25
 *
 * 提示：
 * -100.0 < x < 100.0
 * -2^31 <= n <= 2^31-1
 * n 是一个整数
 * -10^4 <= x^n <= 10^4
 */
class PowxN {
    public double myPow(double x, int n) {
        if (n == 0) return 1;
        if (n == Integer.MIN_VALUE) {
            // 把 x 是 INT_MIN 的情况单独拿出来处理
            // 避免 -x 整型溢出
            return myPow(1 / x, -(n + 1)) / x;
        }

        if (n < 0) {
            return myPow(1 / x, -n);
        }

        if (n % 2 == 1) {
            // n 是奇数
            return x * myPow(x, n - 1);
        } else {
            // n 是偶数
            double sub = myPow(x, n / 2);
            return sub * sub;
        }
    }
}


/**
 * @lc app=leetcode.cn id=65 lang=java
 *
 * [65] 有效数字
 *
 * https://leetcode.cn/problems/valid-number/description/
 *
 * algorithms
 * Hard (27.58%)
 * Likes:    354
 * Dislikes: 0
 * Total Accepted:    65.3K
 * Total Submissions: 236.6K
 * Testcase Example:  '"0"'
 *
 * 有效数字（按顺序）可以分成以下几个部分：
 *      一个 小数 或者 整数
 *      （可选）一个 'e' 或 'E' ，后面跟着一个 整数
 * 小数（按顺序）可以分成以下几个部分：
 *      （可选）一个符号字符（'+' 或 '-'）
 *      下述格式之一：
 *      至少一位数字，后面跟着一个点 '.'
 *      至少一位数字，后面跟着一个点 '.' ，后面再跟着至少一位数字
 *      一个点 '.' ，后面跟着至少一位数字
 * 整数（按顺序）可以分成以下几个部分：
 *      （可选）一个符号字符（'+' 或 '-'）
 *      至少一位数字
 *
 * 部分有效数字列举如下：["2", "0089", "-0.1", "+3.14", "4.", "-.9", "2e10", "-90E3", "3e+7", "+6e-1", "53.5e93", "-123.456e789"]
 * 部分无效数字列举如下：["abc", "1a", "1e", "e3", "99e2.5", "--6", "-+3", "95a54e53"]
 * 给你一个字符串 s ，如果 s 是一个 有效数字 ，请返回 true 。
 *
 * 示例 1：
 * 输入：s = "0"
 * 输出：true
 *
 * 示例 2：
 * 输入：s = "e"
 * 输出：false
 *
 * 示例 3：
 * 输入：s = "."
 * 输出：false
 *
 * 提示：
 * 1 <= s.length <= 20
 * s 仅含英文字母（大写和小写），数字（0-9），加号 '+' ，减号 '-' ，或者点 '.' 。
 */
class ValidNumber {
    public boolean isNumber(String s) {
        String regex = "^[+-]?(\\d+|\\d+(\\.\\d*)?|\\.\\d+)([eE][+-]?\\d+)?$";
        return s.matches(regex);
    }
}


/**
 * @lc app=leetcode.cn id=69 lang=java
 *
 * [69] x 的平方根
 *
 * https://leetcode.cn/problems/sqrtx/description/
 *
 * algorithms
 * Easy (38.48%)
 * Likes:    1352
 * Dislikes: 0
 * Total Accepted:    745.2K
 * Total Submissions: 1.9M
 * Testcase Example:  '4'
 *
 * 给你一个非负整数 x ，计算并返回 x 的 算术平方根 。
 * 由于返回类型是整数，结果只保留 整数部分 ，小数部分将被 舍去 。
 * 注意：不允许使用任何内置指数函数和算符，例如 pow(x, 0.5) 或者 x ** 0.5 。
 *
 * 示例 1：
 * 输入：x = 4
 * 输出：2
 *
 * 示例 2：
 * 输入：x = 8
 * 输出：2
 * 解释：8 的算术平方根是 2.82842..., 由于返回类型是整数，小数部分将被舍去。
 *
 * 提示：
 * 0 <= x <= 2^31 - 1
 */
class Sqrtx {
    public int mySqrt(int x) {
        if (x == 0) {
            return 0;
        }

        long left = 1;  // 左边界从1开始
        long right = x;  // 右边界为x

        while (left < right) {
            long mid = left + (right - left + 1) / 2;  // 取中间值，向上取整

            if (mid * mid > x) {
                right = mid - 1;  // 平方大于x，将右边界移动到mid的左侧
            } else {
                left = mid;  // 平方小于等于x，将左边界移动到mid
            }
        }

        return (int) left;
    }
}
