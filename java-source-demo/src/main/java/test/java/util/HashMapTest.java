package test.java.util;

import com.alibaba.fastjson2.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import util.MapUtil;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

/**
 * Created by C on 2019/12/19.
 */
@Slf4j
public class HashMapTest {
    public static void main(String[] args) {
        testJson();
        testStreamSort1();
        testStreamSort2();
        testStreamMaxAndMin();
        testMerge();
        testKeySetRemove();
        testGroupingBy();
        testReducing();
        testPutAllIfAbsent();
    }

    public static void testJson() {
        log.info("---------- testJson begin ----------");
        Map<Integer, Integer> map = new HashMap<>();
        log.info("map: {}", map);
        log.info("map to json: {}", JSON.toJSONString(map));

        map.put(1, 1);
        map.put(2, 2);
        log.info("map: {}", map);

        log.info("map to json: {}", JSON.toJSONString(map));
        log.info("json to map: {}", JSON.parseObject("{}", HashMap.class));
        log.info("---------- testJson end ----------");
    }

    public static void testStreamSort1() {
        log.info("---------- testStreamSort1 begin ----------");
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 8);
        map.put(2, 7);
        map.put(4, 5);
        map.put(3, 6);
        log.info("map: {}", map);
        Map<Integer, Integer> result = new LinkedHashMap<>();
        map.entrySet().stream().filter(e -> e.getValue() > 5).sorted(Map.Entry.comparingByValue()).forEachOrdered(e -> result.put(e.getKey(), e.getValue()));
        log.info("result: {}", result);
        log.info("result keySet: {}", result.keySet());
        log.info("result values: {}", result.values());
        Map<Integer, Integer> result2 = new LinkedHashMap<>();
        map.entrySet().stream().sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed()).forEachOrdered(e -> result2.put(e.getKey(), e.getValue()));
        log.info("result2: {}", result2);
        List<Integer> result3 = map.entrySet().stream().sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed()).map(Map.Entry::getKey).collect(Collectors.toList());
        log.info("result3: {}", result3);
        List<Integer> result4 = map.entrySet().stream().sorted(Map.Entry.comparingByValue()).map(Map.Entry::getKey).collect(Collectors.toList());
        log.info("result4: {}", result4);
        log.info("---------- testStreamSort1 end ----------");
    }

    public static void testStreamSort2() {
        log.info("---------- testStreamSort2 begin ----------");
        Map<Integer, Integer[]> map = new HashMap<>();
        map.put(0, new Integer[] {0, 0});
        map.put(1, new Integer[] {8, 3});
        map.put(2, new Integer[] {7, 4});
        map.put(3, new Integer[] {6, 1});
        map.put(4, new Integer[] {5, 2});
        log.info("map: {}", map);
        Map<Integer, Integer[]> result = new LinkedHashMap<>();
        map.entrySet().stream().filter(e -> e.getValue()[0] > 0).sorted((e1,e2) -> e1.getValue()[0].compareTo(e2.getValue()[0])).forEachOrdered(e -> result.put(e.getKey(), e.getValue()));
        log.info("result: {}", result);
        LinkedHashMap<Integer, Integer[]> result2 = map.entrySet().stream().filter(e -> e.getValue()[0] > 0).sorted(Comparator.comparing(e -> e.getValue()[0])).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        log.info("result2: {}", result2);
        List<Integer> result3 = map.entrySet().stream().filter(e -> e.getValue()[0] > 0).sorted(Comparator.comparing(e -> e.getValue()[0])).map(e -> e.getKey()).collect(Collectors.toList());
        log.info("result3: {}", result3);
        log.info("---------- testStreamSort2 end ----------");
    }

    public static void testStreamMaxAndMin() {
        log.info("---------- testStreamMax begin ----------");
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 8);
        map.put(2, 7);
        map.put(3, 6);
        map.put(4, 5);
        log.info("map: {}", map);
        int result = map.keySet().stream().max(Integer::compareTo).get();
        log.info("result max: {}", result);
        int key1 = map.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();
        log.info("result max: {}", key1);
        int key2 = map.entrySet().stream().min(Map.Entry.comparingByValue()).get().getKey();
        log.info("result min: {}", key2);
        log.info("---------- testStreamMax end ----------");
    }

    public static void testMerge() {
        log.info("---------- testMerge begin ----------");
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 8);
        map.put(2, 7);
        map.put(3, 6);
        map.put(4, 5);
        log.info("map: {}", map);
        Map<Integer, Integer> result1 = new HashMap<>();
        result1.put(1, 1);
        result1.put(2, 2);
        result1.put(3, 7);
        result1.put(4, 6);
        map.forEach((k, v) -> {
            result1.merge(k, v, Integer::compareTo);
        });
        log.info("result1: {}", result1);
        Map<Integer, Integer> result2 = new HashMap<>();
        result2.put(1, 1);
        result2.put(2, 2);
        result2.put(3, 7);
        result2.put(4, 6);
        map.forEach((k, v) -> {
            result2.merge(k, v, (v1, v2) -> v2 > v1 ? v2 : v1);
        });
        log.info("result2: {}", result2);
        Map<Integer, Integer> result3 = new HashMap<>();
        result3.put(1, 1);
        result3.put(2, 2);
        result3.put(3, 7);
        result3.put(4, 6);
        map.forEach((k, v) -> {
            result3.merge(k, v, (v1, v2) -> {
                log.info("v1={},v2={}", v1, v2);
                return v1 - v2 > 0 ? v1 - v2 : 0;
            });
        });
        log.info("result3: {}", result3);
        log.info("---------- testMerge end ----------");
    }

    public static void testKeySetRemove() {
        log.info("---------- testKeySetRemove begin ----------");
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 8);
        map.put(2, 7);
        map.put(3, 6);
        map.put(4, 6);
        Set<Integer> keySet = map.keySet();
        log.info("keySet: {}", keySet);
        keySet.remove(1);
        log.info("keySet1: {}", keySet);
        log.info("map1: {}", map);
        map.keySet().removeIf(i -> i >= 3);
        log.info("map2: {}", map);
        log.info("---------- testKeySetRemove end ----------");
    }

    public static void testGroupingBy() {
        log.info("---------- testGroupingBy begin ----------");
        List<A> aList = new ArrayList<>();
        aList.add(new A(1, 1, 1));
        aList.add(new A(1, 3, 5));
        aList.add(new A(1, 3, 3));
        aList.add(new A(2, 3, 1));
        aList.add(new A(2, 1, 1));
        aList.add(new A(2, 1, 5));
        log.info("aList: {}", aList);
        Map<Integer, Map<Integer, List<A>>> map = aList.stream().collect(Collectors.groupingBy(A::getType, Collectors.groupingBy(A::getValue)));
        log.info("map: {}", map);
        log.info("---------- testGroupingBy end ----------");
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
     *
     * 参见： {@link test.java.util.CollectionsTest#testReducing()}
     */
    public static void testReducing() {
        log.info("---------- testReducing begin ----------");
        List<A> aList = new ArrayList<>();
        aList.add(new A(1, 1, 1));
        aList.add(new A(1, 3, 5));
        aList.add(new A(1, 3, 3));
        aList.add(new A(2, 3, 1));
        aList.add(new A(2, 1, 1));
        aList.add(new A(2, 1, 5));
        log.info("aList: {}", aList);
        try {
            Map<Integer, Map<Integer, A>> map1 = aList.stream().collect(Collectors.groupingBy(A::getType, Collectors.groupingBy(A::getValue, Collectors.reducing(null, BinaryOperator.minBy(Comparator.comparing(A::getTime))))));
            log.info("map1: {}", map1);
        } catch (Exception e) {
            log.info("e=", e);
        }
        // 在 type,value 相等的情况下，把初始值 A(100, 10, 2) 的time值与存在的值比较，返回较小的对象
        Map<Integer, Map<Integer, A>> map2 = aList.stream().collect(Collectors.groupingBy(A::getType, Collectors.groupingBy(A::getValue, Collectors.reducing(new A(100, 10, 2), BinaryOperator.minBy(Comparator.comparing(A::getTime))))));
        log.info("map2: {}", map2);
        // 在 type,value 相等的情况下，返回 time 较小的对象
        Map<Integer, Map<Integer, Optional<A>>> map3 = aList.stream().collect(Collectors.groupingBy(A::getType, Collectors.groupingBy(A::getValue, Collectors.reducing(BinaryOperator.minBy(Comparator.comparing(A::getTime))))));
        log.info("map3: {}", map3);
        // 在 type,value 相等的情况下，返回 time 较小的对象
        Map<Integer, Map<Integer, Optional<A>>> map4 = aList.stream().collect(Collectors.groupingBy(A::getType, Collectors.groupingBy(A::getValue, Collectors.minBy(Comparator.comparing(A::getTime)))));
        log.info("map4: {}", map4);
        log.info("---------- testReducing end ----------");
    }

    public static void testPutAllIfAbsent() {
        log.info("---------- testPutAllIfAbsent begin ----------");
        Map<Integer, Integer> target = new HashMap<>();
        Map<Integer, Integer> src1 = new HashMap<>();
        src1.put(1, 11);
        src1.put(2, 21);
        src1.put(3, 31);
        src1.put(4, 41);
        Map<Integer, Integer> src2 = new HashMap<>();
        src2.put(3, 32);
        src2.put(4, 42);
        src2.put(5, 52);
        src2.put(6, 62);
        MapUtil.putAllIfAbsent(target, src1);
        MapUtil.putAllIfAbsent(target, src2);
        log.info("target: {}", target);
        log.info("---------- testPutAllIfAbsent end ----------");
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class A {
        private int type;
        private int value;
        private int time;
    }
}
