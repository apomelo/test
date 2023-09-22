package test.org.springframework.transaction.annotation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import test.org.springframework.SpringBootMain;

/**
 * @author C
 * @date 2022/8/8
 */
@Component
public class TransactionalTest {
    private static final Logger logger = LoggerFactory.getLogger(TransactionalTest.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringBootMain.class, args);

        TransactionalTest transactionalTest = context.getBean("transactionalTest", TransactionalTest.class);
        transactionalTest.test();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void test() {
        logger.info("------ currentProxy={}", AopContext.currentProxy());
        logger.info("------ insert 1");
        jdbcTemplate.execute("insert test1 (value1) values (1)");
        logger.info("------ insert 1");
        jdbcTemplate.execute("insert test1 (value1) values (1)");
        this.test2();
        test3();
        test4();
    }

    @Transactional(propagation = Propagation.NEVER)
    public void test2() {
        logger.info("------ currentProxy={}", AopContext.currentProxy());
        logger.info("------ insert 2");
        jdbcTemplate.execute("insert test1 (value1) values (2)");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void test3() {
        logger.info("------ currentProxy={}", AopContext.currentProxy());
        logger.info("------ insert 3");
        jdbcTemplate.execute("insert test1 (value1) values (3)");
    }

    @Transactional(propagation = Propagation.NEVER)
    public void test4() {
        logger.info("------ currentProxy={}", AopContext.currentProxy());
        logger.info("------ insert 4");
        jdbcTemplate.execute("insert test1 (value1) values (4)");
    }
}
