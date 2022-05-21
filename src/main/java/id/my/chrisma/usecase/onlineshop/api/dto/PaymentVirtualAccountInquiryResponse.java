package id.my.chrisma.usecase.onlineshop.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class PaymentVirtualAccountInquiryResponse implements Serializable {
    private String virtualAccountNumber;
    private long totalAmount;

    public PaymentVirtualAccountInquiryResponse(String virtualAccountNumber, long totalAmount) {
        this.virtualAccountNumber = virtualAccountNumber;
        this.totalAmount = totalAmount;
    }
}
