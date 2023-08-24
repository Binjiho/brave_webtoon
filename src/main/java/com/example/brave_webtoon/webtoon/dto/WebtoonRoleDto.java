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
public class WebtoonRoleDto {
    private Long id;
    private Long webtoonId;
    private String title;
    private String name;
    private String role;
    private Integer deleteYn;
    private String uploadPath;
    private List<VoteEntity> voteEntityList;

    @QueryProjection
    public WebtoonRoleDto(Long id, Long webtoonId, String title, String name, String role, int deleteYn, String uploadPath, List<VoteEntity> voteEntityList) {
        this.id = id;
        this.webtoonId = webtoonId;
        this.title = title;
        this.name = name;
        this.role = role;
        this.deleteYn = deleteYn;
        this.uploadPath = uploadPath;
        this.voteEntityList = voteEntityList;
    }

//    public static WebtoonRoleDto toDto(final WebtoonRoleEntity webtoonRoleEntity){
//        return WebtoonRoleDto
//                .builder()
//                .id(webtoonRoleEntity.getId())
//                .webtoonId(webtoonRoleEntity.getWebtoonEntity().getId())
//                .title(webtoonRoleEntity.getTitle())
//                .role(webtoonRoleEntity.getRole())
//                .deleteYn(webtoonRoleEntity.getDeleteYn())
//                .uploadPath(webtoonRoleEntity.getUploadPath())
//                .build();
//    }
}
