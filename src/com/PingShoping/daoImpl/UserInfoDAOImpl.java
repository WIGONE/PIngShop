package com.PingShoping.daoImpl;

import com.PingShoping.bean.UserInfo;
import com.PingShoping.biz.IUserInfoBiz;
import com.PingShoping.dao.DBHelper;
import com.PingShoping.dao.IUserInfoDAO;

import java.util.UUID;

public class UserInfoDAOImpl implements IUserInfoDAO {

    @Override
    public UserInfo loginTel(String utel, String pwd) {
        System.out.println("正在执行loginTel");
        DBHelper db = new DBHelper();
        String sql = "select * from userinfo where utel = ?  and pwd = md5(?) and status != 0";

        return db.findSingle(UserInfo.class,sql, utel ,pwd);
    }

    @Override
    public UserInfo loginEmail(String umail, String pwd) {
        DBHelper db = new DBHelper();
        String sql = "select * from userinfo where umail = ?  and pwd = md5(?) and  status != 0";
        return db.findSingle(UserInfo.class,sql, umail,pwd );
    }

    @Override
    public int RegisterTel(String utel, String pwd) {
        DBHelper db = new DBHelper();
        String sql = "select * from userinfo where utel = ?   and status != 0";
      if(db.findSingle(UserInfo.class,sql, utel )!=null) {
          return 0;
      }
      Integer status =1;
       String user_id = String.valueOf(UUID.randomUUID());
        System.out.println(user_id);
        String sql1 = "insert  into userinfo values(?,?,?,md5(?),?)";
      String umail = null;
        return db.update(sql1,user_id,utel,umail,pwd,status);
    }

    @Override
    public int RegisterEmail(String umail, String pwd) {


        return 0;
    }

    @Override
    public int UpdatePwd(Integer user_id, String pwd) {

        return 0;
    }
}
