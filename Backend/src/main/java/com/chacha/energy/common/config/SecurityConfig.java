package com.chacha.energy.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {

    // private final TokenProvider tokenProvider;

    private final String FRONT_DOMAIN;
    private final String BACK_DOMAIN;

    public SecurityConfig(@Value("${DOMAIN.FRONT}") String frontDomain,
                          @Value("${DOMAIN.BACK}") String backDomain
                          // ,TokenProvider tokenProvider
    ) {

        this.FRONT_DOMAIN = frontDomain;
        this.BACK_DOMAIN = backDomain;
        // this.tokenProvider = tokenProvider;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable) //csrf 비활성
                .httpBasic(AbstractHttpConfigurer::disable) //HTTP 기본인증 비활성
                // 시큐리티가 세션을 만들지도 사용하지도 않음.
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
        // .addFilterAfter(new JwtFilter(tokenProvider),
        // 	UsernamePasswordAuthenticationFilter.class)
        ;

        return http.build();
    }

    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final String LOCALHOST = "localhost:";

        final String[] ALLOWED_HOSTS = new String[]{
                LOCALHOST
        };

        final String[] PROTOCOLS = {"http://", "https://"};

        final int[] ALLOWED_PORTS = {80, 443, 8081, 8082, 8083};

        // 허용할 origin 목록
        List<String> allowedOrigins = new ArrayList<>();
        allowedOrigins.add(FRONT_DOMAIN);
        allowedOrigins.add(BACK_DOMAIN);


        for (String protocol : PROTOCOLS) {
            for (String host: ALLOWED_HOSTS){
                for (int port: ALLOWED_PORTS){
                    allowedOrigins.add(protocol + host + port);
                }
            }
        }

        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOrigins(allowedOrigins);
        corsConfiguration.setAllowedMethods(List.of(
                HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.PUT.name(),
                HttpMethod.PATCH.name(),
                HttpMethod.DELETE.name(),
                HttpMethod.OPTIONS.name()
        ));

        corsConfiguration.setAllowedHeaders(List.of(
                HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS,
                HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN,
                HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS,
                HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS,
                HttpHeaders.CONTENT_TYPE,
                HttpHeaders.AUTHORIZATION
        ));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}

