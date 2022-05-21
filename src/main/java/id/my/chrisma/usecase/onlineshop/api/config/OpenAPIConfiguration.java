package id.my.chrisma.usecase.onlineshop.api.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfiguration {
    @Value("${token.url}")
    private String tokenUrl;

    @Bean
    public OpenAPI phoneContactApi() {
        return new OpenAPI()
                .info(new Info().title("Usecase Online Shop API")
                        .description("Simple API that provides endpoints to enable minimum checkout process")
                        .version("1.0")
                        .license(new License().name("Apache-2.0").url("https://www.apache.org/licenses/LICENSE-2.0")))
                .components(new Components().addSecuritySchemes("oauth2Password",
                        new SecurityScheme().type(SecurityScheme.Type.OAUTH2).flows(
                                new OAuthFlows().password(
                                        new OAuthFlow().tokenUrl(tokenUrl)
                                )
                        )));

    }
}
