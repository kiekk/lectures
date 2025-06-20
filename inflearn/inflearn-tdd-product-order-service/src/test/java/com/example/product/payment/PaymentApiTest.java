package com.example.product.payment;

import com.example.product.Apitest;
import com.example.product.ProductSteps;
import com.example.product.order.OrderSteps;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PaymentApiTest extends Apitest {
    @Test
    void 상품주문() {
        ProductSteps.상품등록요청(ProductSteps.상품등록요청_생성());
        OrderSteps.상품주문요청(OrderSteps.상품주문요청_생성());

        ExtractableResponse<Response> response = PaymentSteps.주문결제요청(PaymentSteps.주문결제요청_생성());

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

}
