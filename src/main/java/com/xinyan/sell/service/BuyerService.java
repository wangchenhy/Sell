package com.xinyan.sell.service;

import com.xinyan.sell.dto.OrderDTO;

/**
 * @Author: ZengQingQuan
 * @Description:
 * @Date: Create in 23:22 2018/11/21 0021
 *
 * 买家
 */
public interface BuyerService {

    //查询一个订单
    OrderDTO findOrderOne(String openid, String orderId);

    //取消订单
    OrderDTO cancelOrder(String openid, String orderId);
}