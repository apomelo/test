package test.java.util;

import com.alibaba.fastjson2.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author C
 * @date 2023/12/12
 */
@Slf4j
public class CollectionsTest {
    public static void main(String[] args) {
        testSynchronizedSortedSet();
        testReducing();
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
        log.info("stateSortedSet1 to json: {}", JSON.toJSONString(stateSortedSet1));

        Set<State> stateSortedSet2 = Collections.synchronizedSortedSet(new TreeSet<>((a, b) -> {
            if (a.getPriority() != b.getPriority()) {
                return a.getPriority() - b.getPriority();
            } else {
                return 1;   // 这句表示不去重优先级相等时后加入的排序在后面
//                return -1;  // 这句表示不去重优先级相等时后加入的排序在前面
//                return 0;  // 这句表示去重优先级相等时保留第一个
            }
        }));
        stateSortedSet2.add(new State(1, 1));
        stateSortedSet2.add(new State(1, 1));
        stateSortedSet2.add(new State(2, 2));
        stateSortedSet2.add(new State(3, 2));
        stateSortedSet2.add(new State(2, 3));
        log.info("stateSortedSet2: {}", stateSortedSet2);
        log.info("stateSortedSet2 to json: {}", JSON.toJSONString(stateSortedSet2));
        log.info("---------- testSynchronizedSortedSet end ----------");
    }

    /**
     * 核心作用
     * Collectors.reducing 主要用于以下场景：
     * 聚合操作：将流中的元素合并为一个结果（如求和、求最大值、拼接字符串等）。
     * 自定义归约逻辑：提供更灵活的归约逻辑，尤其是当默认的 sum()、max() 等操作不满足需求时。
     * 处理空流：可以指定一个默认值（身份值），当流为空时返回该默认值。
     *
     * 注意事项
     * 二元操作的结合性：在并行流中，归约操作必须是可结合的（Associative），否则结果可能不一致。
     * 初始值的选择：确保初始值（如求和的 0）不影响最终结果（需符合 op(identity, x) == x）。
     */
    public static void testReducing() {
        testReducing1();
        testReducing2();
        testReducing3();
    }

    /**
     * 求和（基础版本）
     */
    public static void testReducing1() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        Integer sum = numbers.stream()
                .collect(Collectors.reducing(0, (a, b) -> a + b));
        log.info("sum: {}", sum); // 输出 15
    }

    /**
     * 求最大值（带 Optional 的版本）
     */
    public static void testReducing2() {
        List<Integer> numbers = Arrays.asList(10, 5, 20, 15);
        Optional<Integer> max = numbers.stream()
                .collect(Collectors.reducing((a, b) -> a > b ? a : b));
        log.info("max: {}", max.orElse(-1)); // 输出 20
    }

    /**
     * 带映射函数的版本（字符串拼接）
     */
    public static void testReducing3() {
        List<String> words = Arrays.asList("Hello", "World", "Java");
        String concatenated = words.stream()
                .collect(Collectors.reducing(
                        "",                 // 初始值
                        str -> str.toUpperCase(), // 映射函数
                        (a, b) -> a + " " + b    // 合并操作
                ));
        log.info(concatenated); // 输出 " HELLO WORLD JAVA"
    }

    @Data
    @AllArgsConstructor
    static class State {
        int priority;
        int type;
    }
}
