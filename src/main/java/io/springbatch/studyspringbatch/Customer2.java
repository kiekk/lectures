package io.springbatch.studyspringbatch;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Customer2 {
    @Id
    private long id;
    private String firstname;
    private String lastname;
    private String birthdate;

}
