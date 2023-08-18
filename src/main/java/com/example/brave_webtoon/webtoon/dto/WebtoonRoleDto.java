package com.example.brave_webtoon.webtoon.dto;

import com.example.brave_webtoon.webtoon.entity.WebtoonRoleEntity;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class WebtoonRoleDto {
    private Long id;
    private Long webtoonId;
    private String title;
    private String role;
    private Integer deleteYn;
    private String saveName;
    private String uploadPath;

    @QueryProjection
    public WebtoonRoleDto(Long id, Long webtoonId, String title, String role, int deleteYn, String saveName, String uploadPath) {
        this.id = id;
        this.webtoonId = webtoonId;
        this.title = title;
        this.role = role;
        this.deleteYn = deleteYn;
        this.saveName = saveName;
        this.uploadPath = uploadPath;
    }

    public static WebtoonRoleDto toDto(final WebtoonRoleEntity webtoonRoleEntity){
        return WebtoonRoleDto
                .builder()
                .id(webtoonRoleEntity.getId())
                .webtoonId(webtoonRoleEntity.getWebtoonEntity().getId())
//                .webtoonId(webtoonRoleEntity.getWebtoonId())
                .title(webtoonRoleEntity.getTitle())
                .role(webtoonRoleEntity.getRole())
                .deleteYn(webtoonRoleEntity.getDeleteYn())
                .saveName(webtoonRoleEntity.getSaveName())
                .uploadPath(webtoonRoleEntity.getSaveName())
                .build();
    }
}
