package com.example.studyhtmx.service;

import com.example.studyhtmx.entity.IotDevice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IotDeviceService {

    private final List<IotDevice> devices = List.of(new IotDevice(1L, "QWER-001"),
            new IotDevice(2L, "ASDF-342"),
            new IotDevice(3L, "ZXCV-976"),
            new IotDevice(4L, "TYUI-253"),
            new IotDevice(5L, "GHJK-975"),
            new IotDevice(6L, "VBNM-225")
    );

    public List<IotDevice> getDevices() {
        return devices;
    }

    public double getTemperature(IotDevice device) {
        try {
            Thread.sleep((long) (Math.random() * 790) + 10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return (Math.random() * 20) + 20;
    }

    public IotDevice getDevice(long id) {
        return devices.stream()
                .filter(iotDevice -> iotDevice.getId().equals(id))
                .findAny()
                .orElseThrow();
    }
}