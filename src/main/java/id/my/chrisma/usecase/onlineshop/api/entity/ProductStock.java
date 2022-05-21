package id.my.chrisma.usecase.onlineshop.api.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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

    @Version
    private Long version;

    public Integer getAvailableStock() {
        return Math.max(this.originalStock - this.reservedStock, 0);
    }

    public boolean isEnoughToReserve(int delta) {
        return this.getAvailableStock() >= delta;
    }

    public void increaseReservedStock(int delta) {
        this.reservedStock += delta;
    }

    public void releaseReservedStock(int delta) {
        this.reservedStock -= delta;
    }
}
