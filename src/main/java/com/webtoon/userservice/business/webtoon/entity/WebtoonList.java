package com.webtoon.userservice.business.webtoon.entity;

import com.webtoon.userservice.common.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WebtoonList extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "webtoon_list_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "webtoon_id", nullable = false)
  private Webtoon webtoon;

  private int price;

  @OneToMany(mappedBy = "webtoonList")
  List<WebtoonImage> webtoonImages = new ArrayList<>();

}
