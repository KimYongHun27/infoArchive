package com.meta12.infoArchive.config;

import com.meta12.infoArchive.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserService userService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",
                                "/main",
                                "/login",
                                "/signup",
                                "/account/find",
                                "/account/find-id",
                                "/account/reset-password",
                                "/top10",
                                "/category/**",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/img/**"
                        ).permitAll()

                        .requestMatchers("/mypage", "/mypage/**").authenticated()
                        .requestMatchers("/payment", "/payment/**").authenticated()

                        .requestMatchers("/admin", "/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")

                        // 강사 신청은 로그인한 일반 회원도 가능
                        .requestMatchers("/instructor/apply", "/instructor/apply/**").authenticated()

                        // 강사센터는 강사만 가능
                        .requestMatchers("/instructor", "/instructor/**").hasRole("INSTRUCTOR")

                        .anyRequest().authenticated()
                )

                .userDetailsService(userService)

                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .successHandler((request, response, authentication) -> {
                            request.getSession().setAttribute("loginUser", authentication.getName());

                            String role = authentication.getAuthorities()
                                    .iterator()
                                    .next()
                                    .getAuthority();

                            request.getSession().setAttribute("loginRole", role);

                            response.sendRedirect("/main");
                        })
                        .failureUrl("/login?error=true")
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .clearAuthentication(true)
                        .permitAll()
                );

        return http.build();
    }
}