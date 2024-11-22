package com.example.todolog.filter;


import com.example.todolog.error.errorcode.ErrorCode;
import com.example.todolog.error.exception.CustomException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

// 로그인 필터
public class LoginFilter implements Filter {

    // 화이트 리스트 선언
    private static final String[] WHITELIST = {"/", "/users/signup", "/login", "/logout"};

    // 필터 작동 메서드
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain Chain) throws IOException, ServletException {

        // HTTP 요청 객체 변환
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        // URI 객체 가져오기
        String requestURI = httpRequest.getRequestURI();

        // HTTP 응답 객체 변환
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // 화이트리스트 검증
        if(!isWhitelist(requestURI)) {

            // 세션을 가져옴, 없으면 null
            HttpSession session = httpRequest.getSession(false);

            // 세션이 존재하지 않거나 sessionKey 속성이 없으면 401 코드 반환
            if(session == null || session.getAttribute("sessionKey") == null) {
                throw new CustomException(ErrorCode.SESSION_NOT_FOUND);
            }
        }

        // 인증이 통과된 경우 다음 필터로 요청 전달
        Chain.doFilter(request, response);
    }

    // 화이트리스트 확인 메서드
    private boolean isWhitelist(String requestURI) {

    // 화이트리스트 검증
    return PatternMatchUtils.simpleMatch(WHITELIST, requestURI);
    }
}
