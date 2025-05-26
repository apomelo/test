package test.org.apache.commons.beanutils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtilsBean;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author C
 * @date 1/16/2025
 */
@Slf4j
public class BeanUtilsBeanTest {
    public static void main(String[] args) {
        test1();
        test2();
        test3();
    }

    private static void test1() {
        A a = new A();
        a.setId(1);
        a.setName("a");
        a.setC(new C(1));
        a.setMap(Map.of(1, 1));
        a.setList(List.of(1));
        a.setSet(Set.of(1));

        B b = new B();
        try {
            BeanUtilsBean.getInstance().copyProperties(b, a);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        log.info("test1,a:{}", a);
        log.info("test1,b:{}", b);
    }

    private static void test2() {
        A a = new A();

        B b = new B();
        b.setId(2);
        b.setName("b");
        b.setC(new C(2));
        b.setMap(Map.of(2, 2));
        b.setList(List.of(2));
        b.setSet(Set.of(2));

        try {
            BeanUtilsBean.getInstance().copyProperties(b, a);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        log.info("test2,a:{}", a);
        log.info("test2,b:{}", b);
    }

    private static void test3() {
        A a = new A();
        a.setId(1);
        a.setName("a");
        a.setC(new C(1));
        a.setMap(Map.of(1, 1));
        a.setList(List.of(1));
        a.setSet(Set.of(1));

        B b = new B();
        b.setId(2);
        b.setName("b");
        b.setC(new C(2));
        b.setMap(Map.of(2, 2));
        b.setList(List.of(2));
        b.setSet(Set.of(2));

        try {
            BeanUtilsBean.getInstance().copyProperties(b, a);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        log.info("test3,a:{}", a);
        log.info("test3,b:{}", b);
    }


    @Data
    public static class A {
        private int id;
        private String name;
        private C c;
        private Map<Integer, Integer> map;
        private List<Integer> list;
        private Set<Integer> set;
    }

    @Data
    public static class B {
        private int id;
        private String name;
        private C c;
        private Map<Integer, Integer> map;
        private List<Integer> list;
        private Set<Integer> set;
    }

    @Data
    @AllArgsConstructor
    public static class C {
        private int count;
    }
}
