package id.my.chrisma.usecase.onlineshop.api.repository;

import id.my.chrisma.usecase.onlineshop.api.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member,Long> {
    @Query("SELECT u.member FROM Oauth2ApiUser u WHERE u.username = :username")
    Member findMemberByUsername(@Param("username") String username);

    Member findByVaNumber(String vaNumber);
}
