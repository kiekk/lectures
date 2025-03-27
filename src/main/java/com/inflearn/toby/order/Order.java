package com.inflearn.toby.order;

import java.math.BigDecimal;

public class Order {
    private Long id;
    private String no;
    private BigDecimal total;

    public static Order create(String no, BigDecimal total) {
        Order order = new Order();
        order.no = no;
        order.total = total;
        return order;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public BigDecimal getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", no='" + no + '\'' +
                ", total=" + total +
                '}';
    }
}
