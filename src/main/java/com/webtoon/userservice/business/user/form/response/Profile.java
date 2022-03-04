package com.webtoon.userservice.business.user.form.response;

import com.webtoon.userservice.business.user.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Set;

@Getter
@AllArgsConstructor
@Builder
public class Profile {
  private String email;
  private String name;
  private String userKey;
  private Set<Role> roles;
}
