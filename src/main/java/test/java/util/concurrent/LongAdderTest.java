package test.java.util.concurrent;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author C
 * @date 2023/8/7
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(2)
public class LongAdderTest {
    private static AtomicLong atomicLong = new AtomicLong();
    private static LongAdder longAdder = new LongAdder();

    @Benchmark
    @Threads(10)
    public void atomicLongAdd() {
        atomicLong.getAndIncrement();
    }

    @Benchmark
    @Threads(10)
    public void longAdderAdd() {
        longAdder.increment();
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder().include(LongAdderTest.class.getSimpleName()).build();
        new Runner(options).run();
    }
}
