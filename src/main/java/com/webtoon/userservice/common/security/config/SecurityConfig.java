package com.webtoon.userservice.common.security.config;

import com.webtoon.userservice.common.cookie.CookieUtil;
import com.webtoon.userservice.common.jwt.JwtUtil;
import com.webtoon.userservice.common.redis.RedisUtil;
import com.webtoon.userservice.common.security.filter.JwtAuthenticationFilter;
import com.webtoon.userservice.common.security.filter.JwtAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  private final JwtUtil jwtUtil;
  private final CookieUtil cookieUtil;
  private final RedisUtil redisUtil;
  private final JwtAuthorizationFilter jwtAuthorizationFilter;

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .cors()
        .and()
        .csrf().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .exceptionHandling()
        .and()
        .formLogin().disable()
        .httpBasic().disable();

    http.headers().frameOptions().disable();

    http.addFilter(getJwtAuthenticationFilter())
        .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
    ;

    http.authorizeHttpRequests().antMatchers("/**").permitAll();
  }



  private JwtAuthenticationFilter getJwtAuthenticationFilter() throws Exception {
    return new JwtAuthenticationFilter(authenticationManager(), redisUtil, cookieUtil, jwtUtil);
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();

    configuration.addAllowedOrigin("http://localhost:3000");
    configuration.addAllowedHeader("*");
    configuration.addAllowedMethod("*");
    configuration.setAllowCredentials(true);

    List<String> exposeHeaders = new ArrayList<>();
    exposeHeaders.add("Authorization");
    configuration.setExposedHeaders(exposeHeaders);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);

    return source;
  }
}
