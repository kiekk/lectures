package com.example.actuatordemo.info;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MyCustomInfoContributor implements InfoContributor {
    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("myCustomInfo", Map.of(
                "key1", "value1",
                "key2", "value2"
        ));
    }
}
