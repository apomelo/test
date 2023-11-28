package test.java.util.concurrent;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import test.java.util.CollectionsTest;

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
        Set<State> stateSortedSet1 = new ConcurrentSkipListSet<>(Comparator.comparing(State::getPriority));
        stateSortedSet1.add(new State(1, 1));
        stateSortedSet1.add(new State(1, 1));
        stateSortedSet1.add(new State(2, 2));
        stateSortedSet1.add(new State(3, 2));
        stateSortedSet1.add(new State(2, 3));
        log.info("stateSortedSet1: {}", stateSortedSet1);
        log.info("stateSortedSet1: {}", JSON.toJSONString(stateSortedSet1));

        Set<State> stateSortedSet2 = new ConcurrentSkipListSet<>((a, b) -> {
            if (a.getPriority() != b.getPriority()) {
                return a.getPriority() - b.getPriority();
            } else {
//                return 1;   // 这句表示优先级相等时后加入的排序在后面
                return -1;  // 这句表示优先级相等时后加入的排序在前面
            }
        });
        stateSortedSet2.add(new State(1, 1));
        stateSortedSet2.add(new State(1, 1));
        stateSortedSet2.add(new State(2, 2));
        stateSortedSet2.add(new State(3, 2));
        stateSortedSet2.add(new State(2, 3));
        log.info("stateSortedSet2: {}", stateSortedSet2);
        log.info("stateSortedSet2: {}", JSON.toJSONString(stateSortedSet2));
        log.info("---------- testSort end ----------");
    }


    @Data
    @AllArgsConstructor
    static class State {
        int priority;
        int type;
    }
}
