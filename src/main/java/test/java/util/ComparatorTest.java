package test.java.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author C
 * @date 2023/6/9
 */
@Slf4j
public class ComparatorTest {
    public static void main(String[] args) {
        testComparator();
    }

    private static void testComparator() {
        log.info("---------- testComparator begin ----------");
        List<A> aList = new ArrayList<A>() {{
            add(new A(1, 6));
            add(new A(2, 6));
            add(new A(2, 5));
        }};
        List<A> sort = aList.stream().sorted(new A.AComparator()).collect(Collectors.toList());
        log.info("sort:{}", sort);
        A max = aList.stream().max(new A.AComparator()).get();
        log.info("max:{}", max);
        A min = aList.stream().min(new A.AComparator()).get();
        log.info("min:{}", min);
        log.info("---------- testComparator end ----------");
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class A {
        private int score;
        private int time;

        static class AComparator implements Comparator<A> {
            /**
             * score 大的排在前面, score 相同时 time 小的排在前面
             * @param o1 the first object to be compared.
             * @param o2 the second object to be compared.
             * @return
             */
            @Override
            public int compare(A o1, A o2) {
                int score1 = o1.getScore();
                int score2 = o2.getScore();
                int time1 = o1.getTime();
                int time2 = o2.getTime();
                if (score1 > score2) {
                    return -1;
                } else if (score1 < score2) {
                    return 1;
                } else {
                    return Integer.compare(time1, time2);
                }
            }
        }
    }
}
