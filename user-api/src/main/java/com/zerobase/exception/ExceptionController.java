package com.zerobase.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.ServletException;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionResponse> customRequestException(
            final CustomException e) {

        showErrorCode(e.getMessage());
        return ResponseEntity.badRequest().body(
                new ExceptionResponse(
                        e.getMessage(),
                        e.getErrorCode()
                )
        );
    }

    /*@ExceptionHandler(ServletException.class)
    public ResponseEntity<String> servletException(
            final CustomException e) {

        showErrorCode(e.getMessage());
        return ResponseEntity.badRequest().body("잘못된 인증 시도");
    }*/

    public static void showErrorCode(String errorCode) {
        log.warn("api Exception : {}", errorCode);
    }

    @Getter
    @ToString
    @AllArgsConstructor
    public static class ExceptionResponse {
        private String message;
        private ErrorCode errorCode;
    }
}
