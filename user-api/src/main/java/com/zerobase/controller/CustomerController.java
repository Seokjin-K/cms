package com.zerobase.controller;

import com.zerobase.domain.common.UserVo;
import com.zerobase.domain.config.JwtAuthenticationProvider;
import com.zerobase.domain.customer.ChangeBalanceForm;
import com.zerobase.domain.customer.CustomerDto;
import com.zerobase.domain.model.Customer;
import com.zerobase.exception.CustomException;
import com.zerobase.service.customer.CustomerBalanceService;
import com.zerobase.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.zerobase.exception.ErrorCode.NOT_FOUND_USER;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {

    private final JwtAuthenticationProvider provider;
    private final CustomerService customerService;
    private final CustomerBalanceService customerBalanceService;

    @GetMapping("/getInfo")
    public ResponseEntity<CustomerDto> getInfo(
            @RequestHeader(name = "X-AUTH-TOKEN") String token
    ) {
        UserVo userVo = provider.getUserVo(token);
        Customer customer = customerService.findByIdAndEmail(
                        userVo.getId(),
                        userVo.getEmail())
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER));
        return ResponseEntity.ok(CustomerDto.from(customer));
    }

    @PostMapping("/balance")
    public ResponseEntity<Integer> changeBalance(
            @RequestHeader(name = "X-AUTH-TOKEN") String token,
            @RequestBody ChangeBalanceForm form) {

        UserVo vo = provider.getUserVo(token);

        return ResponseEntity.ok(
                customerBalanceService.changeBalance(
                        vo.getId(), form).getCurrentMoney()
        );
    }
}
