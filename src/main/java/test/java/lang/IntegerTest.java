package test.java.lang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by C on 2019/9/16.
 */
public class IntegerTest {
    private static final Logger logger = LoggerFactory.getLogger(IntegerTest.class);

    public static void main(String[] args) {
        testSrc();
//        testInstance();
//        testDivide();
//        testParse();
//        testVariable();
    }

    private static void testSrc() {
        int i = 65536;
        int q = (i * 52429) >>> (16+3);
        logger.info("q: {}", q);
        int q1 = i * 52429;
        logger.info("q1: {}", q1);
        int q2 = q1 >>> (16+3);
        logger.info("q2: {}", q2);
        int q3 = q1 >> (16+3);
        logger.info("q3: {}", q3);
    }

    private static void testInstance() {
        int[] a = new int[0];
        Object b = a;
//        if (b instanceof int) {
//            logger.info("int");
//        }
        if (b instanceof Integer) {
            logger.info("Integer");
        }
        if (b instanceof int[]) {
            logger.info("int[]");
        }
        if (b instanceof Integer[]) {
            logger.info("Integer[]");
        }

        int c = 1;
        Object d = c;
//        if (d instanceof int) {
//            logger.info("int");
//        }
        if (d instanceof Integer) {
            logger.info("Integer");
        }
        if (d instanceof int[]) {
            logger.info("int[]");
        }
        if (d instanceof Integer[]) {
            logger.info("Integer[]");
        }
    }

    private static void testDivide() {
        int a = 3 / 2;
        logger.info("a={}", a);
        long day = System.currentTimeMillis() / 1000 / (24 * 60 * 60);
        logger.info("day={}", day);
    }

    private static void testParse() {
        String s = "2000000000";
        int i = Integer.parseInt(s);
        logger.info("{}", i);
    }

    private static void testVariable() {
        testVariable2(1, 2, 3);
        testVariable2();
    }

    private static void testVariable2(int... i) {
        int[] j = i;
        logger.info("{}", j);
    }
}
