package test.java.lang;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

/**
 * Created by C on 2020/1/3.
 */
@Slf4j
public class StringTest {

    public static void main(String[] args) {
        test1();
        test2();
        test3();
        testSplit1();
    }

    private static void test1() {
        String s1 = new String("一二三".getBytes(), StandardCharsets.UTF_8);
        log.info("{}", s1.length());
        log.info("{}", s1.getBytes().length);
        String s2 = "abc";
        log.info("{}", s2.length());
        log.info("{}", s2.getBytes().length);
    }

    private static void test2() {
        String s1 = "Giroro";
        log.info("{}", s1.toLowerCase());
        String s2 = "giroro";
        log.info("{}", s2.toLowerCase());
        log.info("{}", s1.toLowerCase().equals(s2));
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
        log.info("time: {}", end1 - start1);

        long start2 = System.nanoTime();
        for (long i = 0; i < count; i ++) {
            boolean b = s1.equals(s2);
        }
        long end2 = System.nanoTime();
        log.info("time: {}", end2 - start2);

        long start3 = System.nanoTime();
        for (long i = 0; i < count; i ++) {
            boolean b = s1 == s3;
        }
        long end3 = System.nanoTime();
        log.info("time: {}", end3 - start3);

        long start4 = System.nanoTime();
        for (long i = 0; i < count; i ++) {
            boolean b = s1.equals(s3);
        }
        long end4 = System.nanoTime();
        log.info("time: {}", end4 - start4);
    }

    private static void testSplit1() {
        String s1 = "123e";
        String[] split1 = s1.split("e");
        log.info("s1:{}", split1.length);
        log.info("s1:{}", (Object) split1);
        String s2 = "e";
        String[] split2 = s2.split("e");
        log.info("s2:{}", split2.length);
        log.info("s2:{}", (Object) split2);
        String s3 = "";
        String[] split3 = s3.split("e");
        log.info("s3:{}", split3.length);
        log.info("s3:{}", (Object) split3);
        String s4 = "e123";
        String[] split4 = s4.split("e");
        log.info("s4:{}", split4.length);
        log.info("s4:{}", (Object) split4);
    }
}
