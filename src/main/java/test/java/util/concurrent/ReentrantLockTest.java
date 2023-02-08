package test.java.util.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by C on 2018/12/3.
 */
@Slf4j
public class ReentrantLockTest {

    private int id;
    private ReentrantLock lock = new ReentrantLock();
    private ReentrantLockTest(int id) {
        this.id = id;
    }

    public static void main(String[] args) {
        testLock();
    }

    /**
     * 测试同一个 class 的不同实例中实例变量加锁情况
     * 结论：不同实例中的锁对象不是同一个
     */
    private static void testLock() {
        ReentrantLockTest reentrantLockTest1 = new ReentrantLockTest(1);
        Thread thread1 = new Thread(reentrantLockTest1::testA);
        thread1.start();
        Thread thread2 = new Thread(reentrantLockTest1::testB);
        thread2.start();

        ReentrantLockTest reentrantLockTest2 = new ReentrantLockTest(2);
        Thread thread3 = new Thread(reentrantLockTest2::testA);
        thread3.start();
        Thread thread4 = new Thread(reentrantLockTest2::testB);
        thread4.start();
    }

    private void testA() {
        lock.lock();
        for (int i = 0; i < 10; i ++) {
            log.info("This is {} time invoke in testA, instanceId:{}", i, this.id);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        lock.unlock();
    }

    private void testB() {
        lock.lock();
        for (int i = 0; i < 10; i ++) {
            log.info("This is {} time invoke in testB, instanceId:{}", i, this.id);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        lock.unlock();
    }
}
