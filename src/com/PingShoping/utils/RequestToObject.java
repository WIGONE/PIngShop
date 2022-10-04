package com.PingShoping.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Servelet里面的 HttpServletRequest 请求封装成一个JavaBean对象
 * company 源辰信息
 * @author 38929
 * @date 2021年8月6日
 * @version 1.0
 * Email 1198865589@qq.com
 */
public class RequestToObject {

	public static <T> T getParamesToObject(Class<T> cls , HttpServletRequest request){
		T t = null; //返回实体类对象
		
		try {
			t = cls.newInstance();//实例化对象
			
			//获取请求中的参数名  即表单控件中 name属性的属性值 或ajaxd定义的键  枚举
			Enumeration<String> names = request.getParameterNames();
	
			//获取传入类的类中所有的方法
			Method[] methods = cls.getMethods();
			//存储set方法的map
			Map<String, Method> setters = new HashMap<String, Method>();
			
			String  methodName = null;//方法名
			for(Method md : methods) {  //循环所有的方法
				methodName = md.getName();//获取数组中方法的方法名
				
				if( methodName.startsWith("set")){ //筛选出set开头的方法
					setters.put(methodName, md);
				}
			}
			
			String name = null;//表单元素name得值
			String value = null;//请求参数的具体的值
			Method method = null;//匹配的方法
			
			Class<?> [] types = null;//形参的参数类型列表
			Class<?>  type = null;//单个参数的类型
			
			//循环枚举对象
			while( names.hasMoreElements()) {
				name = names.nextElement(); //获取请求的参数的  -> 键
				methodName = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);// sexXxx 严格遵循大小写 
				
				method = setters.getOrDefault( methodName, null);// 根据方法名查询对应的方法  无对应则返回null 
				
				value = request.getParameter(name);//获取请求中的参数 
				//判空
				if( null == value || "" == value) { // 请求无数据
					continue;
				}
				if( null == method) { //如果没有对应的方法 则 说明这个类中属性 不仅需要 注值  则 跳过 不管
					continue;
				}
				
				types = method.getParameterTypes();//有对应的方法 则获取这个方法的形参的参数类型列表
				if( null == types || types.length <= 0) { //说明没有形参 也无法注值
					continue;
				}
				type = types[0];//set方法 默认只有一个形参 所以取第一个形参的类型
			
				//类型匹配 开启注值
				if( Integer.TYPE == type) { //说明要的是一个整型值
					method.invoke( t, Integer.parseInt( value ));//反向激活  对象  参数值  
				}else if( Integer.class == type) { //整型对象类型 可以设置null   Integer包装类
					method.invoke( t, Integer.valueOf( value ));
				}else if( Long.TYPE == type) { 
					method.invoke( t, Long.parseLong( value ));
				}else if( Long.class == type) { 
					method.invoke( t, Long.valueOf( value ));
				}else if( Short.TYPE == type) { 
					method.invoke( t, Short.parseShort( value ));
				}else if( Short.class == type) { 
					method.invoke( t, Short.valueOf( value ));
				}else if( Boolean.TYPE == type) { 
					method.invoke( t, Boolean.parseBoolean( value ));
				}else if( Boolean.class == type) { 
					method.invoke( t, Boolean.valueOf( value ));
				}else if( Float.TYPE == type) { 
					method.invoke( t, Float.parseFloat( value ));
				}else if( Float.class == type) { 
					method.invoke( t, Boolean.valueOf( value ));
				}else if( Double.TYPE == type) { 
					method.invoke( t, Double.parseDouble( value ));
				}else if( Double.class == type) { 
					method.invoke( t, Double.valueOf( value ));
				}else if( String.class == type) { 
					method.invoke( t,  value );
				}else if(Character.TYPE == type  || Character.class == type) { //单个字符
					char [] cs = value.toCharArray();
					if( cs.length > 1)  {
						throw new IllegalArgumentException("参数长度太大");
					}
					method.invoke( t,  cs[0]);
				}
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} 
		return  t; 
	}
}
