package com.xing.goods.category.domain;

import java.util.List;

/*
 *分类模块的实体类 对应·数据库 
 * */
public class Category {
/*
 * pid 是这张表的 对应的外键还是这张表   外键pid需找到外键对应的表  然后写成 这张表对应的类
 *     自身 双向关联 父类关联 子类 在这张表中 有 子类 没 父类 有父类没子类 
 * */
	
		private String cid;// 主键
		private String cname;// 分类名称
		private Category parent;// 父分类
		private String desc;// 分类描述
		
		private List<Category> children;// 子分类

		public String getCid() {
			return cid;
		}

		public void setCid(String cid) {
			this.cid = cid;
		}

		public String getCname() {
			return cname;
		}

		public void setCname(String cname) {
			this.cname = cname;
		}

		public Category getParent() {
			return parent;
		}

		public void setParent(Category parent) {
			this.parent = parent;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

		public List<Category> getChildren() {
			return children;
		}

		public void setChildren(List<Category> children) {
			this.children = children;
		}
	}
