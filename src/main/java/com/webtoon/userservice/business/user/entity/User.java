package com.webtoon.userservice.business.user.entity;

import com.webtoon.userservice.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static javax.persistence.FetchType.EAGER;

/**
 * Create Class
 * @date : 2021/12/01 11:23 오후
 * @author : lee
 * @version : 1.0.0
 * @role : 유저 엔티티
 */

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private Long id;

  @Column(name = "auth_id", unique = true)
  private String authId;

  @Column(name = "email", unique = true, nullable = false, columnDefinition = "varchar(100)")
  private String email;

  @Column(name = "password")
  private String password;

  @Column(name = "user_key")
  private String userKey;

  @Column(name = "name", columnDefinition = "varchar(50)")
  private String name;

  private boolean active;

  private boolean del;

  @ElementCollection(fetch = EAGER)
  private List<String> roles = new ArrayList<>();

  @Builder
  private User(String authId, String email, String password, String userKey, String name,
               boolean active, boolean del, List<String> roles) {
    this.authId = authId;
    this.email = email;
    this.password = password;
    this.userKey = userKey;
    this.name = name;
    this.active = active;
    this.del = del;
    this.roles = roles;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role))
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
    this.del = false;
    this.userKey = UUID.randomUUID().toString();
  }

  public void encryptPassword(String encryptPassword) {
    this.password = encryptPassword;
  }
}
