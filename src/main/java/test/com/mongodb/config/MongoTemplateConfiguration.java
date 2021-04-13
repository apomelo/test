package test.com.mongodb.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.domain.EntityScanner;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.annotation.Persistent;
import org.springframework.data.mapping.model.FieldNamingStrategy;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.util.Collections;

/**
 * Created by C on 2019/1/12.
 */
@Configuration
@Conditional(MongoCondition.class)
@ConditionalOnClass({MultipleMongoProperties.class})
public class MongoTemplateConfiguration {

    private final ApplicationContext applicationContext;

    @Autowired
    @Qualifier("commonMongoProperties")
    private MongoProperties commonMongoProperties;

    @Autowired
    @Qualifier("db0MongoProperties")
    private MongoProperties db0MongoProperties;

    public MongoTemplateConfiguration(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public MongoMappingContext mongoMappingContext(BeanFactory beanFactory,
                                                   CustomConversions conversions) throws ClassNotFoundException {
        MongoMappingContext context = new MongoMappingContext();
        context.setInitialEntitySet(new EntityScanner(this.applicationContext)
                .scan(Document.class, Persistent.class));
        Class<?> strategyClass = this.commonMongoProperties.getFieldNamingStrategy();
        if (strategyClass != null) {
            context.setFieldNamingStrategy(
                    (FieldNamingStrategy) BeanUtils.instantiate(strategyClass));
        }
        context.setSimpleTypeHolder(conversions.getSimpleTypeHolder());
        return context;
    }

    @Bean
    public CustomConversions mongoCustomConversions() {
        return new CustomConversions(Collections.emptyList());
    }

    @Primary
    @Bean("commonMongoTemplate")
    public MongoTemplate commonMongoTemplate(MongoMappingContext context, BeanFactory beanFactory) {
        return new MongoTemplate(commonMongoDbFactory(), commonMappingMongoConverter(context, beanFactory));
    }

    @Bean
    @Primary
    public MappingMongoConverter commonMappingMongoConverter(MongoMappingContext context, BeanFactory beanFactory) {
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(commonMongoDbFactory());
        MappingMongoConverter mappingConverter = new MappingMongoConverter(dbRefResolver,
                context);
        try {
            mappingConverter.setCustomConversions(beanFactory.getBean(CustomConversions.class));
        } catch (NoSuchBeanDefinitionException ignore) {
        }
        mappingConverter.setTypeMapper(new DefaultMongoTypeMapper(null));
        return mappingConverter;
    }

    @Bean
    @Primary
    public SimpleMongoDbFactory commonMongoDbFactory() {
        return new SimpleMongoDbFactory(commonMongoClient(), this.commonMongoProperties.getMongoClientDatabase());
    }

    @Bean
    @Primary
    public MongoClient commonMongoClient() {
        return new MongoClient(new MongoClientURI(this.commonMongoProperties.getUri()));
    }
    
    @Bean("db0MongoTemplate")
    public MongoTemplate db0MongoTemplate(MongoMappingContext context, BeanFactory beanFactory) {
        return new MongoTemplate(db0MongoDbFactory(), db0MappingMongoConverter(context, beanFactory));
    }

    @Bean
    public MappingMongoConverter db0MappingMongoConverter(MongoMappingContext context, BeanFactory beanFactory) {
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(db0MongoDbFactory());
        MappingMongoConverter mappingConverter = new MappingMongoConverter(dbRefResolver,
                context);
        try {
            mappingConverter.setCustomConversions(beanFactory.getBean(CustomConversions.class));
        } catch (NoSuchBeanDefinitionException ignore) {
        }
        mappingConverter.setTypeMapper(new DefaultMongoTypeMapper(null));
        return mappingConverter;
    }

    @Bean
    public SimpleMongoDbFactory db0MongoDbFactory() {
        MongoClient db0MongoClient = new MongoClient(new MongoClientURI(this.db0MongoProperties.getUri()));
        return new SimpleMongoDbFactory(db0MongoClient, this.db0MongoProperties.getMongoClientDatabase());
    }
}
