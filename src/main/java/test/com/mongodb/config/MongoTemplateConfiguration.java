package test.com.mongodb.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoDriverInformation;
import com.mongodb.client.MongoClient;
import com.mongodb.client.internal.MongoClientImpl;
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
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.mapping.model.FieldNamingStrategy;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.util.ArrayList;

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
        return new CustomConversions(CustomConversions.StoreConversions.NONE, new ArrayList<>());
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
    public SimpleMongoClientDatabaseFactory commonMongoDbFactory() {
        return new SimpleMongoClientDatabaseFactory(commonMongoClient(), this.commonMongoProperties.getMongoClientDatabase());
    }

    @Bean
    @Primary
    public MongoClient commonMongoClient() {
        ConnectionString connectionString = new ConnectionString(this.commonMongoProperties.getUri());
        MongoClientSettings.builder().applyConnectionString(connectionString);
        return new MongoClientImpl(commonMongoClientSettings(), commonMongoDriverInformation());
    }

    @Bean
    @Primary
    public MongoClientSettings commonMongoClientSettings() {
        ConnectionString connectionString = new ConnectionString(this.commonMongoProperties.getUri());
        return MongoClientSettings.builder().applyConnectionString(connectionString).build();
    }

    @Bean
    @Primary
    public MongoDriverInformation commonMongoDriverInformation() {
        return MongoDriverInformation.builder().build();
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
    public SimpleMongoClientDatabaseFactory db0MongoDbFactory() {
        return new SimpleMongoClientDatabaseFactory(db0MongoClient(), this.db0MongoProperties.getMongoClientDatabase());
    }

    @Bean
    @Primary
    public MongoClient db0MongoClient() {
        ConnectionString connectionString = new ConnectionString(this.db0MongoProperties.getUri());
        MongoClientSettings.builder().applyConnectionString(connectionString);
        return new MongoClientImpl(db0MongoClientSettings(), db0MongoDriverInformation());
    }

    @Bean
    public MongoClientSettings db0MongoClientSettings() {
        ConnectionString connectionString = new ConnectionString(this.db0MongoProperties.getUri());
        return MongoClientSettings.builder().applyConnectionString(connectionString).build();
    }

    @Bean
    public MongoDriverInformation db0MongoDriverInformation() {
        return MongoDriverInformation.builder().build();
    }
}
