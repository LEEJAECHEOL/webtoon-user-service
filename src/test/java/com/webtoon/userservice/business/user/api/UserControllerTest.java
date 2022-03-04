package com.webtoon.userservice.business.user.api;

import com.webtoon.userservice.business.user.form.request.Join;
import com.webtoon.userservice.business.user.service.UserService;
import com.webtoon.userservice.common.BaseControllerUnitTest;
import com.webtoon.userservice.common.security.config.SecurityConfig;
import com.webtoon.userservice.common.security.filter.JwtAuthorizationFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class, excludeFilters = {
    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
        classes = {SecurityConfig.class,
            JwtAuthorizationFilter.class,
            JwtAuthorizationFilter.class})
})
class UserControllerTest extends BaseControllerUnitTest {

  @MockBean
  private UserService userService;

  @BeforeEach
  public void setup() {
    //Init MockMvc Object and build
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  @Test
  @DisplayName("회원가입")
  public void join_test() throws Exception {
    // given
    String email = "test@naver.com";
    String password = "Qwe123!@#";
    String name = "name";
    Join join = new Join(email, password, name);

    Mockito.doNothing().when(userService).save(join.toEntity());

    // when
    ResultActions resultActions = mockMvc.perform(post("/api/join")
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .characterEncoding(StandardCharsets.UTF_8)
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .content(objectMapper.writeValueAsString(join)));

    // then

    resultActions.andDo(print())
        .andExpect(status().isCreated());

  }

}