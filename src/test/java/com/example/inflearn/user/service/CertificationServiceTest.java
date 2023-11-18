package com.example.inflearn.user.service;

import com.example.inflearn.mock.FakeMailSender;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CertificationServiceTest {

    @Test
    void 이메일과_컨텐츠가_제대로_만들어져서_보내지는지_테스트한다() {
        // given
        final FakeMailSender mailSender = new FakeMailSender();
        final CertificationServiceImpl certificationService = new CertificationServiceImpl(mailSender);

        // when
        final String email = "shyoon991@gmail.com";
        final Long userId = 1L;
        final String certificationCode = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa";
        certificationService.send(email, userId, certificationCode);

        // then
        assertThat(mailSender.email).isEqualTo(email);
        assertThat(mailSender.title).isEqualTo("Please certify your email address");
        assertThat(mailSender.content)
                .isEqualTo("Please click the following link to certify your email address: http://localhost:8080/api/users/1/verify?certificationCode=aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
    }
}
