package com.PingShoping.controller;


import com.PingShoping.biz.IGoodsInfoBiz;
import com.PingShoping.bizImpl.GoodsInfoBizImpl;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/good/*")
public class GoodsServlet extends BaseServlet{

    public void finds(HttpServletRequest request, HttpServletResponse response) throws IOException {
        IGoodsInfoBiz biz = new GoodsInfoBizImpl();
        //layui 界面的表格数据参数 一定要数组 才能解析 并且返回的数据不符合规范，正确的成功状态码应为："code": 0 格式遵循UI要求
        this.send(response, 0, biz.finds());
    }
    public void findGood(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String goods_id = request.getParameter("goods_id");
        System.out.println(goods_id);
        IGoodsInfoBiz biz = new GoodsInfoBizImpl();
        //layui 界面的表格数据参数 一定要数组 才能解析 并且返回的数据不符合规范，正确的成功状态码应为："code": 0 格式遵循UI要求
        this.send(response, 200, biz.findGood(goods_id));
    }
}
