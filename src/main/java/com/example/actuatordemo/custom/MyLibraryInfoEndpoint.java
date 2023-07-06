package com.example.actuatordemo.custom;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Objects;

@Endpoint(id = "myLibraryInfo")
public class MyLibraryInfoEndpoint {

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
