package com.xing.goods.pager;

public class demo {

	public static void main(String[] args) {
		sorwe();
	}
	
	public static void  sorwe(){
		StringBuilder svl= new StringBuilder();

		
		String p0_Cmd="Buy";//业务类型
		String p1_MerId="props.get";//商号编码 易宝唯一标识
		String p2_Order = "req.getParamete";//订单编号
		
		svl.append("{").append("nihao");
		svl.append("chan");
		svl.append("}").append(" "+1);
		
		
		
		svl.append("?").append("p0_Cmd=").append(p0_Cmd);
		svl.append("&").append("p1_MerId=").append(p1_MerId);
		svl.append("&").append("p2_Order=").append(p2_Order);
		
		
		
		
		System.out.println(svl.toString());
	}
}
