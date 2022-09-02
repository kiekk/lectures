package io.batch.apiservice.apiservice.controller;

import io.batch.apiservice.apiservice.domain.ApiInfo;
import io.batch.apiservice.apiservice.domain.ApiRequestVO;
import io.batch.apiservice.apiservice.domain.ProductVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/product")
public class ApiController {

    @PostMapping("1")
    public String product1(@RequestBody ApiInfo apiInfo) {
        List<ProductVO> productList = apiInfo.getApiRequestList().stream()
                .map(ApiRequestVO::getProductVO)
                .collect(Collectors.toList());
        System.out.println("productList = " + productList);
        return "product1 was successfully processed";
    }

    @PostMapping("2")
    public String product2(@RequestBody ApiInfo apiInfo) {
        List<ProductVO> productList = apiInfo.getApiRequestList().stream()
                .map(ApiRequestVO::getProductVO)
                .collect(Collectors.toList());
        System.out.println("productList = " + productList);
        return "product2 was successfully processed";
    }

    @PostMapping("3")
    public String product3(@RequestBody ApiInfo apiInfo) {
        List<ProductVO> productList = apiInfo.getApiRequestList().stream()
                .map(ApiRequestVO::getProductVO)
                .collect(Collectors.toList());
        System.out.println("productList = " + productList);
        return "product3 was successfully processed";
    }

}
