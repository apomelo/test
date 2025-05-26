package test.java.util.concurrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by C on 2019/6/12.
 */
public class ExecutorFactory {
    private static final Logger logger = LoggerFactory.getLogger(ExecutorFactory.class);
    private static ExecutorFactory instance = new ExecutorFactory();
    private static ThreadPoolExecutor threadPoolExecutor;
    private static ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;

    private ExecutorFactory() {
        init();
    }

    public static ExecutorFactory getInstance() {
        return instance;
    }

    public static ThreadPoolExecutor getThreadPoolExecutor() {
        return threadPoolExecutor;
    }

    public static ScheduledThreadPoolExecutor getScheduledThreadPoolExecutor() {
        return scheduledThreadPoolExecutor;
    }

    private void init() {
        threadPoolExecutor = new ThreadPoolExecutor(10, 10000, 60L, TimeUnit.SECONDS,
                new SynchronousQueue<>(),
                (r, executor) -> logger.error("Task " + r.toString() + " rejected from " + executor.toString()));
        scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(10);
    }

    public void shutdown() {
        threadPoolExecutor.shutdown();
        scheduledThreadPoolExecutor.shutdown();
    }
}
