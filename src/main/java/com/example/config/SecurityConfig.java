package com.example.config;

import static com.example.constants.EndpointConstants.LOGIN_PATH;
import static com.example.constants.EndpointConstants.USER_BASE_URL;

import com.example.domain.user.service.IUserService;
import com.example.security.JwtFilter;
import com.example.utils.service.IJwtService;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/** Configuration class for Spring Security */
@Log4j2
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    /**
     * Configures the security filter chain.
     *
     * @param http the HttpSecurity object to configure
     * @param jwtService the JWT service
     * @param userService the user service
     * @return the SecurityFilterChain object
     * @throws Exception if an error occurs
     */
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http, IJwtService jwtService, IUserService userService) throws Exception {
        log.info("securityFilterChain called");

        http.csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        auth ->
                                auth.requestMatchers(USER_BASE_URL + LOGIN_PATH)
                                        .permitAll()
                                        .anyRequest()
                                        .authenticated())
                .addFilterBefore(
                        jwtFilter(jwtService, userService),
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Creates a password encoder bean.
     *
     * @return the password encoder bean
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        log.info("passwordEncoder called");

        return new BCryptPasswordEncoder();
    }

    /**
     * Creates a JWT filter bean.
     *
     * @param jwtService the JWT service
     * @param userService the user service
     * @return the JWT filter bean
     */
    @Bean
    public JwtFilter jwtFilter(IJwtService jwtService, IUserService userService) {
        log.info("jwtFilter called");

        return new JwtFilter(jwtService, userService);
    }
}
