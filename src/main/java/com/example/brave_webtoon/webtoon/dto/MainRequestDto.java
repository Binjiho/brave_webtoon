package com.example.brave_webtoon.webtoon.dto;

import com.example.brave_webtoon.webtoon.entity.WebtoonEntity;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class MainRequestDto {

    private List<WebtoonEntity> webtoonEntityList;
    private List<MainResponseDto> mainResponseDtoList;
    private boolean hasNext;
    private Integer lastOffset;
}
