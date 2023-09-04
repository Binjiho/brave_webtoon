package com.example.brave_webtoon.webtoon.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class WebtoonRolePagingDto {
    private List<WebtoonTitleDto> webtoonTitle;
    private List<WebtoonRoleResponseDto> webtoonRoleList;
    private Integer page;
    private Integer pageSize;
    private Integer startPage;
    private Integer endPage;
    private boolean hasNext;
    private boolean hasPrev;
}
