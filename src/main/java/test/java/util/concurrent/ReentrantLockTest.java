package test.java.util.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by C on 2018/12/3.
 */
public class ReentrantLockTest {

    private ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        ReentrantLockTest reentrantLockTest1 = new ReentrantLockTest();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                reentrantLockTest1.testA();
            }
        });
        thread1.start();
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                reentrantLockTest1.testB();
            }
        });
        thread2.start();

        ReentrantLockTest reentrantLockTest2 = new ReentrantLockTest();
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                reentrantLockTest2.testA();
            }
        });
        thread3.start();
        Thread thread4 = new Thread(new Runnable() {
            @Override
            public void run() {
                reentrantLockTest2.testB();
            }
        });
        thread4.start();
    }

    private void testA() {
        lock.lock();
        for (int i = 0; i < 10; i ++) {
            System.out.println("This is " + i + " time invoke in testA");
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
            System.out.println("This is " + i + " time invoke in testB");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        lock.unlock();
    }
}
