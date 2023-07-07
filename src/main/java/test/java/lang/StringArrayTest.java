package test.java.lang;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * Created by C on 2019/1/15.
 */
@Slf4j
public class StringArrayTest {
    public static void main(String[] args) {
        test1();
        test2();
    }

    public static void test1() {
        String[] strs = {"123", "234", "345"};
        log.info(Arrays.toString(strs));
        strs[1] = "qqq";
        log.info(Arrays.toString(strs));
        String a = "1,";
        String[] as = a.split(",");
        log.info(Arrays.toString(as));
    }

    private static void test2() {
        String strs1[] = new String[] {"s1", "s2", "s3", "s4", "s5"};
        for (String s : strs1) {
            log.info("print string strs1: {}", s);
        }
        String[] strs2 = new String[] {"s1", "s2", "s3", "s4", "s5"};
        for (String s : strs2) {
            log.info("print string strs2: {}", s);
        }
    }
}
