package com.xing.goods.pager;

/**
 * @author xiang
 *
 */
public class Expression {
//这个类是来完成sql语句的 运算   向 cid=1  bname like %java%
	private String name;//名称
	private String oper;//运算符
	private String value;//值
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOper() {
		return oper;
	}
	public void setOper(String oper) {
		this.oper = oper;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "Expression [name=" + name + ", oper=" + oper + ", value=" + value + "]";
	}
	public Expression(String name, String oper, String value) {
		super();
		this.name = name;
		this.oper = oper;
		this.value = value;
	}
	
}
