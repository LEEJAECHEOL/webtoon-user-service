package com.webtoon.userservice.common.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class JwtUtil {
  private final static String CLAIM_USER_KEY = "userKey";

  // 나중에 MSA로 변경할 때 config-service로 부터 시크릿 키 가져올 예정
  private String secretKey = "secret-key";

  @PostConstruct
  protected void init() {
    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
  }


  /**
   * @date : 2021/12/04 7:34 오후
   * @author : lee
   * @version : 1.0.0
   * @role : AccessToken 발행
   */

  public String generateAccessToken(String userKey) {
    return this.createToken(userKey, JwtProperties.ACCESS_TOKEN_EXPIRE_TIME);
  }


  /**
   * @date : 2021/12/04 7:34 오후
   * @author : lee
   * @version : 1.0.0
   * @role : RefreshToken 발행
   */

  public String generateRefreshToken(String userKey) {
    return this.createToken(userKey, JwtProperties.REFRESH_TOKEN_EXPIRE_TIME);
  }


  /**
   * @date : 2021/12/04 7:54 오후
   * @author : lee
   * @version : 1.0.0
   * @role : userKey값 가져ㅑ오기
   */

  public String getUserKey(String token) {
    return this.getClaims(token).get(CLAIM_USER_KEY, String.class);
  }


  /**
   * @date : 2021/12/04 7:55 오후
   * @author : lee
   * @version : 1.0.0
   * @role : 토큰 만료 시간 가져오기
   */

  public Long getExpireTime(String token) {
    return this.getClaims(token).getExpiration().getTime();
  }


  /**
   * @date : 2021/12/04 7:55 오후
   * @author : lee
   * @version : 1.0.0
   * @role : 토큰 Claims 가져오기
   */

  private Claims getClaims(String token) {
    return Jwts.parser()
        .setSigningKey(secretKey)
        .parseClaimsJws(token)
        .getBody();
  }

  /**
   * @date : 2021/12/04 7:34 오후
   * @author : lee
   * @version : 1.0.0
   * @role : 토큰 생성
   */

  private String createToken(String userKey, long duration) {
    Map<String, Object> payloads = new HashMap<>();
    payloads.put(CLAIM_USER_KEY, userKey);

    return Jwts.builder()
        .setSubject("token")
        .setClaims(payloads)
        .setExpiration(new Date(System.currentTimeMillis() + duration))
        .signWith(SignatureAlgorithm.HS512, secretKey)
        .compact();
  }

}
