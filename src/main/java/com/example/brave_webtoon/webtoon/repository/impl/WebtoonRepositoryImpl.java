package com.example.brave_webtoon.webtoon.repository.impl;

import com.example.brave_webtoon.webtoon.entity.WebtoonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WebtoonRepositoryImpl extends JpaRepository<WebtoonEntity, Long> {

    Optional<WebtoonEntity> findByTitle(String title);
    @Modifying
    @Query("update WebtoonEntity webtoon set webtoon.hit = webtoon.hit+1 where webtoon.id =:id")
    int updateHit(Long id);
}
