package util.num;

import lombok.extern.slf4j.Slf4j;

/**
 * 运算工具类
 */
@Slf4j
public class MathUtil {

    /**
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

    public static long multiply(long x, long y) {
        long r = x * y;
        long ax = Math.abs(x);
        long ay = Math.abs(y);
        if (((ax | ay) >>> 31 != 0)) {
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
