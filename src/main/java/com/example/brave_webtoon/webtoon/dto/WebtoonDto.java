package com.example.brave_webtoon.webtoon.dto;

import com.example.brave_webtoon.webtoon.entity.WebtoonEntity;
import com.example.brave_webtoon.webtoon.entity.WebtoonRoleEntity;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class WebtoonDto {
    private Long id;
    private String title;
    private Integer deleteYn;
    private String saveName;
    private String uploadPath;
    private List<WebtoonRoleEntity> webtoonRoleEntityList;
    private boolean hasNext;
    private int lastOffset;

    @QueryProjection
    public WebtoonDto(String title, String saveName, String uploadPath, List<WebtoonRoleEntity> webtoonRoleEntityList) {
        this.title = title;
        this.saveName = saveName;
        this.uploadPath = uploadPath;
        this.webtoonRoleEntityList = webtoonRoleEntityList;
    }

    public static WebtoonDto toDto(final WebtoonEntity webtoonEntity){
        return WebtoonDto
                .builder()
                .id(webtoonEntity.getId())
                .title(webtoonEntity.getTitle())
                .deleteYn(webtoonEntity.getDeleteYn())
                .saveName(webtoonEntity.getSaveName())
                .uploadPath(webtoonEntity.getSaveName())
                .build();
    }
}
