package test.com.mongodb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import sun.misc.Signal;
import sun.misc.SignalHandler;

/**
 * Created by C on 2019/1/29.
 */
@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationStartup.class);
    private ApplicationContext applicationContext;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        applicationContext = event.getApplicationContext();
        if (applicationContext.getParent() != null) {
            return;
        }
        try {
            applicationContext.getBean(MongoTest.class).start();
            registerShutdown();
        } catch (Exception e) {
            logger.error("启动失败, cause: {}", e);
            System.exit(-1);
        }
    }

    private void registerShutdown() {
        SignalProcess signalProcess = new SignalProcess();
        Signal.handle(new Signal("TERM"), signalProcess);
    }

    private class SignalProcess implements SignalHandler {
        @Override
        public void handle(Signal signal) {
            logger.info("receive signal TERM!!!");
            Runtime.getRuntime().halt(SpringApplication.exit(applicationContext));
        }
    }
}
