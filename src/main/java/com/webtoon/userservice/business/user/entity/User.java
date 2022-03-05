package com.webtoon.userservice.business.user.entity;

import com.webtoon.userservice.business.user.enums.Role;
import com.webtoon.userservice.common.entity.BaseEntity;
import lombok.*;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

import static javax.persistence.FetchType.EAGER;

/**
 * Create Class
 *
 * @author : lee
 * @version : 1.0.0
 * @date : 2021/12/01 11:23 오후
 * @role : 유저 엔티티
 */

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "is_delete = 0")
@AllArgsConstructor
@Builder
public class User extends BaseEntity implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private Long id;

  @Column(name = "auth_id")
  private String authId;

  @Column(name = "email", unique = true, nullable = false, columnDefinition = "varchar(100)")
  private String email;

  @Column(name = "password")
  private String password;

  @Column(name = "user_key", unique = true, updatable = false)
  private String userKey;

  @Column(name = "name", columnDefinition = "varchar(50)")
  private String name;

  @Column(name = "active", columnDefinition = "tinyint(1) default 0")
  private boolean active;

  @Column(name = "is_deleted", columnDefinition = "tinyint(1) default 0")
  private boolean isDeleted;

  @Enumerated(EnumType.STRING)
  @ElementCollection(fetch = EAGER)
  private Set<Role> roles = new HashSet<>();

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.roles.stream()
        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole()))
        .collect(Collectors.toList());
  }

  @Override
  public String getUsername() {
    return this.email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  public void init() {
    this.active = true;
    this.isDeleted = false;
    this.userKey = UUID.randomUUID().toString();
  }

  public void encryptPassword(String encryptPassword) {
    this.password = encryptPassword;
  }
}
