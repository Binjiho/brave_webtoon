package com.example.brave_webtoon.webtoon.dto.admin;

import com.example.brave_webtoon.webtoon.entity.WebtoonEntity;
import com.example.brave_webtoon.webtoon.entity.WebtoonRoleEntity;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class WebtoonResponseDto {

    private List<WebtoonEntity> webtoonEntityList;
    private Integer page;
    private Integer pageSize;
    private Integer startPage;
    private Integer endPage;
    private boolean hasNext;
    private boolean hasPrev;
}
