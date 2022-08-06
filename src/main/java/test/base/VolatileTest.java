package test.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author C
 * @date 2022/7/30
 */
public class VolatileTest {
    private static final Logger logger = LoggerFactory.getLogger(VolatileTest.class);

    private static boolean flag1 = false;
    private volatile static boolean flag2 = false;
    private static AtomicBoolean flag3 = new AtomicBoolean(false);

    public static void main(String[] args) throws InterruptedException {
        test1();
        test2();
        test3();
    }

    private static void test1() throws InterruptedException {
        new Thread(() -> {
            logger.info("flag1 is waiting...");
            while (!flag1) {
            }
            logger.info("flag1 wait success!!!");
        }).start();

        TimeUnit.SECONDS.sleep(1);

        new Thread(() -> {
            logger.info("flag1 is preparing...");
            flag1 = true;
            logger.info("flag1 prepare success!!!");
        }).start();

        TimeUnit.SECONDS.sleep(1);
    }

    private static void test2() throws InterruptedException {
        new Thread(() -> {
            logger.info("flag2 is waiting...");
            while (!flag2) {
            }
            logger.info("flag2 wait success!!!");
        }).start();

        TimeUnit.SECONDS.sleep(1);

        new Thread(() -> {
            logger.info("flag2 is preparing...");
            flag2 = true;
            logger.info("flag2 prepare success!!!");
        }).start();

        TimeUnit.SECONDS.sleep(1);
    }

    private static void test3() throws InterruptedException {
        new Thread(() -> {
            logger.info("flag3 is waiting...");
            while (!flag3.get()) {
            }
            logger.info("flag3 wait success!!!");
        }).start();

        TimeUnit.SECONDS.sleep(1);

        new Thread(() -> {
            logger.info("flag3 is preparing...");
            flag3.set(true);
            logger.info("flag3 prepare success!!!");
        }).start();

        TimeUnit.SECONDS.sleep(1);
    }
}
