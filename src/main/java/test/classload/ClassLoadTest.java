package test.classload;

import lombok.extern.slf4j.Slf4j;
import test.singleinstance.StaticInner;

import java.util.concurrent.TimeUnit;

/**
 * @author C
 * @date 2023/6/26
 */
@Slf4j
public class ClassLoadTest {
    public static void main(String[] args) throws InterruptedException {
        testInherit();
        testStaticInner();
    }

    private static void testInherit() throws InterruptedException {
        log.info("---------- testInherit begin ----------");
        log.info("sleep start");
        TimeUnit.SECONDS.sleep(1);
        log.info("sleep end");
        Parent parent = new Child();
        parent.print("at main invoke");
        log.info("---------- testInherit begin ----------");
    }

    private static void testStaticInner() throws InterruptedException {
        log.info("---------- testStaticInner begin ----------");
        log.info("sleep start");
        TimeUnit.SECONDS.sleep(1);
        log.info("sleep end");
        StaticInner.getInstance();
        log.info("---------- testStaticInner begin ----------");
    }

    static class Parent {
        /**
         * 成员变量和成员代码块是从上到下依次执行
         */
        int memberA = 1;
        {
            // 下面语句编译不通过, 编译时会报: "java: 非法前向引用"
//            log.info("Parent memberB: {}", memberB);
        }
        int memberB = 2;

        /**
         * 静态变量和静态代码块是从上到下依次执行
         */
        static int staticA = 1;
        static {
            log.info("Parent staticA: {}", staticA);
            // 下面语句编译不通过, 编译时会报: "java: 非法前向引用"
//            log.info("Parent staticB:{}", staticB);
        }
        static int staticB = 2;

        /**
         * 父类构造器
         */
        Parent() {
            log.info("Parent constructor");
            // 注意！！！ 子类重写 print 方法时调用的是子类的 print 方法
            print("at parent constructor");
        }

        // 语句块放后面，但是比构造器先执行
        {
            log.info("Parent memberA: {}", memberA);
        }

        void print(String s) {
            log.info("Parent print: {}, staticA: {}, memberA: {}", s, staticA, memberA);
        }
    }

    static class Child extends Parent {
        /**
         * 成员变量和成员代码块是从上到下依次执行
         */
        int memberA = 1;
        {
            // 下面语句编译不通过, 编译时会报: "java: 非法前向引用"
//            log.info("Child memberB: {}", memberB);
        }
        int memberB = 2;

        /**
         * 静态变量和静态代码块是从上到下依次执行
         */
        static int staticA = 1;
        static {
            log.info("Child staticA: {}", staticA);
            // 下面语句编译不通过, 编译时会报: "java: 非法前向引用"
//            log.info("Child staticB:{}", staticB);
        }
        static int staticB = 2;

        /**
         * 子类构造器
         */
        Child() {
            log.info("Child constructor");
            print("at child constructor");
        }

        // 语句块放后面，但是比构造器先执行
        {
            log.info("Child memberA: {}", memberA);
        }

        @Override
        void print(String s) {
            log.info("Child print: {}, staticA: {}, memberA: {}, super.MemberA:{}", s, staticA, memberA, super.memberA);
        }
    }
}
