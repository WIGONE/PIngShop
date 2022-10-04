package com.PingShoping.utils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.jsp.PageContext;

import com.jspsmart.upload.File;
import com.jspsmart.upload.Files;
import com.jspsmart.upload.Request;
import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;

/**
 * 文件上传的工具类
 * company 源辰信息
 * @author 30387
 * @date 2022年8月23日
 * @version 1.0
 * Email 3038704857@qq.com
 */
public class FileUploadUtil {
	
	//注意: 如果上传的类型或文件大小不匹配 将会报各种异常信息  此时 请及时检查常量值中是否书写错误  -> 字母 |空格 
	public static String uploadPath = "../images"; // 默认的上传路径    ../加上上一级目录
	public static final String ALLOWEDFILESLIST = "gif,jpeg,png,jpg"; // 允许上传的文件类型
	public static final String DENIEDFILESLIST = "exe,bat,jsp";// 不允许上传的文件类型
	public static final long TOTALMAXFILESIZE = 100 * 1024 * 1024;// 所有上传文件的最大值
	public static final long MAXFILESIZE = 10 * 1024 * 1024;// 单个文件上传的最大值
	//private String temp;
	
	//普通文本信息 -> 单文件上传 -> 多文件上传 -> 多文件域  + 多个文件上传	
	public Map<String,String> uploads(PageContext pageContext) throws ServletException, IOException, SQLException, SmartUploadException{
		//存储上传文件信息
		Map<String,String> map = new HashMap<String,String>();
		
		//上传工具
		SmartUpload su = new SmartUpload();
		//初始化数据
		su.initialize(pageContext);
		//配置文件上传的相关参数
		su.setAllowedFilesList(ALLOWEDFILESLIST);// 允许上传的文件类型
		su.setDeniedFilesList(DENIEDFILESLIST);// 不允许上传的文件类型
		su.setTotalMaxFileSize(TOTALMAXFILESIZE);// 所有上传文件的最大值
		su.setMaxFileSize(MAXFILESIZE);// 单个文件上传的最大值
		su.setCharset("UTF-8");
		su.upload();
		
		//获取请求中上传的普通文本 非文件信息
		//注意 此时request 是 com.jspsmart.upload.Request 而不是 HTTPServletRequest
		Request request = su.getRequest();
		
		//获取请求中所有参数对应表单元素的控件名称 name属性值
		Enumeration<String> enumers = request.getParameterNames();
		//表单元素的控件名称
		String name = null;
		//迭代枚举对象
		while(enumers.hasMoreElements()) {
			name = enumers.nextElement();//键
			map.put(name, request.getParameter(name));//值
		}
		//return map;//此时 只是 上传的普通文本信息
				
		/*
		 思考问题
		1、如何上传文件 
		2、文件存在服务器的哪个位置
		3、文件如何写入到指定的位置
		4、获取存储后的文件路径  如何处理 存储到image_path 
		5、如果服务器指定位置 有 多张相同名称图片 又如何处理		 
		 */
		
		Files files = su.getFiles();//获取所有的上传文件
		
		//如果无文件信息 则直接返回普通文本信息
		if(files == null || files.getCount() <= 0 || files.getSize() <= 0) {
			return map;//此时只是上传的普通文本信息
		}
		//意味着有上传的文件
		Collection<File> fls = files.getCollection();
		
		// TODO 获取上传的服务器绝对路径  -> Tomcat的安装路径   -> E:\Tomcat\apache-tomcat-9.0.62\webapps\项目名 \
		// String basePath = pageContext.getRequest().getRealPath("/"); // E:\Tomcat\apache-tomcat-9.0.62\webapps\goods\
		String basePath = pageContext.getServletContext().getRealPath("/"); // 对应的实例名 jsp的内置对象 application	
		System.out.println(basePath);//D:\Tomcat\apache-tomcat-9.0.65-windows-x64\apache-tomcat-9.0.65\webapps\Goods\
		
		String fieldName = null;//上传的控件名
		String fileName = null;//上传的文件名 相对路径
		String filePath = null;//文件保存的路径 绝对路径
		
		//多文件 多文件域
		String pathStr = null;//多图路径1.png;2.png
		String temp = null;//多文件域 网页上传的控件名
		
		//则循环取出文件
		for(File file : fls) {
			if(!file.isMissing()) {//文件未丢失的情况下 进行文件的存储
				//System.out.println(file.getFieldName() + "--" + file.getFileName() + "--" + file.getFilePathName());
				
				temp = file.getFieldName();//控件名 g_image photo
				if(StringUtil.checkNull(temp)) {//如果该变量是空的 意味着 第一个文件域 要上传的文件
					fieldName = temp;
				}else {//否则就是后续的文件上传
					if(!temp.equals(fieldName)) {//不同文件域上传
						map.put(fieldName,pathStr);//首页存储 第一个文件域中的所有的多图图片
						pathStr = "";//清空多图路径 准备存储下一个文件域信息
						fieldName = temp;
					}
				}
				
				//为了避免 用户上传 相同文件名的文件 自定义文件名 时间戳 + fileName -> 123_yc.png
				fieldName = file.getFieldName();
				fileName = uploadPath + "/" + new Date().getTime()+ "-" + file.getFileName();//images/123_yc.png
				//将上传的文件存储到服务器的指定目录 服务器瑞吉 + 自定义文件名
				filePath = basePath + fileName;		
				
				//判断是否是多图
				if(StringUtil.checkNull(pathStr)) {
					pathStr = fileName;//当前文件域的第一张图片 1.png
				}else {
					pathStr += ";" + fileName;//后续的图片 1.png;2.png;...
				}
				
				//开始存储上传文件信息
				file.saveAs(filePath,SmartUpload.SAVE_PHYSICAL);
			}
		}
		//map.put(fieldName, fileName);//存储上传的文件信息 控件名 -> 单个自定义的文件相对路径 存入map
		map.put(fieldName, pathStr);//存储上传的文件信息 控件名 -> 单个自定义的文件相对路径 存入map ->多图路径 ../images/1.png; ../images/2.png;
		return map;//返回最终的信息
	}
}
