package id.my.chrisma.usecase.onlineshop.api.resource;

import id.my.chrisma.usecase.onlineshop.api.builder.JwtTokenBuilder;
import id.my.chrisma.usecase.onlineshop.api.constant.UserType;
import id.my.chrisma.usecase.onlineshop.api.dto.TokenResponse;
import id.my.chrisma.usecase.onlineshop.api.service.JdbcClientDetailsService;
import id.my.chrisma.usecase.onlineshop.api.service.JdbcUserDetailsService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.util.Map;

@RestController
public class AuthResource {
    private JdbcUserDetailsService userDetailsService;
    private JdbcClientDetailsService clientDetailsService;
    private PasswordEncoder passwordEncoder;
    private JwtTokenBuilder tokenBuilder;
    private JwtDecoder jwtDecoder;

    @Value("${jwt.access-token.hour-to-live}")
    private long accessTokenHourToLive;
    @Value("${jwt.refresh-token.hour-to-live}")
    private long refreshTokenHourToLive;

    public AuthResource(@Qualifier("userDetailsService") JdbcUserDetailsService userDetailsService,
                        @Qualifier("clientDetailsService") JdbcClientDetailsService clientDetailsService,
                        PasswordEncoder passwordEncoder,
                        JwtTokenBuilder tokenBuilder,
                        JwtDecoder jwtDecoder,
                        @Value("${jwt.access-token.hour-to-live}") long accessTokenHourToLive,
                        @Value("${jwt.refresh-token.hour-to-live}") long refreshTokenHourToLive) {
        this.userDetailsService = userDetailsService;
        this.clientDetailsService = clientDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.tokenBuilder = tokenBuilder;
        this.jwtDecoder = jwtDecoder;
        this.accessTokenHourToLive = accessTokenHourToLive;
        this.refreshTokenHourToLive = refreshTokenHourToLive;
    }

    @PostMapping(path = "/token")
    public TokenResponse requestToken(@RequestParam Map<String,String> params) {
        try {
            String grantType = params.get("grant_type").toLowerCase();
            TokenResponse token;
            switch (grantType) {
                case "password":
                    token = generateTokenForMember(params.get("username"), params.get("password"));
                    break;
                case "client_credentials":
                    token = generateTokenForClient(params.get("client_id"), params.get("client_secret"));
                    break;
                case "refresh_token":
                    token = refreshToken(params.get("refresh_token"));
                    break;
                default:
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"grant type not recognized");
            }
            return token;
        }
        catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "not authenticated");
        }
    }

    private TokenResponse generateTokenForMember(String username, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if(passwordEncoder.matches(password, userDetails.getPassword())) {
            return buildTokenResponse(username, extractScope(userDetails), UserType.MEMBER);
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "not authenticated");
    }

    private TokenResponse generateTokenForClient(String clientId, String clientSecret) {
        UserDetails userDetails = clientDetailsService.loadUserByUsername(clientId);

        if(clientSecret.equals(userDetails.getPassword())) {
            return buildTokenResponse(clientId, extractScope(userDetails), UserType.CLIENT);
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "not authenticated");
    }

    private TokenResponse refreshToken(String refreshToken) {
        Jwt jwtToken = jwtDecoder.decode(refreshToken);
        Map<String, Object> claims = jwtToken.getClaims();
        boolean isRefreshToken = Boolean.parseBoolean((String)claims.get("is_refresh_token"));
        if(!isRefreshToken) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "token is not refresh token");
        }

        if(jwtToken.getExpiresAt().isBefore(Instant.now())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "refresh token is expired");
        }

        String subject = jwtToken.getSubject();
        String scopes = null;
        UserType userType = UserType.valueOf((String)claims.get("user_type"));
        switch (userType) {
            case MEMBER:
                scopes = userDetailsService.getScopesByUsername(subject);
                break;
            case CLIENT:
                scopes = clientDetailsService.getScopesByClientId(subject);
                break;
        }
        return buildTokenResponse(subject,scopes,userType);
    }

    private TokenResponse buildTokenResponse(String username, String scope, UserType userType) {
        String accessToken = buildAccessToken(username, scope, accessTokenHourToLive);
        String refreshToken = buildRefreshToken(username, scope, refreshTokenHourToLive, userType);
        return new TokenResponse(accessToken, scope, refreshToken, accessTokenHourToLive);
    }

    private String extractScope(UserDetails details) {
        return details.getAuthorities().stream().findFirst().get().getAuthority();
    }

    private String buildAccessToken(String subject, String scopes, long hourToLive) {
        return tokenBuilder.build(subject, hourToLive, Map.of(
                "scope",scopes,
                "is_refresh_token", Boolean.FALSE.toString()
        ));
    }

    private String buildRefreshToken(String subject, String scopes, long hourToLive, UserType userType) {
        return tokenBuilder.build(subject, hourToLive, Map.of(
                "scope",scopes,
                "is_refresh_token", Boolean.TRUE.toString(),
                "user_type", userType.name()
        ));
    }
}
