package com.example.brave_webtoon.webtoon.controller;

import com.example.brave_webtoon.webtoon.dto.*;
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
@Tag(name="Webtoon-API", description = "웹툰 API Document")
public class WebtoonController {

    private final WebtoonService webtoonService;

    @GetMapping("/webtoon/webtoonList")
    @Operation(summary = "웹툰 리스트 화면", description = "웹툰 리스트를 화면에 출력")
    @Parameters({
            @Parameter(name="pageSize", description = "페이징 사이즈", required = true),
            @Parameter(name="offset", description = "페이징 시작 index", required = false),
            @Parameter(name="title", description = "검색 조건 - 제목", required = false)
    })
    @ResponseBody
    public List<MainRequestDto> getWebtoonList(
            @RequestParam(value="pageSize") int pageSize,
            @RequestParam(value="offset", required = false, defaultValue="0") int offset,
            @RequestParam(value="title", required = false) String title
    ){
        List<MainRequestDto> result = webtoonService.findMainWebtoonList(pageSize, offset, title);
        return result;
    }

    @GetMapping("/webtoon/webtoonRoleList")
    @Operation(summary = "웹툰 캐릭터 리스트 화면", description = "웹툰 캐릭터 리스트를 화면에 출력 with 더보기")
    @Parameters({
            @Parameter(name="id", description = "webtoonId", required = true),
            @Parameter(name="pageSize", description = "페이징 사이즈", required = true),
            @Parameter(name="offset", description = "페이징 시작 index", required = false)
    })
    @ResponseBody
    public List<WebtoonDto> getWebtoonRoleList(
            @RequestParam(value="id") Long id,
            @RequestParam(value="pageSize") int pageSize,
            @RequestParam(value="offset", required = false, defaultValue="0") int offset
    ){
        List<WebtoonDto> result = webtoonService.findAllWebtoonRoleList(id, pageSize, offset);
        return result;
    }

    @GetMapping("/webtoon/webtoonVote/{id}")
    @Operation(summary = "웹툰 캐릭터 상세 화면", description = "웹툰 캐릭터중 한명을 화면에 출력")
    @Parameters({
            @Parameter(name="id", description = "webtoonRoleId", required = true)
    })
    @ResponseBody
    public List<WebtoonRoleDto> openVoteWebtoonDetailPage(@PathVariable Long id){
        List<WebtoonRoleDto> result = webtoonService.findByWebtoonRoleId(id);
        return result;
    }

    @PostMapping("/webtoon/webtoonVote")
    @Operation(summary = "웹툰 캐릭터 상세화면 투표하기", description = "웹툰 캐릭터중 한명과 연예인을 투표하기")
    @Parameters({
            @Parameter(name="webtoonId", description="webtoonId", required = true),
            @Parameter(name="webtoonRoleId", description="webtoonRoleId", required = true),
            @Parameter(name="personName", description="인물이름", required = true),
            @Parameter(name="personUrl", description="인물검색API URL결과값", required = true)
    })
    @ResponseBody
    public void postVoteWebtoonRole(@RequestBody VoteDto voteDto){
        webtoonService.saveVote(voteDto);
    }

    @GetMapping("/webtoon/webtoonVote/result/{id}")
    @Operation(summary = "웹툰 캐릭터 투표 결과 화면", description = "웹툰 캐릭터중 한명의 투표결과를 화면에 출력")
    @Parameters({
            @Parameter(name="id", description = "webtoonRoleId", required = true)
    })
    @ResponseBody
    public List<VoteResultDto> findVoteResultByWebtoonRoleId(@PathVariable Long id){
        List<VoteResultDto> result = webtoonService.findResultByWebtoonRoleId(id);
        return result;
    }
    
}
