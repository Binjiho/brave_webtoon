package com.example.brave_webtoon.webtoon.repository;

import com.example.brave_webtoon.webtoon.dto.WebtoonDto;
import com.example.brave_webtoon.webtoon.dto.WebtoonRoleListDto;
import com.example.brave_webtoon.webtoon.entity.WebtoonEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.brave_webtoon.webtoon.entity.QWebtoonEntity.webtoonEntity;
import static com.example.brave_webtoon.webtoon.entity.QWebtoonRoleEntity.webtoonRoleEntity;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

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

    /**
     * QueryDSL @QueryProjection을 이용하여 DTO조회
     * @param webtoonId
     * @return
     */
    public List<WebtoonDto> findAllRoleByWebtoonId(Long webtoonId) {
        return queryFactory
                .select(
                        webtoonEntity.title,
                        webtoonEntity.saveName,
                        webtoonEntity.uploadPath,
                        webtoonRoleEntity.id,
                        webtoonRoleEntity.webtoonEntity.id,
                        webtoonRoleEntity.title,
                        webtoonRoleEntity.role,
                        webtoonRoleEntity.deleteYn,
                        webtoonRoleEntity.saveName,
                        webtoonRoleEntity.uploadPath
                )
                .from(webtoonEntity)
                .leftJoin(webtoonEntity.webtoonRoleEntityList, webtoonRoleEntity)
                .where(webtoonEntity.id.eq(webtoonId))
                .transform(
                        groupBy(webtoonEntity.id).list(
                                Projections.constructor(WebtoonDto.class,
                                        webtoonEntity.title,
                                        webtoonEntity.saveName,
                                        webtoonEntity.uploadPath,
                                        list(
                                                Projections.constructor(WebtoonRoleListDto.class,
                                                        webtoonRoleEntity.id,
                                                        webtoonRoleEntity.webtoonEntity.id,
                                                        webtoonRoleEntity.title,
                                                        webtoonRoleEntity.role,
                                                        webtoonRoleEntity.deleteYn,
                                                        webtoonRoleEntity.saveName,
                                                        webtoonRoleEntity.uploadPath
                                                )
                                        )
                                )
                        )
                );
    }

//    public List<WebtoonDto> findAllRoleByWebtoonId(Long webtoonId) {
//        return queryFactory
//                .select(webtoonRoleEntity.id,
//                        webtoonRoleEntity.webtoonEntity.id,
//                        webtoonRoleEntity.title,
//                        webtoonRoleEntity.role,
//                        webtoonRoleEntity.deleteYn,
//                        webtoonRoleEntity.saveName,
//                        webtoonRoleEntity.uploadPath,
//                        webtoonEntity.title,
//                        webtoonEntity.saveName,
//                        webtoonEntity.uploadPath
//                )
//                .from(webtoonRoleEntity)
//                .leftJoin(webtoonRoleEntity.webtoonEntity, webtoonEntity)
//                .where(webtoonRoleEntity.webtoonEntity.eq(WebtoonEntity.builder().id(webtoonId).build()))
//                .transform(
//                        groupBy(webtoonRoleEntity.id).list(
//                                Projections.constructor(WebtoonDto.class,
//                                        webtoonEntity.title,
//                                        webtoonEntity.saveName,
//                                        webtoonEntity.uploadPath,
//                                        list(
//                                                Projections.constructor(WebtoonRoleListDto.class,
//                                                        webtoonRoleEntity.id,
//                                                        webtoonRoleEntity.webtoonEntity.id,
//                                                        webtoonRoleEntity.title,
//                                                        webtoonRoleEntity.role,
//                                                        webtoonRoleEntity.deleteYn,
//                                                        webtoonRoleEntity.saveName,
//                                                        webtoonRoleEntity.uploadPath
//                                                )
//                                        )
//                                )
//                        )
//                );
//    }

}
