<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.mvc.model.Member" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>Member List - Model2</title>
</head>
<body>
<h1>Member List (Model2)</h1>
<%
    List<Member> members = (List<Member>) request.getAttribute("members");
%>
<ul>
    <%
        for (Member m : members) {
    %>
    <li><%= m.getUsername() %> / <%= m.getPassword() %>
    </li>
    <%
        }
    %>
</ul>
<a href="/member/registerForm">Go to Register Form</a>
</body>
</html>
