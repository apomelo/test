package test.algorithm;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author C
 * @date 2022/3/22
 */
@Slf4j
public class MyTest {
    public static void main(String[] args) {
        test();
    }

    private static void test() {
        MyTest myTest = new MyTest();
        log.info("{}", myTest);
    }
}
