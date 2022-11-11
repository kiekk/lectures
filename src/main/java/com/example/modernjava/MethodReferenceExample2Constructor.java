package com.example.modernjava;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.function.Function;

public class MethodReferenceExample2Constructor {
    public static void main(String[] args) {
        // basic way
        Product2 product2 = new Product2(1L, "A", new BigDecimal("100"));
        Section section = new Section(1);

        // lambda expression
        Function<Integer, Section> sectionFactory = i -> new Section(i);
        Section section1WithFunction = sectionFactory.apply(2);

        System.out.println(section);
        System.out.println(section1WithFunction);

        Function<Integer, Section> sectionFactoryWithMethodReference = Section::new;
        Section section2WithFunction = sectionFactoryWithMethodReference.apply(3);
        System.out.println(section2WithFunction);
    }
}

@Data
@AllArgsConstructor
class Section {
    private int number;
}

@Data
@AllArgsConstructor
class Product2 {
    private Long id;
    private String name;
    private BigDecimal price;
}