package com.webtoon.userservice.business.writer.service;

import com.webtoon.userservice.business.user.enums.Role;
import com.webtoon.userservice.business.writer.entity.Writer;
import com.webtoon.userservice.business.writer.repository.WriterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WriterService {
  private final WriterRepository writerRepository;

  @Transactional(rollbackFor = Exception.class)
  public void applyWriter(Writer writer) {
    writerRepository.save(writer);
    writer.getUser().getRoles().add(Role.WRITER);
  }


}
