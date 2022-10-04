package com.PingShoping.daoImpl;

import com.PingShoping.bean.UserInfo;
import com.PingShoping.biz.IUserInfoBiz;
import com.PingShoping.bizImpl.UserInfoBizimpl;
import com.PingShoping.dao.ICartInfoDAO;
import com.PingShoping.dao.IGoodsInfoDAO;
import com.PingShoping.dao.IUserInfoDAO;

import java.util.UUID;

public class Testo1 {
    public static void main(String[] args) {
        /*ICartInfoDAO cd = new CartInfoDAOImpl();
      *//*  int result =  cd.delCart(001,001);
        System.out.println(result);*//*
        int re= cd.addCart(3,117,"1");*/

       /* IUserInfoDAO iud = new UserInfoDAOImpl();
        System.out.println(iud.loginTel("1231234","123"));*/

        IUserInfoBiz iub =new UserInfoBizimpl();
        UserInfo ui = iub.loginTel("1231234","123");
        System.out.println(ui);
    }
}
