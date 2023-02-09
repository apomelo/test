package test.java.lang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

/**
 * Created by C on 2020/1/3.
 */
public class StringTest {
    private static final Logger logger = LoggerFactory.getLogger(StringTest.class);

    public static void main(String[] args) {
//        test1();
//        test2();
//        test3();
        test4();
    }

    private static void test1() {
        String s1 = new String("一二三".getBytes(), StandardCharsets.UTF_8);
        logger.info("{}", s1.length());
        logger.info("{}", s1.getBytes().length);
        String s2 = "abc";
        logger.info("{}", s2.length());
        logger.info("{}", s2.getBytes().length);
    }

    private static void test2() {
        String s1 = "Giroro";
        logger.info("{}", s1.toLowerCase());
        String s2 = "giroro";
        logger.info("{}", s2.toLowerCase());
        logger.info("{}", s1.toLowerCase().equals(s2));
    }

    /**
     * 字符串 equals 和 == 的速度判断
     */
    private static void test3() {
        String s1 = new String("asdf");
        String s2 = new String("asdf");
        String s3 = s1;

        long count = 1_000_000_000;
        long start1 = System.nanoTime();
        for (long i = 0; i < count; i ++) {
            boolean b = s1 == s2;
        }
        long end1 = System.nanoTime();
        logger.info("time: {}", end1 - start1);

        long start2 = System.nanoTime();
        for (long i = 0; i < count; i ++) {
            boolean b = s1.equals(s2);
        }
        long end2 = System.nanoTime();
        logger.info("time: {}", end2 - start2);

        long start3 = System.nanoTime();
        for (long i = 0; i < count; i ++) {
            boolean b = s1 == s3;
        }
        long end3 = System.nanoTime();
        logger.info("time: {}", end3 - start3);

        long start4 = System.nanoTime();
        for (long i = 0; i < count; i ++) {
            boolean b = s1.equals(s3);
        }
        long end4 = System.nanoTime();
        logger.info("time: {}", end4 - start4);
    }

    private static void test4() {
        String s1 = "";
        String[] split = s1.split("#");
        logger.info("{}", split.length);
        logger.info("{}", split);
    }
}
