package sample.cafekiosk.spring.api.service.order.response;

import lombok.Getter;
import sample.cafekiosk.spring.api.service.response.ProductResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class OrderResponse {
    private Long id;
    private int totalPrice;
    private LocalDateTime registeredDateTime;
    private List<ProductResponse> products = new ArrayList<>();
}
