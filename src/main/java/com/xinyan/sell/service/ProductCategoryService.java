package com.xinyan.sell.service;

import com.xinyan.sell.po.ProductCategory;

import java.util.List;

/**
 * 王宸
 * 2018/11/14
 *
 * 类目 业务接口
 */
public interface ProductCategoryService {

    //买家端

    /**查询单个
     * @param categoryId
     * @return
     */
    ProductCategory findOne(Integer categoryId);

    /**查询全部
     * @param
     * @return
     */
    List<ProductCategory> findAll();

    /**查询多个
     * @param categoryTypeList
     * @return
     */
    List<ProductCategory> findAll(List<Integer> categoryTypeList);
}
