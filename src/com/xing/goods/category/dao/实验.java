package com.xing.goods.category.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.junit.Test;

import com.xing.goods.book.domain.Book;
import com.xing.goods.category.domain.Category;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;

public class 实验 {
	private QueryRunner qr = new TxQueryRunner();
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
	public void findAll() throws SQLException {
		/*
		 * 1.查询出所有1级分类
		 * */
		String sql ="select *from t_category where pid is null";
		//随便拿出一个map 里面有  5 map {cid:xx, cname:xx, pid:xx, desc:xx, orderBy:xx}
		  List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler());
			
			System.out.println();

		//List<Category> parents = toCategoryList(mapList);//把集合的内容都返回来了
		//System.out.println(parents);
		System.out.println("这是maplist"+mapList);
		//List<Category> parents = toCategoryList(mapList);//把集合的内容都返回来了
//		
//		for(Category parent : parents) {   //parent里面的 Cid 是 pid 对应的号
//			// 查询出当前父分类的所有子分类
//			//List<Category> children = findByParent(parent.getCid());//cid
//			String cid = parent.getCid();
//			System.out.println(cid);
//			//// 设置给父分类
//			//parent.setChildren(children);
//			List<Map<String,String>> m1 =  new ArrayList<Map<String,String>>();{
//		
//		             
//		
//			System.out.println(m1);
//		}
		
		
		
		List<String> m = new ArrayList<String>();
		m.add("s");
		m.add("q");
		m.add("f");
		System.out.println(m);
		Map<String,Object> s = new HashMap<String,Object>();
		s.put("s", 12);
		s.put("a", 25);
		s.put("z", 99);
		
		System.out.println(s);
		List<Map<String, Object>> map = new ArrayList<Map<String,Object>>();
	    map.add(s);
	//就是把map集合用list封装起来
		System.out.println(map.get(0));
		
}
	
}