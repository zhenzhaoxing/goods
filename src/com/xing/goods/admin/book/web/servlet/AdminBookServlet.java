package com.xing.goods.admin.book.web.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xing.goods.book.domain.Book;
import com.xing.goods.book.service.BookService;
import com.xing.goods.category.domain.Category;
import com.xing.goods.category.service.CategoryService;
import com.xing.goods.pager.PageBean;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;

public class AdminBookServlet extends BaseServlet {
private BookService bookservice = new BookService();
private CategoryService  categoryService = new CategoryService();

/***
 * 
 * 
 * 
 * 
 * 删除功能
 * 
 * 
 * 
 * */
public String delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
	
	String bid = req.getParameter("bid");
	
	
	/*
	 * 删除图片 这是保存的路径
	 * */
	Book book = bookservice.load(bid);
	String savepath = this.getServletContext().getRealPath("/");//获取真实的路径
	new File(savepath, book.getImage_w()).delete();//删除文件
	new File(savepath, book.getImage_b()).delete();//删除文件
	
	bookservice.delete(bid);
	req.setAttribute("msg", "删除成功");
	return "f:/adminjsps/msg.jsp";

}

/**
 * 修改图书
 * @param req
 * @param resp
 * @return
 * @throws ServletException
 * @throws IOException
 * @throws SQLException 
 */
public String edit(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException, SQLException {
			
	/*
	 * 1. 把表单数据封装到Book对象中
	 * 2. 封装cid到Category中
	 * 3. 把Category赋给Book
	 * 4. 调用service完成工作
	 * 5. 保存成功信息，转发到msg.jsp
	 */
	
	Map map = req.getParameterMap();
	Book book = CommonUtils.toBean(map, Book.class);
	
	Category category = CommonUtils.toBean(map, Category.class);
	book.setCategory(category);
	 bookservice.edit(book);
	 
	req.setAttribute("msg", "修改成功");
	return "f:/adminjsps/msg.jsp";
}













/**
 * 加载图书 第一步
 * @param req
 * @param resp
 * @return
 * @throws ServletException
 * @throws IOException
 * @throws SQLException 
 */

	public String loads(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
		
		String bid = req.getParameter("bid");
		Book book = bookservice.load(bid);
		/**
		 *          Category parent = new Category();
					parent.setCid((String)map.get("pid"));
					category.setParent(parent);
		 *   
		 * 
		 * */
		
		/*
		 * 2. 获取所有一级分类，保存之
		 */
		req.setAttribute("parents", categoryService.findParents());
		/*
		 * 3. 获取当前图书所属的一级分类下所有2级分类
		 */
		String pid = book.getCategory().getParent().getCid();
		req.setAttribute("children", categoryService.findChildren(pid));
		
		/*
		 * 4. 转发到desc.jsp显示
		 */
		return "f:/adminjsps/admin/book/desc.jsp";
		
	}













/**
 * 添加图书 第一步
 * @param req
 * @param resp
 * @return
 * @throws ServletException
 * @throws IOException
 */
public String addPre(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
			
	List<Category> parents = categoryService.findParents();
	req.setAttribute("parents", parents);
	return "f:/adminjsps/admin/book/add.jsp";
	
}
/**
 * @throws SQLException 
 * 
 * 返回的是list 但是js中没有list 必须用json返回转变一下
 * 
 * 
 * 
 * 
 * 
 * */
//这是异步请求   为随着一级分类改变而改变 这是局部刷新
public String ajaxFindChildren(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException, SQLException {
	/*
	 * 1. 获取pid
	 * 2. 通过pid查询出所有2级分类
	 * 3. 把List<Category>转换成json，输出给客户端
	 */
	String pid = req.getParameter("pid");//异步传的其实是一级的cid
	List<Category> children = categoryService.findChildren(pid);
	String json= toJson(children);
	  resp.getWriter().print(json);
	return null;
}



//转变单个的// {"cid":"fdsafdsa", "cname":"fdsafdas"}
private String toJson(Category category){
	StringBuilder sb = new StringBuilder("{");
	
	sb.append("\"cid\"").append(":").append("\"").append(category.getCid()).append("\"");
	sb.append(",");
	sb.append("\"cname\"").append(":").append("\"").append(category.getCname()).append("\"");
	sb.append("}");
	
	
	return sb.toString();
	
}

//转变多个的
//[{"cid":"fdsafdsa", "cname":"fdsafdas"}, {"cid":"fdsafdsa", "cname":"fdsafdas"}]
private String toJson(List<Category> categorylist){
	
	StringBuilder sb = new StringBuilder("[");
	for(int i=0;i<categorylist.size();i++){
		sb.append(toJson(categorylist.get(i)));
		if(i<categorylist.size()-1){
			sb.append(",");
		}
	}
	sb.append("]");
	return sb.toString();
	
}


































/**
 * 显示所有分类
 * @param req
 * @param resp
 * @return
 * @throws ServletException
 * @throws IOException
 */
public String findCategoryAll(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
	/*
	 * 1. 通过service得到所有的分类
	 * 2. 保存到request中，转发到left.jsp
	 */
	List<Category> parents = categoryService.findAll();
	req.setAttribute("parents", parents);
	return "f:/adminjsps/admin/book/left.jsp";
}

/**
 * 获取当前页码
 * @param req
 * @return
 */
private int getPc(HttpServletRequest req) {
	int pc = 1;
	String param = req.getParameter("pc");
	if(param != null && !param.trim().isEmpty()) {
		try {
			pc = Integer.parseInt(param);
		} catch(RuntimeException e) {}
	}
	return pc;
}

/**
 * 截取url，页面中的分页导航中需要使用它做为超链接的目标！
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
	if(index != -1) {
		url = url.substring(0, index);
	}
	return url;
}

/**
 * 按bid查询
 * @param req
 * @param resp
 * @return
 * @throws ServletException
 * @throws IOException
 */
public String load(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
	String bid = req.getParameter("bid");//获取链接的参数bid
	Book book = bookservice.load(bid);//通过bid得到book对象
	req.setAttribute("book", book);//保存到req中
	return "f:/adminjsps/admin/book/desc.jsp";//转发到desc.jsp
}

/**
 * 按分类查
 * @param req
 * @param resp
 * @return
 * @throws ServletException
 * @throws IOException
 * @throws SQLException 
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
	 * 3. 获取查询条件，本方法就是cid，即分类的id
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
	return "f:/adminjsps/admin/book/list.jsp";
}

/**
 * 按作者查
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
	return "f:/adminjsps/admin/book/list.jsp";
}

/**
 * 按出版社查询
 * @param req
 * @param resp
 * @return
 * @throws ServletException
 * @throws IOException
 */
public String findByPress(HttpServletRequest req, HttpServletResponse resp)
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
	return "f:/adminjsps/admin/book/list.jsp";
}

/**
 * 按图名查
 * @param req
 * @param resp
 * @return
 * @throws ServletException
 * @throws IOException
 */
public String findByBname(HttpServletRequest req, HttpServletResponse resp)
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
	return "f:/adminjsps/admin/book/list.jsp";
}

/**
 * 多条件组合查询
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
	Book criteria = CommonUtils.toBean(req.getParameterMap(), Book.class);
	/*
	 * 4. 使用pc和cid调用service#findByCategory得到PageBean
	 */
	PageBean<Book> pb = bookservice.findByCombination(criteria, pc);
	/*
	 * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
	 */
	pb.setUrl(url);
	req.setAttribute("pb", pb);
	return "f:/adminjsps/admin/book/list.jsp";
}
}
