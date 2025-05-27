package util;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.List;

/**
 * @author C
 * @date 1/10/2025
 */
public class JVMUtil {
    public static void printJVMInfo() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        List<String> jvmArguments = runtimeMXBean.getInputArguments();
        for (String arg : jvmArguments) {
            System.out.println(arg);
        }
    }
}
