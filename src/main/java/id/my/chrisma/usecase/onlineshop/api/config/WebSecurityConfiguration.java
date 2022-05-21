package id.my.chrisma.usecase.onlineshop.api.config;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private PasswordEncoder passwordEncoder;

    public WebSecurityConfiguration(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .cors()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/token", "/swagger-ui**", "/swagger-ui/**", "/v3/api-docs/**")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/product-catalogue")
                .hasAuthority("SCOPE_read")
                .anyRequest()
                .authenticated()
                .and()
                .oauth2ResourceServer().jwt();
    }
}
