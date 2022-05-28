package com.example.requestmapping.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 AbstractHandlerMethodMapping 에서 bean 으로 등록된 객체들 중
 handler 인 객체(@Controller, @RequestMapping 어노테이션이 붙은)를 찾아
 해당 객체 내부에 선언된 메소드 중 request mapping 이 가능한(@RequestMapping)
 메소드를 찾아 handler mapping 을 통해 MappingRegistry 객체에 등록합니다.

 다만 상속, 구현 내부에 메소드가 @RequestMapping 어노테이션이 있다면 handler mapping 을 통해 MappingRegistry 객체에 등록합니다.
 */
@Controller
@RequestMapping("/path")
public class MyController extends SuperController implements MyInterface {

    @RequestMapping("/home")
    public String home() {
        return "home";
    }
}


/*
    //2022-05-28 내용 추가
    1. MyController 에 @RequestMapping("/path") 이 생략되었을 경우 MyInterface 의 @RequestMapping("/if") 가 동작합니다.
    따라서 결과는 "/if/path" 로 핸들러 매핑이 등록됩니다.

    2. MyController 에 @RequestMapping("/path") 이 생략되었고, MyInterface 에도 @RequestMapping("/if") 가 생략되었을 경우
    SuperInterface 의 @RequestMapping("/superif") 가 동작합니다.
    따라서 결과는 "/superif/home" 로 핸들러 매핑이 등록됩니다.

    3. MyController 에 @RequestMapping("/path") 이 생략되었고, MyInterface 에도 @RequestMapping("/if") 가 생략되었고, SuperInterface 에도 @RequestMapping("/superif") 가 생략되었을 경우
    SuperController 의 @RequestMapping("/super") 가 동작합니다.
    따라서 결과는 "/super/home" 로 핸들러 매핑이 등록됩니다.

    하지만 MyInterface, SuperInterface, SuperController 전부 @RequestMapping 이 등록되어 있어도,
    MyController 클래스 레벨에 @RequestMapping 이 등록되어 있다면 해당 정보들은 무시됩니다.
    이 경우 MyController 의 @RequestMapping("/path") 가 동작합니다.
    따라서 결과는 "/path/home" 로 핸들러 매핑이 등록됩니다.

 */

