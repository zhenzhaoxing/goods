package com.xing.goods.book.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.junit.Test;

import com.xing.goods.book.domain.Book;
import com.xing.goods.category.domain.Category;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;

public class test {
           
	List<Object> params = new ArrayList<Object>();//sql里面有问号 他是对应的值
	private QueryRunner   qr= new TxQueryRunner();

                @Test
	public void test() {
		    params.add(10);
		    params.add(12);
		    System.out.println(params);
		    System.out.println(params.toArray());
	}
	
	

	public void test2() {
		 //goods/BookServlet?method=findByCategory&cid=5F79D0D246AD4216AC04E9C5FAB3199E&pc=2
				
               String url="goods/BookServlet?method=findByCategory&cid=5F79D0D246AD4216AC04E9C5FAB3199E&pc=2";				
				int index = url.lastIndexOf("&pc=");
				if (index != -1) {// 说明有值
					url = url.substring(0, index);

				}
	System.out.println(index);
	     System.out.println(url);
	
	}
	

	public void test3() throws SQLException {
		String bid="000A18FDB38F470DBE9CD0972BADB23F";
		String sql = "SELECT * FROM t_book b, t_category c WHERE b.cid=c.cid AND b.bid=?";
		// 一行记录中，包含了很多的book的属性，还有一个cid属性
		Map<String,Object> map = qr.query(sql, new MapHandler(), bid);
		System.out.println(map);
		// 把Map中除了cid以外的其他属性映射到Book对象中 book中的cid没有 但是得带上 所以请求后勤部 数据库外键 不上
		Book book = CommonUtils.toBean(map, Book.class);
		System.out.println(book);
		
		// 把Map中cid属性映射到Category中，即这个Category只有cid 只要cid
		Category category = CommonUtils.toBean(map, Category.class);
		// 两者建立关系
		book.setCategory(category);
		
		//后台 把pid获取出来，创建一个Category parnet，把pid赋给它，然后再把parent赋给category
				if(map.get("pid") != null) {
					Category parent = new Category();
					parent.setCid((String)map.get("pid"));
					category.setParent(parent);
				}
	}
	
}
