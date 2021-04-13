package test.java.util;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by C on 2019/12/19.
 */
public class HashMapTest {
    private static final Logger logger = LoggerFactory.getLogger(HashMapTest.class);
    public static void main(String[] args) {
//        test();
//        testStreamSort();
//        testStreamMax();
//        testMerge();
        testKeySetRemove();
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
}
