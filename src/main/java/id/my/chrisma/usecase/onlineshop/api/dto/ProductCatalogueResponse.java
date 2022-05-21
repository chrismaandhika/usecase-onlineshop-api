package id.my.chrisma.usecase.onlineshop.api.dto;

import id.my.chrisma.usecase.onlineshop.api.entity.ProductCatalogue;
import lombok.Getter;

import java.util.List;

@Getter
public class ProductCatalogueResponse {
    private List<ProductCatalogue> productCatalogues;
    private long totalElements;
    private int pageIndex;
    private int totalPages;

    public ProductCatalogueResponse(List<ProductCatalogue> productCatalogues, long totalElements, int pageIndex, int totalPages) {
        this.totalElements = totalElements;
        this.productCatalogues = productCatalogues;
        this.pageIndex = pageIndex;
        this.totalPages = totalPages;
    }
}
