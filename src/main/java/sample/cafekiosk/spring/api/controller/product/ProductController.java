package sample.cafekiosk.spring.api.controller.product;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sample.cafekiosk.spring.api.controller.product.dto.request.ProductCreateRequest;
import sample.cafekiosk.spring.api.service.ProductService;
import sample.cafekiosk.spring.api.service.response.ProductResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping("new")
    public void createProduct(@RequestBody ProductCreateRequest request) {
        productService.createProduct(request);
    }


    @GetMapping("selling")
    public List<ProductResponse> getSellingProducts() {
        return productService.getSellingProducts();
    }
}
