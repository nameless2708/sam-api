package com.sam.api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EnableOpenApi
@Configuration
public class SwaggerConfig {

    private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES = new HashSet<>(Collections.singletonList("application/json"));
    private static final String AUTO_TOKEN_SCHEME = "X-Auth-Token";
    private static final String VERSION = "1.0.9";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.sam.api"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .produces(DEFAULT_PRODUCES_AND_CONSUMES)
                .securityContexts(Collections.singletonList(securityContext()))
                .securitySchemes(Collections.singletonList(apiKey()))
                .ignoredParameterTypes(AuthenticationPrincipal.class)
                .ignoredParameterTypes(HttpSession.class);
    }

    private ApiKey apiKey() {
        return new ApiKey(AUTO_TOKEN_SCHEME, "X-Auth-Token", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Collections.singletonList(new SecurityReference(AUTO_TOKEN_SCHEME, authorizationScopes));
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "SAM API SDK",
                "SAM API SDK",
                VERSION,
                "Terms of service",
                new Contact(null, null, null), null, null, Collections.emptyList());
    }
}