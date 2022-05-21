package id.my.chrisma.usecase.onlineshop.api.resource;

import id.my.chrisma.usecase.onlineshop.api.dto.CartItemRequest;
import id.my.chrisma.usecase.onlineshop.api.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@RestController
@RequestMapping("/cart")
public class CartResource {
    private CartService cartService;

    public CartResource(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping(path = "/put")
    public ResponseEntity putItems(@RequestBody CartItemRequest request, Principal principal) {
        validate(request);
        cartService.putItem(request.getItems(), principal.getName());
        return ResponseEntity.noContent().build();
    }

    private void validate(CartItemRequest request) {
        if(request.getItems().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "items must not empty");
        }

        if(request.getItems().stream().anyMatch(i -> i.getDelta() < 1)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "delta must be greater than zero");
        }
    }
}