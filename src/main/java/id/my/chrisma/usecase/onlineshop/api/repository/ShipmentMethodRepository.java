package id.my.chrisma.usecase.onlineshop.api.repository;

import id.my.chrisma.usecase.onlineshop.api.entity.ShipmentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentMethodRepository extends JpaRepository<ShipmentMethod, Long> {
}
