package com.xing.goods.order.service;

import java.sql.SQLException;

import com.xing.goods.order.dao.OrderDao;
import com.xing.goods.order.domain.Order;
import com.xing.goods.pager.PageBean;

import cn.itcast.jdbc.JdbcUtils;
/*
 * 119师
 * */
public class OrderService {
	
	private OrderDao orderDao = new OrderDao();

	
	
	/*
	 * 修改订单状态
	 * 
	 * */
	
	public void updateStatus(String oid,int status) throws SQLException{
		
		orderDao.updateStaus(oid, status);
	}
	
	
	/**
	 * 查询订单状态
	 * @param oid
	 * @return
	 */
	public int findStatus(String oid) {
		try {
			return orderDao.findStatus(oid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	
	/*
	 * 生成订单
	 * 
	 * */
	public void createOrder(Order order) throws SQLException{
		//JdbcUtils.beginTransaction();
		orderDao.add(order);
		//JdbcUtils.commitTransaction();
		
	}
	
	/*
	 *加载订单 显示订单详细信息
	 * 
	 * */
	public Order load(String oid) throws SQLException {
		JdbcUtils.beginTransaction();
		try {
		Order	order=orderDao.load(oid);
			JdbcUtils.commitTransaction();
			return order;
		} catch (SQLException e) {
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {}
			throw new RuntimeException(e);
		}
		
		
		
	}
	
	
	
	
	
	
	
	
	/**
	 * 我的订单
	 * @param uid
	 * @param pc
	 * @return
	 */

	public PageBean<Order> myOrders(String uid, int pc) {
		try {
			JdbcUtils.beginTransaction();
			//类型一致
			PageBean<Order> pb = orderDao.findByUser(uid, pc);
			JdbcUtils.commitTransaction();
			return pb;
		} catch (SQLException e) {
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {}
			throw new RuntimeException(e);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*******************************************后台**********************************/
	/**
	 * 查询所有订单
	 * @param uid
	 * @param pc
	 * @return
	 */

	public PageBean<Order> findAll(int pc) {
		try {

			JdbcUtils.beginTransaction();
			PageBean<Order> pb = orderDao.findAll(pc);
			JdbcUtils.commitTransaction();
			return pb;
		} catch (SQLException e) {
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {}
			throw new RuntimeException(e);
		}
		}
	/**
	 * 按状态所有订单
	 * @param uid
	 * @param pc
	 * @return
	 */

	public PageBean<Order> findByStatus(int status, int pc) {
		
		PageBean<Order> pb;
		try {
			JdbcUtils.beginTransaction();
			pb = orderDao.findByStatus(status, pc);
			JdbcUtils.commitTransaction();
			return pb;
		} catch (SQLException e) {
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {}
			throw new RuntimeException(e);
		}
		}
		
		
	
}
