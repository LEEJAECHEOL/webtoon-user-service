package com.webtoon.userservice.business.user.api;

import com.webtoon.userservice.business.user.entity.User;
import com.webtoon.userservice.business.user.form.request.Join;
import com.webtoon.userservice.business.user.form.response.Profile;
import com.webtoon.userservice.business.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
  private final UserService userService;

  @GetMapping("/health")
  public String health() {
    return "Work User-Service";
  }

  @GetMapping("/me/profile")
  public Profile getProfile(@AuthenticationPrincipal User user) {
    return Profile.builder()
        .email(user.getEmail())
        .name(user.getName())
        .userKey(user.getUserKey())
        .roles(user.getRoles())
        .build();
  }


  @PostMapping(value = "/join")
  @ResponseStatus(HttpStatus.CREATED)
  public void join(@Valid @RequestBody Join join) {
    userService.save(join.toEntity());
  }

}
