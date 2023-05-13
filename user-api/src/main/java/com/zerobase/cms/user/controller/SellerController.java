package com.zerobase.cms.user.controller;

import com.zerobase.cms.domain.user.config.JwtAuthenticationProvider;
import com.zerobase.cms.domain.user.domain.common.UserVo;
import com.zerobase.cms.user.domain.customer.ChangeBalanceForm;
import com.zerobase.cms.user.domain.model.Seller;
import com.zerobase.cms.user.domain.seller.SellerDto;
import com.zerobase.cms.user.exception.CustomException;
import com.zerobase.cms.user.exception.ErrorCode;
import com.zerobase.cms.user.service.seller.SellerBalanceService;
import com.zerobase.cms.user.service.seller.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seller")
@RequiredArgsConstructor
public class SellerController {

    private final JwtAuthenticationProvider provider;
    private final SellerService sellerService;
    private final SellerBalanceService sellerBalanceService;

    @GetMapping("/getInfo")
    public ResponseEntity<SellerDto> getInfo(@RequestHeader(name = "X-AUTH-TOKEN") String token){
        UserVo vo = provider.getUserVo(token);
        Seller s = sellerService.findByIdAndEmail(vo.getId(), vo.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        return ResponseEntity.ok(SellerDto.from(s));
    }

    @PostMapping("/balance")
    public ResponseEntity<Integer> changeBalance(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                                 @RequestBody ChangeBalanceForm form) {

        UserVo vo = provider.getUserVo(token);

        return ResponseEntity.ok(sellerBalanceService.changeBalance(vo.getId(), form).getCurrentMoney());
    }

}
