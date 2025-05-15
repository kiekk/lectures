package com.mvc.member;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "memberRegisterFormController", urlPatterns = "/member/registerForm")
public class MemberRegisterFormServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 뷰(JSP)로 포워딩
        String viewPath = "/WEB-INF/member/registerForm.jsp";
        request.getRequestDispatcher(viewPath).forward(request, response);
    }
}
