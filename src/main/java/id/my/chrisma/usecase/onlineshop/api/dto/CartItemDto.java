package id.my.chrisma.usecase.onlineshop.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CartItemDto implements Serializable {
    private Long productId;
    private String name;
    private Long price;
    private Integer quantity;
    private String imageUrl;
}
