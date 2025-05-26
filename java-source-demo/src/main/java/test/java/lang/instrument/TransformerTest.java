package test.java.lang.instrument;

import javassist.*;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

/**
 * @author C
 * @date 2022/8/2
 */
public class TransformerTest implements ClassFileTransformer {
    /**
     * 这里在jdk21中需要显示添加base类路径，否则会报错： javassist.NotFoundException: test.java.lang.instrument.Base
     * 原因： javassist在加载类时，会使用当前线程的ClassLoader来加载类，而在Agent中，当前线程的ClassLoader是AppClassLoader，而不是Base的ClassLoader
     */
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {
        System.out.println("Transforming " + className);
        System.out.println("ClassLoader " + loader);
        try {
            ClassPool cp = ClassPool.getDefault();
            // 方法1: 通过直接引用 Base 显式添加类路径
//            cp.insertClassPath(new ClassClassPath(Base.class));
            // 方法2: 通过 Base 的字符串包名显式添加类路径
            cp.insertClassPath(new ClassClassPath(loader.loadClass("test.java.lang.instrument.Base")));
            // 方法3: 使用 ContextClassLoader 或 SystemClassLoader 获取 Base 所在的类路径
//            ClassLoader cl = Thread.currentThread().getContextClassLoader();
//            if (cl == null) {
//                System.out.println("ContextClassLoader is null");
//                cl = ClassLoader.getSystemClassLoader();
//            }
//            cp.insertClassPath(new LoaderClassPath(cl));

            CtClass cc = cp.get("test.java.lang.instrument.Base");
            CtMethod m = cc.getDeclaredMethod("process");
            m.insertBefore("{ System.out.println(\"start\"); }");
            m.insertAfter("{ System.out.println(\"end\"); }");
            return cc.toBytecode();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}