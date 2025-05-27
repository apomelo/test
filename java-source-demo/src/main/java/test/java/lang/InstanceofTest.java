package test.java.lang;

import lombok.extern.slf4j.Slf4j;

/**
 * @author C
 * @date 2023/9/14
 */
@Slf4j
public class InstanceofTest {
    public static void main(String[] args) {
        test1();
    }


    public static void test1() {
        Parent parent = new Child();
        log.info("instanceof Parent: {}", parent instanceof Parent);
        log.info("instanceof Child: {}", parent instanceof Child);
    }


    private static class Parent {}
    private static class Child extends Parent {}
}
