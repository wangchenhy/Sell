package com.xinyan.sell.repository;

import com.xinyan.sell.po.OrderDetail;
import com.xinyan.sell.po.OrderMaster;
import com.xinyan.sell.utils.KeyUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    /**
     *
     */
    @Test
    public void save() {
        String orderId = KeyUtil.genericKey();

        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId(orderId);
        orderMaster.setBuyerName("王五");
        orderMaster.setBuyerPhone("6584675467");
        orderMaster.setBuyerAddress("广东深圳");
        orderMaster.setBuyerOpenid("654654");
        orderMaster.setOrderAmount(new BigDecimal("28"));


        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId(KeyUtil.genericKey());
        orderDetail.setOrderId(orderId);
        orderDetail.setProductId("fbghwrs657df");
        orderDetail.setProductName("湖南小炒肉");
        orderDetail.setProductPrice(new BigDecimal("28"));
        orderDetail.setProductQuantity(1);
        orderDetail.setProductIcon("http:");

        orderMasterRepository.save(orderMaster);
        OrderDetail orderDetailResult = orderDetailRepository.save(orderDetail);

        Assert.assertNotEquals("保存订单详情", orderDetailResult);
    }

    /**
     * 通过用户微信id查询
     */
    @Test
    public void findByOrderId() {
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId("34725fd6e8264fa5853a2b947f1bd599");
        Assert.assertNotEquals(0, orderDetailList.size());
    }
}