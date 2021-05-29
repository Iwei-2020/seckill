package com.smile.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.smile.pojo.Goods;
import com.smile.vo.GoodsVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author thePassionate
 * @since 2021-03-11
 */
public interface GoodsService extends IService<Goods> {

    /**
     * TODO 获取商品列表
     * @author smilePlus
     * @date 2021/3/11 11:25
     * @return java.util.List<com.smile.vo.GoodsVo>
     */
    List<GoodsVo> findGoodsVo();

    /**
     * TODO 获取商品详情
     * @author smilePlus
     * @date 2021/3/17 16:22
     * @return com.smile.vo.GoodsVo
     */
    GoodsVo findGoodsVoDetailByGoodsId(String goodsId);
}
