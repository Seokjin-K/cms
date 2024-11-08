package com.zerobase.cms.order.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    NOT_FOUND_PRODUCT(
            HttpStatus.BAD_REQUEST, "상품을 찾을 수 없습니다."),

    SAME_ITEM_NAME(
            HttpStatus.BAD_REQUEST, "아이템 이름이 중복입니다.")
    ;

    private final HttpStatus httpStatus;
    private final String detail;
}
