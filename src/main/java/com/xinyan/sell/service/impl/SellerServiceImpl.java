package com.xinyan.sell.service.impl;

import com.xinyan.sell.po.SellerInfo;
import com.xinyan.sell.repository.SellerInfoRepository;
import com.xinyan.sell.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerInfoRepository repository;

    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) {
        return repository.findByOpenid(openid);
    }
}
