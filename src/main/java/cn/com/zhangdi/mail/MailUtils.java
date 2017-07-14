package cn.com.zhangdi.mail;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.Properties;

/**
 * Created by zhangdi on 2017/02/28.
 */
public class MailUtils {

    private String strMailSmtpHost = "";//邮件服务器地址
    private String strUserName = "";//邮件账号
    private String strPassword = "";//邮件密码

    /**
     * 发送简单邮件
     *
     * @param recipient   收件人列表
     * @param ccRecipient 抄送人列表
     * @param strSubject  标题
     * @param strText     正文
     */
    public boolean sendSimpleTextEmail(String[] recipient, String[] ccRecipient, String strSubject, String strText) {
        Properties props = new Properties();
        props.setProperty("mail.smtp.auth", "true");
        props.put("mail.smtp.host", strMailSmtpHost);
        // props.put("mail.debug", "true");
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(strUserName, strPassword);
            }
        });
        // session.setDebug(true);
        try {
            // create a message
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(strUserName));
            for (String strRecipient : recipient) {
                msg.addRecipient(Message.RecipientType.TO, new InternetAddress(strRecipient));
            }

            for (String cc : ccRecipient) {
                msg.addRecipient(Message.RecipientType.CC, new InternetAddress(cc));
            }
            msg.setSubject(strSubject);
            msg.setSentDate(new Date());
            msg.setText(strText);
            Transport.send(msg);
            System.out.println("邮件发送成功," + strSubject);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /***
     *
     * @param itRecipient 收件人列表
     * @param ccRecipient 抄送人列表
     * @param strSubject 邮件标题
     * @param strText 邮件内容
     * @return
     */
    public boolean sendSimpleTextEmail(Iterable<String> itRecipient, Iterable[] ccRecipient, String strSubject, String strText) {
        Properties props = new Properties();
        props.setProperty("mail.smtp.auth", "true");
        props.put("mail.smtp.host", strMailSmtpHost);
        // props.put("mail.debug", "true");
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(strUserName, strPassword);
            }
        });
        // session.setDebug(true);
        try {
            // create a message
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(strUserName));
            for (String strRecipient : itRecipient) {
                msg.addRecipient(Message.RecipientType.TO, new InternetAddress(strRecipient));
            }
            msg.setSubject(strSubject);
            msg.setSentDate(new Date());
            msg.setText(strText);
            Transport.send(msg);
            System.out.println("邮件发送成功,{}" + strSubject);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 发送带附件的邮件
     *
     * @param astrRecipient     收件人列表
     * @param strSubject        标题
     * @param strText           正文
     * @param strAttachmentPath 附件绝对路径
     */
    public void sendEmailWithAttachment(String[] astrRecipient, String strSubject, String strText,
                                        String strAttachmentPath) {
        Properties props = new Properties();
        props.setProperty("mail.smtp.auth", "true");
        props.put("mail.smtp.host", strMailSmtpHost);
        // props.put("mail.debug", "true");
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(strUserName, strPassword);
            }
        });
        // session.setDebug(true);
        try {
            // create a message
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(strUserName));
            // InternetAddress[] address = { new
            // InternetAddress("22655080@qq.com") };
            // msg.setRecipients(Message.RecipientType.TO, address);
            for (String strRecipient : astrRecipient) {
                msg.addRecipient(Message.RecipientType.TO, new InternetAddress(strRecipient));
            }
            msg.setSubject(strSubject);

            // Create a multipar message
            Multipart mp = new MimeMultipart();

            // Create the message part
            MimeBodyPart mbp = new MimeBodyPart();

            // Now set the actual message
            mbp.setText(strText);

            // Set text message part
            mp.addBodyPart(mbp);

            // Part two is attachment
            mbp = new MimeBodyPart();
            FileDataSource fds = new FileDataSource(strAttachmentPath) {
                public String getContentType() {
                    return "application/octet-stream";
                }
            };
            mbp.setDataHandler(new DataHandler(fds));
            mbp.setFileName(fds.getName());
            mp.addBodyPart(mbp);

            // Send the complete message parts
            msg.setContent(mp);

            msg.setSentDate(new Date());
            // Send message
            Transport.send(msg);
            System.out.println("邮件发送成功,{}" + strSubject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
