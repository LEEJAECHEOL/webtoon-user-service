package com.webtoon.userservice.business.point.entity;

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
public class Point extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "point_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @Column(name = "point", precision = 30, scale = 4)
  private BigDecimal point = BigDecimal.ZERO;
}
