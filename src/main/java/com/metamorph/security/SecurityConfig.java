package com.metamorph.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtOuthConverter jwtOuthConverter;

  //JwtAuthenticationConverter
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http,
      JwtOuthConverter jwtOuthConverter) throws Exception {
//Authenticated
    http.csrf(AbstractHttpConfigurer::disable);
    http.headers(headers -> headers.frameOptions(
        HeadersConfigurer.FrameOptionsConfig::disable));

/*
    http.authorizeHttpRequests((auth) ->
        auth.anyRequest().authenticated());
    http.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    http.oauth2ResourceServer(
        oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtOuthConverter)));*/
//Not authenticated*/
    http.authorizeHttpRequests((auth) ->
        auth.requestMatchers(HttpMethod.POST, "/authentication/token")
            .permitAll()
            .requestMatchers("/auth/**").permitAll()
            .requestMatchers("/h2/**").permitAll()
            .anyRequest().authenticated());
    http.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    http.oauth2ResourceServer(
        oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtOuthConverter)));
    return http.build();
  }
}

