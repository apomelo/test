package test.org.eclipse.jetty;

import java.util.HashSet;
import java.util.Set;

/**
 * @author C
 * @date 4/18/2025
 */
public interface JettyConfig {
    int HANDLER_SERVER_PORT = 6001;
    int SERVLET_SERVER_PORT = 6002;
    int JERSEY_SERVER_PORT = 6003;

    String URL = "http://localhost:8080/";

    Set<String> PATH_SET = new HashSet<>() {{
        add("/test/jetty-server/test");
        add("/test/jetty-server/testInit");
    }};
}
