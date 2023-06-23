package multi.backend.project.security;

import lombok.RequiredArgsConstructor;
import multi.backend.project.security.service.CustomUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
                        .loginPage("/user/login?success")
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
