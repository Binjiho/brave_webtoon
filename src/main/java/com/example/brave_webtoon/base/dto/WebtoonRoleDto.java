package com.example.brave_webtoon.base.dto;

import com.example.brave_webtoon.base.entity.WebtoonEntity;
import com.example.brave_webtoon.base.entity.WebtoonRoleEntity;
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

    public static WebtoonRoleDto toDto(final WebtoonRoleEntity webtoonRoleEntity){
        return WebtoonRoleDto
                .builder()
                .id(webtoonRoleEntity.getId())
                .webtoonId(webtoonRoleEntity.getWebtoonId())
                .title(webtoonRoleEntity.getTitle())
                .role(webtoonRoleEntity.getRole())
                .deleteYn(webtoonRoleEntity.getDeleteYn())
                .saveName(webtoonRoleEntity.getSaveName())
                .uploadPath(webtoonRoleEntity.getSaveName())
                .build();
    }
}
