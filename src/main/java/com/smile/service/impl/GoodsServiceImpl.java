package com.smile.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smile.pojo.Goods;
import com.smile.mapper.GoodsMapper;
import com.smile.service.GoodsService;
import com.smile.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author thePassionate
 * @since 2021-03-11
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

    private GoodsMapper goodsMapper;

    @Autowired
    public void setGoodsMapper(GoodsMapper goodsMapper) {
        this.goodsMapper = goodsMapper;
    }

    /**
     * TODO 获取商品列表
     * @author smilePlus
     * @date 2021/3/11 11:26
     * @return java.util.List<com.smile.vo.GoodsVo>
     */
    @Override
    public List<GoodsVo> findGoodsVo() {
        return goodsMapper.findGoodsVo();
    }

    @Override
    public GoodsVo findGoodsVoDetailByGoodsId(String goodsId) {
        return goodsMapper.findGoodsVoDetailByGoodsId(goodsId);
    }
}
