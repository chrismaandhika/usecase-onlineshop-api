package id.my.chrisma.usecase.onlineshop.api.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "member")
@Setter
@Getter
public class Member {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "va_number")
    private String vaNumber;

    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "member_id")
    private Cart cart;
}
