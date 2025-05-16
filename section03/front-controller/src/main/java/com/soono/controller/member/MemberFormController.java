package com.soono.controller.member;

import com.soono.controller.Controller;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class MemberFormController implements Controller {

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 단순히 폼 화면(JSP)으로 forward
        return "/WEB-INF/views/member/form.jsp";
    }
}
