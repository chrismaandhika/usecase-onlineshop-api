package id.my.chrisma.usecase.onlineshop.api.repository;

import id.my.chrisma.usecase.onlineshop.api.constant.CheckoutStatus;
import id.my.chrisma.usecase.onlineshop.api.entity.Checkout;
import id.my.chrisma.usecase.onlineshop.api.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckoutRepository extends JpaRepository<Checkout, Long> {
    Checkout findCheckoutByMemberAndStatus(Member member, CheckoutStatus status);
}
