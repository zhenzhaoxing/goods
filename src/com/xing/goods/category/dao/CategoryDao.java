package com.xing.goods.category.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;

import com.xing.goods.category.domain.Category;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;

/*
 * **
 * 用户模块持久层 与数据库打交道
 * @author qdmmy6
 *  把   这个 parent 里面的 cid 设置 为 pid
 */
public class CategoryDao {
	    //依赖
		private QueryRunner qr = new TxQueryRunner();
		/*
		 * 把一个Map中的数据映射到Category中
		 */
		private Category toCategory(Map<String,Object> map) {
			/* 转为2级分类准备
			 * map {cid:xx, cname:xx, pid:xx, desc:xx, orderBy:xx}
			 * Category{cid:xx, cname:xx, parent:(cid=pid), desc:xx}
			 */
			Category category = CommonUtils.toBean(map, Category.class);
			//重新赋一次值
			String pid = (String)map.get("pid");
			if(pid != null) {//如果父分类ID不为空，
				/*
				 * 使用一个父分类对象来拦截pid
				 * 再把父分类设置给category
				 */
				Category parent = new Category();
				parent.setCid(pid);
				//父类cid 等于pid
				category.setParent(parent);
			}
			return category;
		}
		/*
		 * 可以把多个Map(List<Map>)映射成多个Category(List<Category>) 然后保存集合中
		 */
		private List<Category> toCategoryList(List<Map<String,Object>> mapList) {
			List<Category> categoryList = new ArrayList<Category>();//创建一个空集合
			
			for(Map<String,Object> map : mapList) {//循环遍历每个Map
				Category c = toCategory(map);//把一个Map转换成一个Category
				categoryList.add(c);//添加到集合中
			}
			return categoryList;//返回集合
		}
		
		
		
	
	@Test
	public List<Category> findAll() throws SQLException {
		/*
		 * 1.查询出所有1级分类
		 * */
		String sql ="select *from t_category where pid is null";
		//随便拿出一个map 里面有  5 map {cid:xx, cname:xx, pid:xx, desc:xx, orderBy:xx}
		List<Map<String,Object>> mapList = qr.query(sql, new MapListHandler());

		List<Category> parents = toCategoryList(mapList);//把集合的内容都返回来了
		/*
		 * 2. 循环遍历所有的一级分类，为每个一级分类加载它的二级分类 
		 */
		
		for(Category parent : parents) {   //parent里面的 Cid 是 pid 对应的号
			// 查询出当前父分类的所有子分类
			List<Category> children = findByParent(parent.getCid());//cid
			// 设置给父分类
			parent.setChildren(children);
             
		}

		return parents;
		
	}
	
	
	/**
	 * 通过父分类查询子分类
	 * @param pid
	 * @return
	 * @throws SQLException 
	 */
	public List<Category> findByParent(String pid) throws SQLException {
		String sql = "select * from t_category where pid=?";
		List<Map<String,Object>> mapList = qr.query(sql, new MapListHandler(), pid);
		//返回的是map集合 然后好几个map 再把它们封装到 list集合中
		return toCategoryList(mapList);  //调用方法 
	}
	
	
	
	
	
	/***********************************************以下是后台********************************************************************/
	
	
	/*
	 *加载分类 1 2级都可以
	 * 
	 * */
	
	public Category load(String cid) throws SQLException {
		String sql = "select * from t_category where cid=?";
		  
		return toCategory(qr.query(sql, new MapHandler(), cid));  //调用方法 
	}
	
	
	/*
	 *修改分类 1 2级都可以
	 * 
	 * */
	
	public void edit(Category category) throws SQLException{
		String sql = "update t_category set cname=?, pid=?, `desc`=? where cid=?";
	
	String pid= null;
	/*一下为2级准备*/
	if(category.getParent() != null) {
		pid = category.getParent().getCid();
	}
	/*以上为2级准备*/
	
	Object[] params = {category.getCname(), pid, category.getDesc(), category.getCid()};
	qr.update(sql, params);
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 
	 * 
	 * 后台添加分类 1 2 级都有
	 * 
	 * */
	public void add(Category pare) throws SQLException {
		String sql = "insert into t_category(cid,cname,pid,`desc`) values(?,?,?,?)";
		/*
		 * 因为一级分类，没有parent，而二级分类有！
		 * 我们这个方法，要兼容两次分类，所以需要判断
		 */
		String pid =null;//一级分类 pid 为空
		   //2级分类优质
		if(pare.getParent() != null) {
			pid = pare.getParent().getCid();//1级分类的cid 添加到2级分类中去
		}
		Object[] params = {pare.getCid(), pare.getCname(), pid, pare.getDesc()};		
		qr.update(sql, params);
	}
	/**
	 * 获取所有父分类，但不带子分类的！
	 * @return
	 * @throws SQLException
	 */
	public List<Category> findParents() throws SQLException {
		/*
		 * 1. 查询出所有一级分类
		 */
		String sql = "select * from t_category where pid is null order by orderBy";
		List<Map<String,Object>> mapList = qr.query(sql, new MapListHandler());
		
		return toCategoryList(mapList);
	}
	
	
	
	/**
	 * 查询指定父分类下子分类的个数
	 * @param pid
	 * @return //一级的cid=2级的pid
	 * @throws SQLException 
	 */
	
	public int findChildrenCountByParent(String pid) throws SQLException {
		String sql = "select count(*) from t_category where pid=?";
		Number cnt = (Number)qr.query(sql, new ScalarHandler(), pid);
		return cnt == null ? 0 : cnt.intValue();
	}
	
	
	/**
	 * 删除分类
	 * @param cid
	 * @throws SQLException 
	 */
	public void delete(String cid) throws SQLException {
		String sql = "delete from t_category where cid=?";
		qr.update(sql, cid);
		
	}
}
