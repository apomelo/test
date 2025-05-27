package test.reflect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;

/**
 * @author C
 * @date 2022/3/26
 */
public class BaseReflect {
    private static final Logger logger = LoggerFactory.getLogger(BaseReflect.class);

    long flag;

    public void func0() {
        flag++;
    }

    public void func1() {
        flag--;
    }

    public void func2() {
        flag++;
    }

    public void func3() {
        flag--;
    }

    public static void main(String[] args) throws Throwable {
        BaseReflect bf;
        bf = new BaseReflect(); // 预热部分
        bf.testInterface();
        bf = new BaseReflect();
        bf.testReflect1();
        bf = new BaseReflect();
        bf.testReflect2();
        bf = new BaseReflect();
        bf.testReflect3();
        bf = new BaseReflect();
        bf.testReflect4();

        logger.info("------------------------------------");

        bf = new BaseReflect(); // 实测部分
        bf.testInterface();
        bf = new BaseReflect();
        bf.testReflect1();
        bf = new BaseReflect();
        bf.testReflect2();
        bf = new BaseReflect();
        bf.testReflect3();
        bf = new BaseReflect();
        bf.testReflect4();
    }

    public void testInterface() {
        long before = System.nanoTime();
        Runnable[] rs = {
                this::func0,
                this::func1,
                this::func2,
                this::func3,
        };
        for (int i = 0; i < 1_0000_0000; i++) {
            // 调用
            rs[i & 3].run();
        }
        long time = (System.nanoTime() - before) / 1_000_000;
        logger.info("testInterface: {} {}ms\n", flag, time);
    }

    public void testReflect1() throws Exception {
        long before = System.nanoTime();
        String[] ss = {
                "func0",
                "func1",
                "func2",
                "func3"
        };
        for (int i = 0; i < 1_0000_0000; i ++) {
            // 调用
            BaseReflect.class.getMethod(ss[i & 3]).invoke(this);
        }
        long time = (System.nanoTime() - before) / 1_000_000;
        logger.info("testReflect1: {} {}ms\n", flag, time);
    }

    public void testReflect2() throws Exception {
        long before = System.nanoTime();
        Method[] ms = {
                BaseReflect.class.getMethod("func0"),
                BaseReflect.class.getMethod("func1"),
                BaseReflect.class.getMethod("func2"),
                BaseReflect.class.getMethod("func3"),
        };
        for (int i = 0; i < 1_0000_0000; i ++) {
            // 调用
            ms[i & 3].invoke(this);
        }
        long time = (System.nanoTime() - before) / 1_000_000;
        logger.info("testReflect2: {} {}ms\n", flag, time);
    }

    public void testReflect3() throws Throwable {
        long before = System.nanoTime();
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        String[] ss = {
                "func0",
                "func1",
                "func2",
                "func3"
        };
        for (int i = 0; i < 1_0000_0000; i ++) {
            // 调用
            lookup.unreflect(BaseReflect.class.getMethod(ss[i & 3])).invoke(this);
        }
        long time = (System.nanoTime() - before) / 1_000_000;
        logger.info("testReflect3: {} {}ms\n", flag, time);
    }

    public void testReflect4() throws Throwable {
        long before = System.nanoTime();
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle[] ms = {
                lookup.unreflect(BaseReflect.class.getMethod("func0")),
                lookup.unreflect(BaseReflect.class.getMethod("func1")),
                lookup.unreflect(BaseReflect.class.getMethod("func2")),
                lookup.unreflect(BaseReflect.class.getMethod("func3")),
        };
        for (int i = 0; i < 1_0000_0000; i ++) {
            // 调用
            ms[i & 3].invoke(this);
        }
        long time = (System.nanoTime() - before) / 1_000_000;
        logger.info("testReflect4: {} {}ms\n", flag, time);
    }
}
