package com.example.springdatajpa.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class BookForm {

    private Long id;
    @NotEmpty(message = "상품 이름은 필수 입니다.")
    private String name;
    @NotNull(message = "상품 가격은 필수 입니다.")
    @Min(message = "상품 가격은 0원 이상이어야 합니다.", value = 0)
    private int price;
    @NotNull(message = "상품 재고는 필수 입니다.")
    @Min(message = "상품 재고는 0개 이상이어야 합니다.", value = 0)
    private int stockQuantity;

    @NotEmpty(message = "저자는 필수 입니다.")
    private String author;
    private String isbn;

}
