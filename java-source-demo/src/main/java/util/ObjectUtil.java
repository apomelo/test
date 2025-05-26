package util;

/**
 * @author C
 * @date 2023/6/24
 */
public class ObjectUtil {
    public static String getName(Object object) {
        return getName(object.getClass());
    }

    public static String getName(Class<?> objectClass) {
        String[] split = objectClass.getName().split("\\.");
        return split[split.length - 1];
    }
}
