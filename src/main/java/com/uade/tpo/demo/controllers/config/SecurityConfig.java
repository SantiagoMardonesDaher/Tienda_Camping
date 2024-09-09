package com.uade.tpo.demo.controllers.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.uade.tpo.demo.entity.Role;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

        private final JwtAuthenticationFilter jwtAuthFilter;
        private final AuthenticationProvider authenticationProvider;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(AbstractHttpConfigurer::disable)
                                .authorizeHttpRequests(req -> req
                                                .requestMatchers("/api/v1/auth/**").permitAll()
                                                .requestMatchers("/error/**").permitAll()
                                                
                                                //categorias
                                                .requestMatchers(HttpMethod.GET, "/categories/**").hasAnyAuthority(Role.USER.name(), Role.ADMIN.name())
                                                .requestMatchers(HttpMethod.POST, "/categories/**").hasAuthority(Role.ADMIN.name())
                                                .requestMatchers(HttpMethod.PUT, "/categories/**").hasAuthority(Role.ADMIN.name())
                                                .requestMatchers(HttpMethod.DELETE, "/categories/**").hasAuthority(Role.ADMIN.name())

                                                //productos
                                                .requestMatchers(HttpMethod.GET, "/products/**").hasAnyAuthority(Role.USER.name(), Role.ADMIN.name())
                                                .requestMatchers(HttpMethod.POST, "/products/**").hasAuthority(Role.ADMIN.name())
                                                .requestMatchers(HttpMethod.PUT, "/products/**").hasAuthority(Role.ADMIN.name())
                                                .requestMatchers(HttpMethod.DELETE, "/products/**").hasAuthority(Role.ADMIN.name())

                                                //OrderItems    
                                                .requestMatchers(HttpMethod.GET, "/order-items/**").hasAnyAuthority(Role.USER.name(), Role.ADMIN.name())
                                                .requestMatchers(HttpMethod.POST, "/order-items/**").hasAnyAuthority(Role.USER.name(), Role.ADMIN.name())
                                                .requestMatchers(HttpMethod.PUT, "/order-items/**").hasAnyAuthority(Role.USER.name(), Role.ADMIN.name())
                                                .requestMatchers(HttpMethod.DELETE, "/order-items/**").hasAnyAuthority(Role.USER.name(), Role.ADMIN.name())
                                                
                                                .anyRequest()
                                                .authenticated())
                                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                                .authenticationProvider(authenticationProvider)
                                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }
}
