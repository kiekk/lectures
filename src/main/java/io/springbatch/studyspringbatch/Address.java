package io.springbatch.studyspringbatch;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@Entity
@ToString(exclude = "customer")
public class Address {

    @Id
    @GeneratedValue
    private Long id;
    private String location;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

}
