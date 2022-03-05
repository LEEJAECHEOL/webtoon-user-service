package com.webtoon.userservice.business.webtoon.entity;


import com.webtoon.userservice.business.webtoon.enums.Genre;
import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WebtoonGenre {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "webtoon_genre_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "webtoon_id", nullable = false)
  private Webtoon webtoon;

  @Enumerated(EnumType.STRING)
  @Column(name = "genre", nullable = false)
  private Genre genre;
}
