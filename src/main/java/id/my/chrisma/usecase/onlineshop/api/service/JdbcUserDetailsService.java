package id.my.chrisma.usecase.onlineshop.api.service;

import id.my.chrisma.usecase.onlineshop.api.entity.Oauth2ApiUser;
import id.my.chrisma.usecase.onlineshop.api.entity.Scope;
import id.my.chrisma.usecase.onlineshop.api.repository.ScopeRepository;
import id.my.chrisma.usecase.onlineshop.api.repository.UserRepository;
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
@Qualifier("userDetailsService")
@Slf4j
public class JdbcUserDetailsService implements UserDetailsService {
    private UserRepository userRepo;
    private ScopeRepository scopeRepo;

    public JdbcUserDetailsService(UserRepository userRepo, ScopeRepository scopeRepo) {
        this.userRepo = userRepo;
        this.scopeRepo = scopeRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Oauth2ApiUser user = userRepo.getById(username);
        return User.withUsername(username)
                .authorities(appendScopes(scopeRepo.findByRole(user.getRole())))
                .password(user.getPassword())
                .build();
    }

    private String appendScopes(List<Scope> scopes) {
        return scopes.stream().map(Scope::getScope).collect(Collectors.joining(" "));
    }

    public String getScopesByUsername(String username) {
        Oauth2ApiUser user = userRepo.getById(username);
        List<Scope> scopes = scopeRepo.findByRole(user.getRole());
        return appendScopes(scopes);
    }
}
