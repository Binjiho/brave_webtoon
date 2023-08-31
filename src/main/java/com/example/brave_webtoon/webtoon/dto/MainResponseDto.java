package com.example.brave_webtoon.webtoon.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class MainResponseDto {

    private Long webtoonId;
    private String title;
    private String name;
    private String role;
    private String uploadPath;
    private Long cnt;
    private String personName;
    private String personUrl;
}
