package com.webtoon.userservice.common.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisUtil {
  private final StringRedisTemplate stringRedisTemplate;


  /**
   * @date : 2021/12/04 7:04 오후
   * @author : lee
   * @version : 1.0.0
   * @role : redis row 생성
   */

  public void create(String key, String value, long duration) {
    ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
    Duration expireDuration = Duration.ofSeconds(duration);
    valueOperations.set(key, value, expireDuration);
  }


  /**
   * @date : 2021/12/04 7:06 오후
   * @author : lee
   * @version : 1.0.0
   * @role : key 를 갖는 값 가져오기
   *          없을 경우 null 리턴
   */

  public String getValue(String key) {
    ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();

    return valueOperations.get(key);
  }


  /**
   * @date : 2021/12/04 7:06 오후
   * @author : lee
   * @version : 1.0.0
   * @role : redis key 를 가지고 row 삭제
   */

  public  void delete(String key) {
    stringRedisTemplate.delete(key);
  }

}
