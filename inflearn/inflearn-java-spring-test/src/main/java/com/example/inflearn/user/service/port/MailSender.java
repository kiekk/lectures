package com.example.inflearn.user.service.port;

public interface MailSender {
    void sender(String email, String title, String content);
}
