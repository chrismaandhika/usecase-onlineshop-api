package id.my.chrisma.usecase.onlineshop.api.entity;

import id.my.chrisma.usecase.onlineshop.api.constant.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "payment")
@Setter
@Getter
public class Payment {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_generator")
    @SequenceGenerator(name="payment_generator", sequenceName = "payment_id_seq", allocationSize = 1)
    private Long id;

    @OneToOne
    @JoinColumn(name = "checkout_id", referencedColumnName = "id")
    private Checkout checkout;

    @Column(name = "total_amount")
    private Long totalAmount;

    @Column(name = "virtual_account")
    private String virtualAccount;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Version
    private long version;
}
