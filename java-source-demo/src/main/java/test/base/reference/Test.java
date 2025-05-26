package test.base.reference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by C on 2019/10/16.
 */
public class Test {
    private static final Logger logger = LoggerFactory.getLogger(Test.class);

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        test1(list);
        logger.info("{}", list);

        test2(list);
        logger.info("{}", list);
    }

    public static void test1(List<Integer> list) {
        list = new ArrayList<>();
    }

    public static void test2(List<Integer> list) {
        list.remove(0);
    }
}
