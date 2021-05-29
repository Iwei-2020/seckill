package com.smile.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smile.pojo.Goods;
import com.smile.vo.GoodsVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author thePassionate
 * @since 2021-03-11
 */
@Repository
public interface GoodsMapper extends BaseMapper<Goods> {

    /**
     * TODO 获取商品列表
     * @author smilePlus
     * @date 2021/3/11 11:30
     * @return java.util.List<com.smile.vo.GoodsVo>
     */
    List<GoodsVo> findGoodsVo();

    GoodsVo findGoodsVoDetailByGoodsId(String goodsId);

}
