package test.java.util;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@Threads(2)
@State(Scope.Thread)
public class ListJmhTest {
    @Benchmark
    public void arrayListAdd() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 100_000; i++) {
            list.add(i);
        }
    }
    @Benchmark
    public void arrayListAddToMid() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 100_000; i++) {
            list.add(list.size() / 2, i);
        }
    }

    @Benchmark
    public void arrayListRemoveMid() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 100_000; i++) {
            list.add(i);
        }
        for (int i = 0; i < 1_000; i++) {
            list.remove(list.size() / 2);
        }
    }

    @Benchmark
    public void linkedListAdd() {
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 0; i < 100_000; i++) {
            list.add(i);
        }
    }

    @Benchmark
    public void linkedListAddToMid() {
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 0; i < 100_000; i++) {
            list.add(list.size() / 2, i);
        }
    }

    @Benchmark
    public void linkedListRemoveMid() {
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 0; i < 100_000; i++) {
            list.add(i);
        }
        for (int i = 0; i < 1_000; i++) {
            list.remove(list.size() / 2);
        }
    }

    public static void main(String[] args) throws Exception {
        Options opts = new OptionsBuilder()
                .include(ListJmhTest.class.getSimpleName())
                .resultFormat(ResultFormatType.JSON)
                .build();
        new Runner(opts).run();
    }
}