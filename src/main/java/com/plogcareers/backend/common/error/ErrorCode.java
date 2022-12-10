package com.plogcareers.backend.common.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public enum ErrorCode {
    // Blog Domain
    ERR_POST_NOT_FOUND(HttpStatus.NOT_FOUND, "포스트를 찾을 수 없습니다."),
    ERR_CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "카테고리를 찾을 수 없습니다."),
    ERR_CATEGORY_DUPLICATED(HttpStatus.BAD_REQUEST, "중복되는 카테고리가 이미 존재합니다."),
    ERR_CATEGORY_BLOG_MISMATCHED(HttpStatus.BAD_REQUEST, "카테고리와 블로그가 일치하지 않습니다."),
    ERR_BLOG_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 블로그입니다."),
    ERR_TAG_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 태그입니다"),
    ERR_COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 덧글입니다."),
    ERR_PARENT_COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "답글을 남길 덧글이 존재하지 않습니다."),
    ERR_POSTING_COMMENT_UNMATCHED(HttpStatus.BAD_REQUEST, "덧글과 포스팅이 일치하지 않습니다."),
    ERR_INVALID_PARENT_EXIST(HttpStatus.BAD_REQUEST, "답글을 남기지 못하는 덧글입니다."),
    ERR_NO_PROPER_AUTHORITY(HttpStatus.UNAUTHORIZED, "적절한 권한이 없습니다."),

    ERR_BLOG_POSTING_UNMATCHED(HttpStatus.BAD_REQUEST, "블로그에 해당 포스팅이 속하지 않습니다."),
    // Parameter Validation
    ERR_INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "파라미터가 유효하지 않습니다."),
    // UMS Domain
    ERR_LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "이메일이나, 비밀번호가 일치하지 않습니다."),
    ERR_USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저가 존재하지 않습니다."),

    // COMMON
    ERR_INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 오류입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.code = this.name();
        this.message = message;
    }
}
