package com.azhang.web.service;

public interface MailService {

    /**
     * 邮件发送
     * @param to 发送的邮箱
     * @param subject 发送的主题/标题
     * @param text 发送的内容
     * @return true 发送成功
     */
    boolean sendMail(String to, String subject, String text);

}
