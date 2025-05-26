package test.org.eclipse.jetty.jersey;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.ee10.servlet.ServletContextHandler;
import org.eclipse.jetty.ee10.servlet.ServletHolder;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.component.LifeCycle;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author C
 * @date 4/15/2025
 */
@Slf4j
public class JettyJerseyServer {
    private int port;

    public JettyJerseyServer(int port) {
        this.port = port;
    }

    public void startServer() throws Exception {
        QueuedThreadPool threadPool = new QueuedThreadPool();
        ExecutorService virtualThreadExecutor = Executors.newVirtualThreadPerTaskExecutor();
        threadPool.setVirtualThreadsExecutor(virtualThreadExecutor);

        Server server = new Server(threadPool);
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(port);
        server.addConnector(connector);

        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/");

        // 1. 配置 ResourceConfig，扫描 com.example.demo 包下的资源
        ResourceConfig config = new ResourceConfig().packages("test.org.eclipse.jetty.jersey");
//        ResourceConfig config = new ResourceConfig();
//        config.register(JettyJerseyResource.class);

        // 2. 创建 Jersey 的 ServletContainer
        ServletHolder jerseyServlet = new ServletHolder(new ServletContainer(config));
        context.addServlet(jerseyServlet, "/*");

        server.setHandler(context);
        server.start();
        server.join();

        LifeCycle.stop(server);
        virtualThreadExecutor.close();
    }
}
