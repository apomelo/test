package test.java.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by C on 2019/12/26.
 */
public class HashSetTest {
    private static final Logger logger = LoggerFactory.getLogger(HashSetTest.class);
    public static void main(String[] args) {
        testStream();
    }

    public static void testStream() {
        List<Integer> list = new ArrayList();
        for (int i = 0; i < 10; i ++) {
            list.add(1);
        }

        Set<Integer> set = list.stream().collect(Collectors.toSet());
        logger.info("set:{}", set);
    }
}
