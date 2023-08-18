package com.example.brave_webtoon.webtoon.dto;

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
    private Long webtoonRoleId;
    private String person_name;
    private String person_url;
    private int deleteYn;
    private LocalDateTime createdDate;

    @QueryProjection
    public VoteDto(Long id, Long webtoonRoleId, String person_name, String person_url, int deleteYn) {
        this.id = id;
        this.webtoonRoleId = webtoonRoleId;
        this.person_name = person_name;
        this.person_url = person_url;
        this.deleteYn = deleteYn;
    }

}
