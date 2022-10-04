package com.PingShoping.controller;

import com.PingShoping.biz.ICartInfoBiz;

import com.PingShoping.bizImpl.CartInfoBizImpl;

import com.PingShoping.dao.ICartInfoDAO;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cart/*")
public class CartServlet extends BaseServlet {
    /**
     * 初始化购物车界面信息
     */
    protected void init(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String use_id = req.getParameter("user_id").trim();
        System.out.println(use_id + "用户ID");
        ICartInfoBiz ic = new CartInfoBizImpl();
        this.send(resp, 200, ic.initCart(use_id));
    }

    public void delCart(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println(req.getParameter("user_id") + "删除的用户id");
        String user_id = req.getParameter("user_id").trim();
        System.out.println(req.getParameter("goods_id") + "删除的商品id");
        Integer goods_id = Integer.parseInt(req.getParameter("goods_id").trim());

        ICartInfoBiz ic = new CartInfoBizImpl();
        ic.delCart(user_id, goods_id);
        this.send(resp, 200);
    }

    protected void addCarts(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String user_id =req.getParameter("user_id").trim();
        Integer goods_id =Integer.parseInt(req.getParameter("goods_id").trim());
        System.out.println(user_id + "用户Id");
        System.out.println(goods_id + "商品Id");
        ICartInfoBiz ic = new CartInfoBizImpl();
       ic.addCart(user_id,goods_id,"1");
        this.send(resp, 200);
    }
}
