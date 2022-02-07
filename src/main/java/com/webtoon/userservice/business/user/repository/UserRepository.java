package com.webtoon.userservice.business.user.repository;

import com.webtoon.userservice.business.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String email);

  Optional<User> findByUserKey(String userKey);
}
