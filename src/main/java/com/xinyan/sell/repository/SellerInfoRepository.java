package com.xinyan.sell.repository;

import com.xinyan.sell.po.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 */
public interface SellerInfoRepository extends JpaRepository<SellerInfo, String> {

    SellerInfo findByOpenid(String openid);
}
