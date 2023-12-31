package com.example.brave_webtoon.base.controller;

import com.example.brave_webtoon.webtoon.service.WebCrawlService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final WebCrawlService webCrawlService;

    @GetMapping("/helloworld")
    public String helloWorld(){
        return "HelloWorld!";
    }

//    @GetMapping("/api/login")
//    public String openLoginPage() {
//        return "admin/login";
//    }

//    @GetMapping({"/","main"})
//    public String openMainPage(){
//        return "main";
//    }
//    @PostMapping("/crawl/save")
//    public String saveCrawl(@RequestParam String url) throws IOException {
//        webCrawlService.crawl(url);
//        return "redirect:/";
//    }
}
