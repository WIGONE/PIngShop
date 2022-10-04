package com.PingShoping.email;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Email {
   public static String code;
    public void send(String receive) throws MessagingException {
        //创建Properties类用于记录邮箱的一些属性
        Properties props = new Properties();
        //是否需要密码验证
        props.put("mail.smtp.auth", "true");
        //此处写SMTP服务器
        props.put("mail.smtp.host", "smtp.qq.com");
        //端口号QQ邮箱端口587
        props.put("mail.smtp.port", "587");
        //此处填写，写信人的账号
        props.put("mail.user", "1347896447@qq.com");
        //此处填写16位STMP口令
        props.put("mail.password", "ccuydepuxolugjbj");
        //设置协议类型
        props.put("mail.transport.protocol", "smtp");
        Session session = Session.getInstance(props);
        session.setDebug(false);
        Transport transport = session.getTransport();
        //user为@之前的账号名
        transport.connect("smtp.qq.com", "1347896447", "ccuydepuxolugjbj");
        Message message = createSimpleMail(session, receive);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();

    }

    /**
     * 普通文本邮件模板
     *
     * @param session
     * @return+
     */
    private static Message createSimpleMail(Session session, String receive) throws MessagingException {
        MimeMessage message = new MimeMessage(session);
        //设置发件人
        /*  InternetAddress form = new InternetAddress(props.getProperty("mail.user"));*/
        message.setFrom("1347896447@qq.com");
        //设置收件人邮箱
        InternetAddress to = new InternetAddress(receive);
        message.setRecipients(Message.RecipientType.TO, receive);
        //设置邮件标题
        message.setSubject("验证码邮件");
        //设置邮件的内容体
        EmailContent ec = new EmailContent();
        String cont = ec.getContent();
        code = ec.code;

        message.setContent(cont, "text/html;charset=UTF-8");
        System.out.println(cont);
        return message;
    }


    public String getCode(Email e){
        return e.code;
    }
}
