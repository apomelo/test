package test.java.lang;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 虚拟线程 vs 平台线程 JMH 基准测试
 * <p>
 * 分别测试创建 1000 个虚拟线程和 1000 个平台线程的：
 * 1. 创建 + 执行时间（AverageTime）
 * 2. 内存占用（通过 @AuxCounters 辅助计数器记录）
 *
 * @author C
 * @date 3/31/2026
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@Threads(1)
@State(Scope.Benchmark)
public class VirtualThreadTest {

    private static final int THREAD_COUNT = 10000;

    /**
     * 辅助计数器，用于记录每次迭代的内存占用（单位：KB）
     */
    @AuxCounters(AuxCounters.Type.EVENTS)
    @State(Scope.Thread)
    public static class MemoryCounter {
        /** 线程创建前后的内存差值（KB） */
        public long memoryUsedKB;
    }

    /**
     * 测试创建 1000 个平台线程（Platform Thread）的时间和内存
     * <p>
     * 使用双栅栏保证：所有线程全部启动并存活 → 测量内存 → 再放行线程结束
     */
    @Benchmark
    public void platformThread(MemoryCounter counter) throws InterruptedException {
        Runtime runtime = Runtime.getRuntime();
        // GC 后测量基线内存
        System.gc();
        Thread.sleep(50);
        long beforeMemory = runtime.totalMemory() - runtime.freeMemory();

        // readyLatch: 每个线程启动后 countDown，主线程等待所有线程就绪
        CountDownLatch readyLatch = new CountDownLatch(THREAD_COUNT);
        // gateLatch: 主线程测量完内存后 countDown，放行所有线程继续执行并结束
        CountDownLatch gateLatch = new CountDownLatch(1);

        List<Thread> threads = new ArrayList<>(THREAD_COUNT);
        for (int i = 0; i < THREAD_COUNT; i++) {
            Thread t = new Thread(() -> {
                readyLatch.countDown();
                try {
                    // 阻塞等待主线程放行，保证测量内存时线程全部存活
                    gateLatch.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                // 模拟轻量任务
                long sum = 0;
                for (int j = 0; j < 100; j++) {
                    sum += j;
                }
            });
            threads.add(t);
            t.start();
        }

        // 等待所有线程就绪（全部存活）
        readyLatch.await();

        // 此时 1000 个线程全部存活，测量内存
        long afterMemory = runtime.totalMemory() - runtime.freeMemory();
        counter.memoryUsedKB = Math.max(0, (afterMemory - beforeMemory) / 1024);

        // 放行所有线程，让它们执行任务并结束
        gateLatch.countDown();

        // 等待所有线程结束
        for (Thread t : threads) {
            t.join();
        }
    }

    /**
     * 测试创建 1000 个虚拟线程（Virtual Thread）的时间和内存
     * <p>
     * 使用双栅栏保证：所有线程全部启动并存活 → 测量内存 → 再放行线程结束
     */
    @Benchmark
    public void virtualThread(MemoryCounter counter) throws InterruptedException {
        Runtime runtime = Runtime.getRuntime();
        // GC 后测量基线内存
        System.gc();
        Thread.sleep(50);
        long beforeMemory = runtime.totalMemory() - runtime.freeMemory();

        // readyLatch: 每个线程启动后 countDown，主线程等待所有线程就绪
        CountDownLatch readyLatch = new CountDownLatch(THREAD_COUNT);
        // gateLatch: 主线程测量完内存后 countDown，放行所有线程继续执行并结束
        CountDownLatch gateLatch = new CountDownLatch(1);

        List<Thread> threads = new ArrayList<>(THREAD_COUNT);
        for (int i = 0; i < THREAD_COUNT; i++) {
            Thread t = Thread.ofVirtual().unstarted(() -> {
                readyLatch.countDown();
                try {
                    // 阻塞等待主线程放行，保证测量内存时线程全部存活
                    gateLatch.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                // 模拟轻量任务
                long sum = 0;
                for (int j = 0; j < 100; j++) {
                    sum += j;
                }
            });
            threads.add(t);
            t.start();
        }

        // 等待所有线程就绪（全部存活）
        readyLatch.await();

        // 此时 1000 个线程全部存活，测量内存
        long afterMemory = runtime.totalMemory() - runtime.freeMemory();
        counter.memoryUsedKB = Math.max(0, (afterMemory - beforeMemory) / 1024);

        // 放行所有线程，让它们执行任务并结束
        gateLatch.countDown();

        // 等待所有线程结束
        for (Thread t : threads) {
            t.join();
        }
    }

    /**
     * 测试创建 1000 个平台线程（仅创建不启动）的纯创建开销
     */
    @Benchmark
    public List<Thread> platformThreadCreateOnly() {
        List<Thread> threads = new ArrayList<>(THREAD_COUNT);
        for (int i = 0; i < THREAD_COUNT; i++) {
            Thread t = new Thread(() -> {
            });
            threads.add(t);
        }
        return threads;
    }

    /**
     * 测试创建 1000 个虚拟线程（仅创建不启动）的纯创建开销
     */
    @Benchmark
    public List<Thread> virtualThreadCreateOnly() {
        List<Thread> threads = new ArrayList<>(THREAD_COUNT);
        for (int i = 0; i < THREAD_COUNT; i++) {
            Thread t = Thread.ofVirtual().unstarted(() -> {
            });
            threads.add(t);
        }
        return threads;
    }

    /**
     * 测试创建并启动 1000 个平台线程的开销
     * <p>
     * 使用双栅栏保证：所有线程全部启动并存活 → 再放行线程结束
     */
    @Benchmark
    public List<Thread> platformThreadCreateAndStart() throws InterruptedException {
        CountDownLatch readyLatch = new CountDownLatch(THREAD_COUNT);
        CountDownLatch gateLatch = new CountDownLatch(1);

        List<Thread> threads = new ArrayList<>(THREAD_COUNT);
        for (int i = 0; i < THREAD_COUNT; i++) {
            Thread t = new Thread(() -> {
                readyLatch.countDown();
                try {
                    gateLatch.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            t.start();
            threads.add(t);
        }

        // 等待所有线程就绪（全部存活）
        readyLatch.await();
        // 放行所有线程结束
        gateLatch.countDown();

        for (Thread t : threads) {
            t.join();
        }
        return threads;
    }

    /**
     * 测试创建并启动 1000 个虚拟线程的开销
     * <p>
     * 使用双栅栏保证：所有线程全部启动并存活 → 再放行线程结束
     */
    @Benchmark
    public List<Thread> virtualThreadCreateAndStart() throws InterruptedException {
        CountDownLatch readyLatch = new CountDownLatch(THREAD_COUNT);
        CountDownLatch gateLatch = new CountDownLatch(1);

        List<Thread> threads = new ArrayList<>(THREAD_COUNT);
        for (int i = 0; i < THREAD_COUNT; i++) {
            Thread t = Thread.ofVirtual().unstarted(() -> {
                readyLatch.countDown();
                try {
                    gateLatch.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            t.start();
            threads.add(t);
        }

        // 等待所有线程就绪（全部存活）
        readyLatch.await();
        // 放行所有线程结束
        gateLatch.countDown();

        for (Thread t : threads) {
            t.join();
        }
        return threads;
    }

    public static void main(String[] args) throws Exception {
        Options opts = new OptionsBuilder()
                .include(VirtualThreadTest.class.getSimpleName())
                .resultFormat(ResultFormatType.JSON)
                .build();
        new Runner(opts).run();
    }
}
