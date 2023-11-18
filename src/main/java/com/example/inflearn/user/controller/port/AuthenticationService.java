package com.example.inflearn.user.controller.port;

public interface AuthenticationService {
    void login(Long id);

    void verifyEmail(Long id, String certificationCode);
}
