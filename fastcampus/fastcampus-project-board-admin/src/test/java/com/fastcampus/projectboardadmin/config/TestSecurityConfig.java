package com.fastcampus.projectboardadmin.config;

import com.fastcampus.projectboardadmin.domain.contant.RoleType;
import com.fastcampus.projectboardadmin.dto.AdminAccountDto;
import com.fastcampus.projectboardadmin.service.AdminAccountService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@Import(SecurityConfig.class)
@TestConfiguration
public class TestSecurityConfig {

    @MockBean
    private AdminAccountService adminAccountService;

    @BeforeTestMethod
    public void securitySetup() {
        given(adminAccountService.searchUser(anyString()))
                .willReturn(Optional.of(createAdminAccountDto()));
        given(adminAccountService.saveUser(anyString(), anyString(), anySet(), anyString(), anyString(), anyString()))
                .willReturn(createAdminAccountDto());
    }

    private AdminAccountDto createAdminAccountDto() {
        return AdminAccountDto.of(
                "soonoTest",
                "pw",
                Set.of(RoleType.USER),
                "soono-test@email.com",
                "soono-test",
                "test memo"
        );
    }

}
