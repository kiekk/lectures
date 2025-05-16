package com.soono.controller.member;

import com.soono.controller.Controller;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
