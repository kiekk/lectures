package com.example.actuatordemo.custom;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Objects;

/**
 * @Endpoint = Web, Jmx 모두 지원
 * @WebEncpoint = Web 만 지원
 * @JmxEndpoint = Jmx 만 지원
 */
@Slf4j
@Endpoint(id = "myLibraryInfo")
public class MyLibraryInfoEndpoint {

    @WriteOperation
    public void changeSomething(String name, boolean enableSomething) {
        log.info("name: {}, enableSomething: {}", name, enableSomething);
    }

    @ReadOperation
    // array 는 Selector.Match.ALL_REMAINING
    public String getPathTest(@Selector(match = Selector.Match.ALL_REMAINING) String[] paths) {
        log.info("paths: {}", List.of(paths));
        return "paths : " + List.of(paths);
    }

    @ReadOperation
    // @Nullable 애노테이션은 jakarta 의 @Nullable 이 아닌
    // spring 의 @Nullable 을 사용해야 합니다.
    public List<LibraryInfo> getLibraryInfos(@Nullable String name, boolean includeVersion) {
        List<LibraryInfo> libraryInfos = List.of(
                LibraryInfo.builder().name("logback").version("1.0.0").build(),
                LibraryInfo.builder().name("jackson").version("2.0.0").build()
        );

        if (name != null) {
            libraryInfos = libraryInfos.stream().filter(libraryInfo -> Objects.equals(libraryInfo.getName(), name)).toList();
        }

        if (!includeVersion) {
            libraryInfos = libraryInfos.stream().map(libraryInfo -> LibraryInfo.builder().name(libraryInfo.getName()).build()).toList();
        }

        return libraryInfos;
    }
}
