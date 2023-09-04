package com.example.brave_webtoon.webtoon.dto;

import com.example.brave_webtoon.webtoon.entity.VoteEntity;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class VoteDto {
    private Long id;
    private Long webtoonId;
    private Long webtoonRoleId;
    private String personName;
    private String personUrl;
    private int deleteYn;
    private LocalDateTime createdDate;

    @QueryProjection
    public VoteDto(Long id, Long webtoonId, Long webtoonRoleId, String personName, String personUrl, int deleteYn) {
        this.id = id;
        this.webtoonId = webtoonId;
        this.webtoonRoleId = webtoonRoleId;
        this.personName = personName;
        this.personUrl = personUrl;
        this.deleteYn = deleteYn;
    }

}
