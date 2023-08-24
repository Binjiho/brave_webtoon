package com.example.brave_webtoon.webtoon.repository;

import com.example.brave_webtoon.webtoon.dto.*;

import com.example.brave_webtoon.webtoon.entity.WebtoonEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.brave_webtoon.webtoon.entity.QWebtoonEntity.webtoonEntity;
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
     * @param webtoonRoleId
     * @return
     */
    public List<WebtoonRoleDto> findByWebtoonRoleId(Long webtoonRoleId) {
        return queryFactory
                .selectFrom(webtoonRoleEntity)
                .leftJoin(webtoonRoleEntity.voteEntityList, voteEntity)
                .where(webtoonRoleEntity.id.eq(webtoonRoleId), webtoonRoleEntity.deleteYn.lt(1))
                .groupBy(webtoonRoleEntity.id, voteEntity.personName)
                .transform(
                        groupBy(webtoonRoleEntity.id).list(
                                Projections.constructor(WebtoonRoleDto.class,
                                        webtoonRoleEntity.id,
                                        webtoonRoleEntity.webtoonEntity.id,
                                        webtoonRoleEntity.title,
                                        webtoonRoleEntity.name,
                                        webtoonRoleEntity.role,
                                        webtoonRoleEntity.deleteYn,
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

    public List<VoteResultDto> findResultByWebtoonRoleId(Long webtoonRoleId) {
        return queryFactory
                .selectFrom(webtoonRoleEntity)
                .leftJoin(webtoonRoleEntity.voteEntityList, voteEntity)
                .where(webtoonRoleEntity.id.eq(webtoonRoleId), webtoonRoleEntity.deleteYn.lt(1))
                .groupBy(voteEntity.personName)
                .transform(groupBy(webtoonRoleEntity.id).list(
                            Projections.constructor(
                                    VoteResultDto.class,
                                    webtoonRoleEntity.id,
                                    webtoonRoleEntity.webtoonEntity.id,
                                    webtoonRoleEntity.title,
                                    webtoonRoleEntity.uploadPath,
                                    list(
                                            Projections.constructor(
                                                    VoteResultListDto.class,
                                                    voteEntity.personName.count().as("cnt"),
                                                    voteEntity.personName,
                                                    voteEntity.personUrl
                                            )
                                    )
                            )
                )
            );
    }

}
