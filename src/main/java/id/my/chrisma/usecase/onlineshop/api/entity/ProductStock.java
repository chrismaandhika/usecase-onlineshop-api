package id.my.chrisma.usecase.onlineshop.api.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "product_stock")
@Setter
@Getter
public class ProductStock {
    @Id
    @Column(name = "product_id")
    private Long id;

    @Column(name = "original_stock")
    private Integer originalStock;

    @Column(name = "reserved_stock")
    private Integer reservedStock;

    public Integer getAvailableStock() {
        return Math.max(this.originalStock - this.reservedStock, 0);
    }
}
