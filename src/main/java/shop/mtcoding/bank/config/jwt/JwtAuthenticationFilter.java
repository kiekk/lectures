package shop.mtcoding.bank.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import shop.mtcoding.bank.auth.LoginUser;
import shop.mtcoding.bank.util.CustomResponseUtil;

import java.io.IOException;

import static shop.mtcoding.bank.dto.user.UserRequest.LoginRequest;
import static shop.mtcoding.bank.dto.user.UserResponse.LoginResponse;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        setFilterProcessesUrl("/api/login");
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            ObjectMapper om = new ObjectMapper();
            LoginRequest loginRequest = om.readValue(request.getInputStream(), LoginRequest.class);

            // 강제 로그인
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());

            Authentication authentication = authenticationManager.authenticate(authenticationToken); // UserDetailsService
            return authentication;
        } catch (Exception e) {
            // AuthenticationEntryPoint 에 걸린다.
            throw new InternalAuthenticationServiceException(e.getMessage());
        }
    }

    // attemptAuthentication 에서 return authentication 이 잘 작동하면 메서드가 호출됨.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        LoginUser loginUser = (LoginUser) authResult.getPrincipal();
        String jwtToken = JwtProcess.create(loginUser);
        response.setHeader(JwtVO.HEADER, jwtToken);

        LoginResponse loginResponse = new LoginResponse(loginUser);
        CustomResponseUtil.successAuthentication(response, loginResponse);
    }
}
