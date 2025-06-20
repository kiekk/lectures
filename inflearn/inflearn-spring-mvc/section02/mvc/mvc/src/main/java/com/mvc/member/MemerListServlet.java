package com.mvc.member;

import com.mvc.model.Member;
import com.mvc.model.MemberRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "memerListController", urlPatterns = "/member/list")
public class MemerListServlet extends HttpServlet {

    private final MemberRepository memberRepository = new MemberRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Member> members = memberRepository.findAll();
        request.setAttribute("members", members);

        String viewPath = "/WEB-INF/member/list.jsp";
        request.getRequestDispatcher(viewPath).forward(request, response);
    }
}
