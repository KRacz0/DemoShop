package com.aixo.demoshop.configuration;

import com.aixo.demoshop.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    GoogleOAuth2SuccessHandler googleOAuth2SuccessHandler;
    @Autowired
    CustomUserDetailService customUserDetailService;
    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests((authorize) ->
                {
                    try {
                        authorize.requestMatchers("/", "/register/**","/register", "/shop/**", "h2-console/**").permitAll()
                                .requestMatchers("/", "/login", "/oauth/**").permitAll()
                                .requestMatchers("/cart/**").hasAnyRole("USER","openid") // we know user loged in with google have role user and openid so they will be able to access this
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .anyRequest()
                                .authenticated()
                                .and()
                                .oauth2Login()
                                .loginPage("/login")
                                .userInfoEndpoint()
                                .userService(customOAuth2UserService)
                                .and()
                                .successHandler(googleOAuth2SuccessHandler)
                                .and()
                                .logout()
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .logoutSuccessUrl("/login")
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID")
                                .and()
                                .exceptionHandling()
                                .and().formLogin().loginPage("/login")
                                .successHandler(new MyAuthenticationSuccessHandler())
                                .and()
                                .csrf()
                                .disable();

                        http.headers().frameOptions().disable();

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
        return http.build();
    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }


    /*@Bean
     public AuthenticationManager authenticationManager(AuthenticationManagerBuilder auth) throws Exception {
        return auth.userDetailsService(customUserDetailService).passwordEncoder(bCryptPasswordEncoder()).and().build();
     }*/
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailService);
        authProvider.setPasswordEncoder(bCryptPasswordEncoder());

        return authProvider;
    }

    public void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers("/resources/**", "/static/**", "/images/**", "productImages/**", "/css/**", "/js/**");
    }
}
