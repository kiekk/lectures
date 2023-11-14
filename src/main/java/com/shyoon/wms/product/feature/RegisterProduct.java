package com.shyoon.wms.product.feature;

import com.shyoon.wms.product.domain.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class RegisterProduct {
    private final ProductRepository productRepository;

    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public void request(@RequestBody @Valid final Request request) {
        productRepository.findAll().stream()
                .filter(product -> Objects.equals(product.getCode(), request.code))
                .findFirst()
                .ifPresent(product -> {
                    throw new IllegalArgumentException("이미 등록된 상품입니다.");
                });
        final Product product = request.toDomain();
        productRepository.save(product);

    }

    public record Request(
            @NotBlank(message = "상품명은 필수입니다.")
            String name,
            @NotBlank(message = "상품코드는 필수입니다.")
            String code,
            @NotBlank(message = "상품설명은 필수입니다.")
            String description,
            @NotBlank(message = "브랜드는 필수입니다.")
            String brand,
            @NotBlank(message = "제조사는 필수입니다.")
            String maker,
            @NotBlank(message = "원산지는 필수입니다.")
            String origin,
            @NotNull(message = "카테고리는 필수입니다.")
            Category category,
            @NotNull(message = "온도대는 필수입니다.")
            TemperatureZone temperatureZone,
            @NotNull(message = "무게는 필수입니다.")
            @Min(value = 0, message = "무게는 0보다 작을 수 없습니다.")
            Long weightInGrams,
            @NotNull(message = "상품의 너비는 필수입니다.")
            @Min(value = 0, message = "상품의 너비는 0보다 작을 수 없습니다.")
            Long widthInMillimeters,
            @NotNull(message = "상품의 높이는 필수입니다.")
            @Min(value = 0, message = "상품의 높이는 0보다 작을 수 없습니다.")
            Long heightInMillimeters,
            @NotNull(message = "상품의 길이는 필수입니다.")
            @Min(value = 0, message = "상품의 길이는 0보다 작을 수 없습니다.")
            Long lengthInMillimeters) {

        public Product toDomain() {
            return new Product(
                    name,
                    code,
                    description,
                    brand,
                    maker,
                    origin,
                    category,
                    temperatureZone,
                    weightInGrams,
                    new ProductSize(
                            widthInMillimeters,
                            heightInMillimeters,
                            lengthInMillimeters)
            );
        }

    }

}
