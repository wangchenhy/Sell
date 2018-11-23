package com.xinyan.sell.repository;

import com.xinyan.sell.po.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 用户接口
 */
public interface SellerInfoRepository extends JpaRepository<SellerInfo, String> {

    /**通过买家微信号查询
     * @param openid
     * @return
     */
    SellerInfo findByOpenid(String openid);
}
