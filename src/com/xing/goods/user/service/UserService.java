package com.xing.goods.user.service;

import java.io.IOException;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;

import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.xing.goods.user.dao.UserDao;
import com.xing.goods.user.domain.User;
import com.xing.goods.user.service.UserException.UserException;

import cn.itcast.commons.CommonUtils;
import cn.itcast.mail.Mail;
import cn.itcast.mail.MailUtils;



/*
 *    **
 * 用户模块业务层
 * @author qdmmy6
 *
 */

public class UserService {

	private UserDao userdao = new UserDao();
	
	
	//登录功能
	public User findLoginnameandpassword(User user) throws SQLException{
		//返回给servlet
		return userdao.findBylonginnameangpassword(user.getLoginname(), user.getLoginpass());
	}
	
	/************************修改密码
	 * @throws UserException ******************************/
	
	
	
	public void updatePassword(String uid,String newPass,String oldPass) throws UserException{
		
		
		//校验老密码
		try {
			boolean bool = userdao.findByUidAnWord(uid, oldPass);
		 if(!bool){//表示为false
			throw new UserException("老密码错误");
		}
		
		//否则 更改密码
	userdao.updatePassword(uid, newPass);
		
		
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
	}
	
	/*************************修改密码结束****************************/
	
	
	
	
	
	
	
	
	//查询姓名
public boolean ajaxValidateLoginname(String loginname) throws SQLException{
		return userdao.ajaxValidateLoginname(loginname) ; //==0了说明没有数据 返回true
		
	}
//查询邮箱
 public boolean ajaxValidateEmail(String email) throws SQLException{
	return userdao.ajaxValidateEmail(email) ; //==0了说明没有数据 返回true
	//谁调用 返回这个布尔值
}
 //注册功能

  public void regist(User user) {
	
	//补齐
	user.setUid(CommonUtils.uuid());
	user.setStatus(false);//未激活
	user.setActivationCode(CommonUtils.uuid()+CommonUtils.uuid());
	//到这 3 个加上 和servlet获取的 3 都够了
	
	try {
		userdao.add(user);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	/*
	 * 发邮件
	 * */
	
	
	/**************************这是发邮件的操作*********************************/
	Properties prop = new Properties();
	try {
		prop.load(this.getClass().getClassLoader().getResourceAsStream("email_template.properties"));
	} catch (IOException e1) {
		throw new RuntimeException(e1);
	}
	/*
	 * 登录邮件服务器，得到session
	 */
	String host = prop.getProperty("host");//服务器主机名
	String name = prop.getProperty("username");//登录名
	String pass = prop.getProperty("password");//登录密码
	Session session = MailUtils.createSession(host, name, pass);
	
	/*
	 * 创建Mail对象
	 */
	String from = prop.getProperty("from");
	String to = user.getEmail();
	String subject = prop.getProperty("subject");
	// MessageForm.format方法会把第一个参数中的{0},使用第二个参数来替换。
	// 例如MessageFormat.format("你好{0}, 你{1}!", "张三", "去死吧"); 返回“你好张三，你去死吧！”
	String content = MessageFormat.format(prop.getProperty("content"), user.getActivationCode());
	Mail mail = new Mail(from, to, subject, content);
	/*
	 * 发送邮件
	 */
	try {
		MailUtils.send(session, mail);
	} catch (MessagingException e) {
		throw new RuntimeException(e);
	} catch (IOException e) {
		throw new RuntimeException(e);
	}
}
/**************************以上是发邮件的操作*********************************/

/**
 * 激活功能
 * @param code
 * @throws UserException 
 */


public void activaation(String code) throws UserException {

	/*
	 * 1. 通过激活码查询用户      activationCode
	 * 2. 如果User为null，说明是无效激活码，抛出异常，给出异常信息（无效激活码）
	 * 3. 查看用户状态是否为true，如果为true，抛出异常，给出异常信息（请不要二次激活）
	 * 4. 修改用户状态为true
	 */
	
	try {
		User user = userdao.findByCode(code); //true
		if(user == null) throw new UserException("无效的激活码！");
		if(user.isStatus()) throw new UserException("您已经激活过了，不要二次激活！");
		userdao.updateStaus(user.getUid(), true);//修改状态
	} catch(SQLException e) {
		throw new RuntimeException(e);
	}

}







}
	
	
	
	
	
	
	
	
	
	
