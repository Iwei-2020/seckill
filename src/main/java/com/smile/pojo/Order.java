package com.smile.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author thePassionate
 * @since 2021-03-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_order")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单Id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 用户Id
     */
    private String userId;

    /**
     * 商品Id
     */
    private String goodsId;

    /**
     * 收货地址Id
     */
    private String deliveryAddrId;

    /**
     * 商品名称(冗余)
     */
    private String goodsName;

    /**
     * 商品数量
     */
    private Integer goodsCount;

    /**
     * 商品单价
     */
    private BigDecimal goodsPrice;

    /**
     * 1pc,2android,3ios
     */
    private Integer orderChannel;

    /**
     * 订单状态，0新建未支付；1已支付；2已发货；3已收货；4已退款；5已完成
     */
    private Integer status;

    /**
     * 订单的创建时间
     */
    private Date createDate;

    /**
     * 支付时间
     */
    private Date payDate;


}
