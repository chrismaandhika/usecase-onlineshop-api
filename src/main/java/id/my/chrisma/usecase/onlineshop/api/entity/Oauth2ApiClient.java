package id.my.chrisma.usecase.onlineshop.api.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "oauth2api_client")
@Setter
@Getter
public class Oauth2ApiClient {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "secret")
    private String secret;

    @Column(name = "role")
    private String role;
}
