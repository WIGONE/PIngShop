package com.PingShoping.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自定义的JDBC优化类
 * 	   封装操作数据库的相关方法
 * company 源辰信息
 * @author 38929
 * @date 2022年7月1日
 * @version 1.0
 * Email 1198865589@qq.com
 */
public class DBHelper {

	//配置信息
	//ORACLE
	//private static String driverClassName = "oracle.jdbc.driver.OracleDriver";
	//private String url = "jdbc:oracle:thin:@localhost:1521:ORCL";
	//private String user = "scott";
	//private String password = "a";

	//MySQL

	//相关对象
	private Connection conn = null;//连接对象
	private PreparedStatement pstmt = null;//预编译
	private ResultSet rs = null;//结果集

	// 只在类中第一次加载才执行 且 执行一次   -> 当使用JNDI连接数据库时  可以注释该静态加载  
	static {
		//2、加载并注册依赖oracle.jdbc.driver.OracleDriver   包路径  + 类名  
		try {
			Class.forName( ReadConfig.getInstance().getProperty("driverClassName"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	// 建立连接
	private Connection getConn() {
		// 3、建立连接     url  uname  pwd   ctrl 1 + 2 -> L
		// jdbc:oracle:thin:@<要访问的数据库服务器的ip地址>:<数据库服务器的端口>:<要访问的数据库名>
		// url 统一资源定位符   jdbc:oracle:thin:@数据库的IP地址:端口号:实例名    127.0.0.1 | localhost:1521:ORCL 
		try {
			// conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:ORCL", "scott", "a");
			conn = DriverManager.getConnection(ReadConfig.getInstance().getProperty("url"), ReadConfig.getInstance());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	// 释放资源
	@SuppressWarnings("unused")
	private void closes(ResultSet rs, PreparedStatement pstmt, Connection conn) {
		//7、关闭资源  先开启的后关闭  后开启的先关闭  结果集 -> 语句块 -> 连接
		if( rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if( pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if( conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 更新操作  insert  update delete
	 * Object ... params  不定参数  类似于数组对象   万物皆对象
	 * @param sql  要执行的更新语句
	 * @param params  要执行的SQL语句中 占位符 ? 对应的 参数列表   一一对应 
	 * @return
	 */
	public int update(String sql, Object ... params) {
		int result = -1;//定义默认的范围值

		try {
			conn = this.getConn();//获取连接
			pstmt = conn.prepareStatement(sql);//预编译对象装载SQL
			// 如何给占位符注入对应的参数   ？ 
			this.setParams(pstmt, params);
			result = pstmt.executeUpdate(); // 执行更新  SQL语句无需传入
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closes(rs, pstmt, conn);
		}
		return result;
	}

	/**
	 * 给预编译语句块中占位符 赋值  设置参数
	 * @param pstmt	预编译对象
	 * @param params 要执行的SQL语句中 占位符？ 所对应的参数  一一对应  n-n 
	 */
	private void setParams(PreparedStatement pstmt, Object ... params) {
		// 判空 说明没有参数给我  也就是执行的SQL语句中没有 占位符？ 
		if( params == null || params.length <= 0) { 
			return;
		}
		// 有参数 则循环参数 给预编译语句块中占位符 赋值
		// 先获取参数的长度  节省资源 提高性能 
		for (int i = 0, len = params.length; i < len; i++) {
			// 不清楚占位符的数据类型  万物皆对象 
			try {
				pstmt.setObject( i + 1, params[i]);// 1 代表 第一个？号  从 1开始 以此类推   % +v+ %
			} catch (SQLException e) {
				e.printStackTrace();
				// 项目可以使用日志来记录 log4j
				System.out.println("第" + (i+1) + "个参数注值失败！");
			} 
		}
	}

	/**
	 * 单条查询 返回一条记录   select * from userinfo where user_name = ? and user_pwd = ?
	 * @param sql  查询SQL  
	 * @param params 查询参数
	 * @return map  一条记录
	 */
	public Map<String, Object> findSingle(String sql, Object ... params){
		Map<String, Object> map = null;
		try {
			conn = this.getConn();//获取连接
			pstmt = conn.prepareStatement(sql);//预编译对象装载SQL
			this.setParams(pstmt, params);//需要给占位符注入参数
			rs = pstmt.executeQuery();//执行更新语句

			//现获取所有的列名
			List<String> columnNames = this.getAllColumnNames(rs);
			if( rs.next()) { //处理结果集
				map = new HashMap<String, Object>();
				//map.put("user_id", rs.getInt("user_id"));
				//map.put("user_name", rs.getString("user_name"));
				//map.put("user_pwd", rs.getString("user_pwd"));
				//如果换了查询的表  还会有这些列吗？  select * from emp; 
				//如何查询表所对应各个列的名称？  请去API 先行查看 rs.getMetaData()

				Object value = null;//列所对应的值
				String type = "";//列所对应的值的类型

				//增强for
				for(String columnName : columnNames) {
					//列对应的类型不确定
					//map.put(columnName, rs.getObject(columnName));
					value = rs.getObject(columnName);
					//判空
					if( null == value) {
						map.put(columnName, value);
						continue;
					}
					type = value.getClass().getName(); //获取类型
					//System.out.println(type); //oracle.sql.BLOB   java.math.BigDecimal
					//判断类型
					//TODO 如果是Blob类型 该怎么办 ?  图片的处理？？? 想办法获取对应的数据类型？
					if("oracle.sql.BLOB".equals(type)) {
						//获取对应类型数据
						Blob blob = rs.getBlob(columnName);
						// 获取二进制的数据 
						// 1.7 自动释放资源
						try( InputStream is = blob.getBinaryStream(); ){
							byte [] bt = new byte[(int)blob.length()];
							is.read( bt );
							map.put(columnName, bt ); // 存入字节数组 
						}catch( IOException e ) {
							e.printStackTrace();
						}
					}else {
						map.put(columnName, value);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closes(rs, pstmt, conn);
		}
		return map;
	}

	/**
	 * DB2.0 获取任意表中所有的列名 
	 * 
	 * 如果切换一张表进行查询  还有这些列吗 ？ 比如 ： select *　from  emp  TODO  实现任意表的查询 
		如何查询表所对应各个列的名称？  
		请去API 先行查看方法               rs.getMetaData()	： 
		getMetaData() 	检索此 ResultSet对象的列的数量，类型和属性
		ResultSetMetaData 可用于获取有关ResultSet对象中列的类型和属性的信息的对象
	 * @param rs  结果集 
	 * @return
	 */
	private List<String> getAllColumnNames( ResultSet rs){
		// 存储表中所有的列名 
		List<String>  list = new ArrayList<String>();

		try {
			// 检索此 ResultSet对象的列的数量，类型和属性
			ResultSetMetaData rsmd = rs.getMetaData();
			// 获取列的数量
			int count = rsmd.getColumnCount();
			// 列的范围  确定 循环
			for (int i = 1 ; i <= count; i++) {
				// 获取对应顺序的列名  默认列名大写  转换成小写   
				list.add(rsmd.getColumnName( i ).toLowerCase() ); // 第一列是1，第二列是2，... 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}


	/**
	 * 事务处理 针对于数据库的多条更新语句超级
	 * @param sqls   多条SQL   要执行的更新语句   可以 insert update delete 
	 * @param params 每一条SQ对应一个参数list  多个list存在params    要执行的SQL语句中的占位符？ 所对应参数的值 
	 * @return
	 * @throws SQLException 
	 */
	public int update(List<String> sqls, List<List<Object>> params) throws SQLException {
		int result = -1;// 定义默认的范围值
		try {
			conn = this.getConn();// 获取连接

			// 将提交方式设置为手动提交   JDBC 默认自动提交 true 
			conn.setAutoCommit( false);
			for(int i = 0, len = sqls.size(); i < len; i ++) {
				pstmt = conn.prepareStatement(sqls.get(i));// 预编译对象装载SQL -> list 
				this.setParams(pstmt, params.get(i).toArray());// 需要给占位符注入参数
				result = pstmt.executeUpdate();// 执行更新语句
			}
			conn.commit();// 事务提交
		} catch (SQLException e) {
			e.printStackTrace();
			conn.rollback();// 事务回滚
		} finally {
			conn.setAutoCommit( true); // 恢复提交方法是 -> 自动提交
			this.closes(rs, pstmt, conn);
		}
		return result;
	}


	/**
	 * 多行查询 返回多条  select * from emp; 
	 * @param sql  查询SQL  
	 * @param params 查询参数
	 * @return list 
	 */
	public List<Map<String, Object>> findMultiple(String sql, Object ... params){
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> map = null;
		try {
			conn = this.getConn();//获取连接
			pstmt = conn.prepareStatement(sql);//预编译对象装载SQL
			this.setParams(pstmt, params);//需要给占位符注入参数
			rs = pstmt.executeQuery();//执行更新语句

			//现获取所有的列名
			List<String> columnNames = this.getAllColumnNames(rs);

			while( rs.next()) { //处理结果集
				map = new HashMap<String, Object>();
				Object value = null;//列所对应的值
				String type = "";//列所对应的值的类型

				// 循环列名集合 获得对应的列名
				for(String columnName : columnNames) {
					//列对应的类型不确定
					//map.put(columnName, rs.getObject(columnName));
					value = rs.getObject(columnName);
					//判空
					if( null == value) {
						map.put(columnName, value);
						continue;
					}
					type = value.getClass().getName(); //获取类型
					//System.out.println(type); //oracle.sql.BLOB   java.math.BigDecimal
					//判断类型
					//TODO 如果是Blob类型 该怎么办 ?  图片的处理？？? 想办法获取对应的数据类型？
					if("oracle.sql.BLOB".equals(type)) {
						//获取对应类型数据
						Blob blob = rs.getBlob(columnName);
						// 1.7 自动释放资源
						try( InputStream is = blob.getBinaryStream(); ){
							byte [] bt = new byte[(int)blob.length()];
							is.read( bt );
							map.put(columnName, bt ); // 存入字节数组 
						}catch( IOException e ) {
							e.printStackTrace();
						}
					}else {
						map.put(columnName, value);
					}
				}
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			this.closes(rs, pstmt, conn);
		}
		return list;
	}

	/**
	 * 获取总记录数的方法 新增方法 
	 * @param sql  select count(*) from emp
	 * @param params
	 * @return 
	 */
	public  int  total(String sql, Object ... params){
		int result = 0;
		try {
			conn = this.getConn();
			pstmt = conn.prepareStatement(sql); 
			//设置参数
			setParams(pstmt, params);
			//执行语句块
			rs = pstmt.executeQuery();

			//处理结果集
			if(rs.next()){
				result = rs.getInt(1);//列索引 第一列 
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			this.closes(rs, pstmt, conn);
		}
		return result;
	}

	// DB3.0 动态SQL语句  查询和更新
	// TODO 动态SQL语句  对应的动态参数  多条件查询的更新或查询   新增的代码
	public int update(String sql, List<Object > params) {
		if( params == null || params.isEmpty() ) { // 参数列表无数据
			return this.update(sql);
		}
		return this.update(sql, params.toArray() );
	}
	// 动态SQL查询   句  对应的动态参数  多条件查询的更新或查询   新增的代码
	public List<Map<String, Object>>  findMultiple (String sql, List<Object> params) {
		if( params == null || params.isEmpty() ) { // 参数列表无数据
			return this.findMultiple(sql);
		}
		return this.findMultiple(sql, params.toArray() );
	}
	
	// 动态SQL查询   句  对应的动态参数  多条件查询的更新或查询   新增的代码
	public <T> List<T>  findMultiple (Class<T> cls, String sql, List<Object> params) {
		if( params == null || params.isEmpty() ) { // 参数列表无数据
			return this.findMultiple(cls, sql, params);
		}
		return this.findMultiple(cls, sql, params.toArray());
	}
	// 新增动态SQL查询总数 
	public int total(String sql, List<Object > params) {
		if( params == null || params.isEmpty() ) { // 参数列表无数据
			return this.total(sql);
		}
		return this.total(sql, params.toArray() );
	}

	/**
	 *  DB3.0基于对象的查询 以对象的方式 将将查询结果返回
	 *   c.newInstance() -> new AdminInfo() 
	 *   m.invoke(obj, value)  激活m方法
	 *   获取当前class实例中所有的方法和属性
	 * @param
	 * @param <T> 限定类型
	 * @param sql  查询SQL 
	 * @param params 注入的参数 
	 * @return
	 */
	public <T> List<T> findMultiple( Class<T> cls ,String sql, Object ... params){
		List<T> list = new ArrayList<T>();
		try {
			conn = this.getConn();//获取连接
			pstmt = conn.prepareStatement(sql);//预编译对象装载SQL
			this.setParams(pstmt, params);//需要给占位符注入参数
			rs = pstmt.executeQuery();//执行更新语句

			//现获取所有的列名
			List<String> columnNames = this.getAllColumnNames(rs);

			T t = null ; // 声明一个对象
			Object value = null;// 列所对应的值
			String typeName = "";// 列所对应的值的类型

			// 同通过反射获取当前类中所有的方法methods
			Method[] methods = cls.getDeclaredMethods();

			while( rs.next()) { //处理结果集

				//  调用无参数 的构造方法  AdminInfo admin = new AdminInfo();
				t = cls.newInstance();

				// 循环列名集合 获得对应的列名
				for(String columnName : columnNames) {
					//列对应的类型不确定

					value = rs.getObject(columnName);
					//判空
					if( null == value) {  // 数据库无数据
						continue;
					}

					// 循环类中所有的方法
					for (Method method : methods) {
						// 是否有对应的 setXXX 方法名  set + columnName -> setAid  method的名字 
						String name = "set" +  columnName;
						// 获取列对应值的类型 
						typeName = value.getClass().getName();
						// System.out.println(typeName); //oracle.sql.BLOB   java.math.BigDecimal
						// 找到对应的方法名 并激活方法 
						if( name.equalsIgnoreCase( method.getName())) {
							// 判断数据类型
							if("java.math.BigDecimal".equals(typeName)) {
								try {
									method.invoke( t, rs.getDouble( columnName ));
								}catch(Exception e) {
									method.invoke( t, rs.getString( columnName ));
								}
							}else if("java.lang.String".equals(typeName)) {
								method.invoke( t, rs.getString( columnName ));
							}else if("java.lang.Double".equals(typeName)) {
								method.invoke( t, rs.getDouble( columnName ));
							}else if("java.lang.Integer".equals(typeName)) {
								method.invoke( t, rs.getInt( columnName ));
							}else if("java.lang.Date".equals(typeName)) {
								// MySQL 中date 数据类型 转换成JavaBean 对象中使用String 
								method.invoke( t, rs.getString( columnName ));
							}else if("oracle.sql.BLOB".equals(typeName)) {  
								// TODO 如果是Blob类型 该怎么办 ?  图片的处理？？? 想办法获取对应的数据类型？
								// 获取对应类型数据
								Blob blob = rs.getBlob(columnName);
								// 获取对应二进制流操作
								try(InputStream is = blob.getBinaryStream()){
									byte [] bt = new byte[ (int)blob.length()];
									is.read(bt);
									method.invoke( t, bt);
								}catch(IOException e) {
									e.printStackTrace();
								}
							}else if("oracle.sql.CLOB".equals(typeName)){
								Reader in = rs.getCharacterStream(columnName);
								BufferedReader br = new BufferedReader( in );
								StringBuffer sb =  new StringBuffer();
								try {
									String str = br.readLine();//每次读取一行数据
									while( null != str ) {
										sb.append( str );
										str = br.readLine();
									}
									method.invoke( t, sb.toString());
								} catch (IOException e) {
									e.printStackTrace();
								}
							}else {
								// TODO 后期需要 自行扩展
							}
						}
					}
				}
				list.add(t);
			}
		} catch ( InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException  | SQLException e) {
			e.printStackTrace();
		}finally {
			this.closes(rs, pstmt, conn);
		}
		return list;
	}

	public <T> T findSingle( Class<T> cls ,String sql, Object ... params){
		
		T t = null ; // 声明一个对象
		
		try {
			conn = this.getConn();//获取连接
			pstmt = conn.prepareStatement(sql);//预编译对象装载SQL
			this.setParams(pstmt, params);//需要给占位符注入参数
			rs = pstmt.executeQuery();//执行更新语句

			//现获取所有的列名
			List<String> columnNames = this.getAllColumnNames(rs);

			Object value = null;// 列所对应的值
			String typeName = "";// 列所对应的值的类型

			// 同通过反射获取当前类中所有的方法methods
			Method[] methods = cls.getDeclaredMethods();

			if( rs.next()) { //处理结果集

				//  调用无参数 的构造方法  AdminInfo admin = new AdminInfo();
				t = cls.newInstance();

				// 循环列名集合 获得对应的列名
				for(String columnName : columnNames) {
					//列对应的类型不确定
					value = rs.getObject(columnName);
					//判空
					if( null == value) {  // 数据库无数据
						continue;
					}
					// 循环类中所有的方法
					for (Method method : methods) {
						// 是否有对应的 setXXX 方法名  set + columnName -> setAid  method的名字 
						String name = "set" +  columnName;
						// 获取列对应值的类型 
						typeName = value.getClass().getName();
						
						// System.out.println(typeName); //oracle.sql.BLOB   java.math.BigDecimal
						// 找到对应的方法名 并激活方法 
						if( name.equalsIgnoreCase( method.getName())) {
							// 判断数据类型
							if("java.math.BigDecimal".equals(typeName)) {
								try {
									method.invoke( t, rs.getDouble( columnName ));
								}catch(Exception e) {
									method.invoke( t, rs.getString( columnName ));
								}
							}else if("java.lang.String".equals(typeName)) {
								method.invoke( t, rs.getString( columnName ));
							}else if("java.lang.Double".equals(typeName)) {
								method.invoke( t, rs.getDouble( columnName ));
							}else if("java.lang.Integer".equals(typeName)) {
								method.invoke( t, rs.getInt( columnName ));
							}else if("java.sql.Date".equals(typeName)) {
								// MySQL 中date 数据类型 转换成JavaBean 对象中使用String 
								method.invoke( t, rs.getString( columnName ));
							}else if("oracle.sql.BLOB".equals(typeName)) {  
								// TODO 如果是Blob类型 该怎么办 ?  图片的处理？？? 想办法获取对应的数据类型？
								// 获取对应类型数据
								Blob blob = rs.getBlob(columnName);
								// 获取对应二进制流操作
								try(InputStream is = blob.getBinaryStream()){
									byte [] bt = new byte[ (int)blob.length()];
									is.read(bt);
									method.invoke( t, bt);
								}catch(IOException e) {
									e.printStackTrace();
								}
							}else if("oracle.sql.CLOB".equals(typeName)){
								Reader in = rs.getCharacterStream(columnName);
								BufferedReader br = new BufferedReader( in );
								StringBuffer sb =  new StringBuffer();
								try {
									String str = br.readLine();//每次读取一行数据
									while( null != str ) {
										sb.append( str );
										str = br.readLine();
									}
									method.invoke( t, sb.toString());
								} catch (IOException e) {
									e.printStackTrace();
								}
							}else {
								// TODO 后期需要 自行扩展
							}
						}
					}
				}
			}
		} catch ( InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException  | SQLException e) {
			e.printStackTrace();
		}finally {
			this.closes(rs, pstmt, conn);
		}
		return t;
	}
}
