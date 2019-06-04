package test.java.util.concurrent;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by C on 2018/9/25.
 */
public class ConcurrentHashMapTest {

    public static void main(String[] args) {
        Map<String, String> map = new ConcurrentHashMap<String, String>();
        map.put("1", "a");
        map.put("2", "b");
        map.put("3", "c");

        Collection<String> strs = map.values();
        for (String s : strs) {
            if (s.equals("b")) {
                map.remove("2");
            }
            System.out.println(s);
        }
    }

}
