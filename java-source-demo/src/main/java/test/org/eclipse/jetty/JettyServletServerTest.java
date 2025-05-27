package test.org.eclipse.jetty;

import lombok.extern.slf4j.Slf4j;

/**
 * @author C
 * @date 4/18/2025
 */
@Slf4j
public class JettyServletServerTest {
    public static void main(String[] args) throws Exception {
        log.info("Hello, Jetty Servlet Server!");
        JettyClient.init();
        JettyDataCache.init();

        JettyServletServer server = new JettyServletServer(JettyConfig.SERVLET_SERVER_PORT);
        server.startServer();
    }
}
