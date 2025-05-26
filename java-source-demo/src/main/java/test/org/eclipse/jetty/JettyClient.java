package test.org.eclipse.jetty;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.client.ContentResponse;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.util.component.LifeCycle;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author C
 * @date 4/15/2025
 */
@Slf4j
public class JettyClient {
    private static final HttpClient httpClient = new HttpClient();
    private static final ExecutorService virtualThreadExecutor = Executors.newVirtualThreadPerTaskExecutor();

    public static HttpClient getHttpClient() throws Exception {
        return httpClient;
    }

    // 代理请求方法（使用虚拟线程处理）
    public static JettyPair<Integer, String> doRequest(String baseUrl, String path, String method, Map<String, String> headers) {
        try {
            // 构建目标 URL
            String targetUrl = baseUrl + path;

            // 发送同步请求（虚拟线程适合阻塞操作）
            ContentResponse contentResponse = httpClient.newRequest(targetUrl)
                    .method(method)
                    .headers(hs -> {
                        if (headers != null) {
                            headers.forEach(hs::add);
                        }
                    })
                    .send();

            // 回写响应
            return new JettyPair<>(contentResponse.getStatus(), contentResponse.getContentAsString());
        } catch (Exception e) {
            String errorMsg = "Request error: " + e.getMessage();
            return new JettyPair<>(500, errorMsg);
        }
    }

    public static void init() {
        httpClient.setExecutor(virtualThreadExecutor); // 客户端也使用虚拟线程
        try {
            httpClient.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        addDestroyHook();
    }

    private static void addDestroyHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("Shutdown hook running...");
            LifeCycle.stop(httpClient);
            virtualThreadExecutor.close();
            log.info("Shutdown hook ran!!!");
        }));
    }
}
