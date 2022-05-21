package id.my.chrisma.usecase.onlineshop.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class PaymentCallbackRequest implements Serializable {
    private String virtualAccount;
    private int statusCode;
}
