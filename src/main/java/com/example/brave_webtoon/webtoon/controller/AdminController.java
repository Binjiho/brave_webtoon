package com.example.brave_webtoon.webtoon.controller;

import com.example.brave_webtoon.webtoon.dto.*;
import com.example.brave_webtoon.webtoon.dto.admin.VoteResponseDto;
import com.example.brave_webtoon.webtoon.dto.admin.WebtoonResponseDto;
import com.example.brave_webtoon.webtoon.dto.admin.WebtoonRolePagingDto;
import com.example.brave_webtoon.webtoon.dto.admin.WebtoonRoleResponseDto;
import com.example.brave_webtoon.webtoon.entity.WebtoonEntity;
import com.example.brave_webtoon.webtoon.entity.WebtoonRoleEntity;
import com.example.brave_webtoon.webtoon.service.WebtoonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
            @Parameter(name="search", description = "검색값", required = false),
            @Parameter(name="order", description = "정렬값", required = false)
//            @Parameter(name="title", description = "검색 조건 - 제목", required = false)
    })
    @ResponseBody
    public List<WebtoonResponseDto> getWebtoonList(
            @RequestParam(value="pageSize", defaultValue="10") int pageSize,
            @RequestParam(value="page", required = false, defaultValue="1") int page,
            @RequestParam(value="search", required = false) String search,
            @RequestParam(value="order", defaultValue="최신순,오래된순,투표많은순,투표낮은순", required = false) String order
            ){
        List<WebtoonResponseDto> result = webtoonService.findAllWebtoonList(pageSize, page, search, order);
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
            @Parameter(name="id", description = "webtoonId", required = true),
            @Parameter(name="pageSize", description = "페이징 사이즈", required = true),
            @Parameter(name="page", description = "페이징 시작 index", required = false)
    })
    public List<WebtoonRolePagingDto> getWebtoonRoleList(
            @PathVariable(value="id") Long id,
            @RequestParam(value="pageSize", defaultValue="10") int pageSize,
            @RequestParam(value="page", required = false, defaultValue="1") int page
    ){
        List<WebtoonRolePagingDto> result = webtoonService.findAdminAllWebtoonRoleList(id, pageSize, page);
        return result;
    }

    @PostMapping("/admin/webtoonList/{id}")
    @Operation(summary = "어드민 웹툰Role 리스트 노출 변경", description = "어드민 웹툰Role 리스트 노출 변경")
    @Parameters({
            @Parameter(name="roleId", description = "webtoonRoleId", required = true),
            @Parameter(name="deleteYn", description = "0:삭제안함, 1:삭제", required = true)
    })
    public Long postWebtoonRoleList(
            @PathVariable(value="id") Long id,
            @RequestParam(value="roleId") Long roleId,
            @RequestParam(value="deleteYn") int deleteYn)
    {
        return webtoonService.transWebtoonRoleDeleteYN(roleId, deleteYn);
    }

    @GetMapping("/admin/voteList")
    @Operation(summary = "어드민 웹툰Role 투표 리스트 화면", description = "어드민 웹툰Role 투표 리스트를 화면에 출력")
    @Parameters({
            @Parameter(name="roleId", description = "webtoonRoleId", required = true)
    })
    public List<VoteResponseDto> getVoteList(
            @RequestParam(value="roleId") Long roleId
    ){
        List<VoteResponseDto> result = webtoonService.findVoteList(roleId);
        return result;
    }

    @PostMapping("/admin/voteList")
    @Operation(summary = "어드민 웹툰Role 투표 인물URL 변경", description = "어드민 웹툰Role 투표 인물URL 변경")
    @Parameters({
            @Parameter(name="voteId", description = "voteId", required = true),
            @Parameter(name="personUrl", description = "변경할 인물 이미지 URL", required = true)
    })
    public Long postTransPerson(
            @RequestParam(value="voteId") Long voteId,
            @RequestParam(value="personUrl") String personUrl)
    {
        return webtoonService.transPersonUrl(voteId, personUrl);
    }

}
