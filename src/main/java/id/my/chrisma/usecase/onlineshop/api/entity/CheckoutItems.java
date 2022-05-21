package id.my.chrisma.usecase.onlineshop.api.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "checkout_items")
@Setter
@Getter
public class CheckoutItems implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "checkout_items_id_generator")
    @SequenceGenerator(name="checkout_items_id_generator", sequenceName = "checkout_items_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "checkout_id", referencedColumnName = "id")
    private Checkout checkout;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "unit_price")
    private Long unitPrice;

    @Version
    private Long version;
}
