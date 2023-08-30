package com.example.brave_webtoon.webtoon.dto;

import com.example.brave_webtoon.webtoon.entity.VoteEntity;
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
public class WebtoonRoleDto {
    private Long id;
    private Long webtoonId;
    private String title;
    private String name;
    private String role;
    private Integer deleteYn;
    private Integer hit;
    private String uploadPath;
    private List<VoteEntity> voteEntityList;
    private LocalDateTime createdDate;

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

    public static WebtoonRoleDto toDto(final WebtoonRoleEntity webtoonRoleEntity){
        return WebtoonRoleDto
                .builder()
                .id(webtoonRoleEntity.getId())
                .title(webtoonRoleEntity.getTitle())
                .hit(webtoonRoleEntity.getHit())
                .deleteYn(webtoonRoleEntity.getDeleteYn())
                .uploadPath(webtoonRoleEntity.getUploadPath())
                .createdDate(webtoonRoleEntity.getCreatedDate())
                .build();
    }
    
}
