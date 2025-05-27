package test.java.lang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by C on 2018/9/25.
 */
public class ExceptionTest {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionTest.class);

    public static void main(String[] args) {
        test1();
        test2();
        test3();
    }

    private static void test1() {
        int i = 1;
        try {
            i = i / 0;
        } catch (Exception e) {
//            System.out.println("1111111, " + e.getMessage());
//            System.out.println("1111111, " + e.getCause().getMessage());
//            System.out.println("1111111, " + e.getCause());
            logger.warn("1111111, e=", e);
        }
    }

    private static void test2() {
        int i = 1;
        try {
            logger.info("1111111");
            throw new NullPointerException("EEE");
        } catch (Exception e) {
            logger.warn("2222222, e=", e);
        } finally {
            logger.error("3333333");
        }
    }

    private static void test3() {
        int i = 1;
        try {
            i = i * 0;
        } finally {
            logger.warn("222222");
        }
    }
}
