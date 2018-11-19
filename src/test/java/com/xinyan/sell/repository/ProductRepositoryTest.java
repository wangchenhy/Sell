package com.xinyan.sell.repository;

import com.xinyan.sell.enums.ProductStatus;
import com.xinyan.sell.po.ProductInfo;
import com.xinyan.sell.utils.KeyUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * 商品测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository repository;

    /**
     * 添加商品
     */
    @Test
    public void save(){
        ProductInfo productInfo = new ProductInfo();
        //uuid 来生成主键值
        productInfo.setProductId(KeyUtil.genericKey());
        productInfo.setProductName("麻辣香锅");
        productInfo.setProductPrice(new BigDecimal("35"));
        productInfo.setProductDescription("辣就完事了");
        productInfo.setProductIcon("http：");
        productInfo.setCategoryType(11);
        productInfo.setProductStock(100);
//        productInfo.setProductStatus(ProductStatus.UP.getCode());

        ProductInfo result = repository.save(productInfo);
        Assert.assertNotNull(result);
    }

    /**
     * 根据商品状态查询
     */
    @Test
    public void findByProductStatus() {
        List<ProductInfo> productInfoList = repository.findByProductStatus(0);
        Assert.assertNotEquals(0,productInfoList);
    }
}