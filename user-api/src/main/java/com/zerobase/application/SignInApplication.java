package com.zerobase.application;

import com.zerobase.domain.SignInForm;
import com.zerobase.domain.config.JwtAuthenticationProvider;
import com.zerobase.domain.model.Customer;
import com.zerobase.domain.model.Seller;
import com.zerobase.exception.CustomException;
import com.zerobase.service.customer.CustomerService;
import com.zerobase.service.seller.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.zerobase.domain.common.UserType.CUSTOMER;
import static com.zerobase.domain.common.UserType.SELLER;
import static com.zerobase.exception.ErrorCode.LOGIN_CHECK_FAIL;

@Service
@RequiredArgsConstructor
public class SignInApplication {

    private final CustomerService customerService;
    private final SellerService sellerService;
    private final JwtAuthenticationProvider provider;

    public String customerLoginToken(SignInForm form) {
        // 1. 로그인 가능 여부
        Customer customer = customerService.findValidCustom(
                        form.getEmail(), form.getPassword())
                .orElseThrow(() -> new CustomException(LOGIN_CHECK_FAIL));
        // 2. 토큰 발행

        // 3. 토큰 response
        return provider.createToken(
                customer.getEmail(),
                customer.getId(),
                CUSTOMER
        );
    }

    public String sellerLoginToken(SignInForm form) {
        Seller seller = sellerService.findValidSeller(
                form.getEmail(), form.getPassword())
                .orElseThrow(() -> new CustomException(LOGIN_CHECK_FAIL));

        return provider.createToken(
                seller.getEmail(),
                seller.getId(),
                SELLER
        );
    }
}
