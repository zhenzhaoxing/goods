package com.xing.goods.cart.domain;

import java.math.BigDecimal;

import com.xing.goods.book.domain.Book;
import com.xing.goods.user.domain.User;

public class CartItem {

	
	@Override
	public String toString() {
		return "CartItem [cartItemId=" + cartItemId + ", quantity=" + quantity + ", book=" + book + ", user=" + user
				+ "]";
	}

	private String cartItemId;// 主键
	private int quantity;// 数量
	private Book book; //本表中对应的bid 写的是其外键 对应的图书
	private User user;//本表中对应的uid 写的是其外键 对应的用户
	 
	        // 添加小计方法
		public double getSubtotal() {
			/*
			 * 使用BigDecimal不会有误差
			 * 要求必须使用String类型构造器
			 */
			BigDecimal b1 = new BigDecimal(book.getCurrPrice() + "");
			BigDecimal b2 = new BigDecimal(quantity + "");
			BigDecimal b3 = b1.multiply(b2);
			return b3.doubleValue();
		
		}
	      
		
		
		
		
		
		
		
		
		public String getCartItemId() {
			return cartItemId;
		}

		public void setCartItemId(String cartItemId) {
			this.cartItemId = cartItemId;
		}

		public int getQuantity() {
			return quantity;
		}

		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}

		public Book getBook() {
			return book;
		}

		public void setBook(Book book) {
			this.book = book;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}
		
		public static void main(String[] args) {
			System.out.println(2.0-1.1);//0.8999999999999999
		}
	}
