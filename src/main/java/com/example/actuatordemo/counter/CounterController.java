package com.example.actuatordemo.counter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("counter")
public class CounterController {

    private final MyHttpRequestManager myHttpRequestManager;

    public CounterController(MyHttpRequestManager myHttpRequestManager) {
        this.myHttpRequestManager = myHttpRequestManager;
    }

    @GetMapping("req")
    public String req() {
        myHttpRequestManager.increase();
        return "ok";
    }

}
