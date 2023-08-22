package com.example.brave_webtoon.webtoon.controller;

import com.example.brave_webtoon.webtoon.dto.*;
import com.example.brave_webtoon.webtoon.entity.WebtoonEntity;
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

    @GetMapping("/webtoonList")
    @Operation(summary = "웹툰 리스트 화면", description = "웹툰 리스트를 화면에 출력")
    @ResponseBody
    public List<WebtoonDto> getWebtoonList(){
        List<WebtoonEntity> list = webtoonService.findAllWebtoonList();
        List<WebtoonDto> result = list.stream().map(WebtoonDto::toDto).collect(Collectors.toList());
        return result;
    }

    @GetMapping("/webtoonList/{id}")
    @Operation(summary = "웹툰 캐릭터 리스트 화면", description = "웹툰 캐릭터 리스트를 화면에 출력")
    @Parameters({
            @Parameter(name="id", description = "webtoonId", required = true)
    })
    @ResponseBody
    public List<WebtoonRoleListDto> getWebtoonRoleList(@PathVariable Long id){
        List<WebtoonRoleListDto> result = webtoonService.findAllWebtoonRoleList(id);
        return result;
    }

    @GetMapping("/webtoonVote/{id}")
    @Operation(summary = "웹툰 캐릭터 상세 화면", description = "웹툰 캐릭터중 한명을 화면에 출력")
    @Parameters({
            @Parameter(name="id", description = "webtoonRoleId", required = true)
    })
    @ResponseBody
    public List<WebtoonRoleDto> openVoteWebtoonDetailPage(@PathVariable Long id){
        List<WebtoonRoleDto > result = webtoonService.findByWebtoonRoleId(id);
        return result;
    }

    @PostMapping("/webtoonVote")
    @Operation(summary = "웹툰 캐릭터 상세화면 투표하기", description = "웹툰 캐릭터중 한명과 연예인을 투표하기")
    @Parameters({
            @Parameter(name="webtoonId", description = "webtoonId", required = true),
            @Parameter(name="webtoonRoleId", description = "webtoonRoleId", required = true),
            @Parameter(name="person_name", description = "personName", required = true),
            @Parameter(name="person_url", description = "personUrl", required = true)
    })
    @ResponseBody
    public void postVoteWebtoonRole(@RequestBody VoteDto voteDto){
        webtoonService.saveVote(voteDto);
    }

    @GetMapping("/webtoonVote/result/{id}")
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
