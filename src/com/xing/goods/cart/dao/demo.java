package com.xing.goods.cart.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.junit.Test;

import com.xing.goods.book.domain.Book;
import com.xing.goods.cart.domain.CartItem;
import com.xing.goods.user.domain.User;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;

public class demo {
	private QueryRunner qr = new TxQueryRunner();
    
	
	  
	//转为多个list 订单
	           public List<CartItem> findList(List<Map<String, Object>> query){
						List<CartItem> lists = new ArrayList<CartItem>();
                       	      
	        	     for (Map<String,Object> cartItem : query) {
					           
					            lists.add(toCare(cartItem));
					}
	        	     return lists;
	           }
	//单个 订单
	           public CartItem    toCare(Map<String,Object> map) {
	        	   if(map==null||map.size()==0) return null;
                      CartItem s = new CartItem();
	        	   Book book = CommonUtils.toBean(map, Book.class);
                 User user = CommonUtils.toBean(map, User.class);
                   s.setBook(book);
                   s.setUser(user);
                   return s;
	           }
	           
	           
	           
	
	
	@Test
	public void test() throws SQLException {
		String sql = "select * from t_cartitem c, t_book b where c.bid=b.bid and uid=? order by c.orderBy";
		List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler(), "D1610ADD219540E09FD4561EE5A4D13C");
		System.out.println(mapList);
		List<CartItem> cartItemList = new ArrayList<CartItem>();// 空集合
		for (Map<String, Object> map : mapList) {
			CartItem cartItem = CommonUtils.toBean(map, CartItem.class);
			Book book = CommonUtils.toBean(map, Book.class);
			User user = CommonUtils.toBean(map, User.class);
			System.out.println(book);
			System.out.println(user);

			cartItem.setBook(book);
			cartItem.setUser(user);

			System.out.println(cartItem);
			cartItemList.add(cartItem);
		}
		System.out.println("++++++++++++++++---------------------++++++++++++++++");
		System.out.println(cartItemList);
		System.out.println("-------------------------++++++++++++++++++++++");

	}

	@Test
	public void test2() {
		String ing = "4CE8A95168754849AA6D0D87B0B2BE1E,19DD2A2C01814318B32F13EB7D593532";

		Object[] split = ing.split(",");
		System.out.println(split.length);
		System.out.println(split.toString().contains("1"));
	}

	@Test
	public void test3() {
		int Subtotal = 10;
		String qu = "三十";
		StringBuilder sb = new StringBuilder("{");
		sb.append("\"quantity\"").append(":").append(qu);
		sb.append(",");
		sb.append("\"subtotal\"").append(":").append(Subtotal);
		sb.append("}");

		System.out.println(sb);
	}

}
