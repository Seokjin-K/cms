package com.zerobase.application;

//import com.zerobase.client.MailgunClient;
//import com.zerobase.client.mailgun.SendMailForm;

import com.zerobase.domain.SignUpForm;
import com.zerobase.domain.model.Customer;
import com.zerobase.domain.model.Seller;
import com.zerobase.exception.CustomException;
import com.zerobase.exception.ErrorCode;
import com.zerobase.service.customer.SignUpCustomerService;
import com.zerobase.service.seller.SignUpSellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.RandomStringUtils;

@Service
@RequiredArgsConstructor
public class SignUpApplication {

    //private final MailgunClient mailgunClient;
    private final SignUpCustomerService signUpCustomerService;
    private final SignUpSellerService signUpSellerService;

    public void customerVerify(String email, String code) {
        signUpCustomerService.verifyEmail(email, code);
    }

    public String customerSignup(SignUpForm form) {
        if (signUpCustomerService.isEmailExist(form.getEmail())) {
            throw new CustomException(ErrorCode.ALREADY_REGISTER_USER);
        }
        Customer customer = signUpCustomerService.signUp(form);

        String code = getRandomCode();

        /*SendMailForm sendMailForm = SendMailForm.builder()
                .from("tester@tester.com")
                .to(form.getEmail())
                .subject("Verification Email!")
                .text(
                        getVerificationEmailBody(
                                customer.getEmail(),
                                "customer",
                                customer.getName(),
                                code
                        )
                )
                .build();

        mailgunClient.sendEmail(sendMailForm);*/
        signUpCustomerService.changeCustomerValidateEmail(
                customer.getId(), code);

        return "회원 가입에 성공하였습니다.";
    }

    public void sellerVerify(String email, String code) {
        signUpSellerService.verifyEmail(email, code);
    }

    public String sellerSignup(SignUpForm form) {
        if (signUpSellerService.isEmailExist(form.getEmail())) {
            throw new CustomException(ErrorCode.ALREADY_REGISTER_USER);
        }
        Seller seller = signUpSellerService.signUp(form);

        String code = getRandomCode();

        /*SendMailForm sendMailForm = SendMailForm.builder()
                .from("tester@tester.com")
                .to(form.getEmail())
                .subject("Verification Email!")
                .text(
                        getVerificationEmailBody(
                                seller.getEmail(),
                                "customer",
                                seller.getName(),
                                code
                        )
                )
                .build();

        mailgunClient.sendEmail(sendMailForm);*/
        signUpSellerService.changeSellerValidateEmail(seller.getId(), code);

        return "회원 가입에 성공하였습니다.";
    }

    private String getRandomCode() {
        // 10자리, 글자와 숫자 모두 사용
        return RandomStringUtils.random(
                10, true, true);
    }

    private String getVerificationEmailBody(
            String email, String name, String type, String code) {

        StringBuilder sb = new StringBuilder();
        return sb.append("Hello ")
                .append(name)
                .append("! Please Click Link for verification.\n\n")
                .append("http://localhost:8080/signup/")
                .append(type)
                .append("/verify?email=")
                .append(email)
                .append("&code=")
                .append(code)
                .toString();
    }
}
