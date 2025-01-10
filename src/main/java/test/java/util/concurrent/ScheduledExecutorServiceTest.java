package test.java.util.concurrent;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author C
 * @date 1/7/2025
 */
@Slf4j
public class ScheduledExecutorServiceTest {
    public static void main(String[] args) {
        test1();
        test2();
    }

    public static void test1() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        ScheduledFuture<?> scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(new TestTask1(), 0, 1, TimeUnit.SECONDS);
        try {
            scheduledFuture.get();
        } catch (Exception e) {
            log.error("Exception in main", e);
        }
    }

    private static class TestTask1 implements Runnable {
        int count = 0;
        int limit = 5;

        @Override
        public void run() {
            log.info("TestTask1 is running");
            if (count++ >= limit) {
                throw new RuntimeException("TestTask exception");
            }
        }
    }

    public static void test2() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        ScheduledFuture<?> scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(new TestTask2(), 0, 1, TimeUnit.SECONDS);
        try {
            scheduledFuture.get();
        } catch (Exception e) {
            log.error("Exception in main", e);
        }
    }

    private static class TestTask2 implements Runnable {
        int count = 0;
        int limit = 5;

        @SneakyThrows
        @Override
        public void run() {
            log.info("TestTask2 is running");
            if (count++ >= limit) {
                throw new Throwable("TestTask exception");
            }
        }
    }
}
