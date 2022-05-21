package id.my.chrisma.usecase.onlineshop.api.repository;

import id.my.chrisma.usecase.onlineshop.api.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepostory extends JpaRepository<Payment, Long> {
    Payment findByVirtualAccount(String virtualAccount);
}
