package test.org.eclipse.jetty;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.util.Callback;
import org.eclipse.jetty.util.component.LifeCycle;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author C
 * @date 4/15/2025
 */
@Slf4j
public class JettyHandlerServer {
    private int port;

    public JettyHandlerServer(int port) {
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

        server.setHandler(new MyHandler());

        server.start();
        server.join();

        LifeCycle.stop(server);
        virtualThreadExecutor.close();
    }

    private static class MyHandler extends Handler.Abstract {

        @Override
        public boolean handle(Request req, Response resp, Callback callback) throws Exception {
            try {
                String path = req.getHttpURI().getPath();

                JettyPair<Integer, String> cached = JettyDataCache.get(path);
                if (cached != null) {
                    resp.setStatus(cached.first());
                    resp.write(true, ByteBuffer.wrap(cached.second().getBytes(StandardCharsets.UTF_8)), callback);
                    log.info("Cached response: {}", cached);
                    return true;
                }

                JettyPair<Integer, String> result = doProxy(path, req);

                resp.setStatus(result.first());
                resp.write(true, ByteBuffer.wrap(result.second().getBytes(StandardCharsets.UTF_8)), callback);
                log.info("Proxy response: {}", result);
                return true;
            } catch (Exception e) {
                resp.setStatus(500);
                log.error(e.getMessage(), e);
            }
            return false;
        }

        private JettyPair<Integer, String> doProxy(String path, Request req) {
            Map<String, String> headers = new HashMap<>();
            req.getHeaders().forEach(httpField -> headers.put(httpField.getName(), httpField.getValue()));
            return JettyClient.doRequest(JettyConfig.URL, path, req.getMethod(), headers);
        }
    }
}
