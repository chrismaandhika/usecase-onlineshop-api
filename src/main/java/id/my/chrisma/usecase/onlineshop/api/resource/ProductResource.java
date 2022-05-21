package id.my.chrisma.usecase.onlineshop.api.resource;

import id.my.chrisma.usecase.onlineshop.api.dto.ProductDetail;
import id.my.chrisma.usecase.onlineshop.api.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductResource {
    private ProductService productService;

    public ProductResource(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/detail/{productId}")
    public ProductDetail getProductDetail(@PathVariable("productId") Long productId) {
        return productService.getProductDetail(productId);
    }
}
