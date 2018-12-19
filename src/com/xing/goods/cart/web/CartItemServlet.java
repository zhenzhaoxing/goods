package com.xing.goods.cart.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.xing.goods.book.domain.Book;
import com.xing.goods.cart.domain.CartItem;
import com.xing.goods.cart.service.CartItemService;

import com.xing.goods.user.domain.User;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
/*
 *    **
 * 用户模块业务层 总部
 * @author qdmmy6
 *
 */
public class CartItemServlet extends BaseServlet {

	
	private CartItemService cartItemservice = new CartItemService();
	/**
	 * 提交订单显示页面功能
	 * @param uid
	 * @return
	 * @throws SQLException 
	 */
	
	
	
	public String loadCartItems(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, SQLException {
		//获取参数
		String cartItemIds = req.getParameter("cartItemIds");
		double total = Double.parseDouble(req.getParameter("total"));
		//调用方法		 
		List<CartItem> cartItemList =cartItemservice.loadCartItems(cartItemIds); 
		req.setAttribute("cartItemList", cartItemList);
		req.setAttribute("total", total);
		req.setAttribute("cartItemIds", cartItemIds);//这句话主要给order显示用
		return "f:/jsps/cart/showitem.jsp";
	}
	
	
	
	
	/**
	 * 添加购物车条目
	 * @throws SQLException 
	 * 
	 * */
	public String add(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, SQLException {
				/*
				 * 1.封装表单到cartItem(bid,quantity) 
				 *    CartItem 里面 只有bid 没有 quantity
				 * */
		Map map = req.getParameterMap();
		CartItem cartItem = CommonUtils.toBean(map, CartItem.class);
		Book book = CommonUtils.toBean(map, Book.class);//获取bid
		User user = (User)req.getSession().getAttribute("sessionUser");
		cartItem.setBook(book);
		cartItem.setUser(user);
		/*
		 * 2.调用service完成添加
		 * */
		cartItemservice.add(cartItem);
		return myCart(req, resp);//交给myCart
		
	}
	
	
	/**
	 * 我的购物车
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	

	public String myCart(HttpServletRequest req, HttpServletResponse resp) throws ServletException, 
	IOException {
	//得到uid
		
		HttpSession session = req.getSession();
		User user =	(User) session.getAttribute("sessionUser");  //方法找 userServlet 为了找uid
		String uid= user.getUid();
		/*
		 * 2. 通过service得到当前用户的所有购物车条目
		 */
		List<CartItem> cartItemLIst = cartItemservice.myCart(uid);
		
		/*
		 * 3. 保存起来，转发到/cart/list.jsp
		 */
		req.setAttribute("cartItemLIst", cartItemLIst);
		return "f:/jsps/cart/list.jsp";
		
		
		
		
		
		
		
	}
	
	/**
	 * 批量删除功能
	 * @param req
	 * @param resp
	 * @return
	 * @throws SQLException 
	 * @throws ServletException
	 * @throws IOException
	 */
	public String batchDelete(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException{
		
		/*
		 * 1. 获取cartItemIds参数
		 * 2. 调用service方法完成工作
		 * 3. 返回到list.jsp
		 */
		String cartItemIds = req.getParameter("cartItemIds");
  //		System.out.println(cartItemIds);
	
		//4CE8A95168754849AA6D0D87B0B2BE1E,19DD2A2C01814318B32F13EB7D593532
		cartItemservice.batchDelete(cartItemIds);
		return myCart(req, resp);
	}
	
	
	
	
	/**
	 * 修改条目功能
	 * @param req
	 * @param resp
	 * @return
	 * @throws SQLException 
	 * @throws ServletException
	 * @throws IOException
	 */
	
	public String updateQuantity(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
			String cartItemId = req.getParameter("cartItemId");
			int quantity = Integer.parseInt(req.getParameter("quantity"));
		//将字符串转换成整数
			CartItem cartItem = cartItemservice.updateQuantity(cartItemId, quantity);
			// 给客户端返回一个json对象 这是异步请求
			StringBuilder sb = new StringBuilder("{");
			sb.append("\"quantity\"").append(":").append(cartItem.getQuantity());
			sb.append(",");
			sb.append("\"subtotal\"").append(":").append(cartItem.getSubtotal());
			sb.append("}");
		
			resp.getWriter().print(sb);
			return null;
		
		
		
		
	}
	
	
}
