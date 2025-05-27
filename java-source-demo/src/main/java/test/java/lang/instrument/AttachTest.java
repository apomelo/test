package test.java.lang.instrument;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;

import java.io.IOException;

public class AttachTest {
    /**
     * 在jdk21中需要在jar包中的MANIFEST.MF文件中添加以下配置（可以通过maven自动打包进去）：
     * Agent-Class: test.java.lang.instrument.AgentTest
     * Can-Retransform-Classes: true
     *
     * 注意: jdk1.8中工程需要引入包: jdk所在目录/lib/tools.jar
     */
    public static void main(String[] args) throws AttachNotSupportedException, IOException, AgentLoadException, AgentInitializationException {
        // 传入目标 JVM pid (需要先运行 test.java.lang.instrument.Base 获取)
        VirtualMachine vm = VirtualMachine.attach("37964");
        vm.loadAgent("package/test-project-1.0.0.jar");
    }
}
