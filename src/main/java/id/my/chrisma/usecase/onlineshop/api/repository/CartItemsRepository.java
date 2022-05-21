package id.my.chrisma.usecase.onlineshop.api.repository;

import id.my.chrisma.usecase.onlineshop.api.entity.Cart;
import id.my.chrisma.usecase.onlineshop.api.entity.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemsRepository extends JpaRepository<CartItems,Long> {
    List<CartItems> findByCart(Cart cart);
}
