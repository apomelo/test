package test.org.eclipse.jetty.jersey;

import lombok.extern.slf4j.Slf4j;
import test.org.eclipse.jetty.JettyClient;
import test.org.eclipse.jetty.JettyConfig;
import test.org.eclipse.jetty.JettyDataCache;

/**
 * @author C
 * @date 4/18/2025
 */
@Slf4j
public class JettyJerseyServerTest {
    public static void main(String[] args) throws Exception {
        log.info("Hello, Jetty Jersey Server!");
        JettyClient.init();
        JettyDataCache.init();

        JettyJerseyServer server = new JettyJerseyServer(JettyConfig.JERSEY_SERVER_PORT);
        server.startServer();
    }
}
