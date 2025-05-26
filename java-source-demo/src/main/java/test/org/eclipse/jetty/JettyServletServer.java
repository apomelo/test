package test.org.eclipse.jetty;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.ee10.servlet.ServletContextHandler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.component.LifeCycle;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author C
 * @date 4/15/2025
 */
@Slf4j
public class JettyServletServer {
    private int port;

    public JettyServletServer(int port) {
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
        context.addServlet(new MyServletContainer(), "/*");
        server.setHandler(context);

        server.start();
        server.join();

        LifeCycle.stop(server);
        virtualThreadExecutor.close();
    }

    // 自定义 Servlet 容器
    private static class MyServletContainer extends HttpServlet {

        @Override
        protected void service(HttpServletRequest req, HttpServletResponse resp) {
            try {
                String path = req.getRequestURI();

                JettyPair<Integer, String> cached = JettyDataCache.get(path);
                if (cached != null) {
                    resp.setStatus(cached.first());
                    resp.getWriter().write(cached.second());
                    log.info("Cached response: {}", cached);
                    return;
                }

                JettyPair<Integer, String> result = doProxy(path, req);

                resp.setStatus(result.first());
                resp.getWriter().write(result.second());
                log.info("Proxy response: {}", result);
            } catch (Exception e) {
                resp.setStatus(500);
                log.error(e.getMessage(), e);
            }
        }

        private JettyPair<Integer, String> doProxy(String path, HttpServletRequest req) {
            Map<String, String> headers = new HashMap<>();
            req.getHeaderNames().asIterator().forEachRemaining(name -> headers.put(name, req.getHeader(name)));
            return JettyClient.doRequest(JettyConfig.URL, path, req.getMethod(), headers);
        }
    }
}
