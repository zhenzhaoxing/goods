package com.xing.goods.category.web.serlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xing.goods.category.domain.Category;
import com.xing.goods.category.service.CategoryService;

import cn.itcast.servlet.BaseServlet;




/*   servlet 处理总层依赖 service；
 * 
 * */
public class CategoryServlet extends BaseServlet {

	
	CategoryService  categoryService = new CategoryService();
	
	
	public String findAll(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		//这个一级分类中 含有2级分类
		List<Category> findAll = categoryService.findAll();
		req.setAttribute("findAll", findAll);
		
		return "f:/jsps/left.jsp";
		
	}
	
	
}
