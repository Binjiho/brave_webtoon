package com.example.brave_webtoon.security.handler;

import com.example.brave_webtoon.base.util.ConvertUtil;
import com.example.brave_webtoon.security.constant.AuthConstants;
import com.example.brave_webtoon.security.dto.MemberDto;
import com.example.brave_webtoon.security.dto.UserDetailsDto;
import com.example.brave_webtoon.security.entity.MemberEntity;
import com.example.brave_webtoon.security.util.TokenUtil;
import lombok.extern.log4j.Log4j2;
import org.json.simple.JSONObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * 사용자의 ‘인증’에 대해 성공하였을 경우 수행되는 Handler로 성공에 대한 사용자에게 반환값을 구성하여 전달합니다
 */
@Log4j2
@Configuration
public class CustomAuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        log.debug("3.CustomLoginSuccessHandler");

        // [STEP1] 사용자와 관련된 정보를 모두 조회합니다.
        MemberEntity memberEntity = ((UserDetailsDto) authentication.getPrincipal()).getMemberEntity();
        MemberDto memberDto = MemberDto
                .builder()
                .id(memberEntity.getId())
                .userId(memberEntity.getUserId())
                .userPw(memberEntity.getUserPw())
                .name(memberEntity.getName())
                .htel(memberEntity.getHtel())
                .role(memberEntity.getRole())
                .fromSocial(memberEntity.getFromSocial())
                .deleteYn(memberEntity.getDeleteYn())
                .createdDate(memberEntity.getCreatedDate())
                .modifiedDate(memberEntity.getModifiedDate())
                .build();

        // [STEP2] 조회한 데이터를 JSONObject 형태로 파싱을 수행합니다.
        JSONObject userVoObj = (JSONObject) ConvertUtil.convertObjectToJsonObject(memberDto);

        HashMap<String, Object> responseMap = new HashMap<>();

        JSONObject jsonObject;

        // [STEP3-1] 사용자의 상태가 '휴면 상태' 인 경우 응답 값으로 전달 할 데이터
        if (memberDto.getDeleteYn() > 0) {
            responseMap.put("userInfo", userVoObj);
            responseMap.put("resultCode", 9001);
            responseMap.put("token", null);
            responseMap.put("failMsg", "휴면(삭제)계정입니다.");
            jsonObject = new JSONObject(responseMap);
        }

        // [STEP3-2] 사용자의 상태가 '휴면 상태'가 아닌 경우 응답 값으로 전달 할 데이터
        else {
            // 1. 일반 계정일 경우 데이터 세팅
            responseMap.put("userInfo", userVoObj);
            responseMap.put("resultCode", 200);
            responseMap.put("failMsg", null);
            jsonObject = new JSONObject(responseMap);

            // JWT 발급
             String token = TokenUtil.generateJwtToken(memberDto);
             jsonObject.put("token", token);
             response.addHeader(AuthConstants.AUTH_HEADER, AuthConstants.TOKEN_TYPE + " " + token);
        }

        // [STEP4] 구성한 응답 값을 전달합니다.
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter printWriter = response.getWriter();
        printWriter.print(jsonObject);  // 최종 저장된 '사용자 정보', '사이트 정보' Front 전달
        printWriter.flush();
        printWriter.close();
    }

}
