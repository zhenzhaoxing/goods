package com.xing.goods.cart.service;

import java.sql.SQLException;
import java.util.List;

import com.xing.goods.cart.dao.CartItemDao;
import com.xing.goods.cart.domain.CartItem;

import cn.itcast.commons.CommonUtils;

/*
 *   service  军
 * */
public class CartItemService {

	private CartItemDao cartItemDao = new CartItemDao();
	
	
	
	
	
	/**
	 * 批量删除功能
	 * @param cartItem
	 * @throws SQLException 
	 */
	public void batchDelete(String cartItemIds) throws SQLException{
		cartItemDao.batchDelete(cartItemIds);
	}
	
	
	
	
	
	/**
	 * 添加条目
	 * @param cartItem
	 * @throws SQLException 
	 */
	
	public void add(CartItem cartItem) throws SQLException {
		/*
		 * 1. 使用uid和bid去数据库中查询这个条目是否存在
		 */
		CartItem _cartItem  = cartItemDao.findByUidAndBid(cartItem.getUser().getUid(), cartItem.getBook().getBid());
		if(_cartItem==null){//如果原来没有这个条目，那么就添加条目
			cartItem.setCartItemId(CommonUtils.uuid());
			//除l uuid 以外cartItem 都有
			cartItemDao.addCartItem(cartItem);
			
		}else {//如果原来有这个条目，修改数量
			// 使用原有数量和新条目数量之各，来做为新的数量
			int quantity = cartItem.getQuantity() + _cartItem.getQuantity();
			// 修改这个老条目的数量
			cartItemDao.updateQuantity(_cartItem.getCartItemId(), quantity);
		}
		
	}
	
	
	
	
	
	
	/**
	 * 我的购物车功能
	 * @param uid
	 * @return
	 */
	public List<CartItem> myCart(String uid) {
		try {
			return cartItemDao.findByUser(uid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 批量修改功能
	 * @param uid
	 * @return
	 */
	public CartItem updateQuantity(String cartItemId, int quantity) {
		try {
			cartItemDao.updateQuantity(cartItemId, quantity);
			return cartItemDao.findByCartItemId(cartItemId);
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 提交订单显示页面功能
	 * @param uid
	 * @return
	 * @throws SQLException 
	 */
	public List<CartItem>  loadCartItems(String cartItemIds) throws SQLException{
		return cartItemDao.loadCartItems(cartItemIds);
		
		
		
	}
	
}
