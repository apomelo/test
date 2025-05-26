package test.org.eclipse.jetty.jersey;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.*;
import lombok.extern.slf4j.Slf4j;
import test.org.eclipse.jetty.JettyClient;
import test.org.eclipse.jetty.JettyConfig;
import test.org.eclipse.jetty.JettyDataCache;
import test.org.eclipse.jetty.JettyPair;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Path("/")
public class JettyJerseyResource {
    @GET
    @Path( "{path: test/jetty-server/test1}")
    @Produces(MediaType.TEXT_PLAIN)
    public String sayHi(@PathParam("path") String path, @Context UriInfo uriInfo) {
        String path2 = uriInfo.getRequestUri().getPath();
        return "Hello from Jersey + Jetty!\n  PathParam path: " + path + "\n  RequestUri path: " + path2;
    }

    @GET
    @Path( "test/jetty-server/test2")
    @Produces(MediaType.TEXT_PLAIN)
    public String sayHi(@Context UriInfo uriInfo) {
        String path2 = uriInfo.getRequestUri().getPath();
        return "Hello from Jersey + Jetty!\n  RequestUri path: " + path2;
    }

    @GET
    @Path("{any: .*}")                       // 匹配任何深度的子路径 :contentReference[oaicite:0]{index=0}
    @Produces(MediaType.TEXT_PLAIN)
    public Response handleAll(@PathParam("any") String path,
                              @Context UriInfo uriInfo,
                              @Context Request request,
                              @Context HttpHeaders httpHeaders) {
        JettyPair<Integer, String> cached = JettyDataCache.get(path);
        if (cached != null) {
            log.info("Cached response: {}", cached);
            return Response.status(cached.first())
                    .entity(cached.second())
                    .build();
        }

        JettyPair<Integer, String> result = doProxy(path, request, httpHeaders);
        log.info("Proxy response: {}", result);

        return Response.status(result.first())
                .entity(result.second())
                .build();
    }

    private JettyPair<Integer, String> doProxy(String path, Request request, HttpHeaders httpHeaders) {
        Map<String, String> headers = new HashMap<>();
        httpHeaders.getRequestHeaders().forEach((k, v) -> headers.put(k, v.toString()));
        return JettyClient.doRequest(JettyConfig.URL, path, request.getMethod(), headers);
    }
}
