package sample.cafekiosk.spring.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import sample.cafekiosk.spring.api.controller.product.dto.request.ProductCreateRequest;
import sample.cafekiosk.spring.api.service.response.ProductResponse;
import sample.cafekiosk.spring.domain.product.Product;
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
        String nextProductNumber = createNextProductNumber();

        Product product = request.toEntity(nextProductNumber);
        Product savedProduct = productRepository.save(product);

        return ProductResponse.builder()
                .id(savedProduct.getId())
                .productNumber(nextProductNumber)
                .type(request.getType())
                .sellingStatus(request.getSellingStatus())
                .name(request.getName())
                .price(request.getPrice())
                .build();
    }

    private String createNextProductNumber() {
        String latestProductNumber = productRepository.findLatestProductNumber();
        if (!StringUtils.hasText(latestProductNumber)) {
            return "001";
        }
        return String.format("%03d", Integer.parseInt(latestProductNumber) + 1);
    }
}
