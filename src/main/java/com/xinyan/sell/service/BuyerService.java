package com.xinyan.sell.service;


import com.xinyan.sell.dto.OrderDTO;

/**
 * 买家服务
 */
public interface BuyerService {

    //查询一个订单
    OrderDTO findOrderOne(String openid, String orderId);

    //取消订单
    OrderDTO cancelOrder(String openid, String orderId);
}
