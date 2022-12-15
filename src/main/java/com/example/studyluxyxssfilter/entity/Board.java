package com.example.studyluxyxssfilter.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class Board {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")

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
