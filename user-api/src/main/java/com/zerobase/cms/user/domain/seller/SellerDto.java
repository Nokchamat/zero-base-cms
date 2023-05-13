package com.zerobase.cms.user.domain.seller;

import com.zerobase.cms.user.domain.model.Seller;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SellerDto {

    Long id;
    String email;
    Integer balance;

    public static SellerDto from(Seller seller) {
        return new SellerDto(seller.getId(), seller.getEmail(), seller.getBalance());
    }

}
