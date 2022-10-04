package com.PingShoping.daoImpl;

import com.PingShoping.bean.GoodsInfo;
import com.PingShoping.dao.DBHelper;

import com.PingShoping.dao.IGoodsInfoDAO;

import java.util.List;

public class GoodsInfoDAOImpl implements IGoodsInfoDAO {
   /*
   查询所有商品
    */

    @Override
    public List<GoodsInfo> finds() {
        DBHelper db = new DBHelper();
        String sql = "select * from goodsinfo";
        return db.findMultiple(GoodsInfo.class, sql);
    }

    @Override
    public List<GoodsInfo> findGood(String goods_id) {
        DBHelper db = new DBHelper();
        String sql = "select * from goodsinfo where goods_id = ?";
        return db.findMultiple(GoodsInfo.class, sql,goods_id);
    }
}
