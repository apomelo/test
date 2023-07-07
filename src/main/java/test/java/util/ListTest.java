package test.java.util;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by C on 2019/11/7.
 */
@Slf4j
public class ListTest {
    public static void main(String[] args) {
        testStreamSorted();
        testForeach();
        testIterator();
        testSublist();
        testArraysAsList();
    }

    public static void testStreamSorted() {
        log.info("---------- testStreamSorted begin ----------");
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
        log.info("1: {}", collect);
        List<A> collect2 = sortList.stream().sorted(Comparator.comparing(A::getNum).reversed().thenComparing(A::getId).thenComparing(A::getLevel).reversed()).limit(10).collect(Collectors.toList());
        log.info("2: {}", collect2);
        List<A> collect3 = sortList.stream().sorted(Comparator.comparing(A::getNum).reversed().thenComparing(A::getId).thenComparing(A::getLevel, Comparator.reverseOrder())).limit(10).collect(Collectors.toList());
        log.info("3: {}", collect3);
        List<A> collect4 = sortList.stream().sorted(Comparator.comparing(A::getNum, Comparator.reverseOrder()).thenComparing(A::getId).thenComparing(A::getLevel, Comparator.reverseOrder())).limit(10).collect(Collectors.toList());
        log.info("4: {}", collect4);
        List<A> collect5 = sortList.stream().sorted(Comparator.comparing(A::getNum).thenComparing(A::getLevel)).limit(10).collect(Collectors.toList());
        log.info("5: {}", collect5);
        log.info("---------- testStreamSorted end ----------");
    }

    public static void testForeach() {
        log.info("---------- testForeach begin ----------");
        List<A> aList = new ArrayList<>();
        aList.add(new A(1, 1, 1));
        aList.add(new A(2, 2, 2));

        aList.forEach(a -> {
            a.id += 10;
        });

        log.info("{}", aList);
        log.info("---------- testForeach end ----------");
    }

    public static void testIterator() {
        log.info("---------- testIterator begin ----------");
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            Integer next = iterator.next();
            if (next == 2) {
                log.info("list.size={}", list.size());
                iterator.remove();
                log.info("list.size={}", list.size());
            }
        }
        log.info("---------- testIterator end ----------");
    }

    private static void testSublist() {
        log.info("---------- testSublist begin ----------");
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        log.info("list: {}", list);
        list.subList((list.size() + 1) / 2, list.size()).clear();
        log.info("list: {}", list);
        log.info("---------- testSublist end ----------");
    }

    private static void testArraysAsList() {
        log.info("---------- testArraysAsList begin ----------");
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        try {
            list.remove(0);
        } catch (UnsupportedOperationException e) {
            log.info("this arrays as list is not allow modify!!!, exception: ", e);
        }
        log.info("list: {}", list);
        log.info("---------- testArraysAsList end ----------");
    }


    @Data
    @AllArgsConstructor
    private static class A {
        int id;
        int num;
        int level;
    }
}
