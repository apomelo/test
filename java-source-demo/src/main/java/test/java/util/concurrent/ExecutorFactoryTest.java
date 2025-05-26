package test.java.util.concurrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by C on 2020/4/23.
 */
public class ExecutorFactoryTest {
    private static final Logger logger = LoggerFactory.getLogger(ExecutorFactoryTest.class);
    private static ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = ExecutorFactory.getScheduledThreadPoolExecutor();
    public static void main(String[] args) throws InterruptedException {
        ScheduledFuture<?> scheduledFuture = scheduledThreadPoolExecutor.scheduleAtFixedRate(() -> {
            logger.info("{}", 1);
        }, 0, 3, TimeUnit.SECONDS);
        TimeUnit.SECONDS.sleep(10);
        logger.info("begin cancel scheduled");
        logger.info("{}", scheduledThreadPoolExecutor.getQueue().size());
        scheduledFuture.cancel(false);
        logger.info("{}", scheduledThreadPoolExecutor.getQueue().size());
        TimeUnit.SECONDS.sleep(10);
        logger.info("{}", scheduledThreadPoolExecutor.getQueue().size());
        logger.info("cancel scheduled success");
        scheduledThreadPoolExecutor.shutdown();
    }
}
