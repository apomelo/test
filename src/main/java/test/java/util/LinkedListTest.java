package test.java.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by C on 2019/6/17.
 */
public class LinkedListTest {
    private static final Logger logger = LoggerFactory.getLogger(LinkedListTest.class);
    public static void main(String[] args) {
        testIterator();
    }

    public static void testIterator() {
        LinkedList<Integer> linkedList = new LinkedList();
        ArrayList<Integer> resultList = new ArrayList<>();
        for (int i = 0; i < 10; i ++) {
            linkedList.add(i);
        }

        Iterator<Integer> integerIterator = linkedList.descendingIterator();
        while (integerIterator.hasNext()) {
            resultList.add(integerIterator.next());
            linkedList.push(0);
        }

        logger.info("resultList: {}", resultList);
        logger.info("linkedList: {}", linkedList);
    }
}
