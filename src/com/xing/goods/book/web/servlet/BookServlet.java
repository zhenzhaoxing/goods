package com.xing.goods.book.web.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xing.goods.book.domain.Book;
import com.xing.goods.book.service.BookService;
import com.xing.goods.pager.PageBean;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;

/*
 * web层 总控制层 总部
 * */
public class BookServlet extends BaseServlet {

	private static final Book book = null;
	private BookService bookservice = new BookService();

	/**
	 * 按bid查询加载图书
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String load(HttpServletRequest req, HttpServletResponse resp) {
		String bid = req.getParameter("bid");// 获取连接的参数
		Book book = bookservice.load(bid);// 通过bid得到book对象

		req.setAttribute("book", book);// 保存到req中

		return "f:/jsps/book/desc.jsp";

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
		String url = req.getRequestURI() + "?" + req.getQueryString();// methed=findByCategory&cid=xxx&pc=3
		/*
		 * 如果url中存在pc参数，截取掉，如果不存在那就不用截取。
		 */
                  
		
		/*
		 * 
		 * System.out.println(req.getRequestURL());
	      	System.out.println(req.getRequestURI());
		   System.out.println(url);
		 * http://localhost:8080/goods/BookServlet
            /goods/BookServlet
            /goods/BookServlet?method=findByCategory&cid=5F79D0D246AD4216AC04E9C5FAB3199E&pc=2
		 * */
		
		int index = url.lastIndexOf("&pc=");
		if (index != -1) {// 说明有值
			url = url.substring(0, index);

		}
		return url;// 获取请求路径
	}

	/*
	 * 按分类查
	 */
	public String findByCategory(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, SQLException {
		/*
		 * 1. 得到pc：如果页面传递，使用页面的，如果没传，pc=1
		 */
		int pc = getPc(req);

		/*
		 * 2. 得到url：...
		 */
		String url = getUrl(req);
		   
		/*
		 * 3. 获取查询条件，本方法就是cid，即分类的id 2级的分类 根据一个cid可以查到更过的数据
		 */
		String cid = req.getParameter("cid");
		/*
		 * 4. 使用pc和cid调用service#findByCategory得到PageBean
		 */
		PageBean<Book> pb = bookservice.findByCategory(cid, pc);
		/*
		 * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
		 */

		pb.setUrl(url);
		req.setAttribute("pb", pb);

		return "f:/jsps/book/list.jsp";
	}

	/**
	 * 按作者查
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @throws SQLException
	 */
	public String findByAuthor(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, SQLException {
		/*
		 * 1. 得到pc：如果页面传递，使用页面的，如果没传，pc=1
		 */
		int pc = getPc(req);
		/*
		 * 2. 得到url：...
		 */
		String url = getUrl(req);
		/*
		 * 3. 获取查询条件，本方法就是cid，即分类的id
		 */
		String author = req.getParameter("author");
		/*
		 * 4. 使用pc和cid调用service#findByCategory得到PageBean
		 */
		PageBean<Book> pb = bookservice.findByauthor(author, pc);
		/*
		 * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb", pb);
		return "f:/jsps/book/list.jsp";
	}

	/**
	 * 按出版社查询
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByPress(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*
		 * 1. 得到pc：如果页面传递，使用页面的，如果没传，pc=1
		 */
		int pc = getPc(req);
		/*
		 * 2. 得到url：...
		 */
		String url = getUrl(req);
		/*
		 * 3. 获取查询条件，本方法就是cid，即分类的id
		 */
		String press = req.getParameter("press");
		/*
		 * 4. 使用pc和cid调用service#findByCategory得到PageBean
		 */
		PageBean<Book> pb = bookservice.findByPress(press, pc);
		/*
		 * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb", pb);
		return "f:/jsps/book/list.jsp";
	}

	/**
	 * 按书名查
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */

	public String findByBname(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, SQLException {
		/*
		 * 1. 得到pc：如果页面传递，使用页面的，如果没传，pc=1
		 */
		int pc = getPc(req);
		/*
		 * 2. 得到url：...
		 */
		String url = getUrl(req);
		/*
		 * 3. 获取查询条件，本方法就是cid，即分类的id 2级的分类 根据一个cid可以查到更过的数据
		 */
		String bname = req.getParameter("bname");
		/*
		 * 4. 使用pc和cid调用service#findByCategory得到PageBean
		 */
		PageBean<Book> pb = bookservice.findByBname(bname, pc);
		/*
		 * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
		 */

		pb.setUrl(url);
		req.setAttribute("pb", pb);

		return "f:/jsps/book/list.jsp";

	}

	/**
	 * 多条件组合查询
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */

	public String findByCombination(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		/*
		 * 1. 得到pc：如果页面传递，使用页面的，如果没传，pc=1
		 */
		int pc = getPc(req);
		/*
		 * 2. 得到url：...
		 */

		String url = getUrl(req);
		/*
		 * 3. 获取查询条件，本方法就是cid，即分类的id
		 */
		Book book = CommonUtils.toBean(req.getParameterMap(), Book.class);
		/*
		 * 4. 使用pc和cid调用service#findByCategory得到PageBean
		 */

		PageBean<Book> pb = bookservice.findByCombination(book, pc);

		pb.setUrl(url);
		req.setAttribute("pb", pb);

		return "f:/jsps/book/list.jsp";

	}

}
