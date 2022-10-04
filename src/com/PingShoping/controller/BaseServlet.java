package com.PingShoping.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义基础版  BaseServlet
 * 		xml 解析错误   ： 格式不佳
 * 		响应各种数据类型
 * 		跨域访问
 * company 源辰信息
 * @author 27822
 * @date 2022年5月22日
 * @version 1.0
 * Email 1198865589@qq.com
 */
public class BaseServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	//  重写 service 方法 实现 自动分发 调用 对应业务请求方法
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// http://localhost:8080/goods/goods2?op=finds  url
		// http://localhost:8080/goods/goods/finds      uri
		String uri = request.getRequestURI();// 获取请求的资源地址 goods/goods/finds ->  goods/goods2?op=finds
		String path = uri.substring( uri.lastIndexOf("/") + 1);// finds 截取最后的路径  即处理这个请求的指令值   以此对应的调用的业务方法名称 
		System.out.println( uri + "--" + path); // 
		// 先获取当前Servlet的中所有的方法
		Method[] methods = this.getClass().getDeclaredMethods();
		// 根据path找到对应的方法名称 然后激活调用该方法
		for (Method method : methods) {
			if( path.equals( method.getName())) { // 指令 和 方法名称 一致的情况下
				try {
					method.invoke( this, request,  response); // 反向激活方法 
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
				return;
			}
		}
		// 容错界面 
		this.error(request,  response);
	}

	/**
	 * 资料地址访问错误的容错界面
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	protected void error(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 界面跳转 前端乱码
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		// http://localhost:8080/goods/goods/abcd  -> http://localhost:8080/goods/ + index.html
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
		out.print("<script>alert('请求不被支持');location.href='"+ basePath +"index.html';</script>");
	}
	
	
	/**
	 * 响应一个Object类型的数据
	 * @param response
	 * @param obj
	 * @throws IOException
	 */
	protected void send( HttpServletResponse response, Object obj) throws IOException {
		//response.setHeader("Content-Type", "application/json;charset=utf-8");
		// 解决跨域问题 :  Access-Control-Allow-Origin 
		response.setHeader("Access-Control-Allow-Origin", "*");
		// xml 解析错误   ： 格式不佳  告诉浏览器以json格式解析数据
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = response.getWriter();
		// 利用GSON构建JSON数据
		// Gson gson = new Gson(); // 属性值为null 无法显示  导致前台显示 undefined 
		// 当配置GsonBuilder().serializeNulls().create()之后  属性值为null 也能传递到前台 方便我们的工作调试
		Gson gson = new GsonBuilder().serializeNulls().create();
		out.print( gson.toJson( obj ));
	}
	// TODO  根据需求进行扩展   重载send方法
	/**
	 * 响应一个整型类型的状态码
	 * @param
	 * @param response
	 * @param code
	 * @throws IOException
	 */
	protected void send( HttpServletResponse response, int code) throws IOException {
		// 解决跨域问题 :  Access-Control-Allow-Origin 
		response.setHeader("Access-Control-Allow-Origin", "*");
		// xml 解析错误   ： 格式不佳  告诉浏览器以json格式解析数据
		response.setContentType("text/plain;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print( code);
		out.flush();
		out.close();
	}
	
	/**
	 * 以JSON响应  状态码  和 详细数据 
	 * @param response
	 * @param code 状态码
	 * @param data 对象数据
	 * @throws IOException
	 */
	protected void send( HttpServletResponse response,int code, Object data) throws IOException {
		//Access-Control-Allow-Origin 解决跨域
		//response.setHeader("Access-Control-Allow-Origin", "*");
		//解决前端 XML解析错误  ：格式不佳   告诉浏览器以JSON返回
		response.setContentType("application/json;charset=utf-8");
		Gson gson = new GsonBuilder().serializeNulls().create();
		PrintWriter out = response.getWriter();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		map.put("data", data);
		out.print( gson.toJson( map ) );
		out.flush();
		out.close();
	}
}
