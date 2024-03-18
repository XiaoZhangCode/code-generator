package com.azhang.web.service.impl;

import com.azhang.web.service.MailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MailServiceImpl implements MailService {

    /**
     * springboot专门发送邮件接口
     */
    @Resource
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    public boolean sendMail(String to, String subject, String text) {
        SimpleMailMessage msg = new SimpleMailMessage();
        //发送邮件的邮箱
        msg.setFrom(from);
        //发送到哪(邮箱)
        msg.setTo(to);
        //邮箱标题
        msg.setSubject(subject);
        //邮箱文本
        msg.setText(text);
        try {
            mailSender.send(msg);
        } catch (MailException ex) {
            System.err.println(ex.getMessage());
            return false;
        }
        return true;
    }



}
