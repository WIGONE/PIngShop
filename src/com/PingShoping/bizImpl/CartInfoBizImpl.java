package com.PingShoping.bizImpl;

import com.PingShoping.bean.CartInfo;
import com.PingShoping.biz.ICartInfoBiz;

import com.PingShoping.dao.ICartInfoDAO;
import com.PingShoping.daoImpl.CartInfoDAOImpl;

import java.util.List;

public class CartInfoBizImpl implements ICartInfoBiz {
    @Override
    public int addCart(String user_id, Integer goods_id, String goods_amount) {
        ICartInfoDAO icd=new CartInfoDAOImpl();
        icd.addCart(user_id,goods_id,"1");
        return 0;
    }

    @Override
    public int  delCart(String user_id, Integer goods_id) {
        ICartInfoDAO icd=new CartInfoDAOImpl();
       int result = icd.delCart(user_id,goods_id);
        return result;
    }

    @Override
    public int updateOrder(String user_id, Integer goods_id, String goods_amount) {
        return 0;
    }



    public List<CartInfo> initCart(String user_id) {
        ICartInfoDAO icd=new CartInfoDAOImpl();
        return icd.initCart(user_id);
    }

    }
