package com.PingShoping.bizImpl;

import com.PingShoping.bean.UserInfo;
import com.PingShoping.biz.IUserInfoBiz;
import com.PingShoping.dao.IUserInfoDAO;
import com.PingShoping.daoImpl.UserInfoDAOImpl;

public class UserInfoBizimpl implements IUserInfoBiz {

    @Override
    public UserInfo loginTel(String utel, String pwd) {
        IUserInfoDAO uid = new UserInfoDAOImpl();
        uid.loginTel(utel,pwd);
        return uid.loginTel(utel,pwd);
    }

    @Override
    public UserInfo loginEmail(String umail, String pwd) {
        IUserInfoDAO uid = new UserInfoDAOImpl();
        uid.loginTel(umail,pwd);
        return uid.loginTel(umail,pwd);

    }

    @Override
    public int RegisterTel(String utel, String pwd) {
        return 0;
    }

    @Override
    public int RegisterEmail(String umail, String pwd) {
        return 0;
    }

    @Override
    public int UpdatePwd(String user_id, String pwd) {
        return 0;
    }

    @Override
    public int delUser(String user_id) {
        return 0;
    }
}
