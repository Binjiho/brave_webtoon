package com.example.brave_webtoon.webtoon.repository.impl;

import com.example.brave_webtoon.webtoon.entity.WebtoonRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface WebtoonRoleRepositoryImpl extends JpaRepository<WebtoonRoleEntity, Long> {
    @Modifying
    @Query("update WebtoonRoleEntity webtoonRole set webtoonRole.hit = webtoonRole.hit+1 where webtoonRole.id =:id")
    int updateHit(Long id);
}
