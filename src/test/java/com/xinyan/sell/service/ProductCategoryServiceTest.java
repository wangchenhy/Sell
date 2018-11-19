package com.xinyan.sell.service;

import com.xinyan.sell.po.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * ProductCategoryService 类目业务接口单元测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryServiceTest {

    @Autowired
    private ProductCategoryService categoryService;

    /**
     * 根据ID查询商品
     */
    @Test
    public void findOne() {
        ProductCategory productCategory = categoryService.findOne(1);
        Assert.assertNotNull(productCategory);
    }

    /**
     * 查询多个商品
     */
    @Test
    public void findAll() {
        List<ProductCategory> productCategoryList = categoryService.findAll(Arrays.asList(10, 11, 12));
        Assert.assertNotEquals(0,productCategoryList);
    }

}