package com.PingShoping.bizImpl;

import com.PingShoping.bean.GoodsInfo;


import com.PingShoping.biz.IGoodsInfoBiz;
import com.PingShoping.dao.ICartInfoDAO;
import com.PingShoping.dao.IGoodsInfoDAO;
import com.PingShoping.daoImpl.CartInfoDAOImpl;
import com.PingShoping.daoImpl.GoodsInfoDAOImpl;

import java.util.List;

public class GoodsInfoBizImpl implements IGoodsInfoBiz {
    @Override
    public List<GoodsInfo> findGood(String goods_id) {

        IGoodsInfoDAO igd=new GoodsInfoDAOImpl();
        return  igd.findGood(goods_id);
    }
    @Override
    public List<GoodsInfo> finds() {
        IGoodsInfoDAO igd=new GoodsInfoDAOImpl();
        return  igd.finds();
    }

}
