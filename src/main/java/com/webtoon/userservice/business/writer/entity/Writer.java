package com.webtoon.userservice.business.writer.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.webtoon.userservice.business.user.entity.User;
import com.webtoon.userservice.business.webtoon.entity.Webtoon;
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
public class Writer extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "writer_id")
  private Long id;

  @Column(name = "writer_key", unique = true, nullable = false)
  private String writerKey;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(name = "writer_name", nullable = false)
  private String writerName;

  @Column(name = "introduction", nullable = false)
  private String introduction;

  @JsonIgnore
  @OneToMany(mappedBy = "writer")
  private List<Webtoon> webtoons = new ArrayList<>();

  @Column(name = "active", columnDefinition = "tinyint(1) default 0")
  private boolean active;

}
