package test.java.util;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Created by C on 2019/6/17.
 */
@Slf4j
public class LinkedListTest {

    public static void main(String[] args) {
        testIterator();
    }

    public static void testIterator() {
        testIterator1();
    }

    /**
     * 测试迭代器快速失败的特性
     * 代码尝试在迭代 LinkedList 时添加元素（linkedList.push(0)），但这会引发 ConcurrentModificationException。
     * 因为直接修改列表违反了迭代器的“快速失败”机制，迭代器要求所有结构修改（如添加或删除元素）必须通过迭代器的自身方法完成。
     */
    public static void testIterator1() {
        LinkedList<Integer> linkedList = new LinkedList();
        ArrayList<Integer> resultList = new ArrayList<>();
        for (int i = 0; i < 10; i ++) {
            linkedList.add(i);
        }

        try {
            Iterator<Integer> integerIterator = linkedList.descendingIterator();
            while (integerIterator.hasNext()) {
                Integer next = integerIterator.next();
                resultList.add(next);

                // this will lead to java.util.ConcurrentModificationException
                linkedList.push(0);
            }
        } catch (Exception e) {
            log.error("exception:", e);
        }

        log.info("resultList: {}", resultList);
        log.info("linkedList: {}", linkedList);
    }
}
