package com.example.brave_webtoon.security.repository;

import com.example.brave_webtoon.security.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findByUserId(String userId);
}
