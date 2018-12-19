package com.xing.goods.book.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;

import com.xing.goods.book.domain.Book;
import com.xing.goods.category.domain.Category;
import com.xing.goods.pager.Expression;
import com.xing.goods.pager.PageBean;
import com.xing.goods.pager.PageConstants;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;
/*
 * **
 * 用户模块持久层 与数据库打交道
 * @author qdmmy6
 *  战斗
 */






public class BookDao {

	
	private QueryRunner   qr= new TxQueryRunner();

	
	/*
	 * 删除图书 
	 * */
	
	public void delete(String bid) throws SQLException{
		String sql="delete from t_book where bid=?";
		qr.update(sql,bid);
	}
	
	
	
	
	
	
	
	/*
	 * 修改图书  大图小图就不编辑了
	 * */
	
	public void edit(Book book) throws SQLException {
		String sql = "update t_book set bname=?,author=?,price=?,currPrice=?," +
				"discount=?,press=?,publishtime=?,edition=?,pageNum=?,wordNum=?," +
				"printtime=?,booksize=?,paper=?,cid=? where bid=?";
		Object[] params = {book.getBname(),book.getAuthor(),
				book.getPrice(),book.getCurrPrice(),book.getDiscount(),
				book.getPress(),book.getPublishtime(),book.getEdition(),
				book.getPageNum(),book.getWordNum(),book.getPrinttime(),
				book.getBooksize(),book.getPaper(), 
				book.getCategory().getCid(),book.getBid()};
		qr.update(sql, params);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	 * 
	 *按bid 查询 然后 返回个book对象
	 * 
	 * */
	
	
	public Book findByBid(String bid) throws SQLException {
		String sql = "SELECT * FROM t_book b, t_category c WHERE b.cid=c.cid AND b.bid=?";
		// 一行记录中，包含了很多的book的属性，还有一个cid属性
		Map<String,Object> map = qr.query(sql, new MapHandler(), bid);
		// 把Map中除了cid以外的其他属性映射到Book对象中 book中的cid没有 但是得带上 所以请求后勤部 数据库外键 不上
		Book book = CommonUtils.toBean(map, Book.class);
		// 把Map中cid属性映射到Category中，即这个Category只有cid 只要cid
		Category category = CommonUtils.toBean(map, Category.class);
		// 两者建立关系
		book.setCategory(category);
		
		//后台 把pid获取出来，创建一个Category parnet，把pid赋给它，然后再把parent赋给category
				if(map.get("pid") != null) {
					Category parent = new Category();
					parent.setCid((String)map.get("pid"));
					category.setParent(parent);
				}
		
		
		return book;
	}
	
	
	
	
	
	
	/**
	 * 按分类查询
	 * @param cid
	 * @param pc
	 * @return
	 * @throws SQLException 
	 */
	public PageBean<Book> findByCategory(String cid, int pc) throws SQLException {
		List<Expression> exprList = new ArrayList<Expression>();
		exprList.add(new Expression("cid", "=", cid));
		return findByCriteria(exprList, pc);
	}
	
	/**
	 * 按书名查询
	 * @param cid
	 * @param pc
	 * @return
	 * @throws SQLException 
	 */
	public PageBean<Book> findByBname(String bname, int pc) throws SQLException {
		
		List<Expression> exprList = new ArrayList<Expression>();
		exprList.add(new Expression("bname", "like", "%" + bname + "%"));
		return findByCriteria(exprList, pc);
	}
	/**
	 * 按作者查询
	 * @param cid
	 * @param pc
	 * @return
	 * @throws SQLException 
	 */
	public PageBean<Book> findByAuthor(String author, int pc) throws SQLException {
		List<Expression> exprList = new ArrayList<Expression>();
		exprList.add(new Expression("bname", "like", "%" + author + "%"));
		return findByCriteria(exprList, pc);
	}

	
	

	
	
	/**
	 * 按出版社查
	 * @param press
	 * @param pc
	 * @return
	 * @throws SQLException
	 */
	public PageBean<Book> findByPress(String press,int pc) throws SQLException {
		List<Expression> exprList = new ArrayList<Expression>();
		exprList.add(new Expression("press", "like", "%" + press + "%"));
		return findByCriteria(exprList, pc);
		
	}
	
	/**
	 * 多条件组合查询
	 * @param combination
	 * @param pc
	 * @return
	 * @throws SQLException
	 */
	public PageBean<Book> findByCombination(Book criteria, int pc) throws SQLException {
		List<Expression> exprList = new ArrayList<Expression>();
		exprList.add(new Expression("bname", "like", "%" + criteria.getBname() + "%"));
		exprList.add(new Expression("author", "like", "%" + criteria.getAuthor() + "%"));
		exprList.add(new Expression("press", "like", "%" + criteria.getPress() + "%"));
		return findByCriteria(exprList, pc);
	}
	
	
	
	
	
	
	/********************以上为查询分类别 全都是要调用下面的通用方法
	 * @throws SQLException ************************/
	
	
	
	
	
	
	
	
	
	
	
	
	/*写一个通用方法 不管按什么条件查询 都得调用这个方法  就是传的参数不一样吧了
	 * criteria条件 源于hib--框架 query by criteria
	 *返回service 然后给总部
	 * */
	 public PageBean<Book> findByCriteria(List<Expression> exprlist,int pc) throws SQLException {
	//这个方法获取什么纳
	/*
	 * 1 ps 每一页的记录数 可以自定义 为了优雅一点 写个类 单独调用
	 * 2tr 总记录数
	 * 3 Beanlist中的全部数据
	 * 返回数据 pagelist
	 * */
	int ps= PageConstants.BOOK_PAGE_SIZE;
	
	/* //获取tr 需要查找数据库了
	 * 2. 通过exprList来生成where子句
	 */
	StringBuilder wheresql = new StringBuilder(" where 1=1");
	List<Object> params = new ArrayList<Object>();//sql里面有问号 他是对应的值
	//遍历
	for(Expression expr : exprlist){
		/*
		 * 添加一个条件上，
		 * 1) 以and开头
		 * 2) 条件的名称
		 * 3) 条件的运算符，可以是=、!=、>、< ... is null，is null没有值
		 * 4) 如果条件不是is null，再追加问号，然后再向params中添加一与问号对应的值
		 */
		
		wheresql.append(" and ").append(expr.getName())
		.append(" ").append(expr.getOper()).append(" ");
	//where id =1 ang bid = ?  当运算符不为空 后加问好
		if(!expr.getOper().equals("is null")){
		
			wheresql.append("?");
			params.add(expr.getValue());//把得到的值添加到 params里面

	}	
	}
	/*总记录数  tr 等于多少？
	 * */
	String sql = "select count(*) from t_book "+wheresql;
	Number number =(Number)qr.query(sql, new ScalarHandler(),params.toArray());
	
	int tr =number.intValue();//得到总记录数
	//System.out.println(tr);
    //System.out.println("----------");
	//System.out.println(params.toArray());
	
	
	
	
	
	
	/*
	 * 4. 得到beanList，即当前页记录 也就是 数据东西
	 */
	sql = "select * from t_book " + wheresql + " order by orderBy limit ?,?"; //排序 从几行开始 执行几次 如1，2 从下表为1开始 找2条
	params.add((pc-1) * ps);//当前页首行记录的下标
	params.add(ps);//一共查询几行，就是每页记录数
	List<Book> beanList = qr.query(sql, new BeanListHandler<Book>(Book.class), 
			params.toArray());

	/*
	 * 5. 创建PageBean，设置参数
	 */
	PageBean<Book> pb = new PageBean<Book>();
	//page里没url 不用管 交给总部servlet去管
	pb.setBeanList(beanList);
                                //List<Book> list = pb.getBeanList();
                                //System.out.println(list.get(1).getBname());
	pb.setPc(pc);//当前第几页
	pb.setPs(ps);//每页的显示多少东西
	pb.setTr(tr);//总记录数
	
	
	return pb;
	

	
	
}

//后台查询
	public int findBookCountByCategory(String cid) throws SQLException {
		String sql = "select from t_book where cid=?";
	Number number=	(Number) qr.query(sql, new ScalarHandler(),cid);
		return number==null?0:number.intValue();
	}


	
	
/********************通用方法
 * @throws SQLException ************************/
/*
public static void main(String[] args) throws SQLException {
        BookDao bookDao = new BookDao();
List<Expression> exprList = new ArrayList<Expression>();
        //exprList.add(new Expression("bid", "=", "1"));
exprList.add(new Expression("bname", "like", "%java%"));
//exprList.add(new Expression("edition", "is null", null));


	bookDao.findByCriteria(exprList, 1);
//System.out.println(bookDao.findByCriteria(exprList, 1));

	
}

*/
	
	
	
	

	/*
	 * 添加图书  book里没有cid 需要调cartgory
	 * */
	
	public void add(Book book) throws SQLException {
		
		
		String sql ="insert into t_book(bid,bname,author,price,currPrice," +
				"discount,press,publishtime,edition,pageNum,wordNum,printtime," +
				"booksize,paper,cid,image_w,image_b)" +
				" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] params ={book.getBid(),book.getBname(),book.getAuthor(),
				book.getPrice(),book.getCurrPrice(),book.getDiscount(),
				book.getPress(),book.getPublishtime(),book.getEdition(),
				book.getPageNum(),book.getWordNum(),book.getPrinttime(),
				book.getBooksize(),book.getPaper(), book.getCategory().getCid(),
				book.getImage_w(),book.getImage_b()};
		qr.update(sql, params);
		
		
	}
	
	
	
	
	
}

