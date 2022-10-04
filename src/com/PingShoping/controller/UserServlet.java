package com.PingShoping.controller;

import com.PingShoping.bean.UserInfo;
import com.PingShoping.biz.IUserInfoBiz;
import com.PingShoping.bizImpl.UserInfoBizimpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet("/user/*")
public class UserServlet extends BaseServlet{

 public void loginTel(HttpServletRequest req, HttpServletResponse resp) throws IOException {
     String pwd = req.getParameter("pwd");
     String utel = req.getParameter("utel");
     System.out.println(pwd + "密码");
     System.out.println(utel + "电话");
     IUserInfoBiz iub =new UserInfoBizimpl();
      UserInfo ui = iub.loginTel(utel,pwd);
     if(ui != null){
         String user_id = ui.getUser_id();
         Cookie ck = new Cookie("user_id",user_id);
         ck.setMaxAge(-1);
         resp.addCookie(ck);
         this.send(resp,200);
     }else{
         this.send(resp,500);
     }

 }

    public void loginEmail(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pwd = req.getParameter("pwd");
        String umail = req.getParameter("umail");
        if(pwd == null || umail == null){
            this.send(resp,500);
        }
        IUserInfoBiz iub =new UserInfoBizimpl();
        UserInfo ui = iub.loginEmail(umail,pwd);
        if(ui != null){
            String user_id = ui.getUser_id();
            Cookie ck = new Cookie("user_id",user_id);
            ck.setMaxAge(-1);
            resp.addCookie(ck);
            this.send(resp,200);
        }

    }
}
