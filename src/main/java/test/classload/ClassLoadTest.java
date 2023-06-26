package test.classload;

import lombok.extern.slf4j.Slf4j;

/**
 * @author C
 * @date 2023/6/26
 */
public class ClassLoadTest {
    public static void main(String[] args) {

    }
}

@Slf4j
class A {
    A() {
        log.info("A constructor");
    }
}
