package com.example.brave_webtoon.webtoon.repository;

import com.example.brave_webtoon.webtoon.dto.*;

import com.example.brave_webtoon.webtoon.dto.admin.*;
import com.example.brave_webtoon.webtoon.entity.WebtoonEntity;
import com.example.brave_webtoon.webtoon.entity.WebtoonRoleEntity;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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
                                    webtoonRoleEntity.name,
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

    /**
     * Admin
     */
    public List<WebtoonRolePagingDto> findAdminAllWebtoonRoleList(Long id, int pageSize, int page) {
        List<WebtoonRoleEntity> total = null;
        int totalRecordCount = 0;
        total = queryFactory.selectFrom(webtoonRoleEntity)
                .where(
                        webtoonRoleEntity.webtoonEntity.id.eq(id)
                )
                .fetch();
        if (total != null){
            totalRecordCount = total.size();
        }

        return buildPaging(id, pageSize, page, totalRecordCount);
    }

    public List<WebtoonRolePagingDto> findAllWebtoonRoleResult(Long id, int pageSize, int page, int startPage, int endPage, int offset, boolean hasNext, boolean hasPrev){
        List<WebtoonRoleResponseDto> content = queryFactory
                .selectFrom(webtoonRoleEntity)
                .where(
                        webtoonRoleEntity.webtoonEntity.id.eq(id)
                )
                .orderBy(webtoonRoleEntity.hit.desc(), webtoonRoleEntity.id.desc())
                .offset(offset)
                .limit(pageSize)
                .transform(
                        groupBy(webtoonRoleEntity.id).list(
                                Projections.constructor(WebtoonRoleResponseDto.class,
                                        webtoonRoleEntity.id,
                                        webtoonRoleEntity.name,
                                        webtoonRoleEntity.role,
                                        webtoonRoleEntity.deleteYn,
                                        webtoonRoleEntity.hit,
                                        webtoonRoleEntity.uploadPath
                                )
                        )
                );

        List<WebtoonTitleDto> title = queryFactory
                .selectFrom(webtoonEntity)
                .where(
                        webtoonEntity.id.eq(id)
                )
                .transform(
                        groupBy(webtoonEntity.id).list(
                                Projections.constructor(WebtoonTitleDto.class,
                                        webtoonEntity.id,
                                        webtoonEntity.title,
                                        webtoonEntity.hit,
                                        webtoonEntity.deleteYn,
                                        webtoonEntity.uploadPath,
                                        webtoonEntity.createdDate
                                )
                        )
                );

        List<WebtoonRolePagingDto> result = new ArrayList<>();
        result.add(WebtoonRolePagingDto.builder()
                .webtoonTitle(title)
                .webtoonRoleList(content)
                .page(page)
                .pageSize(pageSize)
                .startPage(startPage)
                .endPage(endPage)
                .hasNext(hasNext)
                .hasPrev(hasPrev)
                .build());

        return  result;
    }

    private List<WebtoonRolePagingDto> buildPaging(Long id, int pageSize, int page, int totalRecordCount) {

        int totalPageCount;
        int startPage;
        int endPage;
        int offset;
        boolean hasNext = false;
        boolean hasPrev = false;

        // 전체 페이지 수 계산
        totalPageCount = ((totalRecordCount - 1) / pageSize) + 1;

        // 현재 페이지 번호가 전체 페이지 수보다 큰 경우, 현재 페이지 번호에 전체 페이지 수 저장
        if (page > totalPageCount) {
            page = totalPageCount;
        }

        // 첫 페이지 번호 계산
        startPage = ( (page-1) / pageSize ) * pageSize + 1;

        // 끝 페이지 번호 계산
        endPage = (startPage + pageSize) - 1;

        // 끝 페이지가 전체 페이지 수보다 큰 경우, 끝 페이지 전체 페이지 수 저장
        if (endPage > totalPageCount) {
            endPage = totalPageCount;
        }

        // OFFSET 계산
        offset = (page-1) * pageSize;

        // 이전 페이지 존재 여부 확인
        hasPrev = startPage > 1;

        // 다음 페이지 존재 여부 확인
        hasNext = endPage < totalPageCount;

        return findAllWebtoonRoleResult(id, pageSize, page, startPage, endPage, offset,  hasNext, hasPrev);
    }

    private BooleanExpression noOffsetBuilder(int offset) {
        if (offset == 0) {
            return null;
        }else{
            return webtoonRoleEntity.id.gt(offset);
        }
    }

    public List<VoteResponseDto> findAllVoteList(Long roleId) {
        return queryFactory
                .selectFrom(voteEntity)
                .where(voteEntity.webtoonRoleEntity.id.eq(roleId))
                .groupBy(voteEntity.personName)
                .orderBy(voteEntity.personName.count().desc(), voteEntity.id.desc())
                .transform(groupBy(voteEntity.personName).list(
                                Projections.constructor(
                                        VoteResponseDto.class,
                                        voteEntity.id,
                                        voteEntity.webtoonEntity.id,
                                        voteEntity.webtoonRoleEntity.id,
                                        voteEntity.personName,
                                        voteEntity.personUrl,
                                        voteEntity.deleteYn,
                                        voteEntity.personName.count().as("cnt")
                                )
                        )
                );
    }

}
