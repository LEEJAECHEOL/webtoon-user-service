package com.webtoon.userservice.business.webtoon.entity;

import com.webtoon.userservice.common.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WebtoonImage extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "webtoon_image_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "webtoon_list_id")
  private WebtoonList webtoonList;

  private String url;
}
