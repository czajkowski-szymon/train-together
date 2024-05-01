package pl.czajkowski.traintogether.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import pl.czajkowski.traintogether.user.models.Role;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.oauth2.core.authorization.OAuth2AuthorizationManagers.hasScope;
import static pl.czajkowski.traintogether.user.models.Permission.*;
import static pl.czajkowski.traintogether.user.models.Role.ADMIN;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityFilterConfig {

    private final AuthenticationManager authenticationManager;

    public SecurityFilterConfig(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Bean
    public SecurityFilterChain filter(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers("/api/v1/users/register", "/api/v1/auth/login").permitAll()

                                .requestMatchers( "/api/v1/admin/**")
                                    .access(hasScope("ROLE_" + ADMIN.name()))

                                .requestMatchers(PATCH, "/api/v1/admin/**")
                                    .access(hasScope(ADMIN_UPDATE.getPermission()))

                                .requestMatchers(DELETE, "/api/v1/admin/**")
                                    .access(hasScope(ADMIN_DELETE.getPermission()))

                                .anyRequest().authenticated()
                )
                .oauth2ResourceServer(
                        oauth2 -> oauth2
                                .jwt(Customizer.withDefaults())
                )
                .sessionManagement(
                        session -> session
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .build();
    }
}
