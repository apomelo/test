package test.java.lang;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by C on 2019/9/16.
 */
@Slf4j
public class IntegerTest {

    public static void main(String[] args) {
//        parseInteger();
//        getChars();
//        rotateLeft();
//        testInstance();
//        testDivide();
//        testParse();
//        testVariable();
//        testBaseConversion();
        testDisplacement();
    }

    private static void parseInteger() {
        log.info("parseInt: {}", Integer.parseInt("2147483648", 10));
    }

    private static void getChars() {
        int i = 65536;
        int q = (i * 52429) >>> (16+3);
        log.info("q: {}", q);
        int q1 = i * 52429;
        log.info("q1: {}", q1);
        int q2 = q1 >>> (16+3);
        log.info("q2: {}", q2);
        int q3 = q1 >> (16+3);
        log.info("q3: {}", q3);
    }

    private static void rotateLeft() {
        int i = 0b11001100;
        int distance = 2;

        int g = i << distance;
        int h = i >>> -distance;
        log.info("g: {}", g);
        log.info("h: {}", h);
        int j = g | h;
        log.info("j: {}", j);
    }

    private static void testInstance() {
        int[] a = new int[0];
        Object b = a;
//        if (b instanceof int) {
//            log.info("int");
//        }
        if (b instanceof Integer) {
            log.info("Integer");
        }
        if (b instanceof int[]) {
            log.info("int[]");
        }
        if (b instanceof Integer[]) {
            log.info("Integer[]");
        }

        int c = 1;
        Object d = c;
//        if (d instanceof int) {
//            log.info("int");
//        }
        if (d instanceof Integer) {
            log.info("Integer");
        }
        if (d instanceof int[]) {
            log.info("int[]");
        }
        if (d instanceof Integer[]) {
            log.info("Integer[]");
        }
    }

    private static void testDivide() {
        int a = 3 / 2;
        log.info("a={}", a);
        long day = System.currentTimeMillis() / 1000 / (24 * 60 * 60);
        log.info("day={}", day);
    }

    private static void testParse() {
        String s = "2000000000";
        int i = Integer.parseInt(s);
        log.info("{}", i);
    }

    private static void testVariable() {
        testVariable2(1, 2, 3);
        testVariable2();
    }

    private static void testVariable2(int... i) {
        int[] j = i;
        log.info("{}", j);
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
        log.info("a = {}", a);
        log.info("stringBuilder = {}", stringBuilder.toString().toUpperCase());
        log.info("b = {}", b);
    }

    private static void testDisplacement() {
//        int a = 0x40000000;
        int a = 1073741824;
        int b = (a + a)  /  2;
        int c = (a + a) >>> 1;
        log.info("a:{}", a);
        log.info("b:{}", b);
        log.info("c:{}", c);
//        int d = 0xc0000000;
        int d = -1073741824;
        int e = (d + d)  /  2;
        int f = (d + d) >>> 1;
        log.info("d:{}", d);
        log.info("e:{}", e);
        log.info("f:{}", f);
    }
}
