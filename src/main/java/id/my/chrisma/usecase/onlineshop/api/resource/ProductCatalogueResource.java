package id.my.chrisma.usecase.onlineshop.api.resource;

import id.my.chrisma.usecase.onlineshop.api.dto.ProductCatalogueResponse;
import id.my.chrisma.usecase.onlineshop.api.service.ProductCatalogueService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductCatalogueResource {
    private ProductCatalogueService productCatalogueService;

    public ProductCatalogueResource(ProductCatalogueService productCatalogueService) {
        this.productCatalogueService = productCatalogueService;
    }

    @GetMapping(path = "/product-catalogue")
    public ProductCatalogueResponse findProductCategories(@RequestParam(name = "keyword", required = false) String keyword, @RequestParam(name = "page-index") int pageIndex) {
        if(StringUtils.hasText(keyword)) {
            return productCatalogueService.findProductCatalogueByNameKeyword(keyword, pageIndex);
        }
        else {
            return productCatalogueService.findProductCatalogue(pageIndex);
        }
    }
}
