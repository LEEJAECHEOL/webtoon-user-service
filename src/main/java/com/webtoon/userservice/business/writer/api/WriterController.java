package com.webtoon.userservice.business.writer.api;

import com.webtoon.userservice.business.user.entity.User;
import com.webtoon.userservice.business.writer.form.request.RegisterWriter;
import com.webtoon.userservice.business.writer.service.WriterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class WriterController {
  private final WriterService writerService;

  @PostMapping("/writer")
  @ResponseStatus(HttpStatus.CREATED)
  public void registerWriter(@Valid @RequestBody RegisterWriter registerWriter,
                             @AuthenticationPrincipal User user) {
//    writerService.applyWriter(registerWriter.toEntity(user));
//    log.debug("tesT : {}", user.getUsername());
  }



}
