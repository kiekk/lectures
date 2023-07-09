package com.example.actuatordemo.timer;

import io.micrometer.core.instrument.Timer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/timer")
public class TimerController {

    private final Timer myTimer;

    public TimerController(Timer myTimer) {
        this.myTimer = myTimer;
    }

    @RequestMapping("timer")
    public String timer() {
        myTimer.record(() -> {
            try {
                Thread.sleep(3_000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        return "ok";
    }
}
