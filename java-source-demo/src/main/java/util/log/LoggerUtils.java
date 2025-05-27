package util.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author C
 * @date 2022/10/22
 */
public class LoggerUtils {
    private static final Logger logger = LoggerFactory.getLogger(LoggerUtils.class);

    public static Logger getLogger() {
        return logger;
    }
}
