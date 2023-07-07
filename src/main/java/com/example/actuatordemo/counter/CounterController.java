package com.example.actuatordemo.counter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("counter")
public class CounterController {

    private final MyHttpRequestManager myHttpRequestManager;
    private final MyHttpRequestWithoutMicrometer myManager;

    public CounterController(MyHttpRequestManager myHttpRequestManager, MyHttpRequestWithoutMicrometer myManager) {
        this.myHttpRequestManager = myHttpRequestManager;
        this.myManager = myManager;
    }

    @GetMapping("req")
    public String req() {
        myHttpRequestManager.increase();
        myManager.increase();
        return "ok";
    }

}
