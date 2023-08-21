package com.example.brave_webtoon.webtoon.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class VoteResultListDto {
    private Long cnt;
    private String personName;
    private String personUrl;
}
