package test.com.mongodb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import test.com.mongodb.entity.TopicVoiceLog;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by C on 2019/1/29.
 */
@Component
public class MongoTest {
    private static final Logger logger = LoggerFactory.getLogger(MongoTest.class);

    @Autowired
    private MultiSourceTimer multiSourceTimer;

    public void start() {
        logger.info("prepare test mongodb");
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            logger.warn("{}", e);
        }
        logger.info("start test mongodb");
        MongoTemplate mongoTemplate = multiSourceTimer.getMongoTemplate("");
        multiSourceTimer.getDb0MongoTemplate();
        TopicVoiceLog topicVoiceLog = getTopicVoiceLog();

        getInsertThousandDataTime(mongoTemplate, topicVoiceLog);
        getInsertTenThousandDataTime(mongoTemplate, topicVoiceLog);
        getInsertHundredThousandDataTime(mongoTemplate, topicVoiceLog);

        getInsertThousandDataTime(mongoTemplate, topicVoiceLog);
        getInsertTenThousandDataTime(mongoTemplate, topicVoiceLog);
        getInsertHundredThousandDataTime(mongoTemplate, topicVoiceLog);

        logger.info("done");
        System.exit(0);
    }

    private void getInsertMillionDataTime(MongoTemplate mongoTemplate, TopicVoiceLog topicVoiceLog) {
        long existDataNum = getExistDataNum(mongoTemplate, TopicVoiceLog.class);
        logger.info("已有{}条数据", existDataNum);
        long preMilliTime = System.currentTimeMillis();
        logger.info("插入一百万条数据, 开始时间: {}ms", preMilliTime);
        insertMillionData(mongoTemplate, topicVoiceLog);
        long postMilliTime = System.currentTimeMillis();
        logger.info("插入一百万条数据, 完成时间: {}ms, 用时: {}ms", postMilliTime, postMilliTime - preMilliTime);
    }

    private void getInsertHundredThousandDataTime(MongoTemplate mongoTemplate, TopicVoiceLog topicVoiceLog) {
        long existDataNum = getExistDataNum(mongoTemplate, TopicVoiceLog.class);
        logger.info("已有{}条数据", existDataNum);
        long preMilliTime = System.currentTimeMillis();
        logger.info("插入十万条数据, 开始时间: {}ms", preMilliTime);
        insertHundredThousandData(mongoTemplate, topicVoiceLog);
        long postMilliTime = System.currentTimeMillis();
        logger.info("插入十万条数据, 完成时间: {}ms, 用时: {}ms", postMilliTime, postMilliTime - preMilliTime);
    }

    private void getInsertTenThousandDataTime(MongoTemplate mongoTemplate, TopicVoiceLog topicVoiceLog) {
        long existDataNum = getExistDataNum(mongoTemplate, TopicVoiceLog.class);
        logger.info("已有{}条数据", existDataNum);
        long preMilliTime = System.currentTimeMillis();
        logger.info("插入一万条数据, 开始时间: {}ms", preMilliTime);
        insertTenThousandData(mongoTemplate, topicVoiceLog);
        long postMilliTime = System.currentTimeMillis();
        logger.info("插入一万条数据, 完成时间: {}ms, 用时: {}ms", postMilliTime, postMilliTime - preMilliTime);
    }

    private void getInsertThousandDataTime(MongoTemplate mongoTemplate, TopicVoiceLog topicVoiceLog) {
        long existDataNum = getExistDataNum(mongoTemplate, TopicVoiceLog.class);
        logger.info("已有{}条数据", existDataNum);
        long preMilliTime = System.currentTimeMillis();
        logger.info("插入一千条数据, 开始时间: {}ms", preMilliTime);
        insertThousandData(mongoTemplate, topicVoiceLog);
        long postMilliTime = System.currentTimeMillis();
        logger.info("插入一千条数据, 完成时间: {}ms, 用时: {}ms", postMilliTime, postMilliTime - preMilliTime);
    }

    private void insertMillionData(MongoTemplate mongoTemplate, TopicVoiceLog topicVoiceLog) {
        for (int i = 0; i < 1000000; i ++) {
            mongoTemplate.insert(topicVoiceLog);
        }
    }

    private void insertHundredThousandData(MongoTemplate mongoTemplate, TopicVoiceLog topicVoiceLog) {
        for (int i = 0; i < 100000; i ++) {
            mongoTemplate.insert(topicVoiceLog);
        }
    }

    private void insertTenThousandData(MongoTemplate mongoTemplate, TopicVoiceLog topicVoiceLog) {
        for (int i = 0; i < 10000; i ++) {
            mongoTemplate.insert(topicVoiceLog);
        }
    }

    private void insertThousandData(MongoTemplate mongoTemplate, TopicVoiceLog topicVoiceLog) {
        for (int i = 0; i < 1000; i ++) {
            mongoTemplate.insert(topicVoiceLog);
        }
    }

    private long getExistDataNum(MongoTemplate mongoTemplate, Class<?> clazz) {
        Query query = new Query();
        long count = mongoTemplate.count(query, clazz);
        return count;
    }

    private TopicVoiceLog getTopicVoiceLog() {
        TopicVoiceLog topicVoiceLog = new TopicVoiceLog();
        long curTime = System.currentTimeMillis();
        int intTime = (int) curTime / 1000;

        topicVoiceLog.setSessionId(UUID.randomUUID().toString());
        topicVoiceLog.setResponseSource("mongo_test");
        topicVoiceLog.setQuestionText("你们公司是干什么的");
        topicVoiceLog.setQuestionGroupName("mongo_test");
        topicVoiceLog.setQuestionVoice("mongo_test");
        topicVoiceLog.setVoiceAttrs(new HashMap<String, Integer>(){{put("123", 123);}});
        topicVoiceLog.setAnswerText("我们是mongo_test公司的, 专门进行mongodb的性能测试");
        topicVoiceLog.setAnswerType("mongo_test");
        topicVoiceLog.setAnswerGroupName("mongo_test");
        topicVoiceLog.setAnswerVoice("mongo_test");
        topicVoiceLog.setMatchedTopicId("你们公司是干什么的");
        topicVoiceLog.setMatchedTopicName("你们公司是干什么的");
        topicVoiceLog.setIsMultiTopic("false");
        topicVoiceLog.setUserCodeId("12345678");
        topicVoiceLog.setQuestionTypeId("mongo_test");
        topicVoiceLog.setQuestionType("mongo_test");
        topicVoiceLog.setDialogueNumber(0);
        topicVoiceLog.setDialogueStartTime(intTime);
        topicVoiceLog.setUserAskTime(intTime);
        topicVoiceLog.setAnswerPlayTime(intTime);
        topicVoiceLog.setDialogueEndTime(intTime);
        topicVoiceLog.setDialogueState(1);
        topicVoiceLog.setVoiceTransferCheckResult(1);
        topicVoiceLog.setVoiceTransferRightResult("我们是mongo_test公司的, 专门进行mongodb的性能测试");
        topicVoiceLog.setRobotAnswerCheckResult(1);
        topicVoiceLog.setRobotAnswerRightResult("我们是mongo_test公司的, 专门进行mongodb的性能测试");
        topicVoiceLog.setCheckFlag(1);
        topicVoiceLog.setCheckUser("admin");
        topicVoiceLog.setUpdateTime(intTime);
        topicVoiceLog.setCreateTime(intTime);

        return topicVoiceLog;
    }
}
