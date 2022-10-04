package com.PingShoping.biz;

import com.PingShoping.bean.GoodsInfo;

import java.util.List;

public interface IGoodsInfoBiz {
    public List<GoodsInfo> findGood(String goods_id);
    public List<GoodsInfo> finds();


}
