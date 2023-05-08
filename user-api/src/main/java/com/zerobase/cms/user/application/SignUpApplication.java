package com.zerobase.cms.user.application;

import com.zerobase.cms.user.client.MailgunClient;
import com.zerobase.cms.user.client.mailgun.SendMailForm;
import com.zerobase.cms.user.domain.SignUpForm;
import com.zerobase.cms.user.domain.model.Customer;
import com.zerobase.cms.user.domain.model.Seller;
import com.zerobase.cms.user.exception.CustomException;
import com.zerobase.cms.user.exception.ErrorCode;
import com.zerobase.cms.user.service.customer.SignUpCustomerService;
import com.zerobase.cms.user.service.seller.SignUpSellerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class SignUpApplication {
    private final MailgunClient mailgunClient;
    private final SignUpCustomerService signUpCustomerService;
    private final SignUpSellerService signUpSellerService;

    public void cusomerVerify(String email, String code){
        signUpCustomerService.verifyEmail(email, code);
    }

    //회원 가입
    public String customerSighUp(SignUpForm form) {
        if (signUpCustomerService.isEmailExist(form.getEmail())) {
            throw new CustomException(ErrorCode.ALREADY_REGISTERED_ACCOUNT);
        } else {
            Customer c = signUpCustomerService.signUp(form);

            String code = getRandomCode();
            SendMailForm sendMailForm = SendMailForm.builder()
                    .from("tester@kshtester.com")
                    .to(form.getEmail())
                    .subject("Verification Email!")
                    .text(getVerificationEmailBody(c.getEmail(), c.getName(), "customer", code))
                    .build();
            log.info("Send email result : "+ mailgunClient.sendEmail(sendMailForm).getBody());

            signUpCustomerService.changeCustomerValidateEmail(c.getId(), code);

            return "회원 가입에 성공했습니다.";
        }

    }

    public void sellerVerify(String email, String code){
        signUpSellerService.verifyEmail(email, code);
    }

    public String sellerSignUp(SignUpForm form) {
        if (signUpSellerService.isEmailExist(form.getEmail())) {
            throw new CustomException(ErrorCode.ALREADY_REGISTERED_ACCOUNT);
        } else {
            Seller s = signUpSellerService.signUp(form);

            String code = getRandomCode();
            SendMailForm sendMailForm = SendMailForm.builder()
                    .from("tester@kshtester.com")
                    .to(form.getEmail())
                    .subject("Verification Email!")
                    .text(getVerificationEmailBody(s.getEmail(), s.getName(), "seller", code))
                    .build();

            log.info("Send email result : "+ mailgunClient.sendEmail(sendMailForm).getBody());

            signUpSellerService.changeSellerValidateEmail(s.getId(), code);

            return "회원 가입에 성공했습니다.";
        }
    }

    private String getRandomCode(){
        return RandomStringUtils.random(10, true, true);
    }

    private String getVerificationEmailBody(String email, String name, String type, String code) {
        StringBuilder builder = new StringBuilder();
        return builder.append("Hello ").append(name).append("! Please Click Link for verification\n\n")
                .append("http://localhost:8081/signup/"+type+"/verify/?email=")
                .append(email)
                .append("&code=")
                .append(code)
                .toString();
    }

}
