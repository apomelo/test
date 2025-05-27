//package test.com.sun.mail;
//
//import com.sun.mail.util.MailSSLSocketFactory;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.activation.DataHandler;
//import javax.activation.FileDataSource;
//import javax.mail.*;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeBodyPart;
//import javax.mail.internet.MimeMessage;
//import javax.mail.internet.MimeMultipart;
//import java.security.GeneralSecurityException;
//import java.util.Properties;
//
///**
// * Created by C on 2020/4/14.
// */
//public class MailUtils {
//    private static final Logger logger = LoggerFactory.getLogger(MailUtils.class);
//
//    public static void sendEmail(String host, String protocol, boolean auth, String username, String password, boolean debug,
//                                 String from, String to, String subject, String text) {
//        Transport ts = null;
//        try {
//            Properties prop = new Properties();
//            // 设置邮件服务器
//            prop.setProperty("mail.host", host);
//            // 邮件发送协议
//            prop.setProperty("mail.transport.protocol", protocol);
//            // 是否需要验证用户名密码
//            prop.put("mail.smtp.auth", auth);
//
//            // 关于QQ邮箱，还要设置SSL加密，加上以下代码即可
//            MailSSLSocketFactory sf = new MailSSLSocketFactory();
//            sf.setTrustAllHosts(true);
//            prop.put("mail.smtp.ssl.enable", true);
//            prop.put("mail.smtp.ssl.socketFactory", sf);
//
//            // 使用JavaMail发送邮件的5个步骤
//
//            // 2、创建定义整个应用程序所需的环境信息的 Session 对象
//            Session session = Session.getDefaultInstance(prop, new Authenticator() {
//                public PasswordAuthentication getPasswordAuthentication() {
//                    //发件人邮件用户名、授权码
//                    return new PasswordAuthentication(username, password);
//                }
//            });
//
//
//            // 开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
//            session.setDebug(debug);
//
//            // 2、通过session得到transport对象
//            ts = session.getTransport();
//
//            // 3、使用邮箱的用户名和授权码连上邮件服务器
//            ts.connect(host, username, password);
//
//            // 4、创建邮件
//
//            // 创建邮件对象
//            MimeMessage message = new MimeMessage(session);
//
//            // 指明邮件的发件人
//            message.setFrom(new InternetAddress(from));
//
//            // 指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
//            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
//
//            // 邮件的标题
//            message.setSubject(subject);
//
//            // 邮件的文本内容
//            // html格式，可以识别html标签
//            message.setContent(text, "text/html;charset=UTF-8");
//            // 普通文本格式
////            message.setText(text);
//
//            // 5、发送邮件
//            ts.sendMessage(message, message.getAllRecipients());
//        } catch (GeneralSecurityException | MessagingException e) {
//            logger.info("e=", e);
//        } finally {
//            if (ts != null) {
//                try {
//                    ts.close();
//                } catch (MessagingException e) {
//                    logger.info("e=", e);
//                }
//            }
//        }
//    }
//
//    public static void sendImageEmail(String host, String protocol, boolean auth, String username, String password, boolean debug,
//                                 String from, String to, String subject, String imgPath, String text) {
//        Transport ts = null;
//        try {
//            Properties prop = new Properties();
//            // 设置邮件服务器
//            prop.setProperty("mail.host", host);
//            // 邮件发送协议
//            prop.setProperty("mail.transport.protocol", protocol);
//            // 是否需要验证用户名密码
//            prop.put("mail.smtp.auth", auth);
//
//            // 关于QQ邮箱，还要设置SSL加密，加上以下代码即可
//            MailSSLSocketFactory sf = new MailSSLSocketFactory();
//            sf.setTrustAllHosts(true);
//            prop.put("mail.smtp.ssl.enable", true);
//            prop.put("mail.smtp.ssl.socketFactory", sf);
//
//            // 使用JavaMail发送邮件的5个步骤
//
//            // 2、创建定义整个应用程序所需的环境信息的 Session 对象
//            Session session = Session.getDefaultInstance(prop, new Authenticator() {
//                public PasswordAuthentication getPasswordAuthentication() {
//                    //发件人邮件用户名、授权码
//                    return new PasswordAuthentication(username, password);
//                }
//            });
//
//
//            // 开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
//            session.setDebug(debug);
//
//            // 2、通过session得到transport对象
//            ts = session.getTransport();
//
//            // 3、使用邮箱的用户名和授权码连上邮件服务器
//            ts.connect(host, username, password);
//
//            // 4、创建邮件
//
//            // 创建邮件对象
//            MimeMessage message = new MimeMessage(session);
//
//            // 指明邮件的发件人
//            message.setFrom(new InternetAddress(from));
//
//            // 指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
//            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
//
//            // 邮件的标题
//            message.setSubject(subject);
//
//            // 准备邮件数据
//
//            // 准备图片数据
//            MimeBodyPart imagePart = new MimeBodyPart();
//            DataHandler dh = new DataHandler(new FileDataSource("src/resources/bz.jpg"));
////            DataHandler dh = new DataHandler(new FileDataSource(imgPath));
//            imagePart.setDataHandler(dh);
//            imagePart.setContentID("bz.jpg");
//
//            // 准备正文数据
//            MimeBodyPart textPart = new MimeBodyPart();
//            textPart.setContent("这是一封邮件正文带图片<img src='cid:bz.jpg'>的邮件", "text/html;charset=UTF-8");
////            textPart.setContent(text, "text/html;charset=UTF-8");
//
//            // 描述数据关系
//            MimeMultipart mm = new MimeMultipart();
//            mm.addBodyPart(textPart);
//            mm.addBodyPart(imagePart);
//            mm.setSubType("related");
//
//            //设置到消息中，保存修改
//            message.setContent(mm);
//            message.saveChanges();
//
//            // 5、发送邮件
//            ts.sendMessage(message, message.getAllRecipients());
//        } catch (GeneralSecurityException | MessagingException e) {
//            logger.info("e=", e);
//        } finally {
//            if (ts != null) {
//                try {
//                    ts.close();
//                } catch (MessagingException e) {
//                    logger.info("e=", e);
//                }
//            }
//        }
//    }
//
//    public static boolean verifyEmail(String email, int length) {
//        boolean tag = verifyEmailLength(email, length);
//        if (tag) {
//            tag = verifyEmailFormat(email);
//        }
//        return tag;
//    }
//
//    public static boolean verifyEmailLength(String email, int length) {
//        boolean tag = true;
//        if (email.length() > length) {
//            tag = false;
//        }
//        return tag;
//    }
//
//    public static boolean verifyEmailFormat(String email) {
//        boolean tag;
//        if (!email.matches("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+")) {
//            tag = false;
//        } else {
//            String mailHost = email.substring(email.lastIndexOf("@") + 1);
//            logger.info("{}", mailHost);
//            tag = MailHostUtils.isMailHostValid(mailHost.toLowerCase());
//        }
//        return tag;
//    }
//}
