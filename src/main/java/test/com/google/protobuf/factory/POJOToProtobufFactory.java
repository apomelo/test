package test.com.google.protobuf.factory;

import com.google.protobuf.GeneratedMessageV3;
import org.apache.commons.lang3.mutable.MutableInt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.List;

/**
 * Created by C on 2019/6/3.
 */
public class POJOToProtobufFactory {
    private static final Logger logger = LoggerFactory.getLogger(POJOToProtobufFactory.class);
    private static final int RECURSIVE_LIMIT = 1000;
    private static final String GET = "get";
    private static final String SET = "set";
    private static final String ADD = "add";
    private static final String LIST = "List";

    public static <T> T convert(Object model, Class<T> protobufClass) {
        return convert(model, protobufClass, new MutableInt(0));
    }

    // 嵌套需要进行循环处理，需要增加循环参数，防止无限循环
    private static <T> T convert(Object model, Class<T> protobufClass, MutableInt limit) {
        // 判断protobufClass是不是继承自GeneratedMessageV3
        if (!GeneratedMessageV3.class.isAssignableFrom(protobufClass)) {
            throw new RuntimeException("Not ProtoBuffer message type, type=" + protobufClass);
        }
        // 先把循环次数加1，再判断，循环次数大于限制，抛出异常
        if (limit.incrementAndGet() > RECURSIVE_LIMIT) {
            throw new RuntimeException("The recursive num reach " + RECURSIVE_LIMIT);
        }

        // 声明protobufObject对象
        T protobufObject = null;
        try {
            // 通过反射获取POJO的字段
            Field[] fields = model.getClass().getDeclaredFields();
            // 通过protobufClass的newBuilder方法获取protobufBuilder对象，protobuf对象是通过Builder对象构建对象
            Object protobufBuilder = protobufClass.getDeclaredMethod("newBuilder").invoke(null);

            // 遍历字段，依次赋值
            for (Field field : fields) {
                // 得到字段名称
                String fieldName = field.getName();
                // 把字段首字母大写，作为之后set、get方法的后缀
                String extName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                // 获取字段Class，例：interface java.util.List
                Class<?> modelType = field.getType();
                // 获取字段Type，例：java.util.List<java.lang.Float>
                Type modelGenericType = field.getGenericType();

                try {
                    // 获取该字段的get方法
                    Method modelGetMethod = model.getClass().getMethod(GET + extName);
                    // 通过调用get方法获取字段的值
                    Object modelValue = modelGetMethod.invoke(model);
                    // 如果字段没有赋值，进入下一个循环
                    if (modelValue == null) {
                        continue;
                    }

                    // 判断该字段是否为List类型
                    if (List.class.isAssignableFrom(modelType)) {
                        // 调用get方法获取返回type 注(protobuf get方法在后面加了"List"字段)
                        Type protobufGenericType = protobufClass.getMethod(GET + extName + LIST).getGenericReturnType();
                        // 定义POJO中该字段type
                        Type modelArgumentType = null;
                        // 定义protobuf中
                        Type protobufArgumentType = null;
                        // 获取list中参数具体类型，如：获取List<Float>中的Float类型
                        if (ParameterizedType.class.isAssignableFrom(modelGenericType.getClass())) {
                            Type[] actualTypeArguments = ((ParameterizedType) modelGenericType).getActualTypeArguments();
                            modelArgumentType = actualTypeArguments[0];
                        }
                        // 获取protobuf中对应的该字段的具体类型
                        if (ParameterizedType.class.isAssignableFrom(protobufGenericType.getClass())) {
                            Type[] actualTypeArguments = ((ParameterizedType) protobufGenericType).getActualTypeArguments();
                            protobufArgumentType = actualTypeArguments[0];
                        }

                        // 把字段由Object转换为List
                        List modelValueList = (List) modelValue;
                        // 遍历该字段
                        for (Object o : modelValueList) {
                            // 判断POJO和protobuf中的参数的类型是否相等，即是否是基础类型
                            if (modelArgumentType.equals(protobufArgumentType)) {
                                // 获取protobufBuilder对象的add方法（具体什么方法，根据类型不同而不同）
                                // 因为add方法中的类型是基础类型，List等的泛型参数类型是包装类型，所以需要先进行类型转化
                                // 由对应的包装类型得到基础类型
                                // 注：同名方法，参数为包装类型和基础类型，是两个方法
                                Method protobufBuilderAddMethed = protobufBuilder.getClass().getMethod(ADD + extName, getPrimitiveType((Class<?>) protobufArgumentType));
                                // 调用该方法
                                protobufBuilderAddMethed.invoke(protobufBuilder, o);
                            } else {
                                // POJO和protobuf中的参数的类型不相同，说明POJO的List泛型参数是一个POJO对象，
                                // protobuf的List泛型参数是一个protobuf对象，需要转换，调用转换方法
                                Object protobufValue = getProtobufValue((Class<?>) modelArgumentType, (Class<?>) protobufArgumentType, o, limit);
                                // 获取protobufBuilder对象的add方法
                                Method protobufBuilderAddMethed = protobufBuilder.getClass().getMethod(ADD + extName, (Class<?>) protobufArgumentType);
                                // 调用该方法
                                protobufBuilderAddMethed.invoke(protobufBuilder, protobufValue);
                            }
                        }

                    } else {
                        // 该字段不是List类型，在此为普通类型，非集合类型
                        // 获取get方法的返回类型
                        Class<?> protobufType = protobufClass.getMethod(GET + extName).getReturnType();

                        // 判断POJO和protobuf中的参数的类型是否相等，即是否是基础类型
                        if (modelType.equals(protobufType)) {
                            // 获取set方法基础类型get方法返回值即为基础类型，非包装类型
                            Method protobufBuilderSetMethod = protobufBuilder.getClass().getMethod(SET + extName, protobufType);
                            // 调用方法
                            protobufBuilderSetMethod.invoke(protobufBuilder, modelValue);
                        } else {
                            // 不相等说明是非基础类型，需要调用getProtobufValue方法把modelType转换为protobufType
                            Object protobufValue = getProtobufValue(modelType, protobufType, modelValue, limit);
                            // 获取set方法
                            Method protobufBuilderSetMethod = protobufBuilder.getClass().getMethod(SET + extName, protobufType);
                            // 调用方法
                            protobufBuilderSetMethod.invoke(protobufBuilder, protobufValue);
                        }
                    }

                } catch (NoSuchMethodException e) {
                    logger.warn("" + e);
                } catch (IllegalAccessException e) {
                    logger.warn("" + e);
                } catch (InvocationTargetException e) {
                    logger.warn("" + e);
                }
            }
            protobufObject = (T) protobufBuilder.getClass().getDeclaredMethod("build").invoke(protobufBuilder);
        } catch (Exception e) {
            logger.warn("{}", e);
        }

        return protobufObject;
    }

    private static <T> Object getProtobufValue(Class<?> modelType, Class<?> protobufType, Object modelValue, MutableInt limit) {
        Object protobufValue;
        // 判断model对象是enum对象还是POJO对象
        if (modelType.isEnum()) {
            // 获取所有protobuf enum对象的列表
            T[] enumConstants = (T[]) protobufType.getEnumConstants();
            // 返回忽略大小写后的名称相同的那一个
            protobufValue = Arrays.stream(enumConstants).filter(e -> e.toString().equalsIgnoreCase(modelValue.toString())).findFirst().get();
        } else {
            // 循环调用，把modelValue转换为protobufType
            protobufValue = convert(modelValue, protobufType, limit);
        }
        return protobufValue;
    }

    private static Class<?> getPrimitiveType(Class<?> objectClass) {
        switch (objectClass.getName()) {
            case "java.lang.Integer":
                return int.class;
            case "java.lang.Double":
                return double.class;
            case "java.lang.Float":
                return float.class;
            case "java.lang.Long":
                return long.class;
            case "java.lang.Short":
                return short.class;
            case "java.lang.Byte":
                return byte.class;
            case "java.lang.Boolean":
                return boolean.class;
            case "java.lang.Character":
                return char.class;
            default:
                return objectClass;
        }
    }
}
