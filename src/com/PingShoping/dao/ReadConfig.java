package com.PingShoping.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 利用单例模式 获取DB的配置信息 
 * 懒汉  饿汉式
 * company 源辰信息
 * @author 38929
 * @date 2022年3月9日
 * @version 1.0
 * Email 1198865589@qq.com
 */
public class ReadConfig extends Properties{
	
	private static final long serialVersionUID = 1560020482133144083L;
	//饿汉式   提前实例化对象
	private static ReadConfig instance = new ReadConfig();

	//构造方法私有化  标志性 
	private ReadConfig() {
		//想办法读取自己的配置文件   
		//JDK1.7 会自动关闭且一定关闭资源   try - with -resources
		try(InputStream is = this.getClass().getClassLoader().getResourceAsStream("db.properties")){
			//业务代码  流处理 
			this.load(is); //读取配置文件
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	//因为构造私有化就意味着外部无法实例化该对象 必须对外提供一个获取实例的方法  并在内部提前实例化 
	//又因为外部无法是实例化 只能通过类名方法 该方法 需声明为静态的 
	public  static ReadConfig getInstance() {
		//静态只能调用静态  故 该对象又必须是静态变量
		return instance;
	}
}
