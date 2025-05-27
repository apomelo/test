package test.base.ienum;

/**
 * Created by C on 2019/6/13.
 */
public class EnumUtils {
    public static <T extends IEnum> T getByCode(Class<T> enumClass, Integer code) {
        for (T e : enumClass.getEnumConstants()) {
            if (e.getCode() == code) {
                return e;
            }
        }
        return null;
    }
}
