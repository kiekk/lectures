package com.example.actuatordemo.timer;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/timer")
public class TimerController {

    private final Timer myTimer;
    private final MeterRegistry meterRegistry;

    public TimerController(Timer myTimer, MeterRegistry meterRegistry) {
        this.myTimer = myTimer;
        this.meterRegistry = meterRegistry;
    }

    @GetMapping("timer")
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


    @GetMapping("timer2")
    public String timer2() throws InterruptedException {
        Timer.Sample sample = Timer.start(meterRegistry);

        // do business login
        Thread.sleep(2_000);

        sample.stop(meterRegistry.timer("my.timer2"));
        return "ok";
    }

    @Timed("my.timer3")
    @GetMapping("timer3/{sleepSeconds}")
    public String timer3(@PathVariable int sleepSeconds) throws InterruptedException {
        Thread.sleep(sleepSeconds * 1_000L);
        return "ok";
    }
}
