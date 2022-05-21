package id.my.chrisma.usecase.onlineshop.api.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "product")
@Setter
@Getter
public class Product {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Long price;

    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "product_id")
    private ProductStock productStock;
}
