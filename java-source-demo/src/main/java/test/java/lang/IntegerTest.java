package test.java.lang;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by C on 2019/9/16.
 */
@Slf4j
public class IntegerTest {

    public static void main(String[] args) {
        parseInteger();
        getChars();
        rotateLeft();
        testInstance();
        testDivide();
        testParse();
        testVariable();
        testBaseConversion();
        testDisplacement();
        testConversionToLong();
    }

    private static void parseInteger() {
        log.info("---------- parseInteger begin ----------");
        log.info("parseInt: {}", Integer.parseInt("2147483647", 10));
        try {
            log.info("parseInt: {}", Integer.parseInt("2147483648", 10));
        } catch (Exception e) {
            log.info("exception: ", e);
        }
        log.info("---------- parseInteger end ----------");
    }

    private static void getChars() {
        log.info("---------- getChars begin ----------");
        int i = 65536;
        // 等于 i / 10 (i是小于65536的整数)
        // 原因 2^19 = 524288, 52429 / 524288 = 0.1
        // 使用位移运算效率比直接除高
        // 选19次方的目的是在不超出整形范围内，精度达到最高
        int q = (i * 52429) >>> (16+3);
        log.info("q: {}", q);
        int q1 = i * 52429;
        log.info("q1: {}", q1);
        int q2 = q1 >>> (16+3);
        log.info("q2: {}", q2);
        int q3 = q1 >> (16+3);
        log.info("q3: {}", q3);
        log.info("---------- getChars end ----------");
    }

    private static void rotateLeft() {
        log.info("---------- rotateLeft begin ----------");
        int i = 0b11001100110011001100110011001100;
        int distance = 2;

        int g = i << distance;
        int h = i >>> -distance;
        log.info("g: {}", Integer.toBinaryString(g));
        log.info("h: {}", Integer.toBinaryString(h));
        int j = g | h;
        log.info("j: {}", Integer.toBinaryString(j));
        log.info("---------- rotateLeft end ----------");
    }

    private static void testInstance() {
        log.info("---------- testInstance begin ----------");
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
        log.info("---------- testInstance end ----------");
    }

    private static void testDivide() {
        log.info("---------- testDivide begin ----------");
        int a = 3 / 2;
        log.info("a={}", a);
        long day = System.currentTimeMillis() / 1000 / (24 * 60 * 60);
        log.info("day={}", day);
        log.info("---------- testDivide end ----------");
    }

    private static void testParse() {
        log.info("---------- testParse begin ----------");
        String s = "2000000000";
        int i = Integer.parseInt(s);
        log.info("{}", i);
        log.info("---------- testParse end ----------");
    }

    private static void testVariable() {
        log.info("---------- testVariable begin ----------");
        testVariable2(1, 2, 3);
        testVariable2();
        log.info("---------- testVariable end ----------");
    }

    private static void testVariable2(int... i) {
        int[] j = i;
        log.info("{}", j);
    }

    private static void testBaseConversion() {
        log.info("---------- testBaseConversion begin ----------");
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
        log.info("---------- testBaseConversion end ----------");
    }

    private static void testDisplacement() {
        log.info("---------- testDisplacement begin ----------");
        // 0x40000000 == 1073741824
//        int a = 0x40000000;
        int a = 1073741824;
        int b = (a + a)  /  2;
        int c = (a + a) >>> 1;
        log.info("a:{}", a);
        log.info("b:{}", b);
        log.info("c:{}", c);
        // 0xc0000000 == -1073741824
//        int d = 0xc0000000;
        int d = -1073741824;
        int e = (d + d)  /  2;
        int f = (d + d) >>> 1;
        log.info("d:{}", d);
        log.info("e:{}", e);
        log.info("f:{}", f);
        log.info("---------- testDisplacement end ----------");
    }

    private static void testConversionToLong() {
        log.info("---------- testConversionToLong begin ----------");
        int a = 100_000_000;
        int b1 = 10_000;
        long b2 = 10_000L;
        int c1 = a * b1 / b1;
        int c2 = (int) ((long) a * b1 / b1);
        int c3 = (int) (((long) a) * b1 / b1);
        int c4 = (int) ((long) (a * b1) / b1);
        int c5 = (int) (a * b2 / b1);
        int c6 = (int) (a * b1 / b2);
        log.info("c1:{}", c1);
        log.info("c2:{}", c2);
        log.info("c3:{}", c3);
        log.info("c4:{}", c4);
        log.info("c5:{}", c5);
        log.info("c6:{}", c6);
        log.info("---------- testConversionToLong end ----------");
    }
}
