package com.example.brave_webtoon.base.controller;

import com.example.brave_webtoon.base.dto.WebtoonDto;
import com.example.brave_webtoon.base.dto.WebtoonRoleDto;
import com.example.brave_webtoon.base.entity.WebtoonEntity;
import com.example.brave_webtoon.base.entity.WebtoonRoleEntity;
import com.example.brave_webtoon.base.service.WebtoonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;
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
    public List<WebtoonRoleDto> getWebtoonRoleList(@PathVariable Long id){
        List<WebtoonRoleDto> result = webtoonService.findAllWebtoonRoleList(id);
        return result;
    }

    @GetMapping("/webtoonList/detail/{id}")
    @Operation(summary = "웹툰 캐릭터 상세 화면", description = "웹툰 캐릭터중 한명을 화면에 출력")
    @Parameters({
            @Parameter(name="id", description = "webtoonRoleId", required = true)
    })
    @ResponseBody
    public List<WebtoonRoleDto> findByWebtoonRoleId(@PathVariable Long id){
        List<WebtoonRoleDto> result = webtoonService.findByWebtoonRoleId(id);
        return result;
    }

//    @PostMapping("/webtoonList/detail")
//    @Operation(summary = "웹툰 캐릭터 상세화면 투표하기", description = "웹툰 캐릭터중 한명과 연예인을 투표하기")
//    @Parameters({
//            @Parameter(name="webtoon_id", description = "webtoonId", required = true),
//            @Parameter(name="webtoon_role_id", description = "webtoonRoleId", required = true),
//            @Parameter(name="person_name", description = "personName", required = true),
//            @Parameter(name="person_url", description = "personUrl", required = true)
//    })
//    @ResponseBody
//    public List<WebtoonRoleDto> postWebtoonRoleDetail(@PathVariable Long id){
//        List<WebtoonRoleEntity> list = webtoonService.findAllWebtoonRoleList(id);
//        List<WebtoonRoleDto> result = list.stream().map(WebtoonRoleDto::toDto).collect(Collectors.toList());
//        return result;
//    }
}
