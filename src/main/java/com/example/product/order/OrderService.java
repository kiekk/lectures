package com.example.product.order;

import com.example.product.Product;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("orders")
public class OrderService {

    private final OrderPort orderPort;

    OrderService(final OrderPort orderPort) {
        this.orderPort = orderPort;
    }

    @Transactional
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)

    public void createOrder(@RequestBody final CreateOrderRequest request) {
        Product product = orderPort.getProductById(request.productId());
        final Order order = new Order(product, request.quantity());

        orderPort.save(order);
    }
}
