package com.example.brave_webtoon.webtoon.dto.admin;

import com.example.brave_webtoon.webtoon.entity.WebtoonEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class WebtoonTitleDto {

    private Long id;
    private String title;
    private Integer hit;
    private Integer deleteYn;
    private String uploadPath;
    private LocalDateTime createdDate;
}
