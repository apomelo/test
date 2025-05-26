package test.java.lang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by C on 2019/7/24.
 */
public class BooleanTest {
    private static final Logger logger = LoggerFactory.getLogger(BooleanTest.class);

    public static void main(String[] args) {
        testAnd();
        testOr();
        testXor();
    }

    public static void testAnd() {
        boolean a = true;
        boolean b = false;
        boolean c = true;
        a &= b;
        logger.info("{}", a);

        a = true;
        a &= c;
        logger.info("{}", a);
    }

    public static void testOr() {
        boolean a = true;
        boolean b = false;
        boolean d = false;
        d |= a && b;
        logger.info("{}", d);
    }

    public static void testXor() {
        boolean a = true;
        boolean b = false;

        boolean d = a ^ b;
        logger.info("{}", d);
        boolean e = a ^ a;
        logger.info("{}", e);
        boolean f = b ^ b;
        logger.info("{}", f);
    }
}
