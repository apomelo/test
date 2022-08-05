package test.org.objectweb.asm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author C
 * @date 2022/8/2
 */
public class BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

    public static void main(String[] args) {
        Base base = new Base();
        base.process();
    }
}
