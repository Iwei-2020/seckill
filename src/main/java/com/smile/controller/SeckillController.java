package com.smile.controller;

import com.smile.pojo.Order;
import com.smile.pojo.SeckillOrder;
import com.smile.pojo.User;
import com.smile.service.GoodsService;
import com.smile.service.OrderService;
import com.smile.service.SeckillOrderService;
import com.smile.vo.GoodsVo;
import com.smile.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author smilePlus
 * @description: 秒杀
 * @date 2021/3/18 16:31
 */
@Controller
@RequestMapping("/seckill")
public class SeckillController {

    private GoodsService goodsService;
    private SeckillOrderService seckillOrderService;
    private OrderService orderService;
    private RedisTemplate<String, Object> redisTemplate;


    /**
     * TODO 优化前QPS：192.5
     *      优化后QPS：
     * @param model "
     * @param user "
     * @param goodsId "
     * @author smilePlus
     * @date 2021/3/22 20:06
     * @return java.lang.String
     */
    @RequestMapping("/doSeckill")
    @Transactional
    public String doSeckill(Model model, User user, String goodsId) {
        if (user == null) {
            return "login";
        }
        GoodsVo goodsVo = goodsService.findGoodsVoDetailByGoodsId(goodsId);
        if (goodsVo.getStockCount() < 1) {
            model.addAttribute("errorMsg", RespBeanEnum.STOCK_LACK);
            return "seckillFail";
        }
        SeckillOrder seckillOrder
                = (SeckillOrder) redisTemplate.opsForValue().get("order:" + user.getId() + ":" + goodsId);
        if (seckillOrder != null) {
            model.addAttribute("errorMsg",RespBeanEnum.REPEAT_ERROR);
            return "seckillFail";
        }
        Order order = orderService.doSeckill(user,goodsVo);
        model.addAttribute("user", user);
        model.addAttribute("order",order);
        model.addAttribute("goods",goodsVo);
        return "orderDetail";
    }

    @Autowired
    public void setSeckillOrderService(SeckillOrderService seckillOrderService) {
        this.seckillOrderService = seckillOrderService;
    }

    @Autowired
    public void setGoodsService(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
