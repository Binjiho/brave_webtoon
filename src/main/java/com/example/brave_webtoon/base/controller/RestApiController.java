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
public class RestApiController {

    private final WebtoonService webtoonService;

    @GetMapping("/helloworld")
    public String helloWorld(){
        return "HelloWorld!";
    }

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
    public List<WebtoonRoleDto> getWebtoonList(@PathVariable Long id){
        List<WebtoonRoleEntity> list = webtoonService.findAllWebtoonRoleList(id);
        List<WebtoonRoleDto> result = list.stream().map(WebtoonRoleDto::toDto).collect(Collectors.toList());
        return result;
    }
}
