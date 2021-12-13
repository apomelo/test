package test.java.util;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

/**
 * Created by C on 2019/12/19.
 */
public class HashMapTest {
    private static final Logger logger = LoggerFactory.getLogger(HashMapTest.class);
    public static void main(String[] args) {
//        test();
        testStreamSort();
//        testStreamMax();
//        testMerge();
//        testKeySetRemove();
//        testGroupingBy();
//        testReducing();
    }

    public static void test() {
        Map<Integer, Integer> map = new HashMap<>();
        logger.info("map: {}", map);
        logger.info("{}", JSON.toJSONString(map));

        map.put(1, 1);
        map.put(2, 2);
        logger.info("map: {}", map);

        logger.info("{}", JSON.toJSONString(map));
        logger.info("{}", JSON.parseObject("{}", HashMap.class));
    }

    public static void testStreamSort() {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 8);
        map.put(2, 7);
        map.put(3, 6);
        map.put(4, 5);
        logger.info("map: {}", map);
        Map<Integer, Integer> result = new LinkedHashMap<>();
//        map.entrySet().stream().sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed()).forEachOrdered(e -> result.put(e.getKey(), e.getValue()));
        map.entrySet().stream().filter(e -> e.getValue() > 5).sorted(Map.Entry.comparingByValue()).forEachOrdered(e -> result.put(e.getKey(), e.getValue()));
        logger.info("result: {}", result);
        logger.info("result: {}", result.keySet());
        logger.info("result: {}", result.values());
        List<Integer> result2 = map.entrySet().stream().sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed()).map(Map.Entry::getKey).collect(Collectors.toList());
        logger.info("result2: {}", result2);
        List<Integer> result3 = map.entrySet().stream().sorted(Map.Entry.comparingByValue()).map(Map.Entry::getKey).collect(Collectors.toList());
        logger.info("result3: {}", result3);
    }

    public static void testStreamMax() {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 8);
        map.put(2, 7);
        map.put(3, 6);
        map.put(4, 5);
        logger.info("map: {}", map);
        int result = map.keySet().stream().max(Integer::compareTo).get();
        logger.info("result: {}", result);
        int key1 = map.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();
        logger.info("result: {}", key1);
        int key2 = map.entrySet().stream().min(Map.Entry.comparingByValue()).get().getKey();
        logger.info("result: {}", key2);
    }

    public static void testMerge() {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 8);
        map.put(2, 7);
        map.put(3, 6);
        map.put(4, 5);
        logger.info("map: {}", map);
        Map<Integer, Integer> result1 = new HashMap<>();
        result1.put(1, 1);
        result1.put(2, 2);
        result1.put(3, 7);
        result1.put(4, 6);
        map.forEach((k, v) -> {
            result1.merge(k, v, Integer::compareTo);
        });
        logger.info("result1: {}", result1);
        Map<Integer, Integer> result2 = new HashMap<>();
        result2.put(1, 1);
        result2.put(2, 2);
        result2.put(3, 7);
        result2.put(4, 6);
        map.forEach((k, v) -> {
            result2.merge(k, v, (v1, v2) -> v2 > v1 ? v2 : v1);
        });
        logger.info("result2: {}", result2);
    }

    public static void testKeySetRemove() {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 8);
        map.put(2, 7);
        Set<Integer> keySet = map.keySet();
        logger.info("keySet: {}", keySet);
        keySet.remove(1);
        logger.info("keySet1: {}", keySet);
        logger.info("keySet2: {}", map.keySet());
    }

    public static void testGroupingBy() {
        List<A> aList = new ArrayList<>();
        aList.add(new A(1, 1, 1));
        aList.add(new A(1, 3, 5));
        aList.add(new A(1, 3, 3));
        aList.add(new A(2, 3, 1));
        aList.add(new A(2, 1, 1));
        aList.add(new A(2, 1, 5));
        logger.info("aList: {}", aList);
        Map<Integer, Map<Integer, List<A>>> map = aList.stream().collect(Collectors.groupingBy(A::getType, Collectors.groupingBy(A::getValue)));
        logger.info("map: {}", map);
    }

    public static void testReducing() {
        List<A> aList = new ArrayList<>();
        aList.add(new A(1, 1, 1));
        aList.add(new A(1, 3, 5));
        aList.add(new A(1, 3, 3));
        aList.add(new A(2, 3, 1));
        aList.add(new A(2, 1, 1));
        aList.add(new A(2, 1, 5));
        logger.info("aList: {}", aList);
        try {
            Map<Integer, Map<Integer, A>> map1 = aList.stream().collect(Collectors.groupingBy(A::getType, Collectors.groupingBy(A::getValue, Collectors.reducing(null, BinaryOperator.minBy(Comparator.comparing(A::getTime))))));
            logger.info("map1: {}", map1);
        } catch (Exception e) {
            logger.info("e=", e);
        }
        Map<Integer, Map<Integer, A>> map2 = aList.stream().collect(Collectors.groupingBy(A::getType, Collectors.groupingBy(A::getValue, Collectors.reducing(new A(1, 1, 1), BinaryOperator.minBy(Comparator.comparing(A::getTime))))));
        logger.info("map2: {}", map2);
        Map<Integer, Map<Integer, Optional<A>>> map3 = aList.stream().collect(Collectors.groupingBy(A::getType, Collectors.groupingBy(A::getValue, Collectors.reducing(BinaryOperator.minBy(Comparator.comparing(A::getTime))))));
        logger.info("map3: {}", map3);
        Map<Integer, Map<Integer, Optional<A>>> map4 = aList.stream().collect(Collectors.groupingBy(A::getType, Collectors.groupingBy(A::getValue, Collectors.minBy(Comparator.comparing(A::getTime)))));
        logger.info("map4: {}", map4);
    }
}

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
class A {
    private int type;
    private int value;
    private int time;
}