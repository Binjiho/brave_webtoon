package com.example.brave_webtoon.webtoon.dto;

import com.example.brave_webtoon.webtoon.entity.VoteEntity;
import com.example.brave_webtoon.webtoon.entity.WebtoonRoleEntity;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class WebtoonRoleListDto {
    private Long id;
    private Long webtoonId;
    private String name;
    private String role;
    private Integer deleteYn;
    private String uploadPath;

    @QueryProjection
    public WebtoonRoleListDto(Long id, Long webtoonId, String name, String role, int deleteYn, String uploadPath) {
        this.id = id;
        this.webtoonId = webtoonId;
        this.name = name;
        this.role = role;
        this.deleteYn = deleteYn;
        this.uploadPath = uploadPath;
    }
}
