package com.example.brave_webtoon.base.repository;

import com.example.brave_webtoon.base.entity.WebtoonEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;

import static com.example.brave_webtoon.base.entity.QWebtoonEntity.webtoonEntity;

@RequiredArgsConstructor
@Repository
public class WebtoonRepository {
    private final JPAQueryFactory queryFactory;

    public List<WebtoonEntity> findAll() {
        return queryFactory.selectFrom(webtoonEntity)
                .fetchAll().fetch();
    }

    public List<WebtoonEntity> findByTitle(String title) {
        return queryFactory.selectFrom(webtoonEntity)
                .where(webtoonEntity.title.eq(title))
                .fetch();
    }
}
