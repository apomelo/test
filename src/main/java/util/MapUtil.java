package util;

import java.util.Map;

/**
 * @author C
 * @date 2023/5/9
 */
public class MapUtil {
    public static <K, V> void putAllIfAbsent(Map<K, V> target, Map<K, V> src) {
        src.forEach((target::putIfAbsent));
    }
}
