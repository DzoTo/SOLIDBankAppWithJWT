package kz.singularity.bankappdelivery.securityConfig;




import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()))
                .info(new Info()
                        .title("BankAppDelivery JWT")
                        .description("Next level of BankAppDelivery")
                        .version("1.0")
                        .contact(new Contact().name("Nurasyl Sakayev")
                                .email("sakaev.nurasyl@gmail.com")
                                .url("https://www.linkedin.com/in/nurasyl-sakayev-299048271/"))
                        .license(new License().name("License of API")
                                .url("API license URL")));
    }
}
