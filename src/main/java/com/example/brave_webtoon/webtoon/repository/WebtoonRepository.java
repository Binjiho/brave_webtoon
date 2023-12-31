package com.example.brave_webtoon.webtoon.repository;

import com.example.brave_webtoon.webtoon.dto.*;
import com.example.brave_webtoon.webtoon.dto.admin.WebtoonResponseDto;
import com.example.brave_webtoon.webtoon.entity.WebtoonEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
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

    public List<MainRequestDto> findWebtoonIdList(int pageSize, int offset, String title) {
        List<WebtoonEntity> webtoonEntityList = queryFactory.selectFrom(webtoonEntity)
                .where(
                        noOffsetBuilder(offset, "w"),
                        webtoonEntity.deleteYn.lt(1),
                        titleBuilder(title,"w")
                )
                .limit(pageSize+1)
                .fetch();

        return checkMainLastPage(webtoonEntityList,pageSize,offset);
    }

    private List<MainRequestDto> checkMainLastPage(List<WebtoonEntity> webtoonEntityList, int pageSize, int offset) {

        boolean hasNext = false;

        // 조회한 결과 개수가 요청한 페이지 사이즈보다 크면 뒤에 더 있음, next = true
        if (webtoonEntityList.size() > pageSize) {
            hasNext = true;
            webtoonEntityList.remove(pageSize);
        }

        List<MainRequestDto> result = new ArrayList<>();
        result.add(MainRequestDto.builder()
                .webtoonEntityList(webtoonEntityList)
                .hasNext(hasNext)
                .lastOffset(offset)
                .build());
        return result;
    }

    public List<MainResponseDto> findWebtoonListWithVote(Long id) {
        return queryFactory.select(
                        Projections.constructor(
                                MainResponseDto.class,
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
                .leftJoin(webtoonRoleEntity.voteEntityList, voteEntity)
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
//                        noOffsetBuilder(offset, "wr"),
                        webtoonEntity.id.eq(webtoonId)
                )
                .orderBy(webtoonRoleEntity.id.asc())
                .offset(offset)
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

    private BooleanExpression titleBuilder(String title, String condition) {
        if (title == null) {
            return null; // BooleanExpression 자리에 null이 반환되면 조건문에서 자동으로 제거된다
        }else{
            if(condition.equals("wr")){
                return webtoonRoleEntity.title.contains(title);
            }else{
                return webtoonEntity.title.contains(title);
            }
        }
    }
    private BooleanExpression noOffsetBuilder(int offset, String condition) {
        if (offset == 0) {
            return null; // BooleanExpression 자리에 null이 반환되면 조건문에서 자동으로 제거된다
        }else{
            if(condition.equals("wr")){
                return webtoonRoleEntity.id.gt(offset);
            }else{
                return webtoonEntity.id.gt(offset);
            }
        }
    }

    /**
     * Admin
     */
    public List<WebtoonResponseDto> findAllWebtoon(int pageSize, int page, String search, String order) {
        List<WebtoonEntity> total = null;
        int totalRecordCount = 0;
        total = queryFactory.selectFrom(webtoonEntity)
                .where( titleBuilder(search,"w") )
                .fetch();
        if (total != null){
            totalRecordCount = total.size();
        }

        return buildPaging(pageSize, page, totalRecordCount, search, order);
    }

    public List<WebtoonResponseDto> findAllWebtoonResult (int pageSize, int page, int startPage, int endPage, int offset, boolean hasNext, boolean hasPrev, String search, String order){
        List<WebtoonEntity> content = queryFactory.selectFrom(webtoonEntity)
                .where(
                        titleBuilder(search,"w")
                )
                .orderBy(orderBoolean(order), webtoonEntity.id.desc())
                .offset(offset)
                .limit(pageSize)
                .fetch();

        List<WebtoonResponseDto> result = new ArrayList<>();
        result.add(WebtoonResponseDto.builder()
                .webtoonEntityList(content)
                .page(page)
                .pageSize(pageSize)
                .startPage(startPage)
                 .endPage(endPage)
                .hasNext(hasNext)
                .hasPrev(hasPrev)
                .build());

        return  result;
    }

    private OrderSpecifier<?> orderBoolean (String order) {
        if(!order.isBlank()){
            switch (order) {
                case "오래된순": return new OrderSpecifier(Order.ASC, webtoonEntity.id);
                case "투표많은순": return new OrderSpecifier(Order.DESC, webtoonEntity.hit);
                case "투표낮은순": return new OrderSpecifier(Order.ASC, webtoonEntity.hit);
                default: return new OrderSpecifier(Order.DESC, webtoonEntity.id);
            }
        }
        return null;
    }

    private List<WebtoonResponseDto> buildPaging(int pageSize, int page, int totalRecordCount, String search, String order) {

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

        return findAllWebtoonResult(pageSize, page, startPage, endPage, offset,  hasNext, hasPrev, search, order);
    }

}
