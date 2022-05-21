package id.my.chrisma.usecase.onlineshop.api.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "delivery_address")
@Setter
@Getter
public class DeliveryAddress {
    @Id
    @Column(name = "id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    @Column(name = "full_address")
    private String fullAddress;
}
