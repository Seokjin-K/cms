package com.zerobase.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    ALREADY_REGISTER_USER(
            HttpStatus.BAD_REQUEST, "이미 가입된 회원입니다."),

    NOT_FOUND_USER(
            HttpStatus.BAD_REQUEST, "일차히는 회원이 없습니다."),

    ALREADY_VERIFY(
            HttpStatus.BAD_REQUEST, "이미 인증이 완료되었습니다."),

    WRONG_VERIFICATION(
            HttpStatus.BAD_REQUEST, "잘못된 인증 시도입니다."),

    EXPIRE_CODE(
            HttpStatus.BAD_REQUEST, "인증 시간이 만료되었습니다."),

    LOGIN_CHECK_FAIL(
            HttpStatus.BAD_REQUEST, "로그인에 실패하였습니다."),

    NOT_ENOUGH_BALANCE(HttpStatus.BAD_REQUEST, "잔액이 부족합니다.");

    private final HttpStatus httpStatus;
    private final String detail;
}
