package com.example.brave_webtoon.webtoon.dto.admin;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class VoteResponseDto {
    private Long id;
    private Long webtoonId;
    private Long webtoonRoleId;
    private String personName;
    private String personUrl;
    private int deleteYn;
    private LocalDateTime createdDate;
    private Long cnt;

    @QueryProjection
    public VoteResponseDto(Long id, Long webtoonId, Long webtoonRoleId, String personName, String personUrl, int deleteYn, Long cnt) {
        this.id = id;
        this.webtoonId = webtoonId;
        this.webtoonRoleId = webtoonRoleId;
        this.personName = personName;
        this.personUrl = personUrl;
        this.deleteYn = deleteYn;
        this.cnt = cnt;
    }

}
