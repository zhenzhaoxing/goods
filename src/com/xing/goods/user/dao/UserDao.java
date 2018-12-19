package com.xing.goods.user.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.xing.goods.user.domain.User;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;

/*
 * **
 * 用户模块持久层 与数据库打交道
 * @author qdmmy6
 *
 */
public class UserDao {

	
	private QueryRunner qr = new TxQueryRunner(); //底层可以链接数据库
	
	
	
	
	
	
	/*更改密码1 先查一下是否有就密码 有就返回 true 说明可以更改
	 * */
	
	
	public  boolean findByUidAnWord(String uid,String password) throws SQLException{
		String sql = "select count(*) from t_user where uid=? and loginpass=?";
		  Number number=(Number)qr.query(sql, new ScalarHandler(),uid,password);
		return number.intValue()>0;
	}
	/*更改密码2     根据uid  修改密码
	 * */
	public void updatePassword(String uid,String password) throws SQLException{
   String sql = "update t_user set loginpass=? where uid=?";
     qr.update(sql,password,uid);
		
		
	}
	
	
	
	
	
	
	/*登录的dao层实现 用户名 密码
	 * 
	 * */
	public User findBylonginnameangpassword (String loginname,String password) throws SQLException{
		String sql = "select *from t_user where loginname =? and loginpass=?";
		
		return qr.query(sql, new BeanHandler<User>(User.class),loginname,password);
		
		
	}
	
	
	
	
	/**
	 * 按uid和password查询
	 * @param uid
	 * @param password
	 * @return
	 * @throws SQLException 
	 */
	public boolean findByUidAndPassword(String uid, String password) throws SQLException {
		String sql = "select count(*) from t_user where uid=? and loginpass=?";
		Number number = (Number)qr.query(sql, new ScalarHandler(), uid, password);//获取行
		return number.intValue() > 0;     //
	   //ScalarHandler: 可以返回指定列的一个值或返回一个统计函数的值
	}
	/**
	 * 通过激活码查询用户
	 * @param code
	 * @return
	 * @throws SQLException 
	 */
	public User findByCode(String code) throws SQLException {
		String sql = "select * from t_user where activationCode=?";
		return qr.query(sql, new BeanHandler<User>(User.class), code);
	}
	
	
	

	
	/*
	 * 校验 用户名是否被注册      到数据库查 有fanhi1没有 返回0   首先 得接到名字参数
	 * */
	public boolean ajaxValidateLoginname(String loginname) throws SQLException{
		
		String sql ="select count(1) from  t_user where loginname=?";
		
		
		Number number=(Number) qr.query(sql, new ScalarHandler(),loginname);
		
		return number.intValue()==0;  //==0了说明没有数据 返回true
	}
	
	
	
	/*
	 * 校验邮箱是否被注册      到数据库查 有fanhi1没有 返回0   首先 得接到名字参数
	 * */
	public boolean ajaxValidateEmail(String email) throws SQLException{
		
		String sql ="select count(1) from  t_user where email=?";
		
		
		Number number=(Number) qr.query(sql, new ScalarHandler(),email);
		
		return number.intValue()==0;  //==0了说明没有数据 返回true
	}
	
	

	/*
	 * 这是数据库里添加注册数据
	 * */
	public void add(User user) throws SQLException{
		String sql ="insert into t_user values(?,?,?,?,?,?)";
		
		qr.update(sql, user.getUid(),user.getLoginname(),user.getLoginpass(),user.getEmail(),user.isStatus(),user.getActivationCode());
		
	}
	
	
	/*
	 * 修改用户状态  根据uid 查找用户
	 * 
	 * */
	
	
	public void updateStaus(String uid,boolean status) throws SQLException{
		
		
		
		String sql ="update t_user set status =? where uid =?";
        qr.update(sql, status,uid);//注意这个次序的和 sql 里面一致
		
		
	
	}
	
	
	
}
