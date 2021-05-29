package com.smile.controller;

import com.smile.pojo.User;
import com.smile.service.GoodsService;
import com.smile.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * TODO
 *
 * @author smilePlus
 * @version 1.0
 * @date 2021/3/7 9:58
 */
@RequestMapping("/goods")
@Controller
@Validated
public class GoodsController {

    private GoodsService goodsService;
    private RedisTemplate<String, Object> redisTemplate;
    private ThymeleafViewResolver thymeleafViewResolver;

    @Autowired
    public void setThymeleafViewResolver(ThymeleafViewResolver thymeleafViewResolver) {
        this.thymeleafViewResolver = thymeleafViewResolver;
    }

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Autowired
    public void setGoodsService(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    /**
     * TODO 优化前WindowQPS：1119
     *      优化后WindowQPS: 1596
     * @param model "
     * @param user  "
     * @return java.lang.String
     * @author smilePlus
     * @date 2021/3/22 9:57
     */
    @RequestMapping(value = "/toList", produces = "text/html;charset=utf-8")
    @ResponseBody
    // HttpSession httpSession
    public String toList(Model model, @NotNull User user,
                         HttpServletRequest request,
                         HttpServletResponse response) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        String html = (String) valueOperations.get("html_goodsList");
        if (StringUtils.hasLength(html)) {
            return html;
        }
        model.addAttribute("user", user);
        model.addAttribute("goodsList", goodsService.findGoodsVo());
        WebContext webContext = new WebContext(request, response,
                request.getServletContext(), request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goodsList", webContext);
        if (StringUtils.hasLength(html)) {
            valueOperations.set("html_goodsList", html, 60, TimeUnit.SECONDS);
        }
        return html;
    }


    @RequestMapping(value = "/toDetail/{goodsId}", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String toDetail(Model model, User user, @PathVariable("goodsId") String goodsId,
                           HttpServletRequest request,
                           HttpServletResponse response) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        String html = (String) valueOperations.get("html_goodsDetail" + goodsId);
        if (StringUtils.hasLength(html)) {
            return html;
        }

        GoodsVo goodsVo = goodsService.findGoodsVoDetailByGoodsId(goodsId);
        Date startDate = goodsVo.getStartDate();
        Date endDate = goodsVo.getEndDate();
        Date nowDate = new Date();
        // 初始状态-1 => 无状态
        int seckillStatus = -1;
        long remainSeconds = 0;
        if (nowDate.before(startDate)) {
            // 秒杀未开始
            seckillStatus = 0;
            remainSeconds = ((startDate.getTime() - nowDate.getTime()) / 1000);
        } else if (nowDate.before(endDate)) {
            // 秒杀进行中
            seckillStatus = 1;
        } else {
            // 秒杀已结束
            seckillStatus = 2;
            remainSeconds = -1;
        }
        model.addAttribute("user", user);
        model.addAttribute("remainSeconds", remainSeconds);
        model.addAttribute("seckillStatus", seckillStatus);
        model.addAttribute("goods", goodsVo);
        WebContext webContext = new WebContext(request, response,
                request.getServletContext(), request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goodsDetail", webContext);
        if (StringUtils.hasLength(html)) {
            valueOperations.set("html_goodsDetail" + goodsId, html, 60, TimeUnit.SECONDS);
        }
        return html;
    }



}
