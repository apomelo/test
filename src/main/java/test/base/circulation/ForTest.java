package test.base.circulation;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ForTest {
    public static void main(String[] args) {
        testBreakEndCal();
    }

    private static void testBreakEndCal() {
        int num = 0;
        for (; num < 100; num ++) {
            if (num == 10) {
                break;
            }
        }
        log.info("num: {}", num);
    }
}
