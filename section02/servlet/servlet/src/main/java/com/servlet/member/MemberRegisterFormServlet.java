package com.servlet.member;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "memberRegisterFormServlet", value = "/member/registerForm")
public class MemberRegisterFormServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("""
                <!DOCTYPE html>
                <html lang='ko'>
                <head>
                    <meta charset='UTF-8'>
                    <title>Register Form</title>
                </head>
                <body>
                    <h1>Register Form (Servlet)</h1>
                    <form action='/member/register' method='post'>
                        Username: <input type='text' name='username'><br>
                        Password: <input type='password' name='password'><br>
                        <button type='submit'>Register</button>
                    </form>
                </body>
                </html>
                """);
    }
}
