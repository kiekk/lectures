package com.servlet.member;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "memberListServlet", value = "/member/list")
public class MemberListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        List<Member> memberList = MemberRegisterServlet.getMemberList();

        out.println("""
                <!DOCTYPE html>
                <html lang='ko'>
                <head>
                <meta charset='UTF-8'>
                <title>Member List</title>
                </head>
                <body>
                <h1>Member List (Servlet)</h1>
                <ul>
                """);

//        for (Member member : memberList) {
//            out.println("<li>" + member.getUsername() + "</li>");
//        }

        // Stream API 사용
        memberList.stream()
                .map(member -> "<li>" + member.getUsername() + "</li>")
                .forEach(out::println);

        out.println("""
                </ul>
                <a href='/member/registerForm'>Go to Register Form</a>
                </body>
                </html>
                """);
    }
}
