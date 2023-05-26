package test.base;

import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;

/**
 * @author C
 * @date 2023/5/26
 */
@Slf4j
public class SynchronizedTest {
    public static void main(String[] args) {
        testLockOtherObject();
    }

    private static void testLockOtherObject() {
        int threadNum = 3;
        int loop = 1_000_000;
        A a = new A();

        Set<Thread> threadSet = new HashSet<>();
        for (int i = 0; i < threadNum; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < loop; j++) {
                    a.increaseNum();
                }
            });
            threadSet.add(thread);
            thread.start();
        }
        threadSet.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                log.warn("exception:", e);
            }
        });
        log.info("num:{}", a.num);
    }

    private static class A {
        int num = 0;
        private void increaseNum() {
            Object c = B.getInstance().c;
            synchronized (c) {
                num++;
            }
        }
    }

    private static class B {
        Object c = new Object();
        private B() {}
        private static B getInstance() {
            return Inner.instance;
        }
        private static class Inner {
            private static final B instance = new B();
        }
    }
}
