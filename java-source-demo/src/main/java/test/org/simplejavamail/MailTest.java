package test.org.simplejavamail;

import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by C on 2020/4/14.
 */
public class MailTest {
    private static final Logger logger = LoggerFactory.getLogger(MailTest.class);

    public static void main(String[] args) {
        test1();
    }

    private static void test1() {
        Mailer mailer = MailerBuilder
                .withSMTPServer("smtp.host.com", 587, "user@host.com", "password")
                .withTransportStrategy(TransportStrategy.SMTP_TLS)
                .withSessionTimeout(10 * 1000)
                .clearEmailValidator()
                .withProperty("mail.smtp.sendpartial", true)
                .withDebugLogging(true)
                .resetClusterKey()
                .buildMailer();
        mailer.shutdownConnectionPool();
        Email email = EmailBuilder.startingBlank()
                .to()
                .withSubject("hey")
                .buildEmail();
        mailer.sendMail(email);
    }
}
