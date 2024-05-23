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
 * SecurityConfig is a configuration class that sets up the security configuration for the application.
 * It includes CORS configuration, password encoding, and security filter chain configuration.
 * This is a configuration class. No unit testing needed.
 */
@Configuration
public class SecurityConfig {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    /**
     * Bean for configuring the AuthenticationManager.
     *
     * @param authenticationConfiguration The AuthenticationConfiguration to set up the AuthenticationManager.
     * @return The configured AuthenticationManager.
     * @throws Exception if an error occurs during the configuration.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Bean for configuring the BCryptPasswordEncoder.
     *
     * @return A BCryptPasswordEncoder instance.
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean for configuring CORS settings.
     * Allows specific origins, methods, and headers.
     *
     * @return A CorsConfigurationSource with the configured CORS settings.
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

    /**
     * Configures the security filter chain.
     * Sets up authorization rules, disables CSRF protection, and configures basic HTTP authentication.
     *
     * @param httpSecurity The HttpSecurity to configure.
     * @return A configured SecurityFilterChain.
     * @throws Exception if an error occurs during the configuration.
     */
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