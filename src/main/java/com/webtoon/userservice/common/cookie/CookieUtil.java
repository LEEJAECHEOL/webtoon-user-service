package com.webtoon.userservice.common.cookie;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class CookieUtil {
  @Value("${spring.profiles.active}")
  private String profileActive;
  private static final String DEV_PROFILE = "dev";


  public Cookie create(String key, String value, long duration) {
    log.debug("==== Cookie create ====");
    Cookie cookie = new Cookie(key, value);
    cookie.setHttpOnly(true);
    cookie.setMaxAge((int) (duration / 1000L));
    cookie.setPath("/");
    cookie.setSecure(getSecureByProfileActive());

    return cookie;
  }


  /**
   * @date : 2021/12/04 6:56 오후
   * @author : lee
   * @version : 1.0.0
   * @role : key 를 가진 쿠키 찾기 없을 경우 null 리턴
   */

  public Cookie find(String key) {
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    final Cookie[] cookies = request.getCookies();

    if (cookies == null)
      return null;

    for (Cookie cookie : cookies) {
      if (cookie.getName().equals(key))
        return cookie;
    }

    return null;
  }


  /**
   * @date : 2021/12/04 6:59 오후
   * @author : lee
   * @version : 1.0.0
   * @role : 쿠키 삭제하기
   */

  public void delete(Cookie cookie) {
    HttpServletResponse response =
      ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    cookie.setMaxAge(0);

    response.addCookie(cookie);
  }


  /**
   * @date : 2021/12/04 6:54 오후
   * @author : lee
   * @version : 1.0.0
   * @role : 개발 환경일 경우 쿠키의 Secure 는 false 배포 환경일 경우 true
   */

  private boolean getSecureByProfileActive() { return DEV_PROFILE.equals(profileActive) ? false : true; }

}
