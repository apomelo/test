package test.com.alibaba.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by C on 2019/8/14.
 */
public class JsonTest {
    private static final Logger logger = LoggerFactory.getLogger(JsonTest.class);

    public static void main(String[] args) {
        testDefault();
        testDefault2();
        testSerializeConfig();
        testParse();
        testAnnotation();
        testGlobal1();
        testGlobal2();
        testNull();
    }

    public static void testDefault() {
        DataA dataA = new DataA(1, "sdf");
        String s = JSON.toJSONString(dataA);
        logger.info("testDefault: {}", s);
        DataA dataA1 = JSON.parseObject(s, DataA.class);
        logger.info("testDefault: {}", dataA1);
    }

    public static void testDefault2() {
        DataA dataA = new DataA();
        String s = JSON.toJSONString(dataA);
        logger.info("testDefault2: {}", s);
        DataA dataA1 = JSON.parseObject(s, DataA.class);
        logger.info("testDefault2: {}", dataA1);
    }

    public static void testSerializeConfig() {
        DataA dataA = new DataA(1, "sdf");
        SerializeConfig config = new SerializeConfig();
        config.setPropertyNamingStrategy(PropertyNamingStrategy.SnakeCase);
        String s = JSON.toJSONString(dataA, config);
        logger.info("testSerializeConfig: {}", s);
        DataA dataA1 = JSON.parseObject(s, DataA.class);
        logger.info("testSerializeConfig: {}", dataA1);
    }

    public static void testParse() {
        String s = "{\"test_string\":\"sdf\"}";
        logger.info("testParse: {}", s);
        DataA dataA1 = JSON.parseObject(s, DataA.class);
        logger.info("testParse: {}", dataA1);
    }

    public static void testAnnotation() {
        DataB dataB = new DataB(1, "sdf");
        String s = JSON.toJSONString(dataB);
        logger.info("testAnnotation: {}", s);
        DataB dataB1 = JSON.parseObject(s, DataB.class);
        logger.info("testAnnotation: {}", dataB1);
    }

    public static void testGlobal1() {
        DataA dataA = new DataA(1, "sdf");

        // TODO: 不生效，官方文档 https://github.com/alibaba/fastjson/wiki/PropertyNamingStrategy_cn
        SerializeConfig.getGlobalInstance().setPropertyNamingStrategy(PropertyNamingStrategy.SnakeCase);
        String s = JSON.toJSONString(dataA);
        logger.info("testGlobal1: {}", s);
        DataA dataA1 = JSON.parseObject(s, DataA.class);
        logger.info("testGlobal1: {}", dataA1);
    }

    public static void testGlobal2() {
        DataA dataA = new DataA(1, "sdf");
        String s = JSON.toJSONString(dataA);
        logger.info("testGlobal2: {}", s);
        DataA dataA1 = JSON.parseObject(s, DataA.class);
        logger.info("testGlobal2: {}", dataA1);
    }

    public static void testNull() {
        byte[] bytes = new byte[0];
        String s = new String(bytes);
        logger.info("testNull: {}", s);
        DataA dataA1 = JSON.parseObject(s, DataA.class);
        logger.info("testNull: {}", dataA1);
    }
}
