package com.example.brave_webtoon.webtoon.dto;

import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class VoteResultDto {
    private Long id;
    private Long webtoonId;
    private String title;
    private String saveName;
    private String uploadPath;
    private List<VoteResultListDto> voteResultListDtoList;
}
