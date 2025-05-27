package test.javassist;

import javassist.*;

import java.io.IOException;

/**
 * @author C
 * @date 2022/8/2
 */
public class JavassistTest {
    /**
     * 需要加上运行参数: --add-opens java.base/java.lang=ALL-UNNAMED
     */
    public static void main(String[] args) throws NotFoundException, CannotCompileException, IllegalAccessException, InstantiationException, IOException {
//        Base base = new Base();   // 取消注释这一行，运行时会报错，原因：JVM不允许在运行时动态重载一个类
        ClassPool cp = ClassPool.getDefault();
        CtClass cc = cp.get("test.javassist.Base");
        CtMethod m = cc.getDeclaredMethod("process");
        // 是否带 {} 没有区别
        m.insertBefore("System.out.println(\"start\");");
        m.insertAfter("{ System.out.println(\"end\"); }");
        cc.writeFile("target/classes");

        Class<?> c = cc.toClass();
        Base b = (Base) c.newInstance();
        b.process();
    }
}