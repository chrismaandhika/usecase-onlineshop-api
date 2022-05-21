package id.my.chrisma.usecase.onlineshop.api.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "cart")
@Setter
@Getter
public class Cart {
    @Id
    @Column(name = "member_id")
    private Long id;

    @Column(name = "last_checkout_init_time")
    private LocalDateTime lastCheckoutInitTime;
}
