package com.PingShoping.dao;

import com.PingShoping.bean.CartInfo;
import com.sun.org.apache.xpath.internal.objects.XString;

import java.util.List;

public interface ICartInfoDAO  {

    public int addCart(String user_id, Integer goods_id, String goods_amount) ;

    public  int delCart(String user_id,Integer goods_id);
    public int updateOrder(String user_id,Integer goods_id,String goods_amount);
    public List<CartInfo> initCart(String user_id) ;

}
