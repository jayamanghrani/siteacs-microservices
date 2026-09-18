package com.jm.backend.securityconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain defaultSecurityConfig(HttpSecurity httpreq)
    {
        httpreq.authorizeHttpRequests(auth->
        auth.
           requestMatchers("/api/onboarding/token").permitAll()
           .requestMatchers("/api/onboarding/upload-contractor-file").hasAuthority("ONBOARDING")
             .anyRequest().authenticated()
            );
            httpreq.formLogin(form-> form.disable());
            httpreq.csrf(csrf-> csrf.disable());
            httpreq.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));//Ye line Spring Security ko bolती hai: "Apना default, automatic session-creation wala behavior band kar do — mujhe pata hai main khud (JWT se) sambhal loonga, tumको session banाने ki zaroorat nahi hai."Sirf aapका JWT hi authentication ka source hoगа, koई dusra parallel mechanism nahi chalега.
            httpreq.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

    return httpreq.build();

    }
//    hasAuthority("ROLE_X") kuch bhi "khud check" nahi karta on-demand — ye sirf ek rule/condition hai jo SecurityFilterChain mein register ho jaата hai.
//    Ye check JwtAuthFilter ke TURANT BAAD, aur Controller tak pahuँчने SE PEHLE hota hai — Spring Security ke apne internal filter-chain mein, automatically, har request pe.
    @Bean
    public BCryptPasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
}
