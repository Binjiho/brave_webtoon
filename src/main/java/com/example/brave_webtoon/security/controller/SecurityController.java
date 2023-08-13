package com.example.brave_webtoon.security.controller;

import com.example.brave_webtoon.security.constant.AuthConstants;
import com.example.brave_webtoon.security.dto.MemberDto;
import com.example.brave_webtoon.security.util.TokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class SecurityController {

    /**
     * [API] 사용자 정보를 기반으로 JWT를 발급하는 API
     *
     * @param memberDto
     * @return ApiResponseWrapper<ApiResponse> : 응답 결과 및 응답 코드 반환
     */
    @PostMapping("/generateToken")
    @ResponseBody
    @Operation(summary = "토큰 발급", description = "사용자 정보를 기반으로 JWT를 발급하는 API")
    public String generateJWT(@RequestBody MemberDto memberDto) {

        String resultToken = TokenUtil.generateJwtToken(memberDto);

        return resultToken;
    }

}
