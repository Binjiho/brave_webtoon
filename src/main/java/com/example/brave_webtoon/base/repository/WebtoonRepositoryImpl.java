package com.example.brave_webtoon.base.repository;

import com.example.brave_webtoon.base.entity.WebtoonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WebtoonRepositoryImpl extends JpaRepository<WebtoonEntity, Long> {
    Optional<WebtoonEntity> findByTitle(String title);
}
