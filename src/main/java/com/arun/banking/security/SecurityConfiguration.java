package com.arun.banking.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security Configuration for Rest Endpoints
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().addFilterAfter(new JWTAuthorizationFilter(),
                UsernamePasswordAuthenticationFilter.class);
        http.authorizeRequests().antMatchers("/api/v1/signin", "/api/v1/healthCheck", "/api/v1/signout/**").permitAll()
                .antMatchers("/api/v1/admin/**").hasAnyAuthority("ADMIN").antMatchers("/api/v1/account/**",
                        "/api/v1/customer", "/api/v1/customer/**", "/api/v1/transaction", "/api/v1/transaction/**")
                .hasAnyAuthority("EMPLOYEE").anyRequest().authenticated();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
