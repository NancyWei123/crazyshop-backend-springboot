package org.target.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.target.product.dto.CreateProductRequest;
import org.target.product.dto.ProductCartInfoDTO;
import org.target.product.dto.ProductDetailDTO;
import org.target.product.dto.ProductOrderInfoDTO;
import org.target.product.entity.Product;
import org.target.product.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    @GetMapping("/{productId}/cart-info")
    public ProductCartInfoDTO getProductCartInfo(@PathVariable Long productId) {
        return productService.getProductCartInfo(productId);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping
    public Product createProduct(@RequestBody CreateProductRequest request) {
        return productService.createProduct(request);
    }

    @GetMapping("/{productId}")
    public ProductDetailDTO getProductDetail(@PathVariable Long productId) {
        return productService.getProductDetail(productId);
    }

    @GetMapping("/{productId}/order-info")
    public ProductOrderInfoDTO getProductOrderInfo(@PathVariable Long productId) {
        return productService.getProductOrderInfo(productId);
    }

    @GetMapping("/shop/{shopId}")
    public List<Product> getProductsByShop(@PathVariable Long shopId) {
        return productService.getProductsByShop(shopId);
    }
}