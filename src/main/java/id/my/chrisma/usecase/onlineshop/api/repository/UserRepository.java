package id.my.chrisma.usecase.onlineshop.api.repository;

import id.my.chrisma.usecase.onlineshop.api.entity.Oauth2ApiUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Oauth2ApiUser, String> {
}
