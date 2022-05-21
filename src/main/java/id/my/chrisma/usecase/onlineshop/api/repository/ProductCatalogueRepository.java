package id.my.chrisma.usecase.onlineshop.api.repository;

import id.my.chrisma.usecase.onlineshop.api.entity.ProductCatalogue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCatalogueRepository extends JpaRepository<ProductCatalogue, String> {
    Page<ProductCatalogue> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
