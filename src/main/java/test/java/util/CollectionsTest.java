package test.java.util;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * @author C
 * @date 2023/12/12
 */
@Slf4j
public class CollectionsTest {
    public static void main(String[] args) {
        testSynchronizedSortedSet();
    }


    /**
     * 测试 {@link java.util.Collections#synchronizedSortedSet(SortedSet)}
     */
    public static void testSynchronizedSortedSet() {
        log.info("---------- testSynchronizedSortedSet begin ----------");
        Set<State> stateSortedSet1 = Collections.synchronizedSortedSet(new TreeSet<>(Comparator.comparing(State::getPriority)));
        stateSortedSet1.add(new State(1, 1));
        stateSortedSet1.add(new State(1, 1));
        stateSortedSet1.add(new State(2, 2));
        stateSortedSet1.add(new State(3, 2));
        stateSortedSet1.add(new State(2, 3));
        log.info("stateSortedSet1: {}", stateSortedSet1);
        log.info("stateSortedSet1: {}", JSON.toJSONString(stateSortedSet1));

        Set<State> stateSortedSet2 = Collections.synchronizedSortedSet(new TreeSet<>((a, b) -> {
            if (a.getPriority() != b.getPriority()) {
                return a.getPriority() - b.getPriority();
            } else {
                return 1;   // 这句表示优先级相等时后加入的排序在后面
//                return -1;  // 这句表示优先级相等时后加入的排序在前面
            }
        }));
        stateSortedSet2.add(new State(1, 1));
        stateSortedSet2.add(new State(1, 1));
        stateSortedSet2.add(new State(2, 2));
        stateSortedSet2.add(new State(3, 2));
        stateSortedSet2.add(new State(2, 3));
        log.info("stateSortedSet2: {}", stateSortedSet2);
        log.info("stateSortedSet2: {}", JSON.toJSONString(stateSortedSet2));
        log.info("---------- testSynchronizedSortedSet end ----------");
    }

    @Data
    @AllArgsConstructor
    static class State {
        int priority;
        int type;
    }
}
