package id.my.chrisma.usecase.onlineshop.api.repository;

import id.my.chrisma.usecase.onlineshop.api.entity.Checkout;
import id.my.chrisma.usecase.onlineshop.api.entity.CheckoutItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckoutItemsRepository extends JpaRepository<CheckoutItems, Long> {
    long deleteByCheckout(Checkout checkout);
}
