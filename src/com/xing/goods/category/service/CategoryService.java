package com.xing.goods.category.service;

import java.sql.SQLException;
import java.util.List;

import com.xing.goods.category.dao.CategoryDao;
import com.xing.goods.category.domain.Category;

/*
 *    **
 * 用户模块业务层
 * @author qdmmy6
 *
 */



public class CategoryService {

private	CategoryDao categoryDao = new CategoryDao();
	
	/*
	 * 查询所有分类
	 * */
public List<Category> findAll() {
	try {
		return categoryDao.findAll();
	} catch (SQLException e) {
		throw new RuntimeException(e);
	}
}
/*
 *添加分类
 * */
public void add(Category pare) throws SQLException {
	
	
	categoryDao.add(pare);
	
	
}
	

/**
 * 获取所有父分类，不带子分类
 * @return
 */
public List<Category> findParents() {
	try {
		return categoryDao.findParents();
	} catch (SQLException e) {
		throw new RuntimeException(e);
	}
}
/*加载分类*/
public Category load(String cid) throws SQLException {
	return categoryDao.load(cid);
}
//修改分类 1级
public void edit(Category category) throws SQLException {
	
	categoryDao.edit(category);
	
	
	
}
/**
 * 删除分类
 * @param cid
 * @throws SQLException 
 */
public void delete(String cid) throws SQLException {
	categoryDao.delete(cid);
	
}
/*查询功能
 * */
public int findChildrenCountByParent(String pid) throws SQLException {
	//一级的cid=2级的pid
	return categoryDao.findChildrenCountByParent(pid);
}
/*查询子分类
 * */
public List<Category> findChildren(String pid) throws SQLException {
	
	return categoryDao.findByParent(pid);
}













	
}
