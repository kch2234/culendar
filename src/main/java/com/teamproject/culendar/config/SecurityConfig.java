package com.teamproject.culendar.config;

import com.teamproject.culendar.domain.Role;
import com.teamproject.culendar.security.CustomUserDetailsService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService; // remember-me에 필요 (로그인처리시사용)
    private final DataSource dataSource; // yml DB 접속정보 설정 remember-me에 필요

    // 시큐리티 필터를 무시할 요청 경로 지정
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(new AntPathRequestMatcher("/sbadmin/**"))
                .requestMatchers(new AntPathRequestMatcher("/imgs/**"));
    }

    // 시큐리티 필터 처리 설정 빈
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(request -> request
                        .requestMatchers("/", "/login", "/signup").permitAll() // 비회원도 접근 가능
                        .requestMatchers("/members/**").hasAnyRole("MEMBER", "ADMIN") // /회원만 접근 가능
                        .requestMatchers("/cancels/**").hasAnyRole("CANCEL", "ADMIN") // 회원 탈퇴
                        .requestMatchers("/admin/**").hasRole("ADMIN") // 관리자만 접근 가능
                        .anyRequest().authenticated()) // 그 외 요청은 인증된 사용자만 접근 가능
                .csrf(csrf -> csrf.disable()) // CSRF 보안 기능 비활성화
                .formLogin(login -> // 로그인 설정
                        login.loginPage("/login")
                                .defaultSuccessUrl("/", true) // 로그인 성공 후 이동할 페이지
                                .failureUrl("/login?error=true")) // 로그인 실패 시 이동할 페이지
                .rememberMe(remember -> // 로그인 유지 설정
                        remember.userDetailsService(userDetailsService)
                                .tokenRepository(tokenRepository())
                                //.rememberMeParameter("auto")
                                .tokenValiditySeconds(604800)) // 로그인 유지 시간
                .logout(logout -> // 로그아웃 설정
                        logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .logoutSuccessUrl("/")
                                .invalidateHttpSession(true)) // 로그아웃 시 세션 무효화
                .exceptionHandling(exception -> exception
                        .accessDeniedPage("/access-denied")); // 접근 거부 페이지
                //TODO: 세션 관리 설정
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

    @Bean
    public PersistentTokenRepository tokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }

}