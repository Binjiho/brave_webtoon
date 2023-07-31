package com.example.brave_webtoon.base.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RestApiController {
    /**
     * json data
     * reservation
     * @return
     */
    @GetMapping("/helloworld")
//    @ResponseBody
    public String helloWorld(){
        return "HelloWorld!";
    }

}
