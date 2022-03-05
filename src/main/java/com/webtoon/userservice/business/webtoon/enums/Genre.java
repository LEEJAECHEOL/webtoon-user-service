package com.webtoon.userservice.business.webtoon.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Genre {
  FANTASY("FANTASY"),
  ROMANCE("ROMANCE"),
  SCHOOL("SCHOOL")
  ;
  private String genre;

  public Genre fromString(String genre) {
    return Genre.valueOf(genre);
  }
}
