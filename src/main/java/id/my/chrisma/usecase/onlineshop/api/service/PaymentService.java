package id.my.chrisma.usecase.onlineshop.api.service;

import id.my.chrisma.usecase.onlineshop.api.constant.CheckoutStatus;
import id.my.chrisma.usecase.onlineshop.api.constant.PaymentStatus;
import id.my.chrisma.usecase.onlineshop.api.dto.PaymentCallbackRequest;
import id.my.chrisma.usecase.onlineshop.api.dto.PaymentVirtualAccountInquiryResponse;
import id.my.chrisma.usecase.onlineshop.api.entity.CartItems;
import id.my.chrisma.usecase.onlineshop.api.entity.Checkout;
import id.my.chrisma.usecase.onlineshop.api.entity.Member;
import id.my.chrisma.usecase.onlineshop.api.entity.Payment;
import id.my.chrisma.usecase.onlineshop.api.repository.CartItemsRepository;
import id.my.chrisma.usecase.onlineshop.api.repository.MemberRepository;
import id.my.chrisma.usecase.onlineshop.api.repository.PaymentRepostory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PaymentService {
    private PaymentRepostory paymentRepostory;
    private MemberRepository memberRepository;
    private CartItemsRepository cartItemsRepository;
    private CheckoutService checkoutService;
    private CartService cartService;

    private static final int PAYMENT_SUCCESS_CODE = 0;

    public PaymentService(PaymentRepostory paymentRepostory, MemberRepository memberRepository, CartItemsRepository cartItemsRepository, CheckoutService checkoutService, CartService cartService) {
        this.paymentRepostory = paymentRepostory;
        this.memberRepository = memberRepository;
        this.cartItemsRepository = cartItemsRepository;
        this.checkoutService = checkoutService;
        this.cartService = cartService;
    }

    @Transactional
    public void initiatePayment(String username) {
        Member member = memberRepository.findMemberByUsername(username);
        Checkout checkout = checkoutService.findInitiatedCheckout(member);
        checkout.setStatus(CheckoutStatus.IN_PAYMENT);

        Payment payment = new Payment();
        payment.setCheckout(checkout);
        payment.setStatus(PaymentStatus.IN_PROCESS);
        payment.setTotalAmount(checkout.getTotalCost());
        payment.setVirtualAccount(member.getVaNumber());
        paymentRepostory.save(payment);
    }

    public PaymentVirtualAccountInquiryResponse inquryByVirtualAccount(String virtualAccount) {
        Payment payment = paymentRepostory.findByVirtualAccount(virtualAccount);
        return new PaymentVirtualAccountInquiryResponse(payment.getVirtualAccount(), payment.getTotalAmount());
    }

    @Transactional
    public void handlePaymentCallback(PaymentCallbackRequest request) {
        Member member = memberRepository.findByVaNumber(request.getVirtualAccount());
        Payment payment = paymentRepostory.findByVirtualAccount(request.getVirtualAccount());
        Checkout checkout = checkoutService.findInPaymentCheckout(member);
        if(request.getStatusCode() == PAYMENT_SUCCESS_CODE) {
            payment.setStatus(PaymentStatus.SUCCEED);
            checkout.setStatus(CheckoutStatus.SUCCEED);
            //TODO: jika status sukses, buat order
        }
        else {
            payment.setStatus(PaymentStatus.FAILED);
            checkout.setStatus(CheckoutStatus.FAILED);
        }
         List<CartItems> cartItemsList = cartService.findCartItemsByMember(member);
        cartItemsList.forEach(i -> {
            if(i.getIsInCheckout()) {
                cartItemsRepository.delete(i);
            }
        });
    }
}
