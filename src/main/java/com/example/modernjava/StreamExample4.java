package com.example.modernjava;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class StreamExample4 {
    public static void main(String[] args) {
        List<Product> products = Arrays.asList(
                new Product(1L, "A", new BigDecimal("100.50")),
                new Product(2L, "B", new BigDecimal("23.00")),
                new Product(3L, "C", new BigDecimal("31.45")),
                new Product(4L, "D", new BigDecimal("80.20")),
                new Product(5L, "E", new BigDecimal("7.50"))
        );

        System.out.println("Products.price >= 30: \n" +
                products.stream()
                        .filter(product -> product.getPrice().compareTo(new BigDecimal("30")) >= 0)
                        .collect(toList()));

        System.out.println("====================================");
        System.out.println("Products.price >= 30 (with joining(\"\\n\"): \n" +
                products.stream()
                        .filter(product -> product.getPrice().compareTo(new BigDecimal("30")) >= 0)
                        .map(Product::toString)
                        .collect(joining("\n")));

        // 합계 구하기
        System.out.println("====================================");
        System.out.println("IntStream.sum: " + IntStream.of(1, 2, 3, 4, 5).sum());

        System.out.println("====================================");
        System.out.println("Total Price: " +
                products.stream()
                        .map(Product::getPrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add));

        System.out.println("====================================");
        System.out.println("Total Price of Products.price >= 30: " +
                products.stream()
                        .map(Product::getPrice)
                        .filter(price -> price.compareTo(new BigDecimal("30")) >= 0)
                        .reduce(BigDecimal.ZERO, BigDecimal::add));

        System.out.println("====================================");
        System.out.println("Number of Products.price >= 30: " +
                products.stream()
                        .filter(product -> product.getPrice().compareTo(new BigDecimal("30")) >= 0)
                        .count());

        OrderedItem item1 = new OrderedItem(1L, products.get(0), 1);
        OrderedItem item2 = new OrderedItem(2L, products.get(2), 3);
        OrderedItem item3 = new OrderedItem(3L, products.get(4), 10);

        Order order = new Order(1L, Arrays.asList(item1, item2, item3));

        System.out.println("=====================================");
        System.out.println("order.totalPrice(): " + order.totalPrice());
    }
}

@AllArgsConstructor
@Data
class Product {
    private Long id;
    private String name;
    private BigDecimal price;
}

@AllArgsConstructor
@Data
class OrderedItem {
    private Long id;
    private Product product;
    private int quantity;

    public BigDecimal getTotalPrice() {
        return product.getPrice().multiply(new BigDecimal(quantity));
    }
}

@AllArgsConstructor
@Data
class Order {
    private Long id;
    private List<OrderedItem> items;

    public BigDecimal totalPrice() {
        return items.stream().map(OrderedItem::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}