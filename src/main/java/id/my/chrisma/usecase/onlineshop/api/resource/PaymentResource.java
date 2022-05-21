package id.my.chrisma.usecase.onlineshop.api.resource;

import id.my.chrisma.usecase.onlineshop.api.dto.PaymentCallbackRequest;
import id.my.chrisma.usecase.onlineshop.api.dto.PaymentVirtualAccountInquiryResponse;
import id.my.chrisma.usecase.onlineshop.api.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/payment")
public class PaymentResource {
    private PaymentService paymentService;

    public PaymentResource(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping(path = "/initiate")
    public ResponseEntity initiatePayment(Principal principal) {
        paymentService.initiatePayment(principal.getName());
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/inquiry/{virtualAccount}")
    public PaymentVirtualAccountInquiryResponse inquiry(@PathVariable("virtualAccount") String virtualAccount) {
        return paymentService.inquryByVirtualAccount(virtualAccount);
    }

    @PostMapping(path = "/callback")
    public ResponseEntity pay(@RequestBody  PaymentCallbackRequest request) {
        paymentService.handlePaymentCallback(request);
        return ResponseEntity.noContent().build();
    }
}
