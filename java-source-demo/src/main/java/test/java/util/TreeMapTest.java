package test.java.util;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.TreeMap;

/**
 * @author C
 * @date 2023/12/14
 */
@Slf4j
public class TreeMapTest {
    public static void main(String[] args) {
        testConstruct();
    }


    /**
     * 测试 {@link TreeMap#TreeMap(Comparator)} 测试 TreeMap 的构造方法
     * TreeMap 只能以 key 进行排序，不能以 value 进行排序
     */
    public static void testConstruct() {
        log.info("---------- testConstruct begin ----------");
        TreeMap<Integer, Integer> treeMap1 = new TreeMap<>();
        treeMap1.put(1, 1);
        treeMap1.put(2, 2);
        treeMap1.put(5, 3);
        treeMap1.put(3, 2);
        treeMap1.put(4, 3);
        log.info("treeMap1: {}", treeMap1);
        log.info("treeMap1: {}", JSON.toJSONString(treeMap1));
        log.info("---------- testConstruct end ----------");
    }
}
