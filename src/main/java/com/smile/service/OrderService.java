package com.smile.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.smile.pojo.Order;
import com.smile.pojo.User;
import com.smile.vo.GoodsVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author thePassionate
 * @since 2021-03-11
 */
public interface OrderService extends IService<Order> {

    Order doSeckill(User user, GoodsVo goodsVo);
}
