package com.example.requestmapping.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 AbstractHandlerMethodMapping 에서 bean 으로 등록된 객체들 중
 handler 인 객체(@Controller, @RequestMapping 어노테이션이 붙은)를 찾아
 해당 객체 내부에 선언된 메소드 중 request mapping 이 가능한(@RequestMapping)
 메소드를 찾아 handler mapping 을 통해 MappingRegistry 객체에 등록합니다.

 이 때 해당 객체 내부에서 찾기 때문에 상속, 구현에 적용된 @RequestMapping 어노테이션은 적용되지 않습니다.
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

