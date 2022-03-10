package com.webtoon.userservice.business.writer.repository;

import com.webtoon.userservice.business.writer.entity.Writer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WriterRepository extends JpaRepository<Writer, Long> {
}
