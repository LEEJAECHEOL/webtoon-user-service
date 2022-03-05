package com.webtoon.userservice.business.user.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
  USER("USER"),
  WRITER("WRITER"),
  ADMIN("ADMIN");

  private String role;

  @JsonCreator
  public Role fromString(String role) {
    return Role.valueOf(role);
  }

}
