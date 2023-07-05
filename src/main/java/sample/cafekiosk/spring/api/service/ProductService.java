package sample.cafekiosk.spring.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sample.cafekiosk.spring.api.controller.product.dto.request.ProductCreateRequest;
import sample.cafekiosk.spring.api.service.response.ProductResponse;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductResponse> getSellingProducts() {
        return productRepository
                .findAllBySellingStatusIn(ProductSellingStatus.forDisplay())
                .stream().map(ProductResponse::of)
                .toList();
    }

    public ProductResponse createProduct(ProductCreateRequest request) {
        // generate productNumber
        // DB 에서 마지막 저장된 Product 의 상품 번호를 읽어와서 +1
        // ex: 001 002 003 004
        String latestProductNumber = productRepository.findLatestProductNumber();

        return null;
    }
}
