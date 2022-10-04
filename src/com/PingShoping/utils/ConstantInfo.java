package com.PingShoping.utils;

/**
 * 常量管理器  常量定义
 * company 源辰信息
 * @author 梦凝哲雪
 * @date 2022年10月1日
 * @version 1.0
 * Email 1198865589@qq.com
 */
public class ConstantInfo {

	// 路径定义
	public static String basePath; // 基址路径，服务器的绝对路径
	public static String uploadPath; // 文件上传的保存路径
	
	// 服务器 会话  
	public static final String CURRENTFRONTLOGINACCOUT = "CURRENTFRONTLOGINACCOUT"; // 当前登录的会员信 息
	public static final String CURRENTBACKLOGINACCOUT = "CURRENTBACKLOGINACCOUT"; // 后台管理员信息
	
	// 验证码相关
	public static final String EMAILCHECKCODE = "EMAILCHECKCODE"; // 邮箱注册时的验证码
	public static final String CHECKCODE = "CHECKCODE"; // 登录时的验证码
	
	// 日志管理
	// public static Logger logger = LogManager.getLogger( ConstantInfo.class );
}
