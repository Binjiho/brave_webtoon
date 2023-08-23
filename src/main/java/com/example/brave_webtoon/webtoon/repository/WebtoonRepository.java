package com.example.brave_webtoon.webtoon.repository;

import com.example.brave_webtoon.webtoon.dto.WebtoonDto;
import com.example.brave_webtoon.webtoon.dto.WebtoonRoleListDto;
import com.example.brave_webtoon.webtoon.entity.WebtoonEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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
    public Slice<WebtoonDto> findAllRoleByWebtoonId(Long webtoonId, int pageSize, int offset) {
        List<WebtoonDto> content = queryFactory
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
//        List<WebtoonDto> content = new ArrayList<>();
//        for (WebtoonDto dto: webtoonDtoList) {
//            content.add(dto);
//        }
        return checkLastPage(content,pageSize,offset);
    }
    private Slice<WebtoonDto> checkLastPage(List<WebtoonDto> content, int pageSize, int offset) {

        boolean hasNext = false;
        Pageable pageable = PageRequest.of(offset,pageSize);

        // 조회한 결과 개수가 요청한 페이지 사이즈보다 크면 뒤에 더 있음, next = true
        if (content.get(0).getWebtoonRoleEntityList().size() > pageSize) {
            hasNext = true;
            content.get(0).getWebtoonRoleEntityList().remove(pageSize);
        }

        return new SliceImpl<>(content, pageable, hasNext);
    }

    private BooleanExpression noOffsetBuilder(int offset) {
        if (offset == 0) {
            return null; // BooleanExpression 자리에 null이 반환되면 조건문에서 자동으로 제거된다
        }
        return webtoonRoleEntity.id.gt(offset);
    }

//    public List<WebtoonDto> findAllRoleByWebtoonId(Long webtoonId) {
//        return queryFactory
//                .select(
//                        webtoonEntity.title,
//                        webtoonEntity.saveName,
//                        webtoonEntity.uploadPath,
//                        webtoonRoleEntity.id,
//                        webtoonRoleEntity.webtoonEntity.id,
//                        webtoonRoleEntity.title,
//                        webtoonRoleEntity.role,
//                        webtoonRoleEntity.deleteYn,
//                        webtoonRoleEntity.saveName,
//                        webtoonRoleEntity.uploadPath
//                )
//                .from(webtoonEntity)
//                .leftJoin(webtoonEntity.webtoonRoleEntityList, webtoonRoleEntity)
//                .where(webtoonEntity.id.eq(webtoonId))
//                .offset(0)
//                .limit(5)
//                .transform(
//                        groupBy(webtoonEntity.id).list(
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
