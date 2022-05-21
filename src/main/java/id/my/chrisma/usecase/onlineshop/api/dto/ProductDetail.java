package id.my.chrisma.usecase.onlineshop.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ProductDetail implements Serializable {
    private Long id;
    private String name;
    private String description;
    private Long price;
    private Integer availableStock;
    private String imageUrl;
}
