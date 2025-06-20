package shop.mtcoding.bank.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import shop.mtcoding.bank.dto.ResponseDto;

public class CustomResponseUtil {

    private static final Logger log = LoggerFactory.getLogger(CustomResponseUtil.class);

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void success(HttpServletResponse response, Object dto) {
        try {
            ResponseDto<Object> responseDto = new ResponseDto<>(1, "로그인성공", dto);
            String responseBody = objectMapper.writeValueAsString(responseDto);
            response.setContentType("application/json; charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println(responseBody);
        } catch (Exception e) {
            log.error("서버 파싱 에러");
        }
    }


    public static void fail(HttpServletResponse response, String message, HttpStatus status) {
        try {
            ResponseDto<Object> responseDto = new ResponseDto<>(-1, message, null);
            String responseBody = objectMapper.writeValueAsString(responseDto);
            response.setContentType("application/json; charset=utf-8");
            response.setStatus(status.value());
            response.getWriter().println(responseBody);
        } catch (Exception e) {
            log.error("서버 파싱 에러");
        }
    }

}
