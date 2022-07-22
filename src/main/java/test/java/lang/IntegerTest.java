package test.java.lang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by C on 2019/9/16.
 */
public class IntegerTest {
    private static final Logger logger = LoggerFactory.getLogger(IntegerTest.class);

    public static void main(String[] args) {
//        parseInteger();
//        getChars();
//        rotateLeft();
//        testInstance();
//        testDivide();
//        testParse();
//        testVariable();
        testBaseConversion();
    }

    private static void parseInteger() {
        logger.info("parseInt: {}", Integer.parseInt("2147483648", 10));
    }

    private static void getChars() {
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

    private static void rotateLeft() {
        int i = 0b11001100;
        int distance = 2;

        int g = i << distance;
        int h = i >>> -distance;
        logger.info("g: {}", g);
        logger.info("h: {}", h);
        int j = g | h;
        logger.info("j: {}", j);
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

    private static void testBaseConversion() {
        int a = 1000;
        String s = Integer.toString(a, Character.MAX_RADIX);
        StringBuilder stringBuilder = new StringBuilder();
        int maxLength = 6;
        int length = s.length();
        if (length <= maxLength) {
            for (int i = 0; i < maxLength - length; i ++) {
                stringBuilder.append("0");
            }
            stringBuilder.append(s);
        } else {
            throw new NumberFormatException("illegal number");
        }
        int b = Integer.parseInt(stringBuilder.toString().toUpperCase(), Character.MAX_RADIX);
        logger.info("a = {}", a);
        logger.info("stringBuilder = {}", stringBuilder.toString().toUpperCase());
        logger.info("b = {}", b);
    }
}
