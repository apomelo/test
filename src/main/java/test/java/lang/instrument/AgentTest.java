package test.java.lang.instrument;

import java.lang.instrument.Instrumentation;

/**
 * @author C
 * @date 2022/8/2
 */
public class AgentTest {
    public static void agentmain(String args, Instrumentation inst) {
        // 指定自定义的Transformer，在其中利用Javassist做字节码替换
        inst.addTransformer(new TransformerTest(), true);
        try {
            // 重定义类并载入新的字节码
            inst.retransformClasses(Base.class);
            System.out.println("Agent Load Done.");
        } catch (Exception e) {
            System.out.println("agent load failed!");
        }
    }
}
