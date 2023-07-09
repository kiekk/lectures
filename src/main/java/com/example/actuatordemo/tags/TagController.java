package com.example.actuatordemo.tags;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/tag")
public class TagController {

    private final MyQueueManagerWithTags myQueueManagerWithTags;

    public TagController(MyQueueManagerWithTags myQueueManagerWithTags) {
        this.myQueueManagerWithTags = myQueueManagerWithTags;
    }

    @GetMapping("push")
    public String push() {
        myQueueManagerWithTags.push();
        return "ok";
    }

    @GetMapping("pop")
    public String pop() {
        myQueueManagerWithTags.pop();
        return "ok";
    }

}
