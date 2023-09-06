package com.example.brave_webtoon.security.filter;

import com.example.brave_webtoon.security.constant.AuthConstants;
import com.example.brave_webtoon.security.util.TokenUtil;
import io.jsonwebtoken.*;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 지정한 URL 별 JWT 유효성 검증을 수행하며 직접적인 사용자 '인증'을 확인합니다.
 *
 * @fileName JwtAuthorizationFilter
 */
@Log4j2
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain chain)
            throws IOException, ServletException {

        // 1. 토큰이 필요하지 않은 API URL에 대해서 배열로 구성합니다.
        List<String> list = Arrays.asList(
                "/swagger-ui",
                "/api-docs",
                "/api/person/",
                "/api/account/",
                "/api/signIn",
                "/crawl",
                "m.chuing.net/db/webtoon/view/people.php" //crawllingSiteUrl
        );

        // 2. 토큰이 필요하지 않은 API URL의 경우 => 로직 처리 없이 다음 필터로 이동
        if (list.stream().anyMatch(obj -> request.getRequestURI().contains(obj)) ) {
//            log.info("=========JwtAuthorizationFilter Not Need Token=========");
            chain.doFilter(request, response);
            return;
        }

//        if (list.contains(request.getRequestURI())) {
//            chain.doFilter(request, response);
//            return;
//        }

        // 3. OPTIONS 요청일 경우 => 로직 처리 없이 다음 필터로 이동
        if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
            chain.doFilter(request, response);
            return;
        }

        // [STEP1] Client에서 API를 요청할때 Header를 확인합니다.
        String header = request.getHeader(AuthConstants.AUTH_HEADER);
        logger.debug("[+] header Check: " + header);

        try {
            // [STEP2-1] Header 내에 토큰이 존재하는 경우
            if (header != null && !header.equalsIgnoreCase("")) {

                // [STEP2] Header 내에 토큰을 추출합니다.
                String token = TokenUtil.getTokenFromHeader(header);

                // [STEP3] 추출한 토큰이 유효한지 여부를 체크합니다.
                if (TokenUtil.isValidToken(token)) {

                    // [STEP4] 토큰을 기반으로 사용자 아이디를 반환 받는 메서드
                    String userId = TokenUtil.getUserIdFromToken(token);
                    logger.debug("[+] userId Check: " + userId);

                    // [STEP5] 사용자 아이디가 존재하는지 여부 체크
                    if (userId != null && !userId.equalsIgnoreCase("")) {
                        chain.doFilter(request, response);
                    } else {
                        throw new Exception("TOKEN isn't userId");
                    }
                    // 토큰이 유효하지 않은 경우
                } else {
                    throw new Exception("TOKEN is invalid");
                }
            }
            // [STEP2-1] 토큰이 존재하지 않는 경우
            else {
                throw new Exception("Token is null");
            }
        } catch (Exception e) {
            // Token 내에 Exception이 발생 하였을 경우 => 클라이언트에 응답값을 반환하고 종료합니다.
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            PrintWriter printWriter = response.getWriter();
            JSONObject jsonObject = jsonResponseWrapper(e);
            printWriter.print(jsonObject);
            printWriter.flush();
            printWriter.close();
        }
    }

    /**
     * 토큰 관련 Exception 발생 시 예외 응답값 구성
     *
     * @param e Exception
     * @return JSONObject
     */
    private JSONObject jsonResponseWrapper(Exception e) {

        String resultMsg = "";
        // JWT 토큰 만료
        if (e instanceof ExpiredJwtException) {
            resultMsg = "TOKEN Expired";
        }
        // JWT 허용된 토큰이 아님
        else if (e instanceof SignatureException) {
            resultMsg = "TOKEN SignatureException Login";
        }
        // JWT 토큰내에서 오류 발생 시
        else if (e instanceof JwtException) {
            resultMsg = "TOKEN Parsing JwtException";
        }
        // 이외 JTW 토큰내에서 오류 발생
        else {
            resultMsg = "OTHER TOKEN ERROR";
        }

        HashMap<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("status", 401);
        jsonMap.put("code", "9999");
        jsonMap.put("message", resultMsg);
        jsonMap.put("reason", e.getMessage());
        JSONObject jsonObject = new JSONObject(jsonMap);
        logger.error(resultMsg, e);
        return jsonObject;
    }

}
