package test.org.eclipse.jetty;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.ExecutionException;

@Slf4j
public class JettyDataCache {
    private static final LoadingCache<String, JettyPair<Integer, String>> loadingCache = CacheBuilder.newBuilder().build(new CacheLoader<>() {
        @Override
        public JettyPair<Integer, String> load(String s) throws Exception {
            return new JettyPair<>(200, "Cached: " + s);
        }
    });

    public static void init() {
        loadingCache.invalidateAll();
        JettyConfig.PATH_SET.forEach(s -> loadingCache.put(s, new JettyPair<>(200, "Initial Cached: " + s)));
    }

    public static JettyPair<Integer, String> get(String path) {
        try {
            if (!StringUtils.isEmpty(path)) {
                path = "/" + path;
            }
            return loadingCache.get(path);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
