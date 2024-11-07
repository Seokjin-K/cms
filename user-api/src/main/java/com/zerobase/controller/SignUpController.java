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

    @PostMapping("customer")
    public ResponseEntity<String> customerSignUp(
            @RequestBody SignUpForm form) {
        return ResponseEntity.ok(signUpApplication.customerSignup(form));
    }

    @GetMapping("/customer/verify")
    public ResponseEntity<String> verifyCustomer(
            @RequestParam("email") String email,
            @RequestParam("code") String code) {
        signUpApplication.customerVerify(email, code);
        return ResponseEntity.ok("인증이 완료되었습니다.");
    }

    @PostMapping("seller")
    public ResponseEntity<String> sellerSignUp(
            @RequestBody SignUpForm form) {
        return ResponseEntity.ok(signUpApplication.sellerSignup(form));
    }

    @GetMapping("/seller/verify")
    public ResponseEntity<String> verifySeller(
            @RequestParam("email") String email,
            @RequestParam("code") String code) {
        this.signUpApplication.sellerVerify(email, code);
        return ResponseEntity.ok("인증이 완료되었습니다.");
    }
}
