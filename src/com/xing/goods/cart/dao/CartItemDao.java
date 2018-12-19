package com.xing.goods.cart.dao;
/*
 * **
 * 用户模块持久层 与数据库打交道
 * @author qdmmy6
 *  战斗团
 */

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.junit.Test;

import com.xing.goods.book.domain.Book;
import com.xing.goods.cart.domain.CartItem;
import com.xing.goods.user.domain.User;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;

public class CartItemDao {

	
	private QueryRunner   qr = new TxQueryRunner();
	

	
	/**
	 * 查询某个用户的某本图书的购物车条目是否存在   uid cid 
	 * @throws SQLException 
	 */
	
	public CartItem findByUidAndBid(String uid,String bid) throws SQLException{
		
		String sql ="select * from t_cartitem where uid=? and bid=?";
		Map<String,Object> map = qr.query(sql, new MapHandler(), uid, bid);
		CartItem cartItem = toCartItem(map);
		return cartItem;
	
	}
	
	
	
	/**
	 * 添加条目
	 * @param cartItem
	 * @throws SQLException 
	 */
	

	public void addCartItem(CartItem cartItem) throws SQLException {
		String sql = "insert into t_cartitem(cartItemId, quantity, bid, uid)" +
				" values(?,?,?,?)";
		Object[] params = {cartItem.getCartItemId(), cartItem.getQuantity(),
				cartItem.getBook().getBid(), cartItem.getUser().getUid()};
		qr.update(sql, params);
	}
	
	
	
	
	
	
	
	
	
	/***********************************************/
	
            //生成where子句
	private String toWhereSql(int len){
	        	//字符串的拼接   //主要是 这个收到的 cartItemId
		StringBuilder sb = new StringBuilder("cartItemId in(");
		for(int i=0;i<len;i++){
			sb.append("?");
			if(i < len - 1) {//大与了后面就不接，
				sb.append(",");
		}
		}
		sb.append(")");
		
		return sb.toString();
		
	}
	/**
	 * 删除功能
	 * @param cartItemId
	 * @param quantity
	 * @throws SQLException 
	 */
	
	
	
	public void batchDelete(String cartItemIds) throws SQLException {
		/*
		 * 1,把cartItemIds转换成数组
		 * */
		Object[] cartItemIdArray = cartItemIds.split(",");
		//生成where子句
		String whereSql = toWhereSql(cartItemIdArray.length);
		//生成sql子句
		String sql ="delete from t_cartitem where "+ whereSql;
		
		
		
		
		//执行sql子句
		qr.update(sql,cartItemIdArray);
		
	}
	
	
	/*****************删除结束**************************/
	
	
	/*****************修改数量开始+ — 
	 * @throws SQLException **************************/
	
	
	
	/**
	 * 修改指定条目的数量
	 * @param cartItemId
	 * @param quantity
	 * @throws SQLException 
	 */
	public void updateQuantity(String cartItemId,int quantity) throws SQLException{
		String sql="update t_cartitem set quantity=? where cartItemId=?";
		qr.update(sql, quantity,cartItemId);
		
	}
	
	/**
	 * 
	 *anid查询
	 * */
	public CartItem findByCartItemId(String cartItemId) throws SQLException {
		String sql = "select * from t_cartItem c, t_book b where c.bid=b.bid and c.cartItemId=?";
		Map<String,Object> map = qr.query(sql, new MapHandler(), cartItemId);
		return toCartItem(map);
	}
	
	
	
	/*****************修改数量结束**************************/
	
	
	
	//通过用户 uid获取
	public List<CartItem> findByUser(String uid) throws SQLException{
		String sql = "select * from t_cartitem c, t_book b where c.bid=b.bid and uid=? order by c.orderBy";
		List<Map<String,Object>> mapList = qr.query(sql, new MapListHandler(), uid);
		return toCartItemList(mapList);
		
		
		
	}

	
	
	/*
	 *  得到 一个map  把一个Map映射成一个Cartitem  这个是  下面那个方法传来的
	 */

	private CartItem toCartItem(Map<String,Object> map) {
		if(map == null || map.size() == 0) return null;
		CartItem cartItem = CommonUtils.toBean(map, CartItem.class);
		Book book = CommonUtils.toBean(map, Book.class);
		User user = CommonUtils.toBean(map, User.class);
		cartItem.setBook(book);
		cartItem.setUser(user);
		return cartItem;
	}
        
	
	/*
	 * 把多个Map(List<Map>)映射成多个CartItem(List<CartItem>)  循环遍历还得调用上面的方法
	 */
	
	private List<CartItem> toCartItemList(List<Map<String, Object>> mapList) {
		List<CartItem> cartItemList = new ArrayList<CartItem>();//空集合
		//循环遍历
		for(Map<String,Object> map : mapList) {
			//调用上边的单个然后组装
			CartItem cartItem = toCartItem(map);		
			cartItemList.add(cartItem);
		}
		return cartItemList;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/***********************提交订单页面  加载订单条目
	 * @throws SQLException *******************************/
	
	public List<CartItem>  loadCartItems(String cartItemIds) throws SQLException{
		
		
		/*
		 * 1,把cartItemIds转换成数组
		 * */
		Object[] cartItemIdArray = cartItemIds.split(",");
		//生成where子句
		String whereSql = toWhereSql(cartItemIdArray.length);
		//生成sql子句
		String sql ="select * from t_cartitem c,t_book b where c.bid=b.bid and "+ whereSql;
	    /*check the manual that corresponds to your MySQL server version for the right syntax to use near 'c.bid=b.bid cartItemId in('58E6FC5C68CF45F2AC6E3C6B131835FC','1E521322BDDE4B16B4' at line 1 Query: select * from t_cartitem c,t_book bwhere c.bid=b.bid cartItemId in(?,?) Parameters: [58E6FC5C68CF45F2AC6E3C6B131835FC, 1E521322BDDE4B16B49A1CF8D356A061]
	             * 4 执行sql 返回 List<CartItem>
	               * */	
		List<Map<String, Object>> query = qr.query(sql, new MapListHandler(),cartItemIdArray);
		return toCartItemList(query);
		
	}
	
	/***********************提交订单页面*******************************/
}
