package test.com.mongodb.config;

import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.*;
import test.com.mongodb.config.MongoCondition;

/**
 * Created by C on 2019/1/12.
 */
@Configuration
@Conditional(MongoCondition.class)
@PropertySource("classpath:mongodb.properties")
public class MultipleMongoProperties {

    @Bean(name = "commonMongoProperties")
    @Primary
    @Conditional(MongoCondition.class)
    @ConfigurationProperties(prefix = "spring.data.mongodb.common")
    public MongoProperties commonMongoProperties() {
        return new MongoProperties();
    }

    @Bean(name = "db0MongoProperties")
    @Conditional(MongoCondition.class)
    @ConfigurationProperties(prefix = "spring.data.mongodb.db0")
    public MongoProperties db0MongoProperties() {
        return new MongoProperties();
    }

}
