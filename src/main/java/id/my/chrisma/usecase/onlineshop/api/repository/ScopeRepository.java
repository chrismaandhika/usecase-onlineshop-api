package id.my.chrisma.usecase.onlineshop.api.repository;

import id.my.chrisma.usecase.onlineshop.api.entity.Scope;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScopeRepository extends JpaRepository<Scope, Scope> {
    List<Scope> findByRole(String role);
}
