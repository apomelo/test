package test.com.mongodb;

import com.mongodb.client.MongoClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import test.com.mongodb.entity.CategoryInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by C on 2019/1/7.
 */
@Component
public class MultiSourceTimer {

    private static final Logger logger = LoggerFactory.getLogger(MultiSourceTimer.class);

    @Value("${multisource.enable:false}")
    private boolean multisourceEnable;

    @Autowired(required = false)
    @Qualifier("commonMongoTemplate")
    private MongoTemplate commonMongoTemplate;

    @Autowired(required = false)
    @Qualifier("db0MongoTemplate")
    private MongoTemplate db0MongoTemplate;

    @Autowired(required = false)
    private MongoClient commonMongoClient;

    @Autowired
    private MongoMappingContext mongoMappingContext;

    @Autowired
    private BeanFactory beanFactory;

    private static Map<String, MongoTemplate> mongoTemplateMap = new HashMap<>();
    private static final String CATEGORY_COLLECTION_NAME = "topic_category_list";   // 表名

    public MongoTemplate getMongoTemplate(String source) {
        if (!multisourceEnable) {
            return commonMongoTemplate;
        }

        if (mongoTemplateMap.containsKey(source)) {
            return mongoTemplateMap.get(source);
        } else {
            Query query = new Query();
            query.addCriteria(Criteria.where("source_value").is(source));
            CategoryInfo categoryInfo = commonMongoTemplate.findOne(query, CategoryInfo.class, CATEGORY_COLLECTION_NAME);
            if (categoryInfo == null) {
                logger.error("getMongoTemplate failed, source={}", source);
                return commonMongoTemplate;
            }

            MongoTemplate mongoTemplate = getFreshMongoTemplate(categoryInfo.getDbName());
            mongoTemplateMap.put(source, mongoTemplate);
            return mongoTemplate;
        }
    }

    public MongoTemplate getDb0MongoTemplate() {
        return db0MongoTemplate;
    }

    public boolean isMultisourceEnable() {
        return multisourceEnable;
    }

    public MongoTemplate getFreshMongoTemplate(String dbName) {
        SimpleMongoClientDatabaseFactory simpleMongoDbFactory = new SimpleMongoClientDatabaseFactory(commonMongoClient, dbName);
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(simpleMongoDbFactory);
        MappingMongoConverter mappingMongoConverter = new MappingMongoConverter(dbRefResolver, mongoMappingContext);
        try {
            mappingMongoConverter.setCustomConversions(beanFactory.getBean(CustomConversions.class));
        } catch (NoSuchBeanDefinitionException ignore) {
        }

        mappingMongoConverter.setTypeMapper(new DefaultMongoTypeMapper(null));
        MongoTemplate mongoTemplate = new MongoTemplate(simpleMongoDbFactory, mappingMongoConverter);
        return mongoTemplate;
    }
}
