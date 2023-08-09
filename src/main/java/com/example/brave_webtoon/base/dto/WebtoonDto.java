package com.example.brave_webtoon.base.dto;

import com.example.brave_webtoon.base.entity.WebtoonEntity;
import lombok.*;

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
