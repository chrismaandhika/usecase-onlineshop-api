package id.my.chrisma.usecase.onlineshop.api.repository;

import id.my.chrisma.usecase.onlineshop.api.entity.DeliveryAddress;
import id.my.chrisma.usecase.onlineshop.api.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryAddressRepository extends JpaRepository<DeliveryAddress,Long> {
    DeliveryAddress findByMember(Member member);
}
