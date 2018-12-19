package com.xing.goods.user.web.servlet;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.xing.goods.user.domain.User;
/**
 * 用户模块WEB层
 * @author qdmmy6
 *
 */
import com.xing.goods.user.service.UserService;
import com.xing.goods.user.service.UserException.UserException;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;

public class UserServlet extends BaseServlet {

	private UserService useservice = new UserService();
	
	
	//退出
	public String quit(HttpServletRequest request, HttpServletResponse responst) throws ServletException, IOException, SQLException {
		request.getSession().invalidate();
	
		return "f:jsps/user/login.jsp";
		
	}
	
	/************更改密码***************/
	
	public String updatePass(HttpServletRequest request, HttpServletResponse responst) throws ServletException, IOException, SQLException {
		/*
		 * 1. 封装表单数据到user中
		 * 2. 从session中获取uid
		 * 3. 使用uid和表单中的oldPass和newPass来调用service方法
		 *   > 如果出现异常，保存异常信息到request中，转发到pwd.jsp
		 * 4. 保存成功信息到rquest中
		 * 5. 转发到msg.jsp
		 */
		//吧一个map 封装转换成 bean user类
		User formUser=CommonUtils.toBean(request.getParameterMap(), User.class);
		
		 //* 2. 从session中获取uid 这个域在登录servlst方法里有了 
		User user = (User)request.getSession().getAttribute("sessionUser");
		// 如果用户没有登录，返回到登录页面，显示错误信息
				if(user == null) {
					request.setAttribute("msg", "您还没有登录！");
					return "f:/jsps/user/login.jsp";
				}
		try {
			useservice.updatePassword(user.getUid(), formUser.getNewpass(),formUser.getLoginpass());
	request.setAttribute("msg", "修改密码成功");
	request.setAttribute("code", "success");
	return "f:/jsps/msg.jsp";
		
		
		} catch (UserException e) {
		//如果发生异常
			request.setAttribute("msg", e.getMessage());//保存异常信息到request
			request.setAttribute("user", formUser);//为了回显
			return "f:/jsps/user/pwd.jsp";
			
		}
	}
	
	
	
	/*************更改密码************/
	
	
	
	
	
	  /*校验用户名
	   * */
public String ajaxValidateLoginname(HttpServletRequest request, HttpServletResponse responst) throws ServletException, IOException, SQLException {
		      
		String loginname = request.getParameter("loginname");
	    boolean b =  useservice.ajaxValidateLoginname(loginname);
		responst.getWriter().print(b);//这个专门 print 返回布尔值
		
		return null;//表示不转发也不重定向
	}

    /*校验eamil邮箱
      * */
    public String ajaxValidateEmail(HttpServletRequest request, HttpServletResponse responst) throws ServletException, IOException {
    
	String email = request.getParameter("email");

           try {
			boolean b= useservice.ajaxValidateEmail(email);
			responst.getWriter().print(b);
           } catch (SQLException e) {
		}
	return null;
}
    /*校验验证码  这个不用访问数据库
    * */
    public String ajaxValidateCodefi(HttpServletRequest request, HttpServletResponse responst) throws ServletException, IOException {
    //获取输入框中的验证码
	String verifyCode = request.getParameter("verifyCode");
	//获取图片上的码
	String code =(String) request.getSession().getAttribute("vCode");
	//比较
	boolean b = verifyCode.equalsIgnoreCase(code);
	responst.getWriter().print(b);
	return null;
}	
	
    
    
	
	     //调用 service dao注册页面 注册功能
	public String regist(HttpServletRequest request, HttpServletResponse responst) throws ServletException, IOException, SQLException {
	
		      
		
		/*
		 * 1. 封装表单数据到User对象
		 */
		 User formUser= CommonUtils.toBean(request.getParameterMap(), User.class);
		 //封装到一个map中
		/*
		 * 2. 校验之, 如果校验失败，保存错误信息，返回到regist.jsp显示
		 */
			Map<String,String> errors = validateRegist(formUser, request.getSession());
			
		
			if(errors.size()>0){
				request.setAttribute("form", "formUser");
			    request.setAttribute("errors", errors);
			    return "f:/jsps/user/regist.jsp";
			
			}
		
		/*
		 * 3. 使用service完成业务
		 */
		useservice.regist(formUser);
		/*
		 * 4. 保存成功信息，转发到msg.jsp显示！
		 */
		
		request.setAttribute("code", "success");//保存信息
		request.setAttribute("msg", "注册功能，请马上到邮箱激活");
		
	
		return "f:/jsps/msg.jsp";

		
	}

	
	/**
	 * 激活功能
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @throws UserException 
	 */
	public String activation(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		 //点击那个链接就跳转到这里
		/*
		 * 1. 获取参数激活码
		 * 2. 用激活码调用service方法完成激活
		 *   > service方法有可能抛出异常, 把异常信息拿来，保存到request中，转发到msg.jsp显示
		 * 3. 保存成功信息到request，转发到msg.jsp显示。
		 */
		
		String cod = req.getParameter("activationCode");
		try {
			useservice.activaation(cod);
		    req.setAttribute("code", "success");
		req.setAttribute("msg", "恭喜你 注册成功");
		} catch (UserException e) {
			// 说明service抛出了异常
						req.setAttribute("msg", e.getMessage());
						req.setAttribute("code", "error");//通知msg.jsp显示X
		}
		
		
		
		return "f:/jsps/msg.jsp";
	
	
	
	}
	/**
	 * 登录功能
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @throws SQLException 
	 */
	public String logins(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		/*
		 * 1. 封装表单数据到User
		 * 2. 校验表单数据
		 * 3. 使用service查询，得到User
		 * 4. 查看用户是否存在，如果不存在：
		 *   * 保存错误信息：用户名或密码错误
		 *   * 保存用户数据：为了回显
		 *   * 转发到login.jsp
		 * 5. 如果存在，查看状态，如果状态为false：
		 *   * 保存错误信息：您没有激活
		 *   * 保存表单数据：为了回显
		 *   * 转发到login.jsp
		 * 6. 登录成功：
		 * 　　* 保存当前查询出的user到session中
		 *   * 保存当前用户的名称到cookie中，注意中文需要编码处理。
		 */
		/*
		 * 1. 封装表单数据到user
		 */

		User formUser = CommonUtils.toBean(request.getParameterMap(), User.class);
		/*
		 * 2. 校验
		 */
		Map<String,String> errors = validateLogin(formUser, request.getSession());
		if(errors.size() > 0) {
			request.setAttribute("form", formUser);
			request.setAttribute("errors", errors);
			return "f:/jsps/user/login.jsp";
		}
		
		/*
		 * 3. 调用userService#login()方法
		 */
		User user = useservice.findLoginnameandpassword(formUser);
		/*
		 * 4. 开始判断
		 */
		if(user == null) {
			request.setAttribute("msg", "用户名或者密码错误！");
			request.setAttribute("user", formUser);
			return "f:/jsps/user/login.jsp";
		} else {
			if(!user.isStatus()) {
				request.setAttribute("msg", "您还没有激活！");
				request.setAttribute("user", formUser);
				return "f:/jsps/user/login.jsp";				
			} else {
				// 保存用户到session
				request.getSession().setAttribute("sessionUser", user);
				// 获取用户名保存到cookie中
				String loginname = user.getLoginname();
				loginname = URLEncoder.encode(loginname, "utf-8");
				Cookie cookie = new Cookie("loginname", loginname);
				cookie.setMaxAge(60 * 60 * 24 * 10);//保存10天
				response.addCookie(cookie);//把这添加到客户端
				return "r:/jsps/main.jsp";//重定向到主页
			}
		}
	}

	
	
	
	
	
	
	
	
	//登录验证
	
	private Map<String,String> validateLogin(User formUser, HttpSession session) throws SQLException {
		Map<String,String> errors = new HashMap<String,String>();
		/*
		 * 1. 校验登录名
		 */
		String loginname = formUser.getLoginname();
	
		if(loginname == null || loginname.trim().isEmpty()) {
			errors.put("loginname", "用户名不能为空！");
		} else if(loginname.length() < 3 || loginname.length() > 20) {
			errors.put("loginname", "用户名长度必须在3~20之间！");
		} 
		
		/*
		 * 2. 校验登录密码
		 */
		String loginpass =  formUser.getLoginpass();
		if(loginpass == null || loginpass.trim().isEmpty()) {
			errors.put("loginpass", "密码不能为空！");
		} else if(loginpass.length() < 3 || loginpass.length() > 20) {
			errors.put("loginpass", "密码长度必须在3~20之间！");
		}

		String verifyCode = formUser.getVerifyCode();
		String vcode = (String) session.getAttribute("vCode");
		if(verifyCode == null || verifyCode.trim().isEmpty()) {
			errors.put("verifyCode", "验证码不能为空！");
		} else if(!verifyCode.equalsIgnoreCase(vcode)) {
			errors.put("verifyCode", "验证码错误！");
		}
		
		return errors;
	}


	private Map<String,String> validateRegist(User formUser, HttpSession session) throws SQLException {
		Map<String,String> errors = new HashMap<String,String>();
		/*
		 * 1. 校验登录名
		 */
		String loginname = formUser.getLoginname();
	
		if(loginname == null || loginname.trim().isEmpty()) {
			errors.put("loginname", "用户名不能为空！");
		} else if(loginname.length() < 3 || loginname.length() > 20) {
			errors.put("loginname", "用户名长度必须在3~20之间！");
		} else if(!useservice.ajaxValidateLoginname(loginname)) {
			errors.put("loginname", "用户名已被注册！");
		}
		
		/*
		 * 2. 校验登录密码
		 */
		String loginpass =  formUser.getLoginpass();
		if(loginpass == null || loginpass.trim().isEmpty()) {
			errors.put("loginpass", "密码不能为空！");
		} else if(loginpass.length() < 3 || loginpass.length() > 20) {
			errors.put("loginpass", "密码长度必须在3~20之间！");
		}
		
		/*
		 * 3. 确认密码校验
		 */
		String reloginpass = formUser.getReloginpass();
		if(reloginpass == null || reloginpass.trim().isEmpty()) {
			errors.put("reloginpass", "确认密码不能为空！");
		} else if(!reloginpass.equals(loginpass)) {
			errors.put("reloginpass", "两次输入不一致！");
		}
		
		/*
		 * 4. 校验email
		 */
		String email = formUser.getEmail();
		if(email == null || email.trim().isEmpty()) {
			errors.put("email", "Email不能为空！");
		} else if(!email.matches("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$")) {
			errors.put("email", "Email格式错误！");
		} else if(!useservice.ajaxValidateEmail(email)) {
			errors.put("email", "Email已被注册！");
		}
		
		/*
		 * 5. 验证码校验
		 */
		String verifyCode = formUser.getVerifyCode();
		String vcode = (String) session.getAttribute("vCode");
		if(verifyCode == null || verifyCode.trim().isEmpty()) {
			errors.put("verifyCode", "验证码不能为空！");
		} else if(!verifyCode.equalsIgnoreCase(vcode)) {
			errors.put("verifyCode", "验证码错误！");
		}
	
		return errors;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
