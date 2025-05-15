# ☘️ 모델 1 방식 - JSP

---

## 📖 내용

- JSP (Java Server Page)를 사용하여 웹 애플리케이션의 모든 로직을 한 파일에서 처리하는 방식이다.
- 1999년 등장하여 초창기 Java 웹 애플리케이션 개발에서 많이 사용되었다.

### JSP (Java Server Page) 등장

- HTML 내에 Java 코드를 삽입하여 동적 웹 페이지를 생성하는 기술로서 서블릿의 단점을 보완하기 위해 등장한 서블릿 기반의 스크립트 언어이다.
- 모든 처리 로직이 하나의 JSP 파일에 존재한다.


### 특징

- HTML 내에 Java 코드를 직접 삽입할 수 있어 화면 로직을 작성하는 데 매우 편리하고 JSTL 과 같은 태그 라이브러리를 사용하여 반복, 조건, 포맷 등 다양한 작업을 쉽게 처리할 수 있다.
- 그러나 여전히 화면 코드와 자바 코드 모두 만들어야 하고 하나로 합치는 구조라서 서블릿에 비해 더 복잡하고 객체 지향적인 흐름을 방해한다.

### JSP 기반 요청 프로세스
<img src="https://user-images.githubusercontent.com/101503543/166148799-14a384e6-64e3-4640-922a-b6990027e423.jpg">

<sub>※ 이미지 출처: https://user-images.githubusercontent.com/101503543/166148799-14a384e6-64e3-4640-922a-b6990027e423.jpg</sub>

### Servlet vs JSP

<img src="https://blog.kakaocdn.net/dn/kyulu/btroKEM8aTG/v3rlRmL2TM0yVKz7Vu3c9K/img.png">

<sub>※ 이미지 출처: https://blog.kakaocdn.net/dn/kyulu/btroKEM8aTG/v3rlRmL2TM0yVKz7Vu3c9K/img.png</sub>

---

## 🔍 중심 로직

```
// /member/register.jsp
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

```

📌

---

## 💬 코멘트

- JSP: HTML 코드가 비즈니스 코드보다 많을 경우
- 서블릿: 비즈니스 코드가 HTML 코드보다 많을 경우

---
