<%@ page import="com.jsp.member.Member" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%
    request.setCharacterEncoding("UTF-8");

    // 세션이나 어플리케이션 영역에 저장해두고 사용
    // 여기서는 어플리케이션 영역에 저장된 memberList를 가져와본다.
    List<Member> memberList = (List<Member>) application.getAttribute("memberList");
    if (memberList == null) {
        memberList = new ArrayList<>();
    }

    String username = request.getParameter("username");
    String password = request.getParameter("password");

    if (username != null && !username.isEmpty()) {
        Member newMember = new Member(username, password);
        memberList.add(newMember);
    }

    // 다시 어플리케이션 영역에 저장
    application.setAttribute("memberList", memberList);

    // 저장 후 리스트 페이지로 이동
    response.sendRedirect("/member/list.jsp");
%>
