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

    /**
     * test the calendar function: dayOfWeek,year,month,day,hour,minute
     * Test the impact of setting timestamp and different time zones on the calendar
     */
    public static void test() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        logger.info("dayOfWeek: {}", dayOfWeek);
        logger.info("TimeZone: {}", calendar.getTimeZone());
        logger.info("year: {}, month: {}, day: {}, hour: {}, minute: {}", year, month, day, hour, minute);

        Calendar calendar2 = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        // set the millisecond
        calendar.setTimeInMillis(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 0);
        int dayOfWeek2 = calendar2.get(Calendar.DAY_OF_WEEK);
        int year2 = calendar2.get(Calendar.YEAR);
        int month2 = calendar2.get(Calendar.MONTH) + 1;
        int day2 = calendar2.get(Calendar.DAY_OF_MONTH);
        int hour2 = calendar2.get(Calendar.HOUR_OF_DAY);
        int minute2 = calendar2.get(Calendar.MINUTE);

        logger.info("dayOfWeek: {}", dayOfWeek2);
        logger.info("TimeZone: {}", calendar.getTimeZone());
        logger.info("year: {}, month: {}, day: {}, hour: {}, minute: {}", year2, month2, day2, hour2, minute2);

        Calendar calendar3 = Calendar.getInstance(TimeZone.getTimeZone("GMT+0"));
        // set the millisecond
        calendar.setTimeInMillis(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 0);
        int dayOfWeek3 = calendar3.get(Calendar.DAY_OF_WEEK);
        int year3 = calendar3.get(Calendar.YEAR);
        int month3 = calendar3.get(Calendar.MONTH) + 1;
        int day3 = calendar3.get(Calendar.DAY_OF_MONTH);
        int hour3 = calendar3.get(Calendar.HOUR_OF_DAY);
        int minute3 = calendar3.get(Calendar.MINUTE);

        logger.info("dayOfWeek: {}", dayOfWeek3);
        logger.info("TimeZone: {}", calendar.getTimeZone());
        logger.info("year: {}, month: {}, day: {}, hour: {}, minute: {}", year3, month3, day3, hour3, minute3);
    }
}
