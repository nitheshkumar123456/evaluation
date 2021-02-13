package com.test.evaluation.repository;

import com.test.evaluation.entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaRepository  extends JpaRepository<Media, Long>, JpaSpecificationExecutor<Media> {
    List<Media> findByIdIn(List<Long> ids);
}
