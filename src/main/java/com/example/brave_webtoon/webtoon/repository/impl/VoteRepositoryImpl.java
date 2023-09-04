package com.example.brave_webtoon.webtoon.repository.impl;

import com.example.brave_webtoon.webtoon.entity.VoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRepositoryImpl extends JpaRepository<VoteEntity, Long> {
}
