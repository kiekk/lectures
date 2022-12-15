package com.example.studyluxyxssfilter.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Board {
    private String id;
    private String title;
    private String contents;

    @Builder
    public Board(String id, String title, String contents) {
        this.id = id;
        this.title = title;
        this.contents = contents;
    }
}
