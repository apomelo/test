package test.java.util.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @author C
 * @date 2023/11/8
 */
@Slf4j
public class ConcurrentSkipListMapTest {
    public static void main(String[] args) {
        testSort();
        headMap();
        subMap();
    }

    public static void testSort() {
        log.info("---------- testSort1 begin ----------");
        ConcurrentSkipListMap<Integer, Integer> map1 = new ConcurrentSkipListMap<>();
        map1.put(1, 8);
        map1.put(3, 6);
        map1.put(2, 7);
        map1.put(4, 5);
        log.info("map1: {}", map1);
        ConcurrentSkipListMap<Integer, Integer> map2 = new ConcurrentSkipListMap<>(Comparator.reverseOrder());
        map2.put(1, 8);
        map2.put(3, 6);
        map2.put(2, 7);
        map2.put(4, 5);
        log.info("map2: {}", map2);
        log.info("---------- testSort1 end ----------");
    }

    public static void headMap() {
        log.info("---------- headMap begin ----------");
        ConcurrentSkipListMap<Integer, Integer> map = new ConcurrentSkipListMap<>(Comparator.reverseOrder());
        map.put(1, 8);
        map.put(3, 6);
        map.put(2, 7);
        map.put(4, 5);
        log.info("map: {}", map);
        ConcurrentNavigableMap<Integer, Integer> headMap1 = map.headMap(2);
        log.info("headMap1: {}", headMap1);
        log.info("headMap1.size(): {}", headMap1.size());
        ConcurrentNavigableMap<Integer, Integer> headMap2 = map.headMap(0);
        log.info("headMap2: {}", headMap2);
        log.info("headMap2.size(): {}", headMap2.size());
        log.info("---------- headMap end ----------");
    }

    public static void subMap() {
        log.info("---------- subMap begin ----------");
        ConcurrentSkipListMap<Integer, Integer> map = new ConcurrentSkipListMap<>(Comparator.reverseOrder());
        map.put(1, 8);
        map.put(3, 6);
        map.put(2, 7);
        map.put(4, 5);
        log.info("map: {}", map);
        ConcurrentNavigableMap<Integer, Integer> subMap1 = map.subMap(4, 2);
        log.info("subMap1: {}", subMap1);
        log.info("subMap1.size(): {}", subMap1.size());
        try {
            ConcurrentNavigableMap<Integer, Integer> subMap2 = map.subMap(2, 4);
            log.info("subMap2: {}", subMap2);
            log.info("subMap2.size(): {}", subMap2.size());
        } catch (Exception e) {
            log.warn("exception:", e);
        }
        log.info("---------- subMap end ----------");
    }
}
