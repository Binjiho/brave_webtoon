package com.example.brave_webtoon.webtoon.controller;

import com.example.brave_webtoon.webtoon.dto.*;
import com.example.brave_webtoon.webtoon.dto.admin.WebtoonResponseDto;
import com.example.brave_webtoon.webtoon.entity.WebtoonEntity;
import com.example.brave_webtoon.webtoon.entity.WebtoonRoleEntity;
import com.example.brave_webtoon.webtoon.service.WebtoonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@Tag(name="Admin-API", description = "어드민 웹툰 API Document")
public class AdminController {

    private final WebtoonService webtoonService;

    @GetMapping("/admin/webtoonList")
    @Operation(summary = "어드민 웹툰 리스트 화면", description = "어드민 웹툰 리스트를 화면에 출력")
    @Parameters({
            @Parameter(name="pageSize", description = "페이징 사이즈", required = true),
            @Parameter(name="page", description = "페이징 시작 index", required = false),
            @Parameter(name="title", description = "검색 조건 - 제목", required = false)
    })
    @ResponseBody
    public List<WebtoonResponseDto> getWebtoonList(
            @RequestParam(value="pageSize") int pageSize,
            @RequestParam(value="page", required = false, defaultValue="1") int page,
            @RequestParam(value="title", required = false) String title
    ){
        List<WebtoonResponseDto> result = webtoonService.findAllWebtoonList(pageSize, page, title);
        return result;
    }

    @PostMapping("/admin/webtoonList")
    @Operation(summary = "어드민 웹툰 리스트 노출 변경", description = "어드민 웹툰 리스트 노출 변경")
    @Parameters({
            @Parameter(name="id", description = "webtoonId", required = true),
            @Parameter(name="deleteYN", description = "deleteYN(0:삭제안함, 1:삭제)", required = true)
    })
    public Long postWebtoonList(@RequestParam(value="id") Long id,
                                @RequestParam(value="deleteYn") int deleteYn){
        return webtoonService.transWebtoonDeleteYN(id, deleteYn);
    }

    @GetMapping("/admin/webtoonList/{id}")
    @Operation(summary = "어드민 웹툰Role 리스트 화면", description = "어드민 웹툰Role 리스트를 화면에 출력")
    @Parameters({
            @Parameter(name="id", description = "webtoonRoleId", required = true)
    })
    public List<WebtoonRoleDto> getWebtoonRoleList(@PathVariable Long id){
        List<WebtoonRoleEntity> list = webtoonService.findAllWebtoonRoleList(id);
        List<WebtoonRoleDto> result = list.stream().map(WebtoonRoleDto::toDto).collect(Collectors.toList());
        return result;
    }

    @PostMapping("/admin/webtoonList/{id}")
    @Operation(summary = "어드민 웹툰Role 리스트 노출 변경", description = "어드민 웹툰Role 리스트 노출 변경")
    @Parameters({
            @Parameter(name="id", description = "webtoonRoleId", required = true),
            @Parameter(name="deleteYN", description = "deleteYN(0:삭제안함, 1:삭제)", required = true)
    })
    public Long postWebtoonRoleList(@RequestParam(value="id") Long id,
                                @RequestParam(value="deleteYn") int deleteYn){
        return webtoonService.transWebtoonRoleDeleteYN(id, deleteYn);
    }
}
