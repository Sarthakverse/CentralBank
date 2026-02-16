package com.sarthak.gatewayserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Flux;

import java.util.Collection;
import java.util.Map;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/actuator/**").permitAll()
                        .pathMatchers("/accounts/api/**").hasRole("USER")
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(grantedAuthoritiesExtractor()))
                );

        return http.build();
    }

    private ReactiveJwtAuthenticationConverter grantedAuthoritiesExtractor() {

        ReactiveJwtAuthenticationConverter converter =
                new ReactiveJwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(this::extractAuthorities);

        return converter;
    }

    private Flux<GrantedAuthority> extractAuthorities(Jwt jwt) {

        Map<String, Object> realmAccess = jwt.getClaim("realm_access");

        if (realmAccess == null || realmAccess.get("roles") == null) {
            return Flux.empty();
        }

        Object rolesObject = realmAccess.get("roles");

        if (!(rolesObject instanceof Collection<?> rolesCollection)) {
            return Flux.empty();
        }

        return Flux.fromIterable(rolesCollection)
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .map(SimpleGrantedAuthority::new);
    }
}
