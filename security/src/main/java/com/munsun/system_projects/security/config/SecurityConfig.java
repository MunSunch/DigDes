package com.munsun.system_projects.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@ComponentScan("com.munsun.system_projects.security.service")
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    private final UserDetailsService service;

    @Autowired
    public SecurityConfig(UserDetailsService service) {
        this.service = service;
    }

    @Bean
    public SecurityFilterChain createSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) -> {
                    requests.anyRequest().authenticated();
                })
                .httpBasic(Customizer.withDefaults())
                .logout(logout -> {
                    logout.invalidateHttpSession(true);
                    logout.clearAuthentication(true);
                    logout.deleteCookies("JSESSIONID");
                })
                .authenticationProvider(daoAuthenticationProvider())
                .build();
    }

    @Bean
    protected DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(createPasswordEncoder());
        provider.setUserDetailsService(service);
        return provider;
    }

    @Bean
    protected BCryptPasswordEncoder createPasswordEncoder() {
        int strengthEncoder = 12;
        return new BCryptPasswordEncoder(strengthEncoder);
    }
}
