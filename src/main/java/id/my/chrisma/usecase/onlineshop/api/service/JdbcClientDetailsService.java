package id.my.chrisma.usecase.onlineshop.api.service;

import id.my.chrisma.usecase.onlineshop.api.entity.Oauth2ApiClient;
import id.my.chrisma.usecase.onlineshop.api.entity.Scope;
import id.my.chrisma.usecase.onlineshop.api.repository.ClientRepository;
import id.my.chrisma.usecase.onlineshop.api.repository.ScopeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Qualifier("clientDetailsService")
@Slf4j
public class JdbcClientDetailsService implements UserDetailsService {
    private ClientRepository clientRepo;
    private ScopeRepository scopeRepo;

    public JdbcClientDetailsService(ClientRepository clientRepo, ScopeRepository scopeRepo) {
        this.clientRepo = clientRepo;
        this.scopeRepo = scopeRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String clientId) throws UsernameNotFoundException {
        Oauth2ApiClient client = clientRepo.getById(clientId);
        return User.withUsername(clientId)
                .authorities(appendScopes(scopeRepo.findByRole(client.getRole())))
                .password(client.getSecret())
                .build();
    }

    private String appendScopes(List<Scope> scopes) {
        return scopes.stream().map(Scope::getScope).collect(Collectors.joining(" "));
    }

    public String getScopesByClientId(String username) {
        Oauth2ApiClient client = clientRepo.getById(username);
        List<Scope> scopes = scopeRepo.findByRole(client.getRole());
        return appendScopes(scopes);
    }
}
