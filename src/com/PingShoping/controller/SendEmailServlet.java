package com.PingShoping.controller;

import com.PingShoping.email.Email;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * 发送邮件 功能：
 *
 * @author itshenjin
 * @author 2018年12月10日
 *
 */
@WebServlet("/send/*")
public class SendEmailServlet extends BaseServlet {
    protected String code;



    protected void fasong(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        System.out.println("正在发送");
        resp.setCharacterEncoding("UTF-8");
        // resp.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("UTF-8");


        try {
//           String usename = req.getParameter("usename");
            String address = req.getParameter("address");
            System.out.println( address +"地址" );
            Email  ml = new Email();
            ml.send(address);
            System.out.println("创建对象");
           code =  ml.getCode(ml);
            System.out.println( code + "打印" );
            resp.getWriter().println( 200 );

        } catch (Exception e) {
            e.printStackTrace();
            req.getSession().setAttribute("error", "邮件发送失败");
        }

    }


        protected void login(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            System.out.println("正在执行方法login");
            String code2 = req.getParameter("yzm");
            System.out.println(code + "发送的吗");
            System.out.println(code2+"第二");
            String address = req.getParameter("address");
            PrintWriter out = resp.getWriter();
            if (code.equals(code2) ) {
                System.out.println("成功");
            }else{
                System.out.println("验证码不相等");
                resp.getWriter().write("<H1>验证失败</H1>");
            }
    }

}

