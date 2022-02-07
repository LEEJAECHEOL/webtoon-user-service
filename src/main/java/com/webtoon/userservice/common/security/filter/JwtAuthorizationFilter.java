package com.webtoon.userservice.common.security.filter;

import com.webtoon.userservice.business.user.entity.User;
import com.webtoon.userservice.business.user.repository.UserRepository;
import com.webtoon.userservice.common.cookie.CookieUtil;
import com.webtoon.userservice.common.jwt.JwtProperties;
import com.webtoon.userservice.common.jwt.JwtUtil;
import com.webtoon.userservice.common.redis.RedisUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
  private final JwtUtil jwtUtil;
  private final UserRepository userRepository;
  private final CookieUtil cookieUtil;
  private final RedisUtil redisUtil;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    log.debug("==== OncePerRequestFilter start ====");
    String accessToken = request.getHeader(JwtProperties.HEADER_STRING);

    if (ObjectUtils.isEmpty(accessToken)) {
      filterChain.doFilter(request, response);
      return;
    }

    if (accessToken.startsWith(JwtProperties.TOKEN_PREFIX)) {
      accessToken = accessToken.replace(JwtProperties.TOKEN_PREFIX, "");
    }
    final Cookie refreshCookie = cookieUtil.find(JwtProperties.REFRESH_TOKEN_NAME);

    String userKey = null;

    try {
      userKey = jwtUtil.getUserKey(accessToken);

      if (ObjectUtils.isEmpty(userKey)) {
        response.sendError(401, "잘못된 토큰입니다.");
        return;
      }
    } catch (ExpiredJwtException e) {
      refreshAccessToken(response, refreshCookie);
      return;
    } catch (Exception e2) {
      response.sendError(401, "잘못된 토큰입니다.");
      return;
    }

    final UserDetails userDetails = getUserDetails(userKey);
    setSecurityContextHolder(request, userDetails);

    filterChain.doFilter(request, response);
  }


  private void refreshAccessToken(HttpServletResponse response, Cookie refreshCookie) throws IOException {
    if (ObjectUtils.isEmpty(refreshCookie)) {
      response.sendError(401, "토큰이 만료되었습니다.");
      return;
    }
    String refreshUserKeyFromCookie = jwtUtil.getUserKey(refreshCookie.getValue());
    String refreshUserKeyFromRedis = redisUtil.getValue(refreshCookie.getValue());

    if (!refreshUserKeyFromCookie.equals(refreshUserKeyFromRedis)) {
      cookieUtil.delete(refreshCookie);
      response.sendError(401, "잘못된 토큰입니다.");
      return;
    }
    final User user = getUserDetails(refreshUserKeyFromRedis);
    String newAccessToken = jwtUtil.generateAccessToken(user.getUserKey());

    response.addHeader(JwtProperties.HEADER_STRING, newAccessToken);
    response.sendError(300, "토큰 갱신");
    return;
  }

  private User getUserDetails(String userKey) {
    return userRepository.findByUserKey(userKey).orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
  }

  private void setSecurityContextHolder(HttpServletRequest request, UserDetails userDetails) {
    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
  }
}
