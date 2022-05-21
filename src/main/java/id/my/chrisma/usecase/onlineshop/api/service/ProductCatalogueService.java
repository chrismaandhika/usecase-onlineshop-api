package id.my.chrisma.usecase.onlineshop.api.service;

import id.my.chrisma.usecase.onlineshop.api.dto.ProductCatalogueResponse;
import id.my.chrisma.usecase.onlineshop.api.entity.ProductCatalogue;
import id.my.chrisma.usecase.onlineshop.api.repository.ProductCatalogueRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ProductCatalogueService {
    private ProductCatalogueRepository productCatelogueRepo;
    private int maxPageSize;

    public ProductCatalogueService(ProductCatalogueRepository productCatelogueRepo,
                                   @Value("${product-catalogue.max-page-size}") int maxPageSize) {
        this.productCatelogueRepo = productCatelogueRepo;
        this.maxPageSize = maxPageSize;
    }

    public ProductCatalogueResponse findProductCatalogue(int pageIndex)  {
        Page<ProductCatalogue> page = productCatelogueRepo.findAll(pageRequest(pageIndex));
        return new ProductCatalogueResponse(
                page.getContent(),
                page.getTotalElements(),
                pageIndex,
                page.getTotalPages()
        );
    }

    public ProductCatalogueResponse findProductCatalogueByNameKeyword(String keyword, int pageIndex)  {
        Page<ProductCatalogue> page = productCatelogueRepo.findByNameContainingIgnoreCase(keyword, pageRequest(pageIndex));
        return new ProductCatalogueResponse(
                page.getContent(),
                page.getTotalElements(),
                pageIndex,
                page.getTotalPages()
        );
    }

    private Pageable pageRequest(int pageIndex) {
        return PageRequest.of(pageIndex, maxPageSize, Sort.by(Sort.Order.asc("name")));
    }
}
