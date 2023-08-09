package com.example.brave_webtoon.base.controller;

import com.example.brave_webtoon.base.dto.WebtoonDto;
import com.example.brave_webtoon.base.dto.WebtoonRoleDto;
import com.example.brave_webtoon.base.entity.WebtoonEntity;
import com.example.brave_webtoon.base.entity.WebtoonRoleEntity;
import com.example.brave_webtoon.base.service.WebtoonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class RestApiController {

    private final WebtoonService webtoonService;

    @GetMapping("/helloworld")
    public String helloWorld(){
        return "HelloWorld!";
    }

    @GetMapping("/webtoonList")
    @ResponseBody
    public List<WebtoonDto> getWebtoonList(){
        List<WebtoonEntity> list = webtoonService.findAllWebtoonList();
        List<WebtoonDto> result = list.stream().map(WebtoonDto::toDto).collect(Collectors.toList());
        return result;
    }

    @GetMapping("/webtoonList/{id}")
    @ResponseBody
    public List<WebtoonRoleDto> getWebtoonList(@PathVariable Long id){
        List<WebtoonRoleEntity> list = webtoonService.findAllWebtoonRoleList(id);
        List<WebtoonRoleDto> result = list.stream().map(WebtoonRoleDto::toDto).collect(Collectors.toList());
        return result;
    }
}
