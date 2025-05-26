package test.java.lang;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class TreadAliveTest {

    public static void main(String[] args) throws InterruptedException {
        MyThread myThread = new MyThread();
        myThread.start();
        MyDemonThread myDemonThread = new MyDemonThread();
        myDemonThread.setThread(myThread);
        myDemonThread.setDaemon(true);
        myDemonThread.start();
        TimeUnit.SECONDS.sleep(1);
        for (int i = 0; i < 10; i++) {
            log.info("myThread: {}", myDemonThread.thread);
            log.info("myThread isAlive: {}", myDemonThread.thread.isAlive());
            TimeUnit.SECONDS.sleep(3);
        }
    }
    
    
    private static class MyThread extends Thread {
        @Override
        public void run() {
            int i = 0;
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                log.info("这是主线程第 {} 次打印", (++i));
                try {
                    if (i > 1) {
                        throw new OutOfMemoryError("123");
                    }
                } catch (OutOfMemoryError e) {
                    log.error("error:", e);
                    // 模拟线程崩溃退出
                    return;
                }
            }
        }
    }
    
    private static class MyDemonThread extends Thread {
        private Thread thread;

        public void setThread(Thread thread) {
            this.thread = thread;
        }
        
        @Override
        public void run() {
            int i = 0;
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(9);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                // 检测线程是否挂了
                if (!thread.isAlive()) {
                    // 重启线程
                    log.info("线程挂了，重启线程");
                    thread = new MyThread();
                    thread.start();
                }
                log.info("这是守护线程第 {} 次检测", (++i));
            }
        }
    }

}
