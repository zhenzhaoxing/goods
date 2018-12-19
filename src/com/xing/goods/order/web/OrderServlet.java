package com.xing.goods.order.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xing.goods.cart.domain.CartItem;
import com.xing.goods.cart.service.CartItemService;
import com.xing.goods.order.domain.Order;
import com.xing.goods.order.domain.OrderItem;
import com.xing.goods.order.service.OrderService;
import com.xing.goods.pager.PageBean;
import com.xing.goods.user.domain.User;

import cn.itcast.commons.CommonUtils;
/**
 *  web 总部
 * **/
import cn.itcast.servlet.BaseServlet;

public class OrderServlet extends BaseServlet {
	OrderService orderService = new OrderService();
	private CartItemService cartItemservice = new CartItemService();

	/**
	 * 支付准备
	 * 
	 * @param req
	 * @return
	 * @throws SQLException
	 */
	public String paymentPre(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, SQLException {
		String oid = req.getParameter("oid");
		req.setAttribute("order", orderService.load(oid));
		return "f:/jsps/order/pay.jsp";
		// 显示 只有 订单号 还有 价钱
	}

	/***************************** 支付方法 ************************************/
	/**
	 * 支付方法
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */

	public String payment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 配置文件
		Properties props = new Properties();
		props.load(this.getClass().getClassLoader().getResourceAsStream("payment.properties"));

		/*
		 * 1. 准备13个参数
		 */

		String p0_Cmd = "Buy";// 业务类型
		String p1_MerId = props.getProperty("p1_MerId");// 商号编码 易宝唯一标识
		String p2_Order = req.getParameter("oid");// 订单编号
		String p3_Amt = req.getParameter("total");// 支付金额
		// String p3_Amt="0.01";//支付金额
		String p4_Cur = "CNY";// 交易币种
		String p5_Pid = "";// 商品名称
		String p6_Pcat = "";// 商品种类
		String p7_Pdesc = "";// 商品描述
		String p8_Url = props.getProperty("p8_Url");
		;// 在支付成功后 ，易宝会访问这个地址
		String p9_SAF = "";// 送货地址
		String pa_MP = "";// 扩展信息
		String pd_FrpId = req.getParameter("yh");// 获取要连接的银行
		String pr_NeedResponse = "1";// 应答机制，固定值为1
		/*
		 * 2. 计算hmac 需要13个参数 需要keyValue 需要加密算法 需要工具类
		 */

		String keyValue = props.getProperty("keyValue");

		String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc,
				p8_Url, p9_SAF, pa_MP, pd_FrpId, pr_NeedResponse, keyValue);

		/*
		 * 3. 重定向到易宝的支付网关
		 */
		StringBuilder sb = new StringBuilder("https://www.yeepay.com/app-merchant-proxy/node");
		sb.append("?").append("p0_Cmd=").append(p0_Cmd);
		sb.append("&").append("p1_MerId=").append(p1_MerId);
		sb.append("&").append("p2_Order=").append(p2_Order);
		sb.append("&").append("p3_Amt=").append(p3_Amt);
		sb.append("&").append("p4_Cur=").append(p4_Cur);
		sb.append("&").append("p5_Pid=").append(p5_Pid);
		sb.append("&").append("p6_Pcat=").append(p6_Pcat);
		sb.append("&").append("p7_Pdesc=").append(p7_Pdesc);
		sb.append("&").append("p8_Url=").append(p8_Url);
		sb.append("&").append("p9_SAF=").append(p9_SAF);
		sb.append("&").append("pa_MP=").append(pa_MP);
		sb.append("&").append("pd_FrpId=").append(pd_FrpId);
		sb.append("&").append("pr_NeedResponse=").append(pr_NeedResponse);
		sb.append("&").append("hmac=").append(hmac);

		resp.sendRedirect(sb.toString());
		return null;
	}

	/**
	 * 回馈方法 当支付成功时，易宝会访问这里 用两种方法访问： 1. 引导用户的浏览器重定向(如果用户关闭了浏览器，就不能访问这里了) 2.
	 * 易宝的服务器会使用点对点通讯的方法访问这个方法。（必须回馈success，不然易宝服务器会一直调用这个方法）
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @throws SQLException
	 */

	public String back(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, SQLException {

		/**
		 * 1.获取12个参数
		 * 
		 */
		String p1_MerId = req.getParameter("p1_MerId");
		String r0_Cmd = req.getParameter("r0_Cmd");
		String r1_Code = req.getParameter("r1_Code");
		String r2_TrxId = req.getParameter("r2_TrxId");
		String r3_Amt = req.getParameter("r3_Amt");
		String r4_Cur = req.getParameter("r4_Cur");
		String r5_Pid = req.getParameter("r5_Pid");
		String r6_Order = req.getParameter("r6_Order");
		String r7_Uid = req.getParameter("r7_Uid");
		String r8_MP = req.getParameter("r8_MP");
		String r9_BType = req.getParameter("r9_BType");
		String hmac = req.getParameter("hmac");
		/*
		 * 2. 获取keyValue
		 */
		Properties props = new Properties();
		props.load(this.getClass().getClassLoader().getResourceAsStream("payment.properties"));
		String keyValue = props.getProperty("keyValue");
		/*
		 * 3. 调用PaymentUtil的校验方法来校验调用者的身份 >如果校验失败：保存错误信息，转发到msg.jsp >如果校验通过： *
		 * 判断访问的方法是重定向还是点对点，如果要是重定向 修改订单状态，保存成功信息，转发到msg.jsp * 如果是点对点：修改订单状态，返回success
		 */
		// 支付网发来的 hmac 也是 上一步用户发的
		boolean bool = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd, r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid,
				r6_Order, r7_Uid, r8_MP, r9_BType, keyValue);
		if (!bool) { // 这个方法判断是否相等 不相等 就无效
			req.setAttribute("code", "error");
			req.setAttribute("msg", "无效的签名，支付失败！（你不是好人）");
			return "f:/jsps/msg.jsp";
		}

		if (r1_Code.equals("1")) {
			orderService.updateStatus(r6_Order, 2);
			if (r9_BType.equals("1")) {
				req.setAttribute("code", "success");
				req.setAttribute("msg", "恭喜，支付成功！");
				return "f:/jsps/msg.jsp";
			} else if (r9_BType.equals("2")) {// 回馈
				resp.getWriter().print("success");
			}
		}
		return null;

	}

	/***************************** 支付方法 ************************************/

	/**
	 * 取消订单
	 * 
	 * @param req
	 * @return
	 * @throws SQLException
	 */
	public String cancel(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, SQLException {
		String oid = req.getParameter("oid");

		int status = orderService.findStatus(oid);
		if (status != 1) {
			req.setAttribute("code", "error");
			req.setAttribute("msg", "状态不对，不能取消");
			return "f:/jsps/msg.jsp";
		}
		orderService.updateStatus(oid, 5);// 设置状态为取消！
		req.setAttribute("code", "success");
		req.setAttribute("msg", "您的订单已取消，您不后悔吗！");
		return "f:/jsps/msg.jsp";

	}

	/**
	 * 确认收货
	 * 
	 * @param req
	 * @return
	 * @throws SQLException
	 */
	public String confirm(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, SQLException {
		String oid = req.getParameter("oid");

		int status = orderService.findStatus(oid);
		if (status != 3) {
			req.setAttribute("code", "error");
			req.setAttribute("msg", "状态不对，不能确认收货");
			return "f:/jsps/msg.jsp";
		}
		orderService.updateStatus(oid, 5);// 设置状态为取消！
		req.setAttribute("code", "success");
		req.setAttribute("msg", "恭喜交易成功！");
		return "f:/jsps/msg.jsp";

	}

	/**
	 * 加载订单
	 * 
	 * @param req
	 * @return
	 * @throws SQLException
	 */
	public String load(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, SQLException {
		String oid = req.getParameter("oid");

		Order order = orderService.load(oid);

		req.setAttribute("order", order);
		String btn = req.getParameter("btn");// btn说明了用户点击哪个超链接来访问本方法的
		req.setAttribute("btn", btn);
		return "/jsps/order/desc.jsp";

	}

	/**
	 * 获取当前页码
	 * 
	 * @param req
	 * @return
	 */
	private int getPc(HttpServletRequest req) {
		int pc = 1;
		String param = req.getParameter("pc");
		if (param != null && !param.trim().isEmpty()) {
			try {
				pc = Integer.parseInt(param);
			} catch (RuntimeException e) {
			}
		}
		return pc;
	}

	/**
	 * 截取url，页面中的分页导航中需要使用它做为超链接的目标！
	 * 
	 * @param req
	 * @return
	 */
	/*
	 * http://localhost:8080/goods/BookServlet?methed=findByCategory&cid=xxx&pc=3
	 * /goods/BookServlet + methed=findByCategory&cid=xxx&pc=3
	 */
	private String getUrl(HttpServletRequest req) {
		String url = req.getRequestURI() + "?" + req.getQueryString();
		/*
		 * 如果url中存在pc参数，截取掉，如果不存在那就不用截取。
		 */
		int index = url.lastIndexOf("&pc=");
		if (index != -1) {
			url = url.substring(0, index);
		}
		return url;
	}

	/**
	 * 我的订单
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String myOrders(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*
		 * 1. 得到pc：如果页面传递，使用页面的，如果没传，pc=1
		 */
		int pc = getPc(req);
		/*
		 * 2. 得到url：...
		 */
		String url = getUrl(req);
		/*
		 * 3. 从当前session中获取User
		 */
		User user = (User) req.getSession().getAttribute("sessionUser");

		/*
		 * 4. 使用pc和uid调用service#findByCategory得到PageBean
		 */
		PageBean<Order> pb = orderService.myOrders(user.getUid(), pc);
		/*
		 * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb", pb);

		return "f:/jsps/order/list.jsp";
	}

	/**
	 * 生成订单
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @throws SQLException
	 */
	public String createOrder(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, SQLException {
		/*
		 * 1. 获取所有购物车条目的id，查询之
		 */
		String cartItemIds = req.getParameter("cartItemIds");
		List<CartItem> cartItemList = cartItemservice.loadCartItems(cartItemIds);
		/*
		 * //这个cartItemList 是含有图书信息的 加载图书条目 2. 创建Order
		 */
		Order order = new Order();
		order.setOid(CommonUtils.uuid());// 设置主键
		order.setOrdertime(String.format("%tF %<tT", new Date()));// 下单时间
		order.setStatus(1);// 设置状态，1表示未付款
		order.setAddress(req.getParameter("address"));// 设置收货地址

		User owner = (User) req.getSession().getAttribute("sessionUser");
		order.setOwner(owner);// 设置订单所有者

		BigDecimal total = new BigDecimal("0");
		for (CartItem cartItem : cartItemList) {
			total = total.add(new BigDecimal(cartItem.getSubtotal() + ""));
		}
		order.setTotal(total.doubleValue());// 设置总计

		/*
		 * 3. 创建List<OrderItem> 一个CartItem对应一个OrderItem
		 */
		List<OrderItem> orderItemList = new ArrayList<OrderItem>();
		for (CartItem cartItem : cartItemList) {// 是购物车条目
			OrderItem orderItem = new OrderItem();// this is order items
			orderItem.setOrderItemId(CommonUtils.uuid());// 设置主键
			orderItem.setQuantity(cartItem.getQuantity());
			orderItem.setSubtotal(cartItem.getSubtotal());
			orderItem.setBook(cartItem.getBook());
			orderItem.setOrder(order);
			orderItemList.add(orderItem);
		}
		order.setOrderItemList(orderItemList);
		/*
		 * 4. 调用service完成添加
		 */
		orderService.createOrder(order);
		// 删除购物车条目
		cartItemservice.batchDelete(cartItemIds);
		/*
		 * 5. 保存订单，转发到ordersucc.jsp
		 */
		req.setAttribute("order", order);
		return "f:/jsps/order/ordersucc.jsp";
	}
}
