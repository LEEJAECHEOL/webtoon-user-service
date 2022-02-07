package com.webtoon.userservice.business.user.form.request;

import com.webtoon.userservice.business.user.entity.User;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
public class Join {
  @NotBlank(message = "이메일을 입력해주세요.")
  @Email(message = "이메일 형식으로 입력해주세요.")
  private String email;

  @NotBlank(message = "비밀번호를 입력해주세요.")
  @Pattern(message = "비밀번호는 최소 8자로 대문자, 소문자, 숫자, 특수문자를 포함해야합니다.",
      regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,}$")
  private String password;

  @NotBlank(message = "이름을 입력해주세요.")
  private String name;

  public User toEntity() {
    return User.builder()
        .email(email)
        .password(password)
        .name(name)
        .build();
  }
}