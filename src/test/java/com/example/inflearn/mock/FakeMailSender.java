package com.example.inflearn.mock;

import com.example.inflearn.user.service.port.MailSender;

public class FakeMailSender implements MailSender {

    public String email;
    public String title;
    public String content;

    @Override
    public void sender(String email, String title, String content) {
        this.email = email;
        this.title = title;
        this.content = content;
    }
}
