package id.my.chrisma.usecase.onlineshop.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CheckoutItemDto implements Serializable {
    private Long productId;
    private Integer quantity;
    private Long unitPrice;
}
