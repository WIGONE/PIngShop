package com.PingShoping.biz;

import com.PingShoping.bean.CartInfo;

import java.util.List;

public interface ICartInfoBiz {
    /**
     * 添加购物车记录
     */
    public int  addCart(String user_id,Integer goods_id, String goods_amount);

    /**
     * 删除一条购物车记录
     * @param user_id
     * @param goods_id
     * @return
     */
    public int  delCart(String user_id,Integer goods_id);

    /**
     * 修改购物车中商品数量
     * @param user_id
     * @param goods_id
     * @param goods_amount
     * @return
     */
    public int updateOrder(String user_id,Integer goods_id,String goods_amount);

    /**
     * 查询所以购物车数据
     *
     * @param user_id
     * @return
     */
    public List<CartInfo> initCart(String user_id) ;

    }
