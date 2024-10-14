package org.library.config;

import lombok.RequiredArgsConstructor;
import org.library.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import java.util.List;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(request -> {
                    var corsConfiguration = new CorsConfiguration();
                    corsConfiguration.setAllowedOriginPatterns(List.of("*"));
                    corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
                    corsConfiguration.setAllowedHeaders(List.of("*"));
                    corsConfiguration.setAllowCredentials(true);
                    return corsConfiguration;
                }))
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/swagger-ui/**", "/swagger-resources/*", "/v3/api-docs/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/books/{id}").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/v1/books/isbn/{isbn}").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/v1/books").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/v1/books").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/v1/books/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/books/{id}").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/v1/authors/{id}").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/v1/authors/{name}").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/v1/authors").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/v1/authors").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/v1/authors/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/authors/{id}").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/v1/genres/{id}").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/v1/genres").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/v1/genres").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/v1/genres/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/genres/{id}").hasRole("ADMIN")
                        .requestMatchers("/error").permitAll()
                )
                .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
