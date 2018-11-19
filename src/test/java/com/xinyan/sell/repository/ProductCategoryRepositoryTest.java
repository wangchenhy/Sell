package com.xinyan.sell.repository;

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
 * ProductCategoryRepository 接口单元测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository repository;

    /**
     * 根据ID查询单个类目
     */
    @Test
    public void findOneTest(){
        ProductCategory productCategory = repository.findOne(1);
        System.out.println(productCategory);
        Assert.assertNotNull("根据ID查询类目",productCategory);

    }

    /**
     * 添加
     */
    @Test
    public void saveTest(){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("女生最爱");
        productCategory.setCategoryType(12);

        ProductCategory result = repository.save(productCategory);
        Assert.assertNotEquals(null,result);
    }

    /**
     * 根据ID更新类目
     */
    @Test
    public void updateTest(){
        ProductCategory productCategory = repository.findOne(3);
        productCategory.setCategoryName("女神最爱");

        ProductCategory result = repository.save(productCategory);
        Assert.assertNotNull(result);

    }

    /**
     * 根据ID查询多个类目
     */
    @Test
    public void findByCategoryTypeInTest(){
        List<ProductCategory> productCategoryList = repository.findByCategoryTypeIn(Arrays.asList(10,11,12));
        Assert.assertNotNull("根据多个类型查询项目",productCategoryList);
    }
}