package test.org.eclipse.jetty;

import org.eclipse.jetty.client.ContentResponse;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.http.HttpMethod;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * @author C
 * @date 4/18/2025
 */
@BenchmarkMode(Mode.Throughput) // 测试吞吐量
@OutputTimeUnit(TimeUnit.SECONDS)
@Warmup(iterations = 3, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 3, timeUnit = TimeUnit.SECONDS)
@State(Scope.Benchmark)
public class JettyJmhTest {

    private HttpClient httpClient;

    @Setup
    public void setup() throws Exception {
        httpClient = JettyClient.getHttpClient();
        JettyClient.init();
    }

    @TearDown
    public void teardown() throws Exception {
        httpClient.stop();
    }

    // 测试 Handler 实现
    @Benchmark
    public void testHandler() throws Exception {
        ContentResponse response = httpClient.newRequest("http://localhost:6001/test/jetty-server/test")
            .method(HttpMethod.GET)
            .send();
        assert response.getStatus() == 200;
    }

    // 测试 Servlet 实现
    @Benchmark
    public void testServlet() throws Exception {
        ContentResponse response = httpClient.newRequest("http://localhost:6002/test/jetty-server/test")
            .method(HttpMethod.GET)
            .send();
        assert response.getStatus() == 200;
    }

    // 测试 Jersey 实现
    @Benchmark
    public void testJersey() throws Exception {
        ContentResponse response = httpClient.newRequest("http://localhost:6003/test/jetty-server/test")
            .method(HttpMethod.GET)
            .send();
        assert response.getStatus() == 200;
    }


    public static void main(String[] args) throws Exception {
        Options opts = new OptionsBuilder()
                .include(JettyJmhTest.class.getSimpleName())
                .resultFormat(ResultFormatType.JSON)
                .build();
        new Runner(opts).run();
    }
}