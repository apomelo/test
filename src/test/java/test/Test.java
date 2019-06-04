package test;

import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Test {

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            @Override
            public void run()
            {

                System.out.println("Interrupting threads");
                Set<Thread> runningThreads = Thread.getAllStackTraces().keySet();
                for (Thread runningThread : runningThreads) {
                    // 判断线程是否是我们要测试的线程
                    if (runningThread.getName().equals("InteruptThread")) {
                        // 如果线程没有被中断，我们进行中断他
                        if (!runningThread.isInterrupted()) {
                            System.out.println("InteruptThread is not interrupted, we are going to interupt it");
                            runningThread.interrupt();
                        }
                    }
                }

                System.out.println("Shutdown hook ran!");
            }
        });

        // 创建并启动子线程类
        InteruptThread thread = new InteruptThread();
        thread.start();
    }

    /**
     * 用来测试要中断的线程类
     */
    static class InteruptThread extends Thread {

        public InteruptThread() {
            this.setName("InteruptThread");
        }

        @Override
        public void run() {
//            while (true) {
                try {
//                    while (true) {
                        // 打印个时间
                        System.out.println(System.currentTimeMillis());
                        // 等待10秒
                        TimeUnit.SECONDS.sleep(10);
//                    }
                } catch (InterruptedException e) {
                    System.out.println("oh yeah, InteruptThread is interrupted");
//                    break;
                } catch (Exception e) {
                    System.out.println("unknown test.exception ocurred");
//                    break;
                }

//            }

        }
    }

}