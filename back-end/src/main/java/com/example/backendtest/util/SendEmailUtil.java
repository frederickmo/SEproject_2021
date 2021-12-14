package com.example.backendtest.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class SendEmailUtil {

    @Value("${mail.from.addr}")
    private String from;

    @Autowired
    JavaMailSender javaMailSender;

    /**
     * 发送普通文本邮件
     *
     * @param to      收件人
     * @param title 主题
     * @param content 内容
     */
    public void sendSimpleMail(String to, String title, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        //收信人
        message.setTo(to);
        //主题
        message.setSubject(title);
        //内容
        message.setText(content);
        //发信人
        message.setFrom(from);

        try {
            javaMailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送HTML邮件
     *
     * @param to      收件人
     * @param title 主题
     * @param content 内容（可以包含<html>等标签）
     */
    public void sendHtmlMail(String to, String title, String content) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(title);
            //true代表支持html
            helper.setText(content, true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
