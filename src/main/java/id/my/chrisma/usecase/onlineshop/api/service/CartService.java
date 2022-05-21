package id.my.chrisma.usecase.onlineshop.api.service;

import id.my.chrisma.usecase.onlineshop.api.dto.CartItemDelta;
import id.my.chrisma.usecase.onlineshop.api.entity.*;
import id.my.chrisma.usecase.onlineshop.api.repository.CartItemsRepository;
import id.my.chrisma.usecase.onlineshop.api.repository.CartRepository;
import id.my.chrisma.usecase.onlineshop.api.repository.MemberRepository;
import id.my.chrisma.usecase.onlineshop.api.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CartService {
    private CartRepository cartRepo;
    private CartItemsRepository cartItemsRepo;
    private MemberRepository memberRepo;
    private ProductRepository productRepo;

    public CartService(CartRepository cartRepo, CartItemsRepository cartItemsRepo, MemberRepository memberRepo, ProductRepository productRepo) {
        this.cartRepo = cartRepo;
        this.cartItemsRepo = cartItemsRepo;
        this.memberRepo = memberRepo;
        this.productRepo = productRepo;
    }

    @Transactional
    public void putItem(List<CartItemDelta> cartItemDeltas, String username) {
        Cart cart = findCartByUsername(username);
        if(cart == null) {
            cart = addCartByUsername(username);
        }
        reserveStocks(cart, cartItemDeltas);
        //TODO: publish event to notify product catalogue that available stock changed
    }

    private void reserveStocks(Cart cart, List<CartItemDelta> cartItemDeltas) {
        Map<Long, CartItemDelta> deltaMap = convertToDeltaMap(cartItemDeltas);
        Map<Long,CartItems> cartItemsMap = getCartItemsMap(cart);
        deltaMap.forEach((productId, cartItemDelta) -> {
            Product product = productRepo.getById(productId);
            ProductStock productStock = product.getProductStock();
            int delta = cartItemDelta.getDelta();
            if(!productStock.isEnoughToReserve(delta)) {
                throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Stock not enough");
            }

            productStock.increaseReservedStock(delta);
            CartItems cartItem = cartItemsMap.get(productId);
            if(cartItem != null) {
                cartItem.increaseQuantity(delta);
            }
            else {
                cartItem = new CartItems(cart, product, delta);
                cartItemsRepo.save(cartItem);
            }
        });
    }

    private Map<Long,CartItemDelta> convertToDeltaMap(List<CartItemDelta> cartItemDeltas) {
        return cartItemDeltas.stream().collect(Collectors.toMap(CartItemDelta::getProductId, cartItemDelta -> cartItemDelta));
    }

    private Map<Long,CartItems> getCartItemsMap(Cart cart) {
        List<CartItems> cartItems = cartItemsRepo.findByCart(cart);
        return cartItems.stream().collect(Collectors.toMap(ci -> ci.getProduct().getId(), ci -> ci));
    }

    private Cart findCartByUsername(String username) {
        return cartRepo.findByUsername(username);
    }

    private Cart addCartByUsername(String username) {
        Member member = memberRepo.findMemberByUsername(username);
        Cart cart = new Cart();
        cart.setId(member.getId());
        cart = cartRepo.save(cart);
        member.setCart(cart);
        return cart;
    }
}
