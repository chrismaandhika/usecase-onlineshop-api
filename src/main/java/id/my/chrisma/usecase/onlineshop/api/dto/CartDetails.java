package id.my.chrisma.usecase.onlineshop.api.dto;

import id.my.chrisma.usecase.onlineshop.api.entity.CartItems;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CartDetails implements Serializable {
    private List<CartItemDto> items;

    public CartDetails() {
        this.items = new ArrayList<>();
    }

    public void addItem(CartItems cartItem, String imageUrl) {
        CartItemDto dto = new CartItemDto();
        dto.setProductId(cartItem.getProduct().getId());
        dto.setName(cartItem.getProduct().getName());
        dto.setPrice(cartItem.getProduct().getPrice());
        dto.setQuantity(cartItem.getQuantity());
        dto.setImageUrl(imageUrl);
        this.items.add(dto);
    }
}
