# ☘️ 프론트 컨트롤러 패턴 이해

---

## 📖 내용

- 웹 애플리케이션이 점점 복잡해지고 유지보수와 확장성을 필요로 하게 되면서 모델 2 방식을 개선한 프론트 컨트롤러 패턴이 등장하게 되었다.
- 프론트 컨트롤러 패턴은 모델 2 방식의 단점을 보완하고 서블릿의 요청과 응답을 좀 더 구조적이고 체계적으로 관리하기 위해 탄생한 패턴이다.
- 프론트 컨트롤러 패턴은 모든 요청을 하나의 서블릿이 처리하고, 그 서블릿이 요청에 따라 적절한 뷰를 선택하여 응답하는 구조로 되어 있다.
  - 서블릿 객체들이 요청마다 동일한 기능을 똑같이 처리
  - 서블릿 객체들이 비슷한 기능의 유사 코드를 중복해서 실행
  - 서블릿 객체들이 재사용해야 하는 객체들의 관리 문제
  - 기타...

<img src="https://i0.wp.com/www.dineshonjava.com/wp-content/uploads/2017/12/FrontController.gif?resize=621%2C220&ssl=1" width="100%px">

<sub>※ 이미지 출처: https://i0.wp.com/www.dineshonjava.com/wp-content/uploads/2017/12/FrontController.gif?resize=621%2C220&ssl=1</sub>

- 모든 클라이언트 요청을 무조건 단일 진입점을 거치도록 강제 함으로서 요청 처리의 일관성을 유지할 수 있고 중앙에서 관리할 수 있게 된다
- 인증, 권한 부여, 로깅, 예외 처리 등의 공통 기능을 프론트 컨트롤러에서 일괄 처리해서 코드 중복을 줄이고, 유지보수를 용이하게 할 수 있다
- 공통 기능을 수정할 때 프론트 컨트롤러에서만 변경하면 되므로 수정이 용이하고 필요 시 각 서블릿을 쉽게 추가하고 확장 할 수 있다

---

## 🔍 중심 로직

```java
// Controller.java
// 요청 처리 방식의 일관성을 위해 interface로 정의
public interface Controller {
  /**
   * 요청을 처리하고, 뷰 경로(viewPath)나 리다이렉트 주소 등을 반환
   */
  String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}


// MemberFrontController.java
@WebServlet(name = "memberFrontControllerServlet", urlPatterns = "/members/*")
public class MemberFrontController extends HttpServlet {

    // 요청 URL -> Controller 매핑정보
    private final Map<String, Controller> controllerMap = new HashMap<>();

    @Override
    public void init() throws ServletException {
        // 매핑 등록
        controllerMap.put("/members/new-form", new MemberFormController());
        controllerMap.put("/members/save", new MemberSaveController());
        controllerMap.put("/members/list", new MemberListController());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        Controller controller = controllerMap.get(requestURI);

        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 해당 컨트롤러(핸들러) 실행
        String viewPath = controller.process(request, response);

        request.getRequestDispatcher(viewPath).forward(request, response);
    }
}
```

📌

---

## 💬 코멘트

---
