package id.my.chrisma.usecase.onlineshop.api.repository;

import id.my.chrisma.usecase.onlineshop.api.entity.Oauth2ApiClient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Oauth2ApiClient, String> {
}
