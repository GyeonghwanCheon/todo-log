package com.example.todolog.error.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    /* 400 BAD_REQUEST : 잘못된 요청 */
    CATEGORY_STEP_OVER(BAD_REQUEST , "카테고리는 3단계 까지 가능합니다."),
    INVALID_PASSWORD_FORMAT(BAD_REQUEST,"비밀번호는 최소 8글자, 영문 + 숫자 + 특수문자로 입력해주십시오."),
    TITLE_DETAIL_NULL(BAD_REQUEST, "title 또는 detail이 비어있습니다."),


    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    UNAUTHORIZED_PASSWORD(UNAUTHORIZED, "password 가 일치하지 않습니다."),
    UNAUTHORIZED_EMAIL(UNAUTHORIZED, "해당 Email을 사용중인 사용자가 존재하지 않습니다."),
    UNAUTHORIZED_USER(UNAUTHORIZED, "권한이 없습니다. 해당유저만 가능합니다."),

    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    SESSION_NOT_FOUND(NOT_FOUND, "로그인이 필요합니다."),
    ID_NOT_FOUND(NOT_FOUND, "id를 찾을 수 없습니다."),
    USER_NOT_FOUND(NOT_FOUND, "해당 id 로 인한 유저 정보를 찾을 수 없습니다"),
    USERNAME_NOT_FOUND(NOT_FOUND , "해당 username 으로 인한 유저 정보를 찾을 수 없습니다"),
    SCHEDULE_NOT_FOUND(NOT_FOUND , "해당 id 로 인한 일정 정보를 찾을 수 없습니다"),
    COMMENT_NOT_FOUND(NOT_FOUND , "해당 id 로 인한 댓글 정보를 찾을 수 없습니다"),
    LIKE_NOT_FOUND(NOT_FOUND , "해당 id 로 인한 좋아요 정보를 찾을 수 없습니다"),
    FEED_NOT_FOUND(NOT_FOUND , "해당 id 로 인한 피드 정보를 찾을 수 없습니다"),
    CATEGORY_NOT_FOUND(NOT_FOUND , "해당 id 로 인한 카테고리 정보를 찾을 수 없습니다"),
    FOLLOWER_NOT_FOUND(NOT_FOUND, "해당 id로 인한 팔로워 정보를 찾을 수 없습니다."),
    FOLLOWING_NOT_FOUND(NOT_FOUND, "해당 id로 인한 팔로잉 정보를 찾을 수 없습니다."),





    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    DUPLICATE_RESOURCE(CONFLICT, "데이터가 이미 존재합니다"),
    BANED_EMAIL(CONFLICT,"이미 탈퇴한 유저입니다")

    ;

    private final HttpStatus httpStatus;
    private final String detail;
}
