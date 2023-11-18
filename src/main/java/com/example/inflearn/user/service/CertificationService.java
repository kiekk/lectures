package com.example.inflearn.user.service;

import com.example.inflearn.user.service.port.MailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CertificationService {

    private final MailSender mailSender;

    public void send(String email, Long userId, String certificationCode) {
        final String certificationUrl = generateCertificationUrl(userId, certificationCode);
        final String title = "Please certify your email address";
        final String content = "Please click the following link to certify your email address: " + certificationUrl;

        mailSender.sender(email, title, content);
    }

    public String generateCertificationUrl(Long userId, String certificationCode) {
        return "http://localhost:8080/api/users/" + userId + "/verify?certificationCode=" + certificationCode;
    }

}
