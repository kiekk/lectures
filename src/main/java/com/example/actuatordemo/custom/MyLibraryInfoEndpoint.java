package com.example.actuatordemo.custom;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;

import java.util.List;

@Endpoint(id = "myLibraryInfo")
public class MyLibraryInfoEndpoint {

    @ReadOperation
    public List<LibraryInfo> getLibraryInfos() {
        return List.of(
                LibraryInfo.builder().name("logback").version("1.0.0").build(),
                LibraryInfo.builder().name("jackson").version("2.0.0").build()
        );
    }
}
