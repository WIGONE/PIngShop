package com.PingShoping.daoImpl;

import com.PingShoping.bean.CartInfo;
import com.PingShoping.dao.DBHelper;
import com.PingShoping.dao.ICartInfoDAO;

import java.util.List;
import java.util.Map;

public class CartInfoDAOImpl implements ICartInfoDAO {

    @Override
    public int addCart(String user_id, Integer goods_id, String goods_amount) {
        DBHelper db = new DBHelper();
        //根据商品Id 查出 商品图片
        String sql = "select * from goodsinfo where goods_id = ?";
        Map map = db.findSingle(sql,goods_id);
        String goods_desc =map.get("goods_desc").toString();
        String goods_photo = map.get("goods_photo").toString();
        String goods_price = map.get("goods_price").toString();
        String goods_name = map.get("goods_name").toString();
        System.out.println(goods_desc);
        String sql1 = "insert  into cartsinfo values(?,?,?,?,?,?,?)";
        int result = db.update(sql1, user_id,goods_id,goods_name,goods_desc,goods_photo,goods_price,goods_amount);
        return result;
    }

    public  int delCart(String user_id,Integer goods_id){
        DBHelper db = new DBHelper();
        String sql ="delete from cartsinfo where user_id = ? and goods_id= ?";
        int result =  db.update(sql,user_id,goods_id);
        return result;
    }

    @Override
    public int updateOrder(String user_id, Integer goods_id, String goods_amount) {
        DBHelper db = new DBHelper();
        String sql ="update cartsinfo set  goods_amount = ? where user_id = ? and goods_id = ?";
        int result = db.update(sql,goods_amount,user_id,goods_id);
        return result;
    }

    @Override
    public List<CartInfo> initCart(String user_id) {
        DBHelper db = new DBHelper();
        String sql = "select * from cartsinfo where user_id = ?";
       return db.findMultiple(CartInfo.class,sql,user_id);

    }
}
