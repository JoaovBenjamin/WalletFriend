package com.joao.WalletFriend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;

@Configuration
public class SecurityConfig{

    @Bean
    public SecurityFilterChain config(HttpSecurity http,MyFilter myFilter) throws Exception{
        http.csrf(csrf -> csrf.disable());
        http.authorizeHttpRequests(auth ->
                auth
                        .requestMatchers(POST,"usuario/register").permitAll()
                        .requestMatchers(POST,"usuario/login").permitAll()
                        .anyRequest().authenticated()


        );

        http.addFilterBefore(myFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
