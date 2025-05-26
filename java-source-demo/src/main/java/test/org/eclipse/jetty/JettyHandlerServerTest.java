package test.org.eclipse.jetty;

import lombok.extern.slf4j.Slf4j;

/**
 * @author C
 * @date 4/18/2025
 */
@Slf4j
public class JettyHandlerServerTest {
    public static void main(String[] args) throws Exception {
        log.info("Hello, Jetty Handler Server!");
        JettyClient.init();
        JettyDataCache.init();

        JettyHandlerServer server = new JettyHandlerServer(JettyConfig.HANDLER_SERVER_PORT);
        server.startServer();
    }
}
