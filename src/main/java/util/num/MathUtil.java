package util.num;

import lombok.extern.slf4j.Slf4j;

/**
 * 运算工具类
 */
@Slf4j
public class MathUtil {



    /**
     * 整个代码表面上是在对整个int做处理，实际有用的只有x y r的符号位而已。(0：正号 1：负号）下面列出x y r的符号位的真值表：
     * x   y   r   (x ^ r) & (y ^ r)
     * 0   0   0   0
     * 0   0   1   1
     * 0   1   0   0
     * 0   1   1   0
     * 1   0   0   0
     * 1   0   1   0
     * 1   1   0   1
     * 1   1   1   0
     * 根据真值表反推，溢出只有“两个负数相加变成正数“和”两个正数相加变成负数“两种情况。考虑值域后确实如此。
     *
     * x+y
     *
     * @param x 加数
     * @param y 加数
     * @return 和
     */
    public static int add(int x, int y) {
        int r = x + y;
        // HD 2-12 Overflow iff both arguments have the opposite sign of the result
        if (((x ^ r) & (y ^ r)) < 0) {
            r = Integer.MAX_VALUE;
            log.error("【{}】+【{}】integer overflow", x, y);
        }
        return r;
    }

    /**
     * x+y+z
     *
     * @param x 加数
     * @param y 加数
     * @param z 加数
     * @return 和
     */
    public static int add(int x, int y, int z) {
        int r = x + y + z;
        if (((x ^ r) & (y ^ r) & (x ^ r)) < 0) {
            r = Integer.MAX_VALUE;
            log.error("【{}】+【{}】integer overflow", x, y);
        }
        return r;
    }

    /**
     * x+y
     *
     * @param x 加数
     * @param y 加数
     * @return 和
     */
    public static long add(long x, long y) {
        long r = x + y;
        // HD 2-12 Overflow iff both arguments have the opposite sign of the result
        if (((x ^ r) & (y ^ r)) < 0) {
            r = Long.MAX_VALUE;
            log.error("【{}】+【{}】long overflow", x, y);
        }
        return r;
    }

    /**
     * x+y+z
     *
     * @param x 加数
     * @param y 加数
     * @param z 加数
     * @return 和
     */
    public static long add(long x, long y, long z) {
        long r = x + y + z;
        if (((x ^ r) & (y ^ r) & (x ^ r)) < 0) {
            r = Long.MAX_VALUE;
            log.error("【{}】+【{}】long overflow", x, y);
        }
        return r;
    }

    /**
     * 溢出只有“正数减负数变成负数“和”负数减正数变成正数“两种情况
     */
    public static int subtract(int x, int y) {
        int r = x - y;
        // HD 2-12 Overflow iff the arguments have different signs and
        // the sign of the result is different than the sign of x
        if (((x ^ y) & (x ^ r)) < 0) {
            r = 0;
            log.error("【{}】-【{}】integer overflow", x, y);
        }
        return r;
    }

    public static long subtract(long x, long y) {
        long r = x - y;
        // HD 2-12 Overflow iff the arguments have different signs and
        // the sign of the result is different than the sign of x
        if (((x ^ y) & (x ^ r)) < 0) {
            r = 0;
            log.error("【{}】-【{}】long overflow", x, y);
        }
        return r;
    }

    public static int multiply(int x, int y) {
        long r = (long) x * (long) y;
        if ((int) r != r) {
            r = Integer.MAX_VALUE;
            log.error("【{}】x【{}】integer overflow", x, y);
        }
        return (int) r;
    }

    /**
     * 首先用((ax | ay) >>> 31 != 0)做了一个判断，等价于ax>2^31 || ay > 2^31
     * 做这个判断一方面是为了排除不会溢出的情况，另一方面也暗示接下来的步骤比较费时
     * 接下来直接用除法做逆运算
     * 考虑额外情况:
     * 1. 除数等于 0
     * 2. x == Long.MIN_VALUE && y == -1
     *      由于溢出，这个式子相乘的结果是Long.MAXVALUE+1，也就是Long.MINVALUE。
     *      但是，除法也溢出成了Long.MAXVALUE+1，还是Long.MINVALUE，导致变成了需要额外判断的特例。
     */
    public static long multiply(long x, long y) {
        long r = x * y;
        long ax = Math.abs(x);
        long ay = Math.abs(y);
        if (((ax | ay) >>> 31 != 0)) {
            // 用除法做了逆运算
            // Some bits greater than 2^31 that might cause overflow
            // Check the result using the divide operator
            // and check for the special case of Long.MIN_VALUE * -1
            if (((y != 0) && (r / y != x)) ||
                    (x == Long.MIN_VALUE && y == -1)) {
                r = Long.MAX_VALUE;
                log.error("【{}】x【{}】long overflow", x, y);
            }
        }
        return r;
    }

    public static int toInt(long value) {
        if ((int) value != value) {
            value = Integer.MAX_VALUE;
            log.error("【{}】integer overflow", value);
        }
        return (int) value;
    }
}
