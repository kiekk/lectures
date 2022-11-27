package com.tobyspring.studytobyspring.mail;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

// 테스트를 수행할 때는 굳이 메일을 전송할 필요가 없기 때문에
// 테스트용 더미 클래스를 만든다.
public class DummyMailSender implements MailSender {
    @Override
    public void send(SimpleMailMessage simpleMessage) throws MailException {

    }

    @Override
    public void send(SimpleMailMessage... simpleMessages) throws MailException {

    }
}
