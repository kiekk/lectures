package com.inflearn.security.config.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inflearn.security.config.security.token.RestAuthenticationToken;
import com.inflearn.security.domain.dto.AccountDto;
import com.inflearn.security.util.WebUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;

import java.io.IOException;

public class RestAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public RestAuthenticationFilter(HttpSecurity http) {
        super(new AntPathRequestMatcher("/api/login", "POST"));
        setSecurityContextRepository(getSecurityContextRepository(http));
    }

    private SecurityContextRepository getSecurityContextRepository(HttpSecurity http) {
        SecurityContextRepository securityContextRepository = http.getSharedObject(SecurityContextRepository.class);
        if (securityContextRepository == null) {
            securityContextRepository = new DelegatingSecurityContextRepository(
                    new RequestAttributeSecurityContextRepository(), new HttpSessionSecurityContextRepository());
        }
        return securityContextRepository;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException {

        if (isNotPostAndAjaxRequest(request)) {
            throw new IllegalArgumentException("Authentication method not supported");
        }

        AccountDto accountDto = objectMapper.readValue(request.getReader(), AccountDto.class);

        if (inValidUsernameAndPassword(accountDto)) {
            throw new AuthenticationServiceException("Username or Password not provided");
        }

        RestAuthenticationToken token = new RestAuthenticationToken(accountDto.getUsername(), accountDto.getPassword());

        return this.getAuthenticationManager().authenticate(token);
    }

    private static boolean isNotPostAndAjaxRequest(HttpServletRequest request) {
        return !HttpMethod.POST.name().equals(request.getMethod()) || !WebUtil.isAjax(request);
    }

    private static boolean inValidUsernameAndPassword(AccountDto accountDto) {
        return !StringUtils.hasText(accountDto.getUsername()) || !StringUtils.hasText(accountDto.getPassword());
    }


}
