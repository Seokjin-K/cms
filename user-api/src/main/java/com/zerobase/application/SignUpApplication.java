package com.zerobase.application;

import com.zerobase.client.MailgunClient;
import com.zerobase.client.mailgun.SendMailForm;
import com.zerobase.domain.SignUpForm;
import com.zerobase.domain.model.Customer;
import com.zerobase.exception.CustomException;
import com.zerobase.exception.ErrorCode;
import com.zerobase.service.SignUpCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.RandomStringUtils;

@Service
@RequiredArgsConstructor
public class SignUpApplication {

    private final MailgunClient mailgunClient;
    private final SignUpCustomerService signUpCustomerService;

    public void customerVerify(String email, String code){
        this.signUpCustomerService.verifyEmail(email, code);
    }

    public String customerSignup(SignUpForm form) {
        if (this.signUpCustomerService.isEmailExist(form.getEmail())) {
            throw new CustomException(ErrorCode.ALREADY_REGISTER_USER);
        }
        Customer customer = this.signUpCustomerService.signUp(form);

        String code = getRandomCode();

        SendMailForm sendMailForm = SendMailForm.builder()
                .from("tester@tester.com")
                .to(form.getEmail())
                .subject("Verification Email!")
                .text(
                        getVerificationEmailBody(
                                customer.getEmail(),
                                customer.getName(),
                                code
                        )
                )
                .build();

        this.mailgunClient.sendEmail(sendMailForm);
        this.signUpCustomerService.changeCustomerValidateEmail(
                customer.getId(), code);

        return "회원 가입에 성공하였습니다.";
    }

    private String getRandomCode() {
        // 10자리, 글자와 숫자 모두 사용
        return RandomStringUtils.random(10, true, true);
    }

    private String getVerificationEmailBody(
            String email, String name, String code) {

        StringBuilder sb = new StringBuilder();
        return sb.append("Hello ")
                .append(name)
                .append("! Please Click Link for verification.\n\n")
                .append("http://localhost:8080/signup/verify/customer?email=")
                .append(email)
                .append("&code=")
                .append(code)
                .toString();
    }
}
