package com.webtoon.userservice.business.user.form.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class Profile {
  private String email;
  private String name;
  private String userKey;
  private List<String> roles;
}
