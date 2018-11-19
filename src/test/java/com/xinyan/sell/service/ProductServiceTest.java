package com.xinyan.sell.service;

import com.xinyan.sell.po.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 商品业务测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    /**
     * 查询单个
     */
    @Test
    public void findOne() {
        ProductInfo productInfo = productService.findOne("1542167003478563456");
        Assert.assertNotNull(productInfo);
    }

    /**
     * 分页查询
     */
    @Test
    public void findAll() {
        PageRequest pageRequest = new PageRequest(0,2);
        Page<ProductInfo> page = productService.findAll(pageRequest);
        Assert.assertNotEquals(0,page.getTotalElements());
    }

    /**
     * 根据状态查询商品
     */
    @Test
    public void findByProductStatus() {
    }
}