package test.com.google.protobuf.factory;

import com.google.protobuf.GeneratedMessageV3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by C on 2019/6/3.
 */
public class ProtobufToPOJOFactory {
    private static final Logger logger = LoggerFactory.getLogger(ProtobufToPOJOFactory.class);

    // TODO: 暂且不能处理嵌套类型，嵌套需要进行循环处理
    public static <T> T convert(GeneratedMessageV3 protobufObject, Class<T> modelClass) {
        T model = null;

        try {
            model = modelClass.newInstance();
            Field[] fields = modelClass.getDeclaredFields();
            for (Field field : fields) {
                String fieldName = field.getName();
                String extName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                Class<?> fieldType = field.getType();

                try {
                    Method protobufGetMethod = protobufObject.getClass().getMethod("get" + extName);
                    Object value = protobufGetMethod.invoke(protobufObject);

                    Method modelSetMethod = modelClass.getMethod("set" + extName, fieldType);
                    modelSetMethod.invoke(model, value);
                } catch (NoSuchMethodException e) {
                    logger.warn(e.toString());
                } catch (IllegalAccessException e) {
                    logger.warn(e.toString());
                } catch (InvocationTargetException e) {
                    logger.warn(e.toString());
                }
            }
        } catch (Exception e) {
            logger.warn(e.toString());
        }

        return model;
    }
}
