package com.example.brave_webtoon.webtoon.repository;

import com.example.brave_webtoon.webtoon.dto.QWebtoonRoleDto;
import com.example.brave_webtoon.webtoon.dto.WebtoonRoleDto;
import com.example.brave_webtoon.webtoon.entity.WebtoonEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.brave_webtoon.webtoon.entity.QWebtoonRoleEntity.webtoonRoleEntity;

@RequiredArgsConstructor
@Repository
public class WebtoonRoleRepository {
    private final JPAQueryFactory queryFactory;

    /**
     * QueryDSL @QueryProjection을 이용하여 DTO조회
     * @param webtoonId
     * @return
     */
    public List<WebtoonRoleDto> findAllByWebtoonId(Long webtoonId) {
        return queryFactory.select(
                new QWebtoonRoleDto(
                        webtoonRoleEntity.id,
                        webtoonRoleEntity.webtoonEntity.id,
                        webtoonRoleEntity.title,
                        webtoonRoleEntity.role,
                        webtoonRoleEntity.deleteYn,
                        webtoonRoleEntity.saveName,
                        webtoonRoleEntity.uploadPath)
                )
                .from(webtoonRoleEntity)
                .where(webtoonRoleEntity.webtoonEntity.eq(WebtoonEntity.builder().id(webtoonId).build()))
                .fetchAll().fetch();
    }

//    public List<WebtoonRoleEntity> findAllByWebtoonId(Long webtoonId) {
//        return queryFactory.selectFrom(webtoonRoleEntity)
//                .where(webtoonRoleEntity.webtoonEntity.eq(WebtoonEntity.builder().id(webtoonId).build()))
//                .fetchAll().fetch();
//    }

    public List<WebtoonRoleDto> findByWebtoonRoleId(Long webtoonRoleId) {
        return queryFactory.select(
                        new QWebtoonRoleDto(
                                webtoonRoleEntity.id,
                                webtoonRoleEntity.webtoonEntity.id,
                                webtoonRoleEntity.title,
                                webtoonRoleEntity.role,
                                webtoonRoleEntity.deleteYn,
                                webtoonRoleEntity.saveName,
                                webtoonRoleEntity.uploadPath)
                )
                .from(webtoonRoleEntity)
                .where(webtoonRoleEntity.id.eq(webtoonRoleId))
                .fetch();
    }

//    public List<WebtoonRoleEntity> findByWebtoonRoleId(Long webtoonRoleId) {
//        return queryFactory.selectFrom(webtoonRoleEntity)
//                .where(webtoonRoleEntity.id.eq(webtoonRoleId))
//                .fetch();
//    }
}
