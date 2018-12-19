package com.xing.goods.book.service;

import java.sql.SQLException;

import com.xing.goods.book.dao.BookDao;
import com.xing.goods.book.domain.Book;
import com.xing.goods.pager.PageBean;

/*
 *    **
 * 用户模块业务层
 * @author qdmmy6
 *
 */
public class BookService {

	
	private   BookDao   bookdao = new BookDao();
	
	/*
	 * 
	 * 按bid 查找 这一次把一本图书的所有信息都查找出来了
	 * 
	 * */
	
	
	
	
	
	
	
	public  Book load(String bid){
		
		try {
			return bookdao.findByBid(bid);
		} catch (SQLException e) {
			
			throw new RuntimeException(e);
		}
	}
	
	
	
	
	//5个方法
	/*
	 * 按分类查
	 * 
	 * 
	 * */
	public PageBean<Book> findByCategory(String cid,int pc) throws SQLException{
		
		
		return bookdao.findByCategory(cid, pc);
		
	}
	/**
	 * 按书名查
	 * @param bname
	 * @param pc
	 * @return
	 */
	
	
	public PageBean<Book> findByBname(String bname,int pc){
		try {
			return bookdao.findByBname(bname, pc);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 按作者查
	 * @param author
	 * @param pc
	 * @return
	 * @throws SQLException 
	 */
	
	
	
	public PageBean<Book> findByauthor(String author,int pc) throws SQLException{
		return bookdao.findByAuthor(author, pc);
		
	}
	/**
	 * 按出版社查
	 * @param press
	 * @param pc
	 * @return
	 */
	public PageBean<Book> findByPress(String press, int pc) {
		try {
			return bookdao.findByPress(press, pc);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	
	/**
	 * 多条件组合查询
	 * @param criteria
	 * @param pc
	 * @return
	 */
	public PageBean<Book> findByCombination(Book criteria, int pc) {
		try {
			return bookdao.findByCombination(criteria, pc);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

            /**后台图书的查询
             * @throws SQLException 
             * */
	public int findBookCountByCategory(String cid) throws SQLException {
		// TODO Auto-generated method stub
		return bookdao.findBookCountByCategory(cid);
	}
	/*
	 * 添加图书
	 * */
	
	
	
	
	public void add(Book book) throws SQLException{
		bookdao.add(book);
	}
	

	/*
	 * 删除图书 
	 * */
	
	public void delete(String bid) throws SQLException{
		bookdao.delete(bid);
		
	}
	//编辑工作
	public void edit(Book book) throws SQLException{
		bookdao.edit(book);
		
	}
	
	
}
