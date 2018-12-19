package com.xing.goods.admin.category.web.Servlet;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xing.goods.book.service.BookService;
import com.xing.goods.category.domain.Category;
import com.xing.goods.category.service.CategoryService;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;

public class AdminCategoryServlet extends BaseServlet {
	private CategoryService categoryService = new CategoryService();
	private BookService bookService = new BookService(); 
	/**
	 * 查询所有分类
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAll(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		List<Category> parets = categoryService.findAll();
		
		
		req.setAttribute("parets", parets);
		      
		 return "f:/adminjsps/admin/category/list.jsp";
	}
	/**
	 * 添加一级分类
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @throws SQLException 
	 */
	public String addParent(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, SQLException {
				
		/*
		 * 1. 封装表单数据到Category中
		 * 2. 调用service的add()方法完成添加
		 * 3. 调用findAll()，返回list.jsp显示所有分类
		 */
		Category pare = CommonUtils.toBean(req.getParameterMap(), Category.class);
		pare.setCid(CommonUtils.uuid());
		categoryService.add(pare);//没有parent
		return findAll(req, resp);
	}
	/**
	 * 添加第二分类：第一步
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	
	public String addChildPre(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		List<Category> parents = categoryService.findParents();
		String pid = req.getParameter("pid");//当前点击的父分类cid    //1级的
		req.setAttribute("parents", parents);
		req.setAttribute("pid", pid);
		
		return "f:/adminjsps/admin/category/add2.jsp";
		
		
	}
	/***
	 * 添加2级 地热不
	 * */
	public String addChild(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, SQLException {
						/*
		 * 1. 封装表单数据到Category中
		 * 2. 需要手动的把表单中的pid映射到child对象中  也就是cid、
		 * 2. 调用service的add()方法完成添加
		 * 3. 调用findAll()，返回list.jsp显示所有分类
		 */
		Category chiln = CommonUtils.toBean(req.getParameterMap(), Category.class);
		chiln.setCid(CommonUtils.uuid());
		//以上是 2级分类的 描述 uid 名字 还差 pid 这这个pid等于其一级分类的uid 获取的没有parents 但是这是添加2级菜单所以得创建对象 造个parent 值就是1级cid
	
		// 手动映射pid
		String cid = req.getParameter("pid");//就是1级的cid
	      //  System.out.println(cid);
	   /***/	
		Category www = new Category();
		www.setCid(cid);
		/*对象**/
		//这个Parent有值了 是父级cid
		chiln.setParent(www);//这是村的对象 只能添加对象
		
		
		
		
		
		
		
		
		categoryService.add(chiln);
		
		
		
		
		
		
		
		return findAll(req, resp);
		
		
		
		
		
		
		
		

	}
	
	
	/*
	 * 
	 * 
	 * 修改方法 一级分类
	 * 
	 * 
	 * ****/
	public String editParentPre(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
		/*1 获取cid
		 * 封装 类 
		 * 传名
		 * */
		String cid	=req.getParameter("cid");
		Category parent = categoryService.load(cid);
		req.setAttribute("parent", parent);
		return "f:/adminjsps/admin/category/edit.jsp";
		//
	}
	
	
	
	
	/*
	 * 
	 * 
	 * 修改方法 一级分类
	 * 
	 * 
	 * ****/
	
	public String editParent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
		/*1
		 * 封装 获取的数据
		 * 传名
		 * */
		
		
		Category category = CommonUtils.toBean(req.getParameterMap(), Category.class);
		categoryService.edit(category);
		return findAll(req, resp);
		//
	}
	
	
	
	/**
	 * 修改二级分类：第一步
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @throws SQLException 
	 */
	public String editChildPre(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, SQLException {
		/*
		 * 1. 获取链接参数cid，通过cid加载Category，保存之
		 * 2. 查询出所有1级分类，保存之 2级
		 * 3. 转发到edit2.jsp
		 */
		
		String cid = req.getParameter("cid");//2级cid
		Category child = categoryService.load(cid);
		req.setAttribute("child", child);
		
		req.setAttribute("parents", categoryService.findParents());//一级
		
		return "f:/adminjsps/admin/category/edit2.jsp";
		
	}

	/**
	 * 修改二级分类：第二步
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @throws SQLException 
	 */
	public String editChild(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, SQLException {
		/*
		 * 1. 封装表单参数到Category child
		 * 2. 把表单中的pid封装到child, ...
		 * 3. 调用service.edit()完成修改
		 * 4. 返回到list.jsp
		 */
		Category child =CommonUtils.toBean(req.getParameterMap(), Category.class);
		
		String  cid1 = req.getParameter("pid");//这个就是一级的cid
//		System.out.println("zhes  "+cid1);
//		System.out.println(cid1);
		Category parent = new Category();
		parent.setCid(cid1);
		
		child.setParent(parent);
	categoryService.edit(child);//chile的parent不为空
		
		
		return findAll(req, resp);
		
	}
	
	
	
	
	
	
	
	
	/**
	 * 删除一级分类：
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @throws SQLException 
	 */
	
	public String deleteParent(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, SQLException {
		/*
		 * 1. 获取链接参数cid，它是一个1级分类的id
		 * 2. 通过cid，查看该父分类下子分类的个数
		 * 3. 如果大于零，说明还有子分类，不能删除。保存错误信息，转发到msg.jsp
		 * 4. 如果等于零，删除之，返回到list.jsp
		 */
		String cid = req.getParameter("cid"); //一级的cid=2级的pid
		int cnt = categoryService.findChildrenCountByParent(cid);
		if(cnt > 0) {
			req.setAttribute("msg", "该分类下还有子分类，不能删除！");
			return "f:/adminjsps/msg.jsp";
		} else {
			categoryService.delete(cid);
			return findAll(req, resp);
		}
	}
	
	
	
	
	
	
	
	
	
	/**
	 * 删除2级分类：
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @throws SQLException 
	 */
	
	
	public String deleteChild(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, SQLException {
		/*
		 * 1. 获取cid，即2级分类id
		 * 2. 获取该分类下的图书个数
		 * 3. 如果大于零，保存错误信息，转发到msg.jsp
		 * 4. 如果等于零，删除之，返回到list.jsp
		 * 2级的cid 是图书的cid
		 */
		String cid = req.getParameter("cid");
		int cnt = bookService.findBookCountByCategory(cid);
		if(cnt>0){
			req.setAttribute("msg", "该分类下还存在图书，不能删除！");
			return "f:/adminjsps/msg.jsp";
		}else{
			categoryService.delete(cid);
			return findAll(req, resp);
		}
		//
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
