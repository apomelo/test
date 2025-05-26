package test.java.util.concurrent;

import com.alibaba.fastjson2.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @author C
 * @date 2023/11/28
 */
@Slf4j
public class ConcurrentSkipListSetTest {
    public static void main(String[] args) {
        testStreamForeachRemove();
        testSort();
        testSortNCount();
        testSortNCount2();
    }

    public static void testStreamForeachRemove() {
        log.info("---------- testStreamForeachRemove begin ----------");
        ConcurrentSkipListSet<Integer> set1 = new ConcurrentSkipListSet<>();
        set1.add(1);
        set1.add(3);
        set1.add(2);
        set1.add(4);
        set1.add(6);
        set1.add(5);
        log.info("set1: {}", set1);
        set1.stream().filter(k -> k < 5).forEach(v -> {
            if (v < 3) {
                log.info("value: {}", v);
                set1.remove(v);
            }
        });
        log.info("---------- testStreamForeachRemove end ----------");
    }

    /**
     * 测试 {@link java.util.concurrent.ConcurrentSkipListSet} 排序
     */
    public static void testSort() {
        log.info("---------- testSort begin ----------");
        ConcurrentSkipListSet<State> stateSortedSet1 = new ConcurrentSkipListSet<>(Comparator.comparing(State::getPriority));
        stateSortedSet1.add(new State(1, 1));
        stateSortedSet1.add(new State(1, 1));
        stateSortedSet1.add(new State(2, 2));
        stateSortedSet1.add(new State(3, 2));
        stateSortedSet1.add(new State(2, 3));
        log.info("stateSortedSet1: {}", stateSortedSet1);
        log.info("stateSortedSet1: {}", JSON.toJSONString(stateSortedSet1));

        ConcurrentSkipListSet<State> stateSortedSet2 = new ConcurrentSkipListSet<>((a, b) -> {
            if (a.getPriority() != b.getPriority()) {
                return a.getPriority() - b.getPriority();
            } else {
//                return 1;   // 这句表示优先级相等时后加入的排序在后面 (这样可能会导致插入相同优先级节点时成环)
                return -1;  // 这句表示优先级相等时后加入的排序在前面 (这样不会导致插入相同优先级节点时成环)
            }
        });
        stateSortedSet2.add(new State(1, 1));
        stateSortedSet2.add(new State(1, 1));
        stateSortedSet2.add(new State(2, 2));
        stateSortedSet2.add(new State(3, 2));
        stateSortedSet2.add(new State(2, 3));
        stateSortedSet2.add(new State(1, 3));
        log.info("stateSortedSet2: {}", stateSortedSet2);
        log.info("stateSortedSet2: {}", JSON.toJSONString(stateSortedSet2));
        log.info("---------- testSort end ----------");
    }

    /**
     * 测试 {@link java.util.concurrent.ConcurrentSkipListSet} 排序 N 次
     */
    public static void testSortNCount() {
        log.info("---------- testSortNCount begin ----------");
        for (int i = 0; i < 1000; i++) {
            testSort(); // 测试时去掉注释
        }
        log.info("---------- testSortNCount end ----------");
    }

    /**
     * 测试 {@link java.util.concurrent.ConcurrentSkipListSet} 排序 N 次
     */
    public static void testSortNCount2() {
        log.info("---------- testSortNCount2 begin ----------");
        ConcurrentSkipListSet<State> stateSortedSet = new ConcurrentSkipListSet<>((a, b) -> {
            if (a.getPriority() != b.getPriority()) {
                return a.getPriority() - b.getPriority();
            } else {
                return 1;   // 这句表示优先级相等时后加入的排序在后面 (这样可能会导致插入相同优先级节点时成环)
//                return -1;  // 这句表示优先级相等时后加入的排序在前面 (这样不会导致插入相同优先级节点时成环)
            }
        });
        for (int i = 0; i < 1000; i++) {
            stateSortedSet.add(new State(i, i));
        }
        log.info("stateSortedSet: {}", stateSortedSet);
        log.info("stateSortedSet: {}", JSON.toJSONString(stateSortedSet));
        log.info("---------- testSortNCount2 end ----------");
    }


    @Data
    @AllArgsConstructor
    static class State {
        int priority;
        int type;
    }
}
