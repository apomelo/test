package test.io.netty.util;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.Timer;
import io.netty.util.TimerTask;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author C
 * @date 2023/4/26
 */
@Slf4j
public class HashedWheelTimerTest {
    public static void main(String[] args) {

        Timer timer = new HashedWheelTimer();

        Timeout timeout1 = timer.newTimeout(new TimerTask() {
            @Override
            public void run(Timeout timeout) {
                log.info("timeout1: " + new Date());
            }
        }, 10, TimeUnit.SECONDS);

        if (!timeout1.isExpired()) {
            timeout1.cancel();
        }

        timer.newTimeout(timeout -> {
            log.info("timeout2: " + new Date());
            Thread.sleep(5000);
        }, 1, TimeUnit.SECONDS);

        timer.newTimeout(timeout -> {
            log.info("timeout3: " + new Date());
        }, 3, TimeUnit.SECONDS);
    }
}
