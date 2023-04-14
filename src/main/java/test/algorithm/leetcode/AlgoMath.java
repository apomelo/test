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
        log.info("DivideTwoIntegers: {}", new DivideTwoIntegers().divide1(10, 3));
        log.info("DivideTwoIntegers: {}", new DivideTwoIntegers().divide1(7, -3));
        log.info("DivideTwoIntegers: {}", new DivideTwoIntegers().divide2(10, 3));
        log.info("DivideTwoIntegers: {}", new DivideTwoIntegers().divide2(7, -3));
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
// @lc code=start
class DivideTwoIntegers {
    public int divide1(int dividend, int divisor) {
        boolean rev = false;
        if (dividend > 0) {
            dividend = -dividend;
            rev = !rev;
        }
        if (divisor > 0) {
            divisor = -divisor;
            rev = !rev;
        }

        int mod = divisor;
        int mid = dividend >> 1;
        int now = -1;
        while (mod >= mid) {
            mod <<= 1;
            now <<= 1;
        }
        int ans = 0;
        while (dividend <= divisor) {
            while (mod < dividend) {
                mod >>= 1;
                now >>= 1;
            }
            while (dividend <= mod) {
                dividend -= mod;
                ans -= now;
            }
        }
        if (ans == Integer.MIN_VALUE && !rev) return Integer.MAX_VALUE;
        return rev ? -ans : ans;
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
// @lc code=end