package id.my.chrisma.usecase.onlineshop.api.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "cart_items")
@Setter
@Getter
public class CartItems {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_items_generator")
    @SequenceGenerator(name="cart_items_generator", sequenceName = "cart__items_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "is_in_checkout")
    private Boolean isInCheckout;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", referencedColumnName = "member_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @Version
    public Long version;

    public CartItems() {
    }

    public CartItems(Cart cart, Product product, Integer quantity) {
        this.quantity = quantity;
        this.isInCheckout = false;
        this.cart = cart;
        this.product = product;
    }

    public void increaseQuantity(int amount) {
        this.quantity += amount;
    }

    public void decreaseQuantity(int amount) {
        this.quantity -= amount;
    }
}
