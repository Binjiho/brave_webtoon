package com.example.brave_webtoon.webtoon.dto;

import com.example.brave_webtoon.webtoon.entity.WebtoonEntity;
import com.example.brave_webtoon.webtoon.entity.WebtoonRoleEntity;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class WebtoonDto {
    private Long id;
    private String title;
    private Integer hit;
    private Integer deleteYn;
    private String uploadPath;
    private List<WebtoonRoleEntity> webtoonRoleEntityList;
    private boolean hasNext;
    private Integer lastOffset;
    private LocalDateTime createdDate;

    @QueryProjection
    public WebtoonDto(String title, String uploadPath, List<WebtoonRoleEntity> webtoonRoleEntityList) {
        this.title = title;
        this.uploadPath = uploadPath;
        this.webtoonRoleEntityList = webtoonRoleEntityList;
    }

    public static WebtoonDto toDto(final WebtoonEntity webtoonEntity){
        return WebtoonDto
                .builder()
                .id(webtoonEntity.getId())
                .title(webtoonEntity.getTitle())
                .hit(webtoonEntity.getHit())
                .deleteYn(webtoonEntity.getDeleteYn())
                .uploadPath(webtoonEntity.getUploadPath())
                .createdDate(webtoonEntity.getCreatedDate())
                .build();
    }
}
