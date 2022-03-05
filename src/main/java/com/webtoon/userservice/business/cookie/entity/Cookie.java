package com.webtoon.userservice.business.cookie.entity;

import com.webtoon.userservice.business.user.entity.User;
import com.webtoon.userservice.common.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cookie extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "cookie_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @Column(name = "point", precision = 30, scale = 0)
  private BigDecimal cookie = BigDecimal.ZERO;
}
