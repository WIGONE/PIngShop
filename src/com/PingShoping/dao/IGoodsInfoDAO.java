package com.PingShoping.dao;

import com.PingShoping.bean.GoodsInfo;

import java.util.List;

public interface IGoodsInfoDAO {
    /**
     * 后台管理员登陆
     * @param aname  昵称
     * @param pwd 密码
     * @return
     */
  /*  public AdminInfo login(String aname, String pwd);
    public AdminInfo login2(String amail, String pwd);
*/
    /**
     * 后台管理员添加
     * @param aname
     * @param pwd
     * @param photo
     * @return
   *//*
    public int insert(String aname, String pwd,   Integer status);
    public int insert2(String amail, String pwd,  Integer status);
*/
    /**
     * 商品查询
     * @return
     */
    public List<GoodsInfo> finds();

    public List<GoodsInfo> findGood(String goods_id) ;




    }
