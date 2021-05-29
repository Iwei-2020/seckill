package com.smile.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smile.mapper.OrderMapper;
import com.smile.mapper.SeckillGoodsMapper;
import com.smile.mapper.SeckillOrderMapper;
import com.smile.pojo.Order;
import com.smile.pojo.SeckillGoods;
import com.smile.pojo.SeckillOrder;
import com.smile.pojo.User;
import com.smile.service.OrderService;
import com.smile.service.SeckillGoodsService;
import com.smile.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author thePassionate
 * @since 2021-03-11
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    private SeckillGoodsService seckillGoodsService;
    private SeckillGoodsMapper seckillGoodsMapper;
    private OrderMapper orderMapper;
    private SeckillOrderMapper seckillOrderMapper;
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Order doSeckill(User user, GoodsVo goodsVo) {
        // 秒杀商品减库存
        SeckillGoods seckillGoods = seckillGoodsMapper.selectOne(new QueryWrapper<SeckillGoods>()
                .eq("goods_id", goodsVo.getId()));
        // seckillGoods.setStockCount(seckillGoods.getStockCount() - 1);
        int update = seckillGoodsMapper.update(null,
                new UpdateWrapper<SeckillGoods>()
                        .setSql("stock_count = stock_count - 1")
                        .eq("goods_id", goodsVo.getId())
                        .gt("stock_count", 0));
        if (update == 0) {
            return null;
        }
        // 生成商品订单
        Order order = new Order();
        order.setUserId(user.getId());
        order.setGoodsId(goodsVo.getId());
        order.setGoodsName(goodsVo.getGoodsName());
        order.setGoodsCount(0);
        order.setGoodsPrice(seckillGoods.getSeckillPrice());
        order.setOrderChannel(0);
        order.setStatus(0);
        order.setCreateDate(new Date());
        order.setPayDate(new Date());
        orderMapper.insert(order);
        // 生成秒杀订单
        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setUserId(user.getId());
        seckillOrder.setGoodsId(goodsVo.getId());
        seckillOrder.setOrderId(order.getId());
        seckillOrderMapper.insert(seckillOrder);
        redisTemplate.opsForValue().set("order:" + user.getId() + ":" + seckillGoods.getGoodsId(), seckillOrder);
        return order;
    }

    @Autowired
    public void setSeckillGoodsMapper(SeckillGoodsMapper seckillGoodsMapper) {
        this.seckillGoodsMapper = seckillGoodsMapper;
    }

    @Autowired
    public void setOrderMapper(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    @Autowired
    public void setSeckillOrderMapper(SeckillOrderMapper seckillOrderMapper) {
        this.seckillOrderMapper = seckillOrderMapper;
    }

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Autowired
    public void setSeckillGoodsService(SeckillGoodsService seckillGoodsService) {
        this.seckillGoodsService = seckillGoodsService;
    }
}
