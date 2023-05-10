package com.example.product;

import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductService {

    private final ProductPort productPort;

    ProductService(ProductPort productPort) {
        this.productPort = productPort;
    }

    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public void addProduct(@RequestBody final AddProductRequest request) {
        final Product product = new Product(request.name(), request.price(), request.discountPolicy());
        productPort.save(product);
    }

    @GetMapping("{productId}")
    public GetProductResponse getProduct(@PathVariable final long productId) {
        final Product product = productPort.getProduct(productId);
        return new GetProductResponse(product.getId(), product.getName(), product.getPrice(), product.getDiscountPolicy());
    }

    @PatchMapping("{productId}")
    @Transactional
    public void updateProduct(@PathVariable final Long productId,
                              @RequestBody final UpdateProductRequest request) {
        final Product product = productPort.getProduct(productId);
        product.update(request.name(), request.price(), request.discountPolicy());
        productPort.save(product);
    }
}
