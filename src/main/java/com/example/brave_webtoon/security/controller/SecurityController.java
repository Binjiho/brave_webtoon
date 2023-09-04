package com.example.brave_webtoon.security.controller;

import com.example.brave_webtoon.security.constant.AuthConstants;
import com.example.brave_webtoon.security.dto.MemberDto;
import com.example.brave_webtoon.security.service.MemberJoinService;
import com.example.brave_webtoon.security.util.TokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SecurityController {

    private final MemberJoinService memberJoinService;

    /**
     * 회원가입
     * @param memberDto
     * @return
     */
    @PostMapping("/account/signUp")
    @Operation(summary = "회원가입", description = "사용자 입력 정보로 회원가입")
    @Parameters({
            @Parameter(name="userId", description = "유저아이디", required = true),
            @Parameter(name="userPw", description = "유저비밀번호", required = true),
            @Parameter(name="name", description = "유저이름", required = true),
            @Parameter(name="htel", description = "유저휴대폰번호", required = true)
    })
    @ResponseBody
    public Long saveMember(@RequestBody final MemberDto memberDto) {
        return memberJoinService.memberSignUp(memberDto);
    }

    /**
     * 아이디 중복체크
     * @param memberDto
     * @return
     */
    @PostMapping("/account/checkId")
    @Operation(summary = "아이디 중복체크", description = "사용자 입력 아이디 중복체크")
    @Parameters({
            @Parameter(name="userId", description = "유저아이디", required = true)
    })
    @ResponseBody
    public int countMemberByLoginId(@RequestBody final MemberDto memberDto) {
        return memberJoinService.memberIdCheck(memberDto);
    }

    /**
     * 로그인 /api/account/signIn
     * [API] 사용자 정보를 기반으로 JWT를 발급하는 API
     * @return ApiResponseWrapper<ApiResponse> : 응답 결과 및 응답 코드 반환
     */
    @PostMapping("/account/singIn")
    @Operation(summary = "로그인 후 토큰 발급", description = "처음 /api/account/signIn으로 form 정보 넘기면 customAuthenticationFilter필터체크 후, 사용자 정보를 기반으로 JWT를 발급")
    @Parameters({
            @Parameter(name="userId", description = "유저아이디", required = true),
            @Parameter(name="userPw", description = "유저비밀번호", required = true)
    })
    public void generateJWT() {
    }

    @PostMapping("/generateToken")
    @Operation(summary = "토큰 발급", description = "사용자 정보를 기반으로 JWT를 발급")
    @Parameters({
            @Parameter(name="userId", description = "유저아이디", required = true),
            @Parameter(name="userPw", description = "유저비밀번호", required = true)
    })
    public String generateJWT(@RequestBody MemberDto memberDto) {

        String resultToken = TokenUtil.generateJwtToken(memberDto);

        return resultToken;
    }

}
