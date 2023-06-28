package test.singleinstance;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by C on 2018/11/16.
 */
@Slf4j
public class StaticInner {
    static {
        log.info("init outer static code block...");
    }

    private StaticInner() {}

    private static class Instance {
        static {
            log.info("init inner static code block...");
        }
        private static final StaticInner instance = new StaticInner();
    }

    public static final StaticInner getInstance() {
        log.info("before get instance");
        return Instance.instance;
    }

}
