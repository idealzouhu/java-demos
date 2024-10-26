package com.zouhu.email;

/**
 * 邮件发送服务类
 *
 * @author zouhu
 * @data 2024-09-05 21:09
 */
public class MailService {
    private final MailProperties properties;

    public MailService(MailProperties properties) {
        this.properties = properties;
    }

    /**
     * 发送邮件
     *
     * @param to
     * @param subject
     * @param text
     */
    public void sendMail(String to, String subject, String text) {
        System.out.println("Sending email...");
        System.out.println("Host: " + properties.getHost());
        System.out.println("Port: " + properties.getPort());
        System.out.println("From: " + properties.getFrom());
        System.out.println("To: " + to);
        System.out.println("Subject: " + subject);
        System.out.println("Text: " + text);
    }
}
