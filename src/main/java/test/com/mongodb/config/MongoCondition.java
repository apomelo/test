package test.com.mongodb.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.io.*;
import java.util.Properties;

/**
 * Created by C on 2019/1/8.
 */
public class MongoCondition implements Condition {
    private static final Logger logger = LoggerFactory.getLogger(MongoCondition.class);

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        try {
            String confPath = MongoCondition.class.getClassLoader().getResource("mongodb.properties").getPath();
            String filePath = (new File(confPath)).getPath();
            Properties properties = new Properties();
            InputStream in = new BufferedInputStream(new FileInputStream(filePath));
            properties.load(new InputStreamReader(in, "utf-8"));

            String mongodbEnableStr = properties.getProperty("mongodb.enable");
            return Boolean.parseBoolean(mongodbEnableStr);
        } catch (Exception e) {
            logger.error("{}", e);
        }
        return false;
    }
}
