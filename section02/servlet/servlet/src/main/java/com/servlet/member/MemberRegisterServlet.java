package com.servlet.member;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "memberRegisterServlet", value = "/member/register")
public class MemberRegisterServlet extends HttpServlet {

    // 임시 저장소 구현
    private static final List<Member> memberList = new ArrayList<>();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Register Member
        Member member = new Member(username, password);
        memberList.add(member);

        response.sendRedirect("/member/list");
    }

    public static List<Member> getMemberList() {
        return memberList;
    }
}
