package sample.cafekiosk.spring.api.service.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import sample.cafekiosk.spring.domain.product.ProductRepository;

@Component
@RequiredArgsConstructor
public class ProductNumberFactory {

    private final ProductRepository productRepository;

    public String createNextProductNumber() {
        String latestProductNumber = productRepository.findLatestProductNumber();
        if (!StringUtils.hasText(latestProductNumber)) {
            return "001";
        }
        return String.format("%03d", Integer.parseInt(latestProductNumber) + 1);
    }
}
