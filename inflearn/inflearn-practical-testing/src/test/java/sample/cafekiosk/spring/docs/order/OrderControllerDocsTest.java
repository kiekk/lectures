package sample.cafekiosk.spring.docs.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import sample.cafekiosk.spring.api.controller.order.OrderController;
import sample.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import sample.cafekiosk.spring.api.service.order.OrderService;
import sample.cafekiosk.spring.api.service.order.request.OrderCreateServiceRequest;
import sample.cafekiosk.spring.api.service.order.response.OrderResponse;
import sample.cafekiosk.spring.api.service.product.response.ProductResponse;
import sample.cafekiosk.spring.docs.RestDocsSupport;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class OrderControllerDocsTest extends RestDocsSupport {

    private final OrderService orderService = mock(OrderService.class);

    @Override
    protected Object initController() {
        return new OrderController(orderService);
    }

    @DisplayName("신규 주문 등록 API")
    @Test
    void createOrder() throws Exception {
        OrderCreateRequest request = OrderCreateRequest.builder()
                .productNumbers(List.of("001"))
                .build();

        given(orderService.createOrder(any(OrderCreateServiceRequest.class), any(LocalDateTime.class)))
                .willReturn(
                        OrderResponse.builder()
                                .id(1L)
                                .registeredDateTime(LocalDateTime.now())
                                .products(List.of(
                                        ProductResponse.builder()
                                                .id(1L)
                                                .productNumber("001")
                                                .type(ProductType.HANDMADE)
                                                .sellingStatus(ProductSellingStatus.SELLING)
                                                .name("아메리카노")
                                                .price(4_000)
                                                .build()
                                ))
                                .totalPrice(4_000)
                                .build()
                );

        mockMvc.perform(post("/api/v1/orders/new")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"))
                .andDo(document("order-create",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("productNumbers").type(JsonFieldType.ARRAY)
                                        .description("상품 번호")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER)
                                        .description("코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING)
                                        .description("상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("메세지"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT)
                                        .description("응답 데이터"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER)
                                        .description("주문 아이디"),
                                fieldWithPath("data.totalPrice").type(JsonFieldType.NUMBER)
                                        .description("주문 총 금액"),
                                /*
                                    Spring Boot 를 띄우지 않아 MessageConverter 설정이 적용되지 않아
                                    LocalDate, LocalDateTime 등이 Array 로 반환
                                    ...
                                 */
                                fieldWithPath("data.registeredDateTime").type(JsonFieldType.ARRAY)
                                        .description("주문 등록 시간"),
                                fieldWithPath("data.products").type(JsonFieldType.ARRAY)
                                        .description("주문 품목"),
                                fieldWithPath("data.products[]").type(JsonFieldType.ARRAY)
                                        .description("주문 품목"),
                                fieldWithPath("data.products[].id").type(JsonFieldType.NUMBER)
                                        .description("상품 아이디"),
                                fieldWithPath("data.products[].productNumber").type(JsonFieldType.STRING)
                                        .description("상품 번호"),
                                fieldWithPath("data.products[].type").type(JsonFieldType.STRING)
                                        .description("상품 타입"),
                                fieldWithPath("data.products[].sellingStatus").type(JsonFieldType.STRING)
                                        .description("상품 상태"),
                                fieldWithPath("data.products[].name").type(JsonFieldType.STRING)
                                        .description("상품 이름"),
                                fieldWithPath("data.products[].price").type(JsonFieldType.NUMBER)
                                        .description("상품 가격")
                        )
                ));
    }

}
