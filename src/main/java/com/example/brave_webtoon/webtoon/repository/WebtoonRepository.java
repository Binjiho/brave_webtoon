package com.example.brave_webtoon.webtoon.repository;

import com.example.brave_webtoon.webtoon.dto.*;
import com.example.brave_webtoon.webtoon.entity.WebtoonEntity;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.example.brave_webtoon.webtoon.entity.QVoteEntity.voteEntity;
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

    /**
     SELECT
         wr.title,
         wr.name,
         wr.role,
         wr.upload_path,
         aa.webtoon_role_id,
         aa.person_name,
         aa.person_url
     FROM
        webtoon_role wr
     INNER JOIN
     (
         SELECT
             wv.webtoon_id,
             wv.webtoon_role_id,
             wv.person_name,
             wv.person_url,
             COUNT(wv.person_name) AS cnt
         FROM
            webtoon_vote wv
         WHERE
            wv.webtoon_id = 1
         GROUP BY wv.person_name
         ORDER BY cnt DESC
     ) aa
        ON (wr.id = aa.webtoon_role_id)
     WHERE
        wr.delete_yn < 1
     GROUP BY aa.webtoon_role_id
     */
    public List<WebtoonEntity> findWebtoonIdList(String title) {
        return queryFactory.selectFrom(webtoonEntity)
                .where(webtoonEntity.deleteYn.lt(1),titleBuilder(title))
                .fetch();
    }

    public List<MainDto> findWebtoonListWithVote(Long id) {
        return queryFactory.select(
                        Projections.constructor(
                                MainDto.class,
                                webtoonRoleEntity.webtoonEntity.id.as("webtoonId"),
                                webtoonRoleEntity.title,
                                webtoonRoleEntity.name,
                                webtoonRoleEntity.role,
                                webtoonRoleEntity.uploadPath,
                                voteEntity.personName.count().as("cnt"),
                                voteEntity.personName,
                                voteEntity.personUrl
                        )
                )
                .from(webtoonRoleEntity)
                .innerJoin(webtoonRoleEntity.voteEntityList, voteEntity)
                .where(webtoonRoleEntity.deleteYn.lt(1),webtoonRoleEntity.webtoonEntity.id.eq(id))
                .groupBy(webtoonRoleEntity.id, voteEntity.personName)
                .orderBy(voteEntity.personName.count().desc())
                .limit(1)
                .fetch();
    }

    /**
     * QueryDSL @QueryProjection을 이용하여 DTO조회
     * @param webtoonId
     * @return
     */
    public List<WebtoonDto> findAllRoleByWebtoonId(Long webtoonId, int pageSize, int offset) {
        List<WebtoonDto> content = queryFactory
                .select(
                        webtoonEntity.title,
                        webtoonEntity.uploadPath,
                        webtoonRoleEntity.id,
                        webtoonRoleEntity.webtoonEntity.id,
                        webtoonRoleEntity.title,
                        webtoonRoleEntity.name,
                        webtoonRoleEntity.role,
                        webtoonRoleEntity.deleteYn,
                        webtoonRoleEntity.uploadPath
                )
                .from(webtoonEntity)
                .leftJoin(webtoonEntity.webtoonRoleEntityList, webtoonRoleEntity)
                .where(
                        noOffsetBuilder(offset),
                        webtoonEntity.id.eq(webtoonId)
                )
                .orderBy(webtoonRoleEntity.id.asc())
                .limit(pageSize+1) //hasNext를 구하기 위해 +1
                .transform(
                        groupBy(webtoonEntity.id).list(
                                Projections.constructor(WebtoonDto.class,
                                        webtoonEntity.title,
                                        webtoonEntity.uploadPath,
                                        list(
                                                Projections.constructor(WebtoonRoleListDto.class,
                                                        webtoonRoleEntity.id,
                                                        webtoonRoleEntity.webtoonEntity.id,
                                                        webtoonRoleEntity.name,
                                                        webtoonRoleEntity.role,
                                                        webtoonRoleEntity.deleteYn,
                                                        webtoonRoleEntity.uploadPath
                                                )
                                        )
                                )
                        )
                );
        return checkLastPage(content,pageSize,offset);
    }
    private List<WebtoonDto> checkLastPage(List<WebtoonDto> content, int pageSize, int offset) {

        boolean hasNext = false;
        Pageable pageable = PageRequest.of(offset,pageSize);

        // 조회한 결과 개수가 요청한 페이지 사이즈보다 크면 뒤에 더 있음, next = true
        if (content.get(0).getWebtoonRoleEntityList().size() > pageSize) {
            hasNext = true;
            content.get(0).getWebtoonRoleEntityList().remove(pageSize);
        }

        List<WebtoonDto> result = new ArrayList<>();
        result.add(WebtoonDto.builder()
                        .id(content.get(0).getId())
                        .title(content.get(0).getTitle())
                        .deleteYn(content.get(0).getDeleteYn())
                        .uploadPath(content.get(0).getUploadPath())
                        .webtoonRoleEntityList(content.get(0).getWebtoonRoleEntityList())
                        .hasNext(hasNext)
                        .lastOffset(offset)
                .build());
        return result;
    }

    private BooleanExpression titleBuilder(String title) {
        if (title == null) {
            return null; // BooleanExpression 자리에 null이 반환되면 조건문에서 자동으로 제거된다
        }
        return webtoonRoleEntity.title.contains(title);
    }
    private BooleanExpression noOffsetBuilder(int offset) {
        if (offset == 0) {
            return null; // BooleanExpression 자리에 null이 반환되면 조건문에서 자동으로 제거된다
        }
        return webtoonRoleEntity.id.gt(offset);
    }

}
