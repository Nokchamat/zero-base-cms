package com.zerobase.cms.user.application;


import com.zerobase.cms.domain.user.config.JwtAuthenticationProvider;
import com.zerobase.cms.user.domain.SignInForm;
import com.zerobase.cms.domain.user.domain.common.UserType;
import com.zerobase.cms.user.domain.model.Customer;
import com.zerobase.cms.user.domain.model.Seller;
import com.zerobase.cms.user.exception.CustomException;
import com.zerobase.cms.user.exception.ErrorCode;
import com.zerobase.cms.user.service.customer.CustomerService;
import com.zerobase.cms.user.service.seller.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignInApplication {

    private final CustomerService customerService;
    private final SellerService sellerService;

    private final JwtAuthenticationProvider provider;

    public String customerLoginToken(SignInForm form) {
        //1. 로그인 가능 여부 체크
        Customer c = customerService.findValidCustomer(form.getEmail(), form.getPassword())
                .orElseThrow(() -> new CustomException(ErrorCode.LOGIN_CHECK_FAIL));

        //2. 토큰 발행
        //3. 토큰을 response
        return provider.createToken(c.getEmail(), c.getId(), UserType.CUSTOMER);
    }

    public String sellerLoginToken(SignInForm form) {
        //1. 로그인 가능 여부 체크
        Seller s = sellerService.findValidSeller(form.getEmail(), form.getPassword())
                .orElseThrow(() -> new CustomException(ErrorCode.LOGIN_CHECK_FAIL));

        //2. 토큰 발행
        //3. 토큰을 response
        return provider.createToken(s.getEmail(), s.getId(), UserType.SELLER);
    }

}
