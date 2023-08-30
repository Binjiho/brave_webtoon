package com.example.brave_webtoon.webtoon.dto;

import com.example.brave_webtoon.webtoon.entity.VoteEntity;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class MainDto {

    private Long webtoonId;
    private String title;
    private String name;
    private String role;
    private String uploadPath;
    private Long cnt;
    private String personName;
    private String personUrl;
}
