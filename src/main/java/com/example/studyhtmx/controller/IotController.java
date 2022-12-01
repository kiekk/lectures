package com.example.studyhtmx.controller;

import com.example.studyhtmx.service.IotDeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/iot")
@RequiredArgsConstructor
public class IotController {

    private final IotDeviceService service;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("devices", service.getDevices());
        return "iot/index";
    }

}
