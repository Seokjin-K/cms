package com.zerobase.controller;

import com.zerobase.application.SignUpApplication;
import com.zerobase.domain.SignUpForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/signup")
public class SignUpController {

    private final SignUpApplication signUpApplication;

    @PostMapping
    public ResponseEntity<String> customerSignUp(@RequestBody SignUpForm form) {
        return ResponseEntity.ok(this.signUpApplication.customerSignup(form));
    }

    @PutMapping("/verify/customer")
    public ResponseEntity<String> verifyCustomer(
            @RequestParam("email") String email,
            @RequestParam("code") String code) {
        this.signUpApplication.customerVerify(email, code);
        return ResponseEntity.ok("인증이 완료되었습니다.");
    }
}
