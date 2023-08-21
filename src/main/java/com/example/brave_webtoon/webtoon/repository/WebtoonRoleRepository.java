package com.example.brave_webtoon.webtoon.repository;

import com.example.brave_webtoon.webtoon.dto.QVoteDto;
import com.example.brave_webtoon.webtoon.dto.QWebtoonRoleDto;
import com.example.brave_webtoon.webtoon.dto.VoteDto;
import com.example.brave_webtoon.webtoon.dto.WebtoonRoleDto;

import com.example.brave_webtoon.webtoon.entity.QVoteEntity;
import com.example.brave_webtoon.webtoon.entity.VoteEntity;
import com.example.brave_webtoon.webtoon.entity.WebtoonEntity;
import com.example.brave_webtoon.webtoon.entity.WebtoonRoleEntity;
import com.querydsl.core.Tuple;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.brave_webtoon.webtoon.entity.QWebtoonRoleEntity.webtoonRoleEntity;
import static com.example.brave_webtoon.webtoon.entity.QVoteEntity.voteEntity;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;


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
                        webtoonRoleEntity.uploadPath,
                        webtoonRoleEntity.voteEntityList
                        )
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
        return queryFactory
                .select(webtoonRoleEntity.id,
                        webtoonRoleEntity.webtoonEntity.id,
                        webtoonRoleEntity.title,
                        webtoonRoleEntity.role,
                        webtoonRoleEntity.deleteYn,
                        webtoonRoleEntity.saveName,
                        webtoonRoleEntity.uploadPath,
                        voteEntity.id,
                        voteEntity.personName,
                        voteEntity.personUrl,
                        voteEntity.deleteYn)
                .from(webtoonRoleEntity)
                .leftJoin(webtoonRoleEntity.voteEntityList, voteEntity)
                .where(webtoonRoleEntity.id.eq(webtoonRoleId))

                .transform(
                        groupBy(webtoonRoleEntity.id).list(
                                Projections.constructor(WebtoonRoleDto.class,
                                        webtoonRoleEntity.id,
                                        webtoonRoleEntity.webtoonEntity.id,
                                        webtoonRoleEntity.title,
                                        webtoonRoleEntity.role,
                                        webtoonRoleEntity.deleteYn,
                                        webtoonRoleEntity.saveName,
                                        webtoonRoleEntity.uploadPath,
                                        list(
                                                Projections.constructor(VoteDto.class,
                                                        voteEntity.id,
                                                        voteEntity.webtoonEntity.id,
                                                        voteEntity.webtoonRoleEntity.id,
                                                        voteEntity.personName,
                                                        voteEntity.personUrl,
                                                        voteEntity.deleteYn)
                                        )
                                )
                        )
                );
    }


//    public List<WebtoonRoleDto> findByWebtoonRoleId(Long webtoonRoleId) {
//        return queryFactory
//                .select(new QWebtoonRoleDto(
//                                webtoonRoleEntity.id,
//                                webtoonRoleEntity.webtoonEntity.id,
//                                webtoonRoleEntity.title,
//                                webtoonRoleEntity.role,
//                                webtoonRoleEntity.deleteYn,
//                                webtoonRoleEntity.saveName,
//                                webtoonRoleEntity.uploadPath,
//                                list(voteEntity)
//                        )
//                )
//                .from(webtoonRoleEntity)
//                .leftJoin(webtoonRoleEntity.voteEntityList, voteEntity)
//                .where(webtoonRoleEntity.id.eq(webtoonRoleId))
//                .fetch();
//    }

}
