package com.zerobase.cms.user.domain.repository;


import com.zerobase.cms.user.domain.model.SellerBalanceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

public interface SellerBalanceHistoryRepository extends JpaRepository<SellerBalanceHistory, Long> {
    Optional<SellerBalanceHistory> findFirstBySeller_IdOrderByIdDesc(@RequestParam("seller_id")Long  id);
}
