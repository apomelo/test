package test.java.lang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by C on 2020/9/24.
 */
public class MathTest {
    private static final Logger logger = LoggerFactory.getLogger(MathTest.class);

    public static void main(String[] args) {
        testPow();
        testToIntExact();
    }

    private static void testPow() {
        int a = (int) Math.pow(2, 0);
        logger.info("a={}", a);
        int b = (int) Math.pow(2, 0 - 1);
        logger.info("b={}", b);
    }

    private static void testToIntExact() {
        long num = -1L;
//        long num = 1l;
        int res = Math.toIntExact(num);
        logger.info("{}", res);
    }
}
