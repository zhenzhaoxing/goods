package com.xing.goods.pager;

import java.util.List;

public class PageBean<T> {

	private int pc;//当前页吗
	private int tr;//总记录数
	private int ps;//每页的记录数 每页显示多少
	private String url;//请求的路经 还有参数例如：/BookServlet?method=findXXX&cid=1&bname=2
	//url用在分页上 都在一个分类上
	private List<T> beanList; //所有得数据
	
	//计算总页数
	public int getTp(){
		int tp=tr/ps;
		return tr%ps==0?tp:tp+1;
		
		
	}

	public int getPc() {
		return pc;
	}
	public void setPc(int pc) {
		this.pc = pc;
	}
	public int getTr() {
		return tr;
	}
	public void setTr(int tr) {
		this.tr = tr;
	}
	public int getPs() {
		return ps;
	}
	public void setPs(int ps) {
		this.ps = ps;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public List<T> getBeanList() {
		return beanList;
	}
	public void setBeanList(List<T> beanList) {
		this.beanList = beanList;
	}
	
	
	
	
}
