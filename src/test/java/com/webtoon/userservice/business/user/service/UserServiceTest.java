package com.webtoon.userservice.business.user.service;

import com.webtoon.userservice.business.user.entity.User;
import com.webtoon.userservice.business.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.mockito.ArgumentMatchers.anyChar;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
  @InjectMocks
  private UserService userService;

  @Mock
  private UserRepository userRepository;

  @Mock
  private BCryptPasswordEncoder passwordEncoder;

  @Test
  @DisplayName("UserService-save")
  public void save_test() {
    String email = "test@naver.com";
    String password = "Qwe123!@#";
    String name = "name";
    User user = User.builder()
        .name(name)
        .email(email)
        .password(password)
        .build();
    user.init();
    user.encryptPassword(passwordEncoder.encode(user.getPassword()));

    given(userRepository.save(user)).willReturn(user);

    userService.save(user);

    Mockito.verify(userRepository).save(user);
  }
}
