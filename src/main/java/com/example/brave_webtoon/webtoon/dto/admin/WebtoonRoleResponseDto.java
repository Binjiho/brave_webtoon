package com.example.brave_webtoon.webtoon.dto.admin;

import com.example.brave_webtoon.webtoon.entity.WebtoonRoleEntity;
import com.querydsl.core.Tuple;
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

    private Long id;
    private String name;
    private String role;
    private Integer deleteYn;
    private Integer hit;
    private String uploadPath;
}
