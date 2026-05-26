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

                        // 누구나 접근 가능
                        .requestMatchers(
                                "/",
                                "/main",
                                "/login",
                                "/signup",
                                "/top10",
                                "/faq",
                                "/category/**",
                                "/product/**",
                                "/account/**",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/img/**",
                                "/uploads/**"
                        ).permitAll()

                        .requestMatchers("/membership", "/membership/**").hasRole("USER")

                        // 보안관리자 전용
                        .requestMatchers("/special/**").hasRole("SPECIAL")
                        .requestMatchers("/api/special/**").hasRole("SPECIAL")

                        // 일반 관리자 전용
                        .requestMatchers("/admin", "/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")

                        // 강사 신청
                        .requestMatchers("/instructor/apply", "/instructor/apply/**").authenticated()

                        // 강사 전용
                        .requestMatchers("/instructor", "/instructor/**").hasRole("INSTRUCTOR")

                        // 로그인 사용자 전용
                        .requestMatchers("/mypage", "/mypage/**").authenticated()
                        .requestMatchers("/payment", "/payment/**").authenticated()
                        .requestMatchers("/cart", "/cart/**").authenticated()
                        .requestMatchers("/wishlist", "/wishlist/**").authenticated()
                        .requestMatchers("/taking-course").authenticated()
                        .requestMatchers("/course/start/**").authenticated()
                        .requestMatchers("/review", "/review/**").authenticated()

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

                            if (role.equals("ROLE_SPECIAL")) {
                                response.sendRedirect("/special");
                            } else if (role.equals("ROLE_ADMIN")) {
                                response.sendRedirect("/admin");
                            } else if (role.equals("ROLE_INSTRUCTOR")) {
                                response.sendRedirect("/instructor");
                            } else {
                                response.sendRedirect("/main");
                            }
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