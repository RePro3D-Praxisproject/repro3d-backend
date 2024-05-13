package org.repro3d;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.repro3d.service.UserDetailsServiceImpl;

import java.util.Arrays;

/**
 * This is a configuration class. No unit testing needed.
 */
@Configuration
public class SecurityConfig {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Cors configuration, letting most things through.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:8765", "http://localhost:4200")); // Angular CLI default port
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Access-Control-Allow-Origin"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        httpSecurity
                .csrf().disable()
                    .authorizeHttpRequests()
                    .requestMatchers(HttpMethod.GET,"/api/user/**")
                    .authenticated()
                .and()
                    .authorizeHttpRequests()
                    .requestMatchers("/api/role/**")
                    .permitAll()
                .and()
                    .authorizeHttpRequests()
                    .requestMatchers(HttpMethod.POST,"/api/user")
                    .permitAll()
                .and()
                    .authorizeHttpRequests()
                    .requestMatchers("/api/printer")
                    .permitAll()
                .and()
                    .authorizeHttpRequests()
                    .requestMatchers(HttpMethod.PUT, "/api/item")
                    .authenticated()
                .and()
                    .authorizeHttpRequests()
                    .requestMatchers("/api/config/**")
                    .permitAll()
                .and()
                    .authenticationManager(authenticationManager)
                .httpBasic();

        return httpSecurity.build();
    }




}