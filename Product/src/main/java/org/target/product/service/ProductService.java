package org.target.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.target.product.dto.CreateProductRequest;
import org.target.product.dto.ProductDetailDTO;
import org.target.product.dto.ProductOrderInfoDTO;
import org.target.product.entity.Product;
import org.target.product.entity.ProductDetail;
import org.target.product.repository.ProductDetailRepository;
import org.target.product.repository.ProductRepository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductDetailRepository productDetailRepository;

    public Product createProduct(CreateProductRequest request) {

        Product product = new Product();
        product.setShopId(request.getShopId());
        product.setName(request.getName());
        product.setImageUrl(request.getImageUrl());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setStatus("ON_SALE");
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        Product savedProduct = productRepository.save(product);

        ProductDetail detail = new ProductDetail();
        detail.setProductId(savedProduct.getId());
        detail.setLongDescription(request.getLongDescription());
        detail.setAttributes(request.getAttributes());
        detail.setGallery(request.getGallery());
        detail.setCreatedAt(Instant.now());
        detail.setUpdatedAt(Instant.now());

        productDetailRepository.save(detail);

        return savedProduct;
    }

    public ProductDetailDTO getProductDetail(Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        ProductDetail detail = productDetailRepository.findByProductId(productId)
                .orElse(null);

        ProductDetailDTO dto = new ProductDetailDTO();
        dto.setProductId(product.getId());
        dto.setName(product.getName());
        dto.setImageUrl(product.getImageUrl());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());

        if (detail != null) {
            dto.setLongDescription(detail.getLongDescription());
            dto.setAttributes(detail.getAttributes());
            dto.setGallery(detail.getGallery());
        }

        return dto;
    }

    public ProductOrderInfoDTO getProductOrderInfo(Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        ProductOrderInfoDTO dto = new ProductOrderInfoDTO();
        dto.setProductId(product.getId());
        dto.setProductName(product.getName());
        dto.setImageUrl(product.getImageUrl());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());

        return dto;
    }

    public List<Product> getProductsByShop(Long shopId) {
        return productRepository.findByShopId(shopId);
    }
}