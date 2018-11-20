package com.xinyan.sell.service;

import com.xinyan.sell.dto.OrderDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    /** 创建订单 */
    @Test
    public void createOrder() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("李四");
        orderDTO.setBuyerPhone("12938908989");
        orderDTO.setBuyerAddress("深圳广东");
        orderDTO.setBuyerOpenid("3421534544");

        OrderDTO order = orderService.createOrder(orderDTO);
        Assert.assertNotEquals(null,order);
    }

    /** 查询单个订单 */
    @Test
    public void findOne() {
        OrderDTO serviceOne = orderService.findOne("a2914b0264a44acaa3ae0a4c6ff085b1");
        Assert.assertNotNull("此订单不存在",serviceOne);
    }

    @Test
    public void findList() {
    }

    @Test
    public void cancel() {
    }

    @Test
    public void finish() {
    }

    @Test
    public void paid() {
    }
}