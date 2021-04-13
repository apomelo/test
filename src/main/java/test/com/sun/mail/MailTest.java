package test.com.sun.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by C on 2020/4/14.
 */
public class MailTest {
    private static final Logger logger = LoggerFactory.getLogger(MailTest.class);

    public static void main(String[] args) {
//        testVerify();
        testSendEmail();
    }

    private static void testVerify() {
        List<String> emails = new ArrayList();
        emails.add("admin@yiibai.com");
        emails.add("yes2dos@gmail.com");
        emails.add("maxsu%google-cn.com");
        emails.add("maxsua@gmail-cn.com");
        emails.add("said#@youtube.co.in");
        emails.add("atosll@domaincom");
        emails.add("kitte#gmail.com");
        emails.add("@yiibai.com");
        emails.add("kit.a@yiibai.com");
        emails.add("kit.a-b@yi-bai.com");

        emails.forEach(email -> {
            boolean verify = MailUtils.verifyEmailFormat(email);
            logger.info("{}: {}", email, verify);
        });
    }

    private static void testSendEmail() {
        long before = System.currentTimeMillis();
        MailUtils.sendEmail("smtp.gmail.com", "smtp", true, "noreply@droidhen.com", "MCH5$EusJ1", false, "noreply@droidhen.com", "listentonatural@foxmail.com",
                "Idle Legend Verification Code",
                "Idle Legend Verification Code,<br/>\n" +
                "<br/>\n" +
                "Dear Adventurer,<br/>\n" +
                "<br/>\n\n\n\n" +
                "We received a request to reset your account password. <br/>\n" +
                "Your Idle Legend verification code is: <br/>\n" +
                "<b>xxxxxxxx</b><br/>\n" +
                "<br/>\n" +
                "If you didn’t request this code, it is possible that someone else is trying to access your account. Do not forward or give this code to anyone.<br/>\n");
        long after = System.currentTimeMillis();
        logger.info("{}", after - before);
    }
}
