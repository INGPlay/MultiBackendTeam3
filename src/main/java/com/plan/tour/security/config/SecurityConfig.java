package com.plan.tour.security.config;

import lombok.RequiredArgsConstructor;
import com.plan.tour.security.service.CustomUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomUserDetailService customUserDetailService;

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(customUserDetailService);

        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().disable()

                .authorizeRequests(a -> a
//                       뷰 페이지
//                        - 패스맵 관련
                        .antMatchers("/user/login", "/user/register").anonymous()
                        .antMatchers("/user/inform/", "/pathmap/update/*", "/pathmap/mark").hasRole("USER")
                        .antMatchers("/admin", "/admin/*").hasRole("ADMIN")
//                        -- 정보 관련
                        .antMatchers("/info/place/**", "/info/wheather/**").hasRole("USER")
//                        -- 리뷰 관련
                        .antMatchers("/review/write").hasRole("USER")
                        .antMatchers("/review/view").hasRole("USER")
                        .antMatchers("/review/edit").hasRole("USER")
                        .antMatchers("/review/update").hasRole("USER")
                        .antMatchers("/review/delete").hasRole("USER")
                        
//                        api
//                        - 패스맵 관련
//                        -- 게시판 관련
                        .antMatchers(HttpMethod.GET, "/api/pathmap, /api/pathmap/*").permitAll()
                        .antMatchers(HttpMethod.POST, "/api/pathmap").hasRole("USER")
                        .antMatchers(HttpMethod.PUT, "/api/pathmap").hasRole("USER")
                        .antMatchers(HttpMethod.DELETE, "/api/pathmap").hasRole("USER")
//                        -- 추천(즐겨찾기) 관련
                        .antMatchers("/api/favorite").hasRole("USER")
//                        -- 댓글 관련
                        .antMatchers(HttpMethod.GET, "/api/comment").permitAll()
                        .antMatchers(HttpMethod.POST, "/api/comment").hasRole("USER")
                        .antMatchers(HttpMethod.DELETE, "/api/comment").hasRole("USER")
//                        -- 정보 관련
                        .antMatchers("/api/wheather", "/api/tour/**").permitAll()

//                        - admin 관련
                        .antMatchers(HttpMethod.GET, "/api/admin/user").hasRole("ADMIN")
                        .antMatchers(HttpMethod.DELETE, "/api/admin/user").hasRole("ADMIN")
                        
//                        - 투어코드 이니셜라이저
                        .antMatchers("/api/tour").hasRole("ADMIN")

//                        // 기본 페이지
//                        .antMatchers("/", "/login", "/register", "/error",
//                                "/css/*", "/js/*", "resources/*", "/favicon.ico").permitAll()
//
//                        // 테스트
                        .antMatchers("/test/user").hasRole("USER")
//                        .antMatchers("/test/manager").hasRole("MANAGER")
                        .antMatchers("/test/admin").hasRole("ADMIN")
                        .anyRequest().permitAll()
                )
                .formLogin(f -> f
                        .loginPage("/user/login")
                        .defaultSuccessUrl("/")
                        .failureUrl("/user/login?fail")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .loginProcessingUrl("/login-process")
                )
                .logout(l -> l
                        .logoutUrl("/user/logout")
                        .logoutSuccessUrl("/")
                        .deleteCookies("JSESSIONID", "remember-me")
                )
                .rememberMe(r -> r
                        .rememberMeParameter("remember-me")         // 기본 파라미터명은 remember-me
                        .tokenValiditySeconds(3600)              // Default 는 14일
                        .alwaysRemember(true)                    // 리멤버 미 기능이 활성화되지 않아도 항상 실행
                        .userDetailsService(customUserDetailService)
                )
                .sessionManagement(m -> m
//                        .sessionFixation().changeSessionId()
//                        .invalidSessionUrl("/invalid")
                                .maximumSessions(1)
                                .maxSessionsPreventsLogin(false)
//                        .expiredUrl("expired")
                )
//                .exceptionHandling(e ->
//                        e.accessDeniedPage("/")
//                );
        ;

        return http.build();
    }

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
