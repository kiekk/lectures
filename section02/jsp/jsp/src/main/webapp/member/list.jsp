<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.jsp.member.Member" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>Member List - Model1</title>
</head>
<body>
<h1>Member List (Model1)</h1>
<%
    List<Member> memberList = (List<Member>) application.getAttribute("memberList");
    if (memberList == null) {
        memberList = List.of();
    }
%>
<ul>
    <%
        for (Member m : memberList) {
    %>
    <li><%= m.getUsername() %> / <%= m.getPassword() %>
    </li>
    <%
        }
    %>
</ul>
<a href="/member/registerForm.jsp">Go to Register Form</a>
</body>
</html>
