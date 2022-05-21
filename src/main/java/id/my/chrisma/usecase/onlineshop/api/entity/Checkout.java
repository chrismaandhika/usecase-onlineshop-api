package id.my.chrisma.usecase.onlineshop.api.entity;

import id.my.chrisma.usecase.onlineshop.api.constant.CheckoutStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "checkout")
@Setter
@Getter
public class Checkout {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "checkout_generator")
    @SequenceGenerator(name="checkout_generator", sequenceName = "checkout_id_seq", allocationSize = 1)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private DeliveryAddress address;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipment_method_id", referencedColumnName = "id")
    private ShipmentMethod shipmentMethod;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_method_id", referencedColumnName = "id")
    private PaymentMethod paymentMethod;

    @Column(name = "shipping_cost")
    private Integer shippingCost;

    @Column(name = "total_item_price")
    private Long totalItemPrice;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CheckoutStatus status;

    public Long getTotalCost() {
        return this.totalItemPrice + this.shippingCost;
    }

    @Version
    private Long version;
}
