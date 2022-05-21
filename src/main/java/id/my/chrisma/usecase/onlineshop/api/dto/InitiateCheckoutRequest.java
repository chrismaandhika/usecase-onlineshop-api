package id.my.chrisma.usecase.onlineshop.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class InitiateCheckoutRequest implements Serializable {
    private List<CheckoutItemDto> items;
}
