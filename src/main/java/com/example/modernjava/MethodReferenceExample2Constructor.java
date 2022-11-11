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

        ProductCreator productCreator = Product2::new;
        Product2 productWithCreator = productCreator.create(2L, "B", new BigDecimal("200"));

        System.out.println(product2);
        System.out.println(productWithCreator);

        ProductA a = createProduct(1L, "A", new BigDecimal("123"), ProductA::new);
        ProductB b = createProduct(1L, "A", new BigDecimal("123"), ProductB::new);

        System.out.println(a);
        System.out.println(b);
    }

    private static <T extends Product2> T createProduct(Long id, String name, BigDecimal price, ProductCreator<T> creator) {
        if (id == null || id < 1L) {
            throw new IllegalArgumentException("The id must be a positive Long.");
        }
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("The name is not given.");
        }
        if (price == null || BigDecimal.ZERO.compareTo(price) >= 0) {
            throw new IllegalArgumentException("The price must be greater than 0.");
        }

        return creator.create(id, name, price);
    }
}

@FunctionalInterface
interface ProductCreator<T extends Product2> {
    T create(Long id, String name, BigDecimal price);
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

class ProductA extends Product2 {

    public ProductA(Long id, String name, BigDecimal price) {
        super(id, name, price);
    }

    @Override
    public String toString() {
        return "A=" + super.toString();
    }
}

class ProductB extends Product2 {

    public ProductB(Long id, String name, BigDecimal price) {
        super(id, name, price);
    }

    @Override
    public String toString() {
        return "B=" + super.toString();
    }
}