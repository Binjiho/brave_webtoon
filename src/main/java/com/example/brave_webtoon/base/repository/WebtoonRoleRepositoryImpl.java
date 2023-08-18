package com.example.brave_webtoon.base.repository;

import com.example.brave_webtoon.base.entity.WebtoonRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WebtoonRoleRepositoryImpl extends JpaRepository<WebtoonRoleEntity, Long> {
}
