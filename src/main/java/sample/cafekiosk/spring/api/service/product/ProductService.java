package sample.cafekiosk.spring.api.service.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import sample.cafekiosk.spring.api.service.product.request.ProductCreateServiceRequest;
import sample.cafekiosk.spring.api.service.product.response.ProductResponse;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;

import java.util.List;

/**
 * readOnly = true : 읽기 전용
 * CRUD 에서 CUD 동작 X / only read
 * JPA: CUD 스냅샷 저장, 변경 감지 X (성능 향상)
 * <p>
 * CQRS - Command / Query 분리
 */
@Transactional(readOnly = true)
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

    @Transactional
    public ProductResponse createProduct(ProductCreateServiceRequest request) {
        String nextProductNumber = createNextProductNumber();
        Product product = request.toEntity(nextProductNumber);
        Product savedProduct = productRepository.save(product);

        return ProductResponse.of(savedProduct);
    }

    private String createNextProductNumber() {
        String latestProductNumber = productRepository.findLatestProductNumber();
        if (!StringUtils.hasText(latestProductNumber)) {
            return "001";
        }
        return String.format("%03d", Integer.parseInt(latestProductNumber) + 1);
    }
}
