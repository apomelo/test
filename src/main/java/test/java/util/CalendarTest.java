package test.java.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by C on 2019/10/29.
 */
public class CalendarTest {
    private static final Logger logger = LoggerFactory.getLogger(CalendarTest.class);

    public static void main(String[] args) {
        test();
    }

    public static void test() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        calendar.setTimeInMillis(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 0);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        logger.info("{}", dayOfWeek);
        logger.info("{}", calendar.getTimeZone());
        logger.info("year {} month {} day {} hour {} minute {}", year, month, day, hour, minute);

        Calendar calendar2 = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        int dayOfWeek2 = calendar2.get(Calendar.DAY_OF_WEEK);
        int year2 = calendar2.get(Calendar.YEAR);
        int month2 = calendar2.get(Calendar.MONTH) + 1;
        int day2 = calendar2.get(Calendar.DAY_OF_MONTH);
        int hour2 = calendar2.get(Calendar.HOUR_OF_DAY);
        int minute2 = calendar2.get(Calendar.MINUTE);

        logger.info("{}", dayOfWeek2);
        logger.info("{}", calendar.getTimeZone());
        logger.info("year {} month {} day {} hour {} minute {}", year2, month2, day2, hour2, minute2);
    }
}
