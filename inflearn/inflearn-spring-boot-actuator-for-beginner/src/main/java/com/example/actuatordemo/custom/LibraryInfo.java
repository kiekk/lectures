package com.example.actuatordemo.custom;

import lombok.Builder;
import lombok.Data;

@Data
public class LibraryInfo {
    private String name;
    private String version;

    @Builder
    private LibraryInfo(String name, String version) {
        this.name = name;
        this.version = version;
    }
}
