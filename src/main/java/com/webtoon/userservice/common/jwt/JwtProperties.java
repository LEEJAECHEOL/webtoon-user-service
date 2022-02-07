package com.webtoon.userservice.common.jwt;

/**
 * Create Interface
 * @date : 2021/12/04 7:13 오후
 * @author : lee
 * @version : 1.0.0
 * @role : JWT 값
 */

public interface JwtProperties {
  String TOKEN_PREFIX = "Bearer ";
  String HEADER_STRING = "Authorization";
  String ACCESS_TOKEN_NAME = "Access-Token";
  String REFRESH_TOKEN_NAME = "Refresh-Token";

  Long ACCESS_TOKEN_EXPIRE_TIME = 60 * 30 * 1000L; // 30분
  Long REFRESH_TOKEN_EXPIRE_TIME = 60 * 60 * 24 * 10 * 1000L;  // 10일

}
