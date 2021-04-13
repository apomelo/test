package test.base.andornor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by C on 2019/7/8.
 */
public class Test {
    private static final Logger logger = LoggerFactory.getLogger(Test.class);
    public static void main(String[] args) {
        int i = 1;
        int j = 1;
        int k = 1;
        if (i != j && k / 0 == 1) {
            logger.info("true");
        } else {
            logger.info("false");
        }

        while (i != j && k / 0 == 1) {
            logger.info("true");
        }
    }
}
