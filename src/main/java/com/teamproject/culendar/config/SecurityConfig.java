package com.teamproject.culendar.config;


import com.teamproject.culendar.domain.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    // 시큐리티 필터 처리 설정 메서드 빈
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(request -> request
                        // 나머지 요청은 누구나 접근 가능 // 비회원도 접근 가능한 경로
                        .requestMatchers("/member").hasRole("MEMBER") // /회원만 접근 가능
                        .requestMatchers("/admin").hasRole("ADMIN") // 관리자만 접근 가능
                        .requestMatchers("/cancel").hasRole("CANCEL") // 회원 탈퇴
                        .anyRequest().permitAll()) // 나머지 요청은 누구나 접근 가능
                .csrf(csrf -> csrf.disable()) // CSRF 보안 기능 비활성화
                .formLogin(login -> // 로그인 설정
                        login.loginPage("/login")
                                .defaultSuccessUrl("/", true)) // 로그인 성공 후 이동할 페이지
                .logout(logout -> // 로그아웃 설정
                        logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .logoutSuccessUrl("/")
                                .invalidateHttpSession(true)) // 로그아웃 시 세션 무효화

                //TODO: 세션 관리 설정
                ;
        return http.build();
    }

    // 비번 암호화 빈
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 시큐리티 인증 담당해주는 매니저 빈
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}