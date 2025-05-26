package test.java.lang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by C on 2019/9/16.
 */
public class ByteTest {
    private static final Logger logger = LoggerFactory.getLogger(ByteTest.class);

    public static void main(String[] args) {
        byte[] a = new byte[0];
        Object b = a;
//        if (b instanceof byte) {
////            logger.info("byte");
////        }
        if (b instanceof Byte) {
            logger.info("Byte");
        }
        if (b instanceof byte[]) {
            logger.info("byte[]");
        }
        if (b instanceof Byte[]) {
            logger.info("Byte[]");
        }
    }
}
