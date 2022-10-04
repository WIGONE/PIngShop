package com.PingShoping.dao;

import com.PingShoping.bean.UserInfo;

public interface IUserInfoDAO {
    public UserInfo loginTel(String utel, String pwd);


    public UserInfo loginEmail(String umail, String pwd);

    public int RegisterTel(String utel, String pwd);


    public int RegisterEmail(String umail, String pwd);


    public int UpdatePwd(Integer user_id, String pwd);
}
