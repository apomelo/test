package test.java.lang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Created by C on 2020/1/3.
 */
public class StringTest {
    private static final Logger logger = LoggerFactory.getLogger(StringTest.class);

    public static void main(String[] args) {
//        test1();
        test2();
    }

    public static void test1() {
        String s1 = new String("一二三".getBytes(), StandardCharsets.UTF_8);
        logger.info("{}", s1.length());
        logger.info("{}", s1.getBytes().length);
        String s2 = "abc";
        logger.info("{}", s2.length());
        logger.info("{}", s2.getBytes().length);
    }

    public static void test2() {
        String s1 = "Giroro";
        logger.info("{}", s1.toLowerCase());
        String s2 = "giroro";
        logger.info("{}", s2.toLowerCase());
        logger.info("{}", s1.toLowerCase().equals(s2));
    }
}
