package com.xinyan.sell.dto;

import com.xinyan.sell.po.OrderDetail;
import com.xinyan.sell.enums.OrderStatus;
import com.xinyan.sell.enums.PayStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.xinyan.sell.utils.EnumUtil;
import com.xinyan.sell.utils.serializer.Date2LongSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 王宸
 * 2018/11/15 0:09
 *
 * order 数据传输对象
 */
@Data
public class OrderDTO {

    /** 订单ID*/
    private String orderId;

    /** 买家姓名 */
    private String buyerName;

    /** 买家姓名 */
    private String buyerPhone;

    /** 买家地址 */
    private String buyerAddress;

    /** 买家微信 openid */
    private String buyerOpenid;

    /** 订单总金额 */
    private BigDecimal orderAmount;

    /** 订单状态 默认为0，新订单*/
    private Integer orderStatus = OrderStatus.NEW.getCode();

    /** 支付状态 默认为0，未支付*/
    private Integer payStatus = PayStatus.WAIT.getCode();

    /** 创建时间 */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    /** 修改时间 */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    /** 订单详情 */
    private List<OrderDetail> orderDetailList;

    @JsonIgnore
    public OrderStatus getOrderStatusEnum() {
        return EnumUtil.getByCode(orderStatus, OrderStatus.class);
    }

    @JsonIgnore
    public PayStatus getPayStatusEnum() {
        return EnumUtil.getByCode(payStatus, PayStatus.class);
    }
}
