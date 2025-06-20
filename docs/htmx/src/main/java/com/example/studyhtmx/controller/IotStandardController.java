package com.example.studyhtmx.controller;

import com.example.studyhtmx.entity.IotDevice;
import com.example.studyhtmx.service.IotDeviceService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/iot/standard")
@RequiredArgsConstructor
public class IotStandardController {

    private final IotDeviceService service;

    @GetMapping
    public String index(Model model) {
        List<IotDevice> devices = service.getDevices();
        model.addAttribute("devices", devices.stream().map(device -> DeviceWithTemperature.of(device, service.getTemperature(device))).collect(Collectors.toList()));
        return "iot/standard/index";
    }

    @Getter
    public static class DeviceWithTemperature {

        private final long id;
        private final String name;
        private final double temperature;

        public DeviceWithTemperature(long id, String name, double temperature) {
            this.id = id;
            this.name = name;
            this.temperature = temperature;
        }

        public static DeviceWithTemperature of(IotDevice device, double temperature) {
            return new DeviceWithTemperature(device.getId(), device.getName(), temperature);
        }
    }
}
