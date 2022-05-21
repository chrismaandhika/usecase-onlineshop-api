package id.my.chrisma.usecase.onlineshop.api.resource;

import id.my.chrisma.usecase.onlineshop.api.dto.ChoosePaymentMethodId;
import id.my.chrisma.usecase.onlineshop.api.dto.ChooseShipmentMethodId;
import id.my.chrisma.usecase.onlineshop.api.dto.InitiateCheckoutRequest;
import id.my.chrisma.usecase.onlineshop.api.service.CheckoutService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping(path = "/checkout")
public class CheckoutResource {
    private CheckoutService checkoutService;

    public CheckoutResource(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping(path = "/initiate")
    public ResponseEntity initiateCheckout(@RequestBody InitiateCheckoutRequest request, Principal principal) {
        checkoutService.initiateCheckout(request, principal.getName());
        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "/payment-method")
    public ResponseEntity choosePaymentMethod(@RequestBody ChoosePaymentMethodId request, Principal principal) {
        checkoutService.choosePaymentMethod(request.getPaymentMethodId(), principal.getName());
        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "/shipment-method")
    public ResponseEntity chooseShipmentMethod(@RequestBody ChooseShipmentMethodId request, Principal principal) {
        checkoutService.chooseShipmentMethod(request.getShipmentMethodId(), principal.getName());
        return ResponseEntity.noContent().build();
    }
}
