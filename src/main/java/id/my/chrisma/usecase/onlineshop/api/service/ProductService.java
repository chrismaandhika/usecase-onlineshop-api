package id.my.chrisma.usecase.onlineshop.api.service;

import id.my.chrisma.usecase.onlineshop.api.dto.ProductDetail;
import id.my.chrisma.usecase.onlineshop.api.entity.Product;
import id.my.chrisma.usecase.onlineshop.api.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Service
public class ProductService {
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductDetail getProductDetail(Long productId) {
        ProductDetail productDetail = new ProductDetail();
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found"));
        productDetail.setId(product.getId());
        productDetail.setName(product.getName());
        productDetail.setDescription(product.getDescription());
        productDetail.setPrice(product.getPrice());
        productDetail.setAvailableStock(product.getProductStock().getAvailableStock());
        productDetail.setImageUrl(buildImageUrl(productId));
        return productDetail;
    }

    public String buildImageUrl(Long productId) {
        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/product/image/{productId}")
                .buildAndExpand(productId)
                .toUriString();
    }
}
