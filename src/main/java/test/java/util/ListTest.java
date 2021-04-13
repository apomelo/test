package test.java.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.Entity.Entity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by C on 2019/11/7.
 */
public class ListTest {
    private static final Logger logger = LoggerFactory.getLogger(ListTest.class);
    public static void main(String[] args) {
        testStreamSorted();
        testForeach();
        testIterator();
    }

    public static void testStreamSorted() {
        List<A> list1 = new ArrayList<A>() {{
            add(new A(3, 5, 7));
            add(new A(3, 6, 8));
            add(new A(3, 9, 2));
            add(new A(3, 7, 3));
            add(new A(3, 8, 4));
            add(new A(3, 9, 5));
        }};
        List<A> list2 = new ArrayList<A>() {{
            add(new A(2, 5, 2));
            add(new A(2, 6, 9));
            add(new A(2, 7, 4));
            add(new A(2, 8, 5));
            add(new A(2, 9, 6));
        }};
        List<A> sortList = new ArrayList<>();
        sortList.addAll(list1);
        sortList.addAll(list2);

        List<A> collect = sortList.stream().sorted(Comparator.comparing(A::getNum).reversed()).limit(10).collect(Collectors.toList());
        logger.info("1: {}", collect);
        List<A> collect2 = sortList.stream().sorted(Comparator.comparing(A::getNum).reversed().thenComparing(A::getId).thenComparing(A::getLevel).reversed()).limit(10).collect(Collectors.toList());
        logger.info("2: {}", collect2);
        List<A> collect3 = sortList.stream().sorted(Comparator.comparing(A::getNum).reversed().thenComparing(A::getId).thenComparing(A::getLevel, Comparator.reverseOrder())).limit(10).collect(Collectors.toList());
        logger.info("3: {}", collect3);
        List<A> collect4 = sortList.stream().sorted(Comparator.comparing(A::getNum, Comparator.reverseOrder()).thenComparing(A::getId).thenComparing(A::getLevel, Comparator.reverseOrder())).limit(10).collect(Collectors.toList());
        logger.info("4: {}", collect4);
        List<A> collect5 = sortList.stream().sorted(Comparator.comparing(A::getNum).thenComparing(A::getLevel)).limit(10).collect(Collectors.toList());
        logger.info("5: {}", collect5);
    }

    public static void testForeach() {
        List<A> aList = new ArrayList<>();
        aList.add(new A(1, 1, 1));
        aList.add(new A(2, 2, 2));

        aList.forEach(a -> {
            a.id += 10;
        });

        logger.info("{}", aList);
    }

    public static void testIterator() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            Integer next = iterator.next();
            if (next == 2) {
                logger.info("list.size={}", list.size());
                iterator.remove();
                logger.info("list.size={}", list.size());
            }
        }
    }

    @Data
    @AllArgsConstructor
    private static class A {
        int id;
        int num;
        int level;
    }
}
