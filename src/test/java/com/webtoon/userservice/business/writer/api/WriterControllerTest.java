package com.webtoon.userservice.business.writer.api;

import com.webtoon.userservice.business.user.entity.User;
import com.webtoon.userservice.business.user.enums.Role;
import com.webtoon.userservice.business.user.repository.UserRepository;
import com.webtoon.userservice.business.user.service.UserService;
import com.webtoon.userservice.business.writer.form.request.RegisterWriter;
import com.webtoon.userservice.business.writer.service.WriterService;
import com.webtoon.userservice.common.BaseControllerUnitTest;
import com.webtoon.userservice.common.cookie.CookieUtil;
import com.webtoon.userservice.common.jwt.JwtUtil;
import com.webtoon.userservice.common.redis.RedisUtil;
import com.webtoon.userservice.common.security.config.SecurityConfig;
import com.webtoon.userservice.common.security.filter.JwtAuthorizationFilter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebMvcTest(controllers = WriterController.class, excludeFilters = {
    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE
    )
})
class WriterControllerTest extends BaseControllerUnitTest {

  @MockBean
  private WriterService writerService;

  @MockBean
  private UserService userService;


  @MockBean
  private CookieUtil cookieUtil;
  @MockBean
  private RedisUtil redisUtil;
  @MockBean
  private JwtUtil jwtUtil;
  @MockBean
  private UserRepository userRepository;


  private org.springframework.security.core.userdetails.User loggedUser;

  @Override
  @BeforeEach
  public void setup() {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
        .apply(springSecurity())
        .build();
    User user = User.builder()
        .id(1L)
        .name("test")
        .email("test@mail.com")
        .userKey(UUID.randomUUID().toString())
        .roles(Collections.singleton(Role.USER))
        .active(true)
        .isDeleted(false)
        .build();
    loggedUser = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    log.debug("test :{}", loggedUser.getUsername());
    log.debug("test :{}", loggedUser.getAuthorities());
    given(userService.loadUserByUsername(loggedUser.getUsername())).willReturn(user);
  }

  @Test
  public void registerWriterTest() throws Exception {
    User user = User.builder()
        .id(1L)
        .name("test")
        .email("test@mail.com")
        .userKey(UUID.randomUUID().toString())
        .roles(Collections.singleton(Role.USER))
        .active(true)
        .isDeleted(false)
        .build();
    String writerName = "writer";
    String introduction = "my introduction";

    RegisterWriter registerWriter = new RegisterWriter(writerName, introduction);

//    Mockito.doNothing().when(writerService).applyWriter(registerWriter.toEntity(user));

    // when
    ResultActions resultActions = mockMvc.perform(post("/api/writer")
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .characterEncoding(StandardCharsets.UTF_8)
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .content(objectMapper.writeValueAsString(registerWriter))
    );

    // then

    resultActions.andDo(print())
        .andExpect(status().isCreated());


  }
}