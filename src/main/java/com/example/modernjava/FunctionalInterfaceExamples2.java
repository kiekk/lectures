package com.example.modernjava;

import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class FunctionalInterfaceExamples2 {

    public static void main(String[] args) {
        List<Product> products = Arrays.asList(
                new Product(1L, "A", new BigDecimal("10.00")),
                new Product(2L, "B", new BigDecimal("55.50")),
                new Product(3L, "C", new BigDecimal("17.45")),
                new Product(4L, "D", new BigDecimal("23.00")),
                new Product(5L, "E", new BigDecimal("110.00")),
                new Product(6L, "F", new BigDecimal("22.00"))
        );

        // for loop 를 사용한 filter
        List<Product> result = new ArrayList<>();
        BigDecimal twenty = new BigDecimal("20");
        for (Product product : products) {
            if (product.getPrice().compareTo(twenty) >= 0) {
                result.add(product);
            }
        }
        System.out.println(result);

        // Predicate 사용한 filter
        List<Product> result2 = filter(products, product -> product.getPrice().compareTo(twenty) >= 0);
        System.out.println(result2);

        System.out.println(filter(products, product -> product.getPrice().compareTo(new BigDecimal("30")) <= 0));
        // Predicate 를 사용하면 동적으로 조건을 전달할 수 있다.

        // 가격이 50 이상인 상품은 할인 적용
        List<DiscountedProduct> discountedProducts = new ArrayList<>();
        List<Product> expensiveProducts = filter(products, product -> product.getPrice().compareTo(new BigDecimal("50")) >= 0);
        for (Product product : expensiveProducts) {
            discountedProducts.add(new DiscountedProduct(product.getId(), product.getName(), product.getPrice().multiply(new BigDecimal("0.5"))));
        }
        System.out.println("expensive products : " + expensiveProducts);
        System.out.println("discounted products : " + discountedProducts);

        // Function 을 사용하여 할인 적용
        List<DiscountedProduct> discountedProducts2 = map(expensiveProducts, product -> new DiscountedProduct(product.getId(), product.getName(), product.getPrice().multiply(new BigDecimal("0.5"))));
        System.out.println("discounted products2 : " + discountedProducts2);

        System.out.println("discounted products2 (<= $30) : " + filter(discountedProducts2, discountedProduct -> discountedProduct.getPrice().compareTo(new BigDecimal("30")) <= 0));
    }

    private static <T> List<T> filter(List<T> list, Predicate<T> predicate) {
        List<T> result = new ArrayList<>();
        for (T t : list) {
            if (predicate.test(t)) {
                result.add(t);
            }
        }
        return result;
    }

    private static <T, R> List<R> map(List<T> list, Function<T, R> function) {
        List<R> result = new ArrayList<>();
        for (T t : list) {
            result.add(function.apply(t));
        }
        return result;
    }

}

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
class Product {

    private Long id;
    private String name;
    private BigDecimal price;

}

@ToString(callSuper = true)
class DiscountedProduct extends Product {
    public DiscountedProduct(Long id, String name, BigDecimal price) {
        super(id, name, price);
    }

}
