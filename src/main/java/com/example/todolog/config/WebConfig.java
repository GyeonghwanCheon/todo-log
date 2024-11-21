package com.example.todolog.config;

import com.example.todolog.filter.LoginFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import at.favre.lib.crypto.bcrypt.BCrypt;


// 로그인 설정 클래스
@Configuration
public class WebConfig {

    // 필터를 등록할 Bean 메서드
    @Bean
    public FilterRegistrationBean<LoginFilter> loginFilter() {

        // 객체 생성
        FilterRegistrationBean<LoginFilter> filterRegistrationBean = new FilterRegistrationBean<>();

        // 필터 설정
        filterRegistrationBean.setFilter(new LoginFilter());

        // 필터를 모든 URL 패턴에 적용
        filterRegistrationBean.addUrlPatterns("/*");

        // 필터 반환
        return filterRegistrationBean;
    }

    @Component
    public class PasswordEncoder {

        public String encode(String rawPassword) {
            return BCrypt.withDefaults().hashToString(BCrypt.MIN_COST, rawPassword.toCharArray());
        }

        public boolean matches(String rawPassword, String encodedPassword) {
            BCrypt.Result result = BCrypt.verifyer().verify(rawPassword.toCharArray(), encodedPassword);
            return result.verified;
        }
    }

}
