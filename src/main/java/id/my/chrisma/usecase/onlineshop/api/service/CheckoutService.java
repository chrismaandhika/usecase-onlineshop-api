package id.my.chrisma.usecase.onlineshop.api.service;

import id.my.chrisma.usecase.onlineshop.api.constant.CheckoutStatus;
import id.my.chrisma.usecase.onlineshop.api.dto.CheckoutItemDto;
import id.my.chrisma.usecase.onlineshop.api.dto.InitiateCheckoutRequest;
import id.my.chrisma.usecase.onlineshop.api.entity.*;
import id.my.chrisma.usecase.onlineshop.api.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CheckoutService {
    private CheckoutRepository checkoutRepository;
    private CheckoutItemsRepository checkoutItemsRepository;
    private DeliveryAddressRepository deliveryAddressRepository;
    private ProductRepository productRepository;
    private MemberRepository memberRepository;
    private PaymentMethodRepository paymentMethodRepository;
    private ShipmentMethodRepository shipmentMethodRepository;
    private CartService cartService;

    public CheckoutService(CheckoutRepository checkoutRepository, CheckoutItemsRepository checkoutItemsRepository, DeliveryAddressRepository deliveryAddressRepository, ProductRepository productRepository, MemberRepository memberRepository, PaymentMethodRepository paymentMethodRepository, ShipmentMethodRepository shipmentMethodRepository, CartService cartService) {
        this.checkoutRepository = checkoutRepository;
        this.checkoutItemsRepository = checkoutItemsRepository;
        this.deliveryAddressRepository = deliveryAddressRepository;
        this.productRepository = productRepository;
        this.memberRepository = memberRepository;
        this.paymentMethodRepository = paymentMethodRepository;
        this.shipmentMethodRepository = shipmentMethodRepository;
        this.cartService = cartService;
    }

    @Transactional
    public void initiateCheckout(InitiateCheckoutRequest request, String username) {
        long totalItemPrice = markInCheckoutAndCountTotalItemPrice(cartService.findCartItemsByUsername(username), request.getItems());
        Member member = memberRepository.findMemberByUsername(username);
        if(isInPaymentCheckoutExists(member)) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "One checkout waiting for payment");
        }
        Checkout checkout = findInitiatedCheckout(member);
        if(checkout != null) {
            checkoutItemsRepository.deleteByCheckout(checkout);
            checkout.setTotalItemPrice(totalItemPrice);
        }
        else {
            checkout = addInitiatedCheckout(member, totalItemPrice);
        }
        addCheckoutItems(checkout, request.getItems());
    }

    @Transactional
    public void choosePaymentMethod(long paymentMethodId, String username) {
        Checkout checkout = findInitiatedCheckout(memberRepository.findMemberByUsername(username));
        checkout.setPaymentMethod(paymentMethodRepository.getById(paymentMethodId));
    }

    @Transactional
    public void chooseShipmentMethod(long shipmentMethodId, String username) {
        Checkout checkout = findInitiatedCheckout(memberRepository.findMemberByUsername(username));
        ShipmentMethod shipmentMethod = shipmentMethodRepository.getById(shipmentMethodId);
        checkout.setShipmentMethod(shipmentMethod);
        checkout.setShippingCost(shipmentMethod.getPrice());
    }

    private Long markInCheckoutAndCountTotalItemPrice(List<CartItems> cartItems, List<CheckoutItemDto> checkoutItems) {
        long totalItemPrice = 0;
        Set<Long> selectedProductIds = new HashSet<>();
        for(CheckoutItemDto i : checkoutItems) {
            selectedProductIds.add(i.getProductId());
            totalItemPrice += (i.getUnitPrice() * i.getQuantity());
        }

        cartItems.forEach(i -> {
            boolean isInCheckout = selectedProductIds.contains(i.getProduct().getId());
            i.setIsInCheckout(isInCheckout);
        });
        return totalItemPrice;
    }

    public Checkout findInitiatedCheckout(Member member) {
        return checkoutRepository.findCheckoutByMemberAndStatus(member, CheckoutStatus.INITIATED);
    }

    public Checkout findInPaymentCheckout(Member member) {
        return checkoutRepository.findCheckoutByMemberAndStatus(member, CheckoutStatus.IN_PAYMENT);
    }

    private boolean isInPaymentCheckoutExists(Member member) {
        return checkoutRepository.findCheckoutByMemberAndStatus(member, CheckoutStatus.IN_PAYMENT) != null;
    }

    private Checkout addInitiatedCheckout(Member member, long totalItemPrice) {
        Checkout checkout = new Checkout();
        checkout.setMember(member);
        checkout.setAddress(deliveryAddressRepository.findByMember(member));
        checkout.setTotalItemPrice(totalItemPrice);
        checkout.setStatus(CheckoutStatus.INITIATED);
        return checkoutRepository.save(checkout);
    }

    private void addCheckoutItems(Checkout checkout, List<CheckoutItemDto> checkoutItemDtoList) {
        checkoutItemDtoList.forEach(i -> {
            CheckoutItems checkoutItems = new CheckoutItems();
            checkoutItems.setCheckout(checkout);
            checkoutItems.setProduct(productRepository.getById(i.getProductId()));
            checkoutItems.setQuantity(i.getQuantity());
            checkoutItems.setUnitPrice(i.getUnitPrice());
            checkoutItemsRepository.save(checkoutItems);
        });
    }
}
