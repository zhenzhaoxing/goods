<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>main</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/main.css'/>">
	   <script type="text/javascript" src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
		<script type="text/javascript" src="<c:url value='/jsps/js/user/main.js'/>"></script>
  </head>
  
  <body>
<table class="table" align="center">
	<tr class="trTop">
		<td colspan="2" class="tdTop">
			<iframe frameborder="0" src="<c:url value='/jsps/top.jsp'/>" name="top"></iframe>
		</td>
	</tr>
	<tr>
		<td class="tdLeft" rowspan="2">
			<iframe frameborder="0" src="<c:url value='/CategoryServlet?method=findAll'/>" name="left"></iframe>
		</td>
		<td class="tdSearch" style="border-bottom-width: 0px;">
			<iframe frameborder="0" src="<c:url value='/jsps/search.jsp'/>" name="search"></iframe>
		</td>
	</tr>
	<tr>
		<td style="border-top-width: 0px;">
			<iframe frameborder="0" src="<c:url value='/jsps/body.jsp'/>" name="body"></iframe>
		</td>
	</tr>
</table>

             
              <div id="sha">
			<p class="i">乐购闪购</p>
			<div>
				<a href="javascript:void(0)" class="first"><i class="iconfont icon-xiangzuo"></i></a>
				<a href="javascript:void(0)"><i class="iconfont icon-you"></i></a>
			</div>
		</div>	
             
              
         <div id="from_shan">
			
		
			
			
			
			
			<div id="boxda">
			<div id="shan">
		<div class="time">10:00场</div>
		
		<img class="img" src="<c:url value='/images/flashpurchase.png'/>"/>
		<div class="sub">距离开始还有</div>
		<div class="bentime">
			<div>00</div>
			<p>:</p>
			<div>00</div>
			<p>:</p>
			<div>00</div>
		</div>
	
	</div>	

	       <div class="you">
				<div id="mi" style="border-top: blue 1px solid;">
					
			<!--  <img src="img/AIweilai_X.jpg">-->
			 <a class="pic" href="<c:url value='/BookServlet?method=load&bid=972BDDC2797D46369DE791C46B978F6E'/>" target="body"> <img class="img" src="<c:url value='/book_img/AIweilai_X.jpg'/>"/></a>
			<h3 class="title">AI·未来  人工智能未来十年大趋势</h3>
			<p class="desc">全面解析/人工智能</p>
			<p class="price">
				<span class="num">42.20元</span>
				
				<del class="bb">
					<span class="num">62.00元</span>
				</del>
		
			</p>
					<div class="bot">
						<p>从书中学到了很多东西。对于人工智能这一块的前瞻性讲的非常好，这是对我是非常有帮助的！</p>
					</div>
				</div>
				
				<div id="mi" style="border-top: red 1px solid;">
					
			<a class="pic" href="<c:url value='/BookServlet?method=load&bid=6F0C4FAF64E34E48AE208828BD148985'/>" target="body"> <img class="img" src="<c:url value='/book_img/mianshi_X.jpg'/>"/></a>
			<h3 class="title">程序员面试宝典（第5版）</h3>
			<p class="desc">揭开知名IT企业面试</p>
			<p class="price">
				<span class="num">37.90元</span>
				
				<del class="bb">
					<span class="num">50.0元</span>
				</del>
		
			</p>
					<div class="bot">
						<p>一本对程序员来说收益无限的书，覆盖面比较广，就是感觉作者应该重新写下序了，以适应如今的互联网局势</p>
					</div>
				</div>
			
				<div id="mi" style="border-top: #FFAC13 1px solid;">
				
			<a class="pic" href="<c:url value='/BookServlet?method=load&bid=9FB4454B93DE43A78883D34292BE0FC2'/>" target="body"> <img class="img" src="<c:url value='/book_img/shuxue_X.jpg'/>"/></a>
			<h3 class="title">程序员的数学</h3>
			<p class="desc">一本为程序员朋友们写的数学书</p>
			<p class="price">
				<span class="num">38.50元</span>
				
				<del class="bb">
					<span class="num">49.00元</span>
				</del>
		
			</p>
					<div class="bot">
						<p>正在看，内容挺有趣</p>
					</div>
				</div>
				<div id="mi" style="border-top: blueviolet 1px solid;">
					 
		<a class="pic" href="<c:url value='/BookServlet?method=load&bid=AEF6EC9CB3834808A622173F4B349175'/>" target="body"> <img class="img" src="<c:url value='/book_img/xiuyang_X.jpg'/>"/></a>
			<h3 class="title">程序员的自我修养</h3>
			<p class="desc">网易云风力荐：莫到用时再读书！</p>
			<p class="price">
				<span class="num">48.70元</span>
				
				<del class="bb">
					<span class="num">65.00元</span>
				</del>
		
			</p>
					<div class="bot">
						<p>对想提高自己“内功心法”而自身对这块技术点又不是很明了的程序员（比如我），挺有帮助的！</p>
					</div>
				</div>
			</div>
	
	</div>     
              
        </div>  
           
           
           
     <div id="lk_link">
			
		<div class="title text-center">
		 <h1 ><strong>友情链接</strong></h1>
		<img src="<c:url value='/images/star.png'/>"  class="img-responsive">
		

		</div>
			
			<ul class="logos">
			
        <li><a href="https://www.ibm.com/cn-zh/"><img src="<c:url value='/images/ibm-logo.png'/>" width="100" alt=""></a></li>
        <li><a href="http://www.facebook.com/"><img class="img" src="<c:url value='/images/facebook-logo.png'/>" width="50" alt=""/></a></li>
        <li><a href="https://www.google.com.hk/"><img src="<c:url value='/images/google-logo.png'/>"  width="50" alt=""></a></li>
        <li><a href="https://zh.airbnb.com/"><img src="<c:url value='/images/airbnb-logo.png'/>"  width="100" alt=""></a></li>
        <li><a href="http://www.chuangxin.com/"><img src="<c:url value='/images/chuangxin.png'/>" width="50"  alt=""></a></li>
        <li><a href="https://www.paypal.com/"><img src="<c:url value='/images/paypal-logo.png'/>"  width="100" alt=""></a></li>
        <li><a href="https://en.wikipedia.org/wiki/Walmart"><img src="<c:url value='/images/walmart-logo.png'/>"  width="100" alt=""></a></li>
    </ul>

			
			
			
		</div>        
           
         <!-- 底部 -->  
        	<div id="hd-foot">
			<p class="red">Copyright © 2017-2018  乐购star 网上书城  联系我</p>
			
			<li> <span>备案号:</span><a href="http://www.miibeian.gov.cn/" target="_blank"你>鲁ICP备18012436号</a></li>
			<p class="one1">地址邮箱: admin@star.com  &nbsp;&nbsp;|&nbsp;&nbsp; 客服热线 8888-555-6666（服务时间：9:00-21:00）</p>
			
			
			
			<h4 class="c1">特此声明 ! ! !，本网站完全是学习所用，购买商品并不能发货，如不能按要求来，一切后果自负。
			网站刚发布难免会有不足之处，如若发现可
			</h4>
			<h4 class="cc">以联系站长修改。</h4>
			
				
			
			
			
			
			 <div class="topo">
                    <a href="" class="text-muted">
                        <i class="icon-phone"></i>
                        <span>关注微信号</span>
                        <span class="caret"></span>
                       
                        <img src="<c:url value='/images/zhenzhaoxing.jpg'/>"  alt="响尾蛇" width="130">
                    </a>
                </div>
			
			
			
			
			
			
			
			
		</div>   
           
           
           
           
           
           
           
           
           
           
           
           
           
                                                                                  
  </body>
</html>
