package com.soono.controller.member;

import com.soono.controller.Controller;
import com.soono.model.Member;
import com.soono.model.MemberRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class MemberSaveController implements Controller {

    private final MemberRepository memberRepository = new MemberRepository();

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Member member = new Member(username, password);
        memberRepository.save(member);

        // JSP에서 사용하기 위해 request attribute에 저장
        request.setAttribute("member", member);

        // 저장 완료 페이지로 forward
        return "/WEB-INF/views/member/result.jsp";
    }
}
