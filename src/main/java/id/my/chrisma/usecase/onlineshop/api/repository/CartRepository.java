package id.my.chrisma.usecase.onlineshop.api.repository;

import id.my.chrisma.usecase.onlineshop.api.entity.Cart;
import id.my.chrisma.usecase.onlineshop.api.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartRepository extends JpaRepository<Cart,Long> {

    @Query("SELECT u.member.cart FROM Oauth2ApiUser u WHERE u.username = :username ")
    Cart findByUsername(@Param("username") String username);

    @Query("SELECT u.member.cart FROM Oauth2ApiUser u WHERE u.member = :member ")
    Cart findByMember(@Param("member") Member member);
}
