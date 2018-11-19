package com.xinyan.sell.service.impl;

import com.xinyan.sell.converter.OrderMaster2OrderDTOConverter;
import com.xinyan.sell.dto.CartDTO;
import com.xinyan.sell.dto.OrderDTO;
import com.xinyan.sell.enums.OrderStatus;
import com.xinyan.sell.enums.PayStatus;
import com.xinyan.sell.po.OrderDetail;
import com.xinyan.sell.enums.ResultStatus;
import com.xinyan.sell.exception.SellException;
import com.xinyan.sell.po.OrderMaster;
import com.xinyan.sell.po.ProductInfo;
import com.xinyan.sell.repository.OrderDetailRepository;
import com.xinyan.sell.repository.OrderMasterRepository;
import com.xinyan.sell.repository.ProductRepository;
import com.xinyan.sell.service.OrderService;
import com.xinyan.sell.service.ProductService;
import com.xinyan.sell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 王宸
 * 2018/11/15 0:17
 * Order 订单业务接口实现类
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {



    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private ProductService productService;


    /**创建订单
     * @param orderDTO
     * @return
     */
    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        //生成订单id orderId
        String orderId = KeyUtil.genericKey();

        //订单总金额
        BigDecimal orderAmount = new BigDecimal(0);

        orderDTO.setOrderId(orderId);
        //查询商品
        for (OrderDetail orderDetail: orderDTO.getOrderDetailList()){
            ProductInfo productInfo = productRepository.findOne(orderDetail.getProductId());
            if (productInfo == null){
                log.info("[创建订单]商品不存在，productId : {}" + orderDetail.getProductId());
                throw new SellException(ResultStatus.PRODUCT_NOT_EXIST);
            }

            //计算订单总金额
            orderAmount = productInfo.getProductPrice().multiply(
                    new BigDecimal(orderDetail.getProductQuantity())).add(orderAmount);

            //订单详情入库
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetail.setDetailId(KeyUtil.genericKey());
            orderDetail.setOrderId(orderDTO.getOrderId());
            orderDetailRepository.save(orderDetail);
        }

        //订单主表入库
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderId(orderDTO.getOrderId());
        orderMasterRepository.save(orderMaster);

        //更新库存
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());

        productService.decreaseStock(cartDTOList);

        return orderDTO;
    }

    /**
     * 查询单个订单
     *
     * @param orderId
     * @return
     */
    @Override
    public OrderDTO findOne(String orderId) {

        OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
        //判断是否存在，不存在抛出一个自己定义的异常
        if (orderMaster == null) {
            throw new SellException(ResultStatus.ORDER_NOT_EXIST);
        }

        //查询订单详情
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        //判断是否为空，为空就抛一个自己定义的异常
        if (CollectionUtils.isEmpty(orderDetailList)) {
            throw new SellException(ResultStatus.ORDERDETAIL_NOT_EXIST);
        }

        //返回一个OrderDTO
        OrderDTO orderDTO = new OrderDTO();
        //需要转换一下
        BeanUtils.copyProperties(orderMaster, orderDTO);
        //设置订单详情的列表
        orderDTO.setOrderDetailList(orderDetailList);

        return orderDTO;
    }

    /**
     * 查询订单列表
     *
     * @param buyerOpenid
     * @param pageable
     * @return
     */
    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);

        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());

        PageImpl<OrderDTO> orderDTOS = new PageImpl<>(orderDTOList, pageable, orderMasterPage.getTotalElements());
        return orderDTOS;
    }

    /**
     * 取消订单
     *
     * @param orderDTO
     * @return
     */
    @Override
    @Transactional//事物
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();

        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatus.NEW.getCode())) {//判断订单是否是新订单，不是完结的订单就进来
            //输出一个错误的日志，可以吧 orderId={}, orderStatus={} 的状态记下来
            log.error("【取消订单】订单状态不正确, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            //抛出自定义异常
            throw new SellException(ResultStatus.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        orderDTO.setOrderStatus(OrderStatus.CANCEL.getCode());//修改成已取消
        //拷贝函数
        BeanUtils.copyProperties(orderDTO, orderMaster);
        //保存结果
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        //判断一下返回是null为失败
        if (updateResult == null) {
            //打印日志
            log.error("【取消订单】更新失败, orderMaster={}", orderMaster);
            //抛出自定义异常
            throw new SellException(ResultStatus.ORDER_UPDATE_FAIL);
        }

        //返回库存
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {//如果没有商品就不用返回
            //打印日志
            log.error("【取消订单】订单中无商品详情, orderDTO={}", orderDTO);
            //抛出自定义异常
            throw new SellException(ResultStatus.ORDER_DETAIL_EMPTY);
        }
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productService.increaseStock(cartDTOList);

        //如果已支付, 需要退款
        if (orderDTO.getPayStatus().equals(PayStatus.PAID.getCode())) {
            //TODO
        }

        return orderDTO;
    }

    /**
     * 完结订单
     *
     * @param orderDTO
     * @return
     */
    @Override
    @Transactional//事物
    public OrderDTO finish(OrderDTO orderDTO) {

        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatus.NEW.getCode())) {//不等于新订单进来
            //新下单才可以完结订单，如果不是就报自定义异常
            log.error("【完结订单】订单状态不正确, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultStatus.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        orderDTO.setOrderStatus(OrderStatus.FINISH.getCode());//把DTO的状态给为完结
        OrderMaster orderMaster = new OrderMaster();
        //对象拷贝
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        //跟新失败
        if (updateResult == null) {
            log.error("【完结订单】更新失败, orderMaster={}", orderMaster);
            throw new SellException(ResultStatus.ORDER_UPDATE_FAIL);
        }

        return orderDTO;
    }

    /**
     * 支付订单
     *
     * @param orderDTO
     * @return
     */
    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatus.NEW.getCode())) {//不是新订单就抛异常
            log.error("【订单支付完成】订单状态不正确, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultStatus.ORDER_STATUS_ERROR);
        }

        //判断支付状态
        if (!orderDTO.getPayStatus().equals(PayStatus.WAIT.getCode())) {//如果订单不是输入待支付就抛异常
            log.error("【订单支付完成】订单支付状态不正确, orderDTO={}", orderDTO);
            throw new SellException(ResultStatus.ORDER_PAY_STATUS_ERROR);
        }

        //修改支付状态
        orderDTO.setPayStatus(PayStatus.PAID.getCode());//改为支付成功
        OrderMaster orderMaster = new OrderMaster();
        //属性拷贝
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null) {//拷贝失败抛异常
            log.error("【订单支付完成】更新失败, orderMaster={}", orderMaster);
            throw new SellException(ResultStatus.ORDER_UPDATE_FAIL);
        }

        return orderDTO;
    }
}
