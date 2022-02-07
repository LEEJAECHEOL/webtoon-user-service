package com.webtoon.userservice.common.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webtoon.userservice.business.user.entity.User;
import com.webtoon.userservice.common.cookie.CookieUtil;
import com.webtoon.userservice.common.jwt.JwtProperties;
import com.webtoon.userservice.common.jwt.JwtUtil;
import com.webtoon.userservice.common.redis.RedisUtil;
import io.jsonwebtoken.Jwt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
  private RedisUtil redisUtil;
  private CookieUtil cookieUtil;
  private JwtUtil jwtUtil;


  public JwtAuthenticationFilter(AuthenticationManager authenticationManager, RedisUtil redisUtil,
                                 CookieUtil cookieUtil, JwtUtil jwtUtil) {
    super(authenticationManager);
    this.redisUtil = redisUtil;
    this.cookieUtil = cookieUtil;
    this.jwtUtil = jwtUtil;
    setFilterProcessesUrl("/api/login");
  }



  /**
   * @date : 2021/12/04 6:16 오후
   * @author : lee
   * @version : 1.0.0
   * @role : 로그인 요청을 하면 실행되는 메서드
   */

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
      throws AuthenticationException {
    log.debug("==== attemptAuthentication ====");
    User user = parseUser(request);

    UsernamePasswordAuthenticationToken authenticationToken =
      new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());

    return getAuthenticationManager().authenticate(authenticationToken);
  }


  /**
   * @date : 2021/12/04 6:36 오후
   * @author : lee
   * @version : 1.0.0
   * @role : 인증 성공 시 실행되는 메서드
   */

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                          FilterChain chain, Authentication authResult)
    throws IOException, ServletException {
    log.debug("==== successfulAuthentication ====");
    final User user = (User) authResult.getPrincipal();

    String accessToken = jwtUtil.generateAccessToken(user.getUserKey());
    String refreshToken = jwtUtil.generateRefreshToken(user.getUserKey());

    Cookie refreshCookie = cookieUtil.create(JwtProperties.REFRESH_TOKEN_NAME,
                                      refreshToken,
                                      JwtProperties.REFRESH_TOKEN_EXPIRE_TIME);

    redisUtil.create(refreshToken, user.getUserKey(), JwtProperties.REFRESH_TOKEN_EXPIRE_TIME);

    response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + accessToken);
    response.addCookie(refreshCookie);
  }


  /**
   * @date : 2021/12/04 6:34 오후
   * @author : lee
   * @version : 1.0.0
   * @role : User parse
   */

  private User parseUser(HttpServletRequest request) {
    ObjectMapper mapper = new ObjectMapper();
    User user = null;

    try {
      user = mapper.readValue(request.getInputStream(), User.class);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return user;
  }
}
