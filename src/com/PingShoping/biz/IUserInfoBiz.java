package com.PingShoping.biz;

import com.PingShoping.bean.UserInfo;

public interface IUserInfoBiz {
    public UserInfo loginTel(String utel, String pwd);
    public UserInfo loginEmail(String umail, String pwd);
    public int RegisterTel(String utel,String pwd);
    public int RegisterEmail( String umail,String pwd);
    public int UpdatePwd(String user_id,String pwd);
    public int delUser(String user_id);
}
