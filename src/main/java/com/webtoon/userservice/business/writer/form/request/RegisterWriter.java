package com.webtoon.userservice.business.writer.form.request;

import com.webtoon.userservice.business.user.entity.User;
import com.webtoon.userservice.business.writer.entity.Writer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterWriter {
  @NotBlank(message = "작가명을 입력해주세요.")
  private String writerName;
  @NotBlank(message = "간단한 작가 소개를 입력해주세요.")
  private String introduction;

  public Writer toEntity(User user) {
    return Writer.builder()
        .writerName(writerName)
        .introduction(introduction)
        .writerKey(UUID.randomUUID().toString())
        .active(true)
        .user(user)
        .build();
  }
}
