package com.example.brave_webtoon.base.repository;

import com.example.brave_webtoon.base.entity.WebtoonRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WebtoonRoleRepository extends JpaRepository<WebtoonRoleEntity, Long> {
    List<WebtoonRoleEntity> findAllByWebtoonId(Long webtoonId);
}
