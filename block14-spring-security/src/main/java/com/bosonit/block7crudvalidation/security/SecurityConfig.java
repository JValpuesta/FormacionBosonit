package com.bosonit.block7crudvalidation.security;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_USER = "USER";

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .addFilterAfter(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/login").permitAll()
                        .requestMatchers(HttpMethod.GET).hasAnyRole(ROLE_ADMIN, ROLE_USER)
                        .requestMatchers(HttpMethod.POST).hasRole(ROLE_ADMIN)
                        .requestMatchers(HttpMethod.POST, "/person").permitAll()
                        .requestMatchers(HttpMethod.PUT).hasRole(ROLE_ADMIN)
                        .requestMatchers(HttpMethod.DELETE).hasRole(ROLE_ADMIN)
                        .anyRequest().authenticated());

        return http.build();
    }

}
