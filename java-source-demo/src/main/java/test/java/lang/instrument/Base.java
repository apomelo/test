package test.java.lang.instrument;

import util.JVMUtil;

import java.lang.management.ManagementFactory;

/**
 * @author C
 * @date 2022/8/2
 */
public class Base {
    public static void main(String[] args) {
        // 设置本类的JVM参数： -XX:+EnableDynamicAgentLoading 可以消除warning信息
        JVMUtil.printJVMInfo();
        String name = ManagementFactory.getRuntimeMXBean().getName();
        String s = name.split("@")[0];
        // 打印当前Pid
        System.out.println("pid:"+s);
        while (true) {
            try {
                Thread.sleep(5000L);
            } catch (Exception e) {
                break;
            }
            process();
        }
    }

    private static void process(){
        System.out.println("process");
    }
}