package test.org.slf4j;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by C on 2019/1/29.
 */
@Slf4j
public class Main {

    public static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("1234");
        logger.debug("123");
        log.warn("12");
    }
}
