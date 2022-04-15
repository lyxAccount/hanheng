package com.example.interfacedemo.controller.service;

import javax.mail.MessagingException;

public interface MailService {

    /**
     * 发送HTML邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     */
    String  sendHtmlMail(String to, String subject, String content) throws MessagingException;

    String sendSimple(String to, String subject, String content) throws MessagingException;
}
