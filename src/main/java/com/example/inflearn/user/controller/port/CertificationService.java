package com.example.inflearn.user.controller.port;

public interface CertificationService {
    void send(String email, Long userId, String certificationCode);

    String generateCertificationUrl(Long userId, String certificationCode);
}
