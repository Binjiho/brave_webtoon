package com.example.brave_webtoon.webtoon.dto.admin;

import com.example.brave_webtoon.webtoon.entity.WebtoonRoleEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class WebtoonRoleResponseDto {

    private List<WebtoonRoleEntity> webtoonRoleEntityList;
    private Integer page;
    private Integer pageSize;
    private Integer startPage;
    private Integer endPage;
    private boolean hasNext;
    private boolean hasPrev;
}
