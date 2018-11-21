package com.xinyan.sell.service.impl;

import com.xinyan.sell.dto.CartDTO;
import com.xinyan.sell.enums.ProductStatus;
import com.xinyan.sell.enums.ResultStatus;
import com.xinyan.sell.exception.SellException;
import com.xinyan.sell.po.ProductInfo;
import com.xinyan.sell.repository.ProductRepository;
import com.xinyan.sell.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 王宸
 * 2018/11/14 17:46
 */
@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    /**根据商品ID查询
     * @param productId
     * @return
     */
    @Override
    public ProductInfo findOne(String productId) {
        return productRepository.findOne(productId);
    }

    /**商品列表查询
     * @return
     */
    @Override
    public List<ProductInfo> findAll() {
        return productRepository.findAll();
    }

    /**分页查询商品
     * @param pageable
     * @return
     */
    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        PageRequest pageRequest = new PageRequest(pageable.getPageNumber(),pageable.getPageSize());
        Page<ProductInfo> page = productRepository.findAll(pageRequest);
        return page;
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return productRepository.save(productInfo);
    }

    /**根据商品状态查询
     * @param productStatus
     * @return
     */
    @Override
    public List<ProductInfo> findByProductStatus(Integer productStatus) {
        return productRepository.findByProductStatus(productStatus);
    }

    /**加库存
     * @param cartDTOList
     */
    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO: cartDTOList) {
            ProductInfo productInfo = productRepository.findOne(cartDTO.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultStatus.PRODUCT_NOT_EXIST);
            }
            Integer result = productInfo.getProductStock() + cartDTO.getProductQuantity();
            productInfo.setProductStock(result);

            productRepository.save(productInfo);
        }

    }

    /**减库存
     * @param cartDTOList
     */
    @Override
    public void decreaseStock(List<CartDTO> cartDTOList) {

        for (CartDTO cartDTO: cartDTOList){
            ProductInfo productInfo = productRepository.findOne(cartDTO.getProductId());
            if (productInfo == null){
                log.info("[更新库存]商品不存在，productId : {}", cartDTO.getProductId());

            }
            productInfo.setProductStock(productInfo.getProductStock() - cartDTO.getProductQuantity());
            productRepository.save(productInfo);
        }
    }

    /**上架
     * @param productId
     * @return
     */
    @Override
    public ProductInfo onSale(String productId) {
        ProductInfo productInfo = productRepository.findOne(productId);
        if (productInfo == null) {
            throw new SellException(ResultStatus.PRODUCT_NOT_EXIST);
        }
        if (productInfo.getProductStatusEnum() == ProductStatus.UP) {
            throw new SellException(ResultStatus.PRODUCT_STATUS_ERROR);
        }

        //更新
        productInfo.setProductStatus(ProductStatus.UP.getCode());
        return productRepository.save(productInfo);
    }

    /**下架
     * @param productId
     * @return
     */
    @Override
    public ProductInfo offSale(String productId) {
        ProductInfo productInfo = productRepository.findOne(productId);
        if (productInfo == null) {
            throw new SellException(ResultStatus.PRODUCT_NOT_EXIST);
        }
        if (productInfo.getProductStatusEnum() == ProductStatus.DOWN) {
            throw new SellException(ResultStatus.PRODUCT_STATUS_ERROR);
        }

        //更新
        productInfo.setProductStatus(ProductStatus.DOWN.getCode());
        return productRepository.save(productInfo);
    }
}
