package com.Util;

import com.DAO.CodeDAO;
import com.DAO.UserDAO;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmailSendUtil {
    private String address;

    public String getCode() {
        return CodeGenerateUtil.generateUniqueCode();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean sendMail() throws MessagingException {
        //创建连接对象 连接到邮件服务器
        Properties properties = new Properties();
        //设置发送邮件的基本参数
        //发送邮件服务器
        properties.put("mail.smtp.host", "smtp.163.com");
        //发送端口
        properties.put("mail.smtp.port", "25");
        properties.put("mail.smtp.auth", "true");
        //设置发送邮件的账号和密码
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                //两个参数分别是发送邮件的账户和密码
                return new PasswordAuthentication("javaservertest@163.com","tmy15954031572");
            }
        });

        //创建邮件对象
        Message message = new MimeMessage(session);
        //设置发件人
        message.setFrom(new InternetAddress("javaservertest@163.com"));
        //设置收件人
        message.setRecipient(Message.RecipientType.TO,new InternetAddress(this.getAddress()));
        //设置主题
        message.setSubject("Verify your e-mail address");
        //设置邮件正文  第二个参数是邮件发送的类型
        String code = this.getCode().substring(0, 4);
        String text = "Your verification code is " + code;
        message.setContent(text,"text/html;charset=UTF-8");
        //发送一封邮件
        try {
            Transport.send(message);
        }catch(Exception exception){
            return false;
        }

        String currentTime = String.valueOf(System.currentTimeMillis());
        String deadTime = String.valueOf(System.currentTimeMillis() + 600000);
        String address = this.getAddress();
        int result = CodeDAO.createCode(currentTime, address, code,
                currentTime, deadTime);

        return result != -1;
    }
}
