package com.webtoon.userservice.business.webtoon.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.webtoon.userservice.business.webtoon.enums.Genre;
import com.webtoon.userservice.business.webtoon.enums.Serial;
import com.webtoon.userservice.business.writer.entity.Writer;
import com.webtoon.userservice.common.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Webtoon extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "webtoon_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "writer_id", nullable = false)
  private Writer writer;

  @JsonIgnore
  @OneToMany(mappedBy = "webtoon")
  private List<WebtoonGenre> genres = new ArrayList<>();

  @Enumerated(EnumType.STRING)
  @Column(name = "serial", nullable = false)
  private Serial serial;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "introduction", nullable = false)
  private String introduction;

  @Column(name = "thumbnail", nullable = false)
  private String thumbnail;

}
