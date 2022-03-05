package com.webtoon.userservice.business.webtoon.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Serial {
  MON("MON"),
  TUE("TUE"),
  WED("WED"),
  THU("THU"),
  FRI("FRI"),
  SAT("SAT"),
  SUN("SUN"),
  FIN("FIN"),
  ;

  private String serial;

  public Serial fromString(String serial) {
    return Serial.valueOf(serial);
  }
}
