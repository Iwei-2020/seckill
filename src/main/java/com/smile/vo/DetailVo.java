package com.smile.vo;

import com.smile.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author smilePlus
 * @description: 商品详情Vo
 * @date 2021/3/23 18:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailVo {
    private User user;
    private GoodsVo goodsVo;
    private int remainSeconds;
    private int seckillStatus;
}
